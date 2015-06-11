/*
 * Copyright 2012 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *     
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openehealth.tewepo.twp.dmp.dmc.server.email.dicommail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;
import org.openehealth.tewepo.twp.dmp.dmc.server.config.Configuration;



/**
 * Class to build a base Message Body
 * 
 * @author devmis
 *
 */
abstract class CBaseMessageBuilder {

	private Logger logger = Logger.getLogger(CBaseMessageBuilder.class);

	/**
	 * Class Constructor
	 * 
	 * @param sComments
	 * @param sxTag
	 * @param sPublicKey
	 */
	public CBaseMessageBuilder(String sComments, String sxTag, String sPublicKey) {
		mPublicKey = sPublicKey;
		mComments = sComments;
		smXTag = sxTag;

		mSecretKey = "";
		mPass = "";

		mID = "";
		mPart = 0;
		mTotal = 0;
		mMethodDN = DBConsts.MDN_NONE;
		mMethodSign = DBConsts.MDN_NONE;
		mReplayToDN = "";
		mKeyIDDN = "";
		mFilesToDel = new Vector();
	}

	/**
	 * Sets the DN Header
	 * 
	 * @param pMessagePart
	 * @param isMessage
	 * @throws MessagingException
	 */
	protected void SetDNHeaders(Part pMessagePart, boolean isMessage)
			throws MessagingException {
		if (mMethodDN != DBConsts.MDN_NONE) {
			if (mMethodDN == DBConsts.MDN_PGP || mMethodDN == DBConsts.MDN_MIX) {

				pMessagePart.setHeader(DBConsts.XTAG_ID, mID);
				pMessagePart.setHeader(DBConsts.XTAG_PART, new Integer(mPart)
						.toString());
				pMessagePart.setHeader(DBConsts.XTAG_TOTAL, new Integer(mTotal)
						.toString());
				// "Disposition-Notification-To"
				// pMessagePart.setHeader(DBConsts.DN_RFC, Configuration
				// .getMainConfig().getProperty("mailSenderAddress")); // relocated to SendMail.java 
				
				
				// optional:
				// if (!isMessage) {
				// pMessagePart.setHeader("ContentId", "<"
				// + FileTools.makeUniqName() + "@tewepo.de" + ">");
				// pMessagePart.setHeader(DBConsts.XTAG_DNKEYID, mKeyIDDN);
				// }

			}

		}
	}

	/**
	 * Make tamp body part 
	 * 
	 * @param mmpPart
	 * @return tmpPart
	 * @throws MessagingException
	 */
	protected MimeBodyPart MakeTempBodyPart(MimeMultipart mmpPart)
			throws MessagingException {
		MimeBodyPart tmpPart = new MimeBodyPart();
		tmpPart.setContent(mmpPart);
		// tmpPart.setHeader("MIME-Version", "1.0"); 
		tmpPart.addHeader("Content-Type", mmpPart.getContentType());
		SetDNHeaders(tmpPart, false);
		return tmpPart;
	}

	/**
	 * BuildMimePgpMultipart
	 * 
	 * @param mbpPart
	 * @param sFileNameGpg
	 * @throws MessagingException
	 * @throws IOException
	 * @throws wrapperException
	 */
	abstract protected MimeMultipart BuildMimePgpMultipart(
			MimeBodyPart mbpPart, String sFileNameGpg)
			throws MessagingException, IOException, wrapperException;

	/**
	 * BuildPlainCommentMessage
	 * 
	 * @return mbp
	 * @throws MessagingException
	 */
	protected MimeBodyPart BuildPlainCommentMessage() throws MessagingException {
		MimeBodyPart mbp = new MimeBodyPart();
		if ((mComments != null) && (mComments.length() != 0)) {
			mbp.setContent(mComments, "text/plain");
			mbp.setHeader(DBConsts.XTAG_STUDYID, smXTag);

			String saveTo = FileTools.makeUniqName();
			saveTo += ".";
			mbp.setFileName(saveTo);
			mbp.setDisposition(Part.ATTACHMENT);
		}
		return mbp;
	}

	/**
	 * BuildPlainDataMessage
	 * 
	 * @param attachedFileName
	 * @param xTag
	 * @return mbp
	 * @throws MessagingException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	protected MimeBodyPart BuildPlainDataMessage(String attachedFileName,
			String xTag) throws MessagingException, FileNotFoundException,
			IOException {

		File attachedFile = new File(attachedFileName);
		if (!attachedFile.exists()) {
			String errorStr = "File " + attachedFileName
					+ " does not exists...";
			throw new RuntimeException(errorStr);
		}

		FileDataSource fds = new FileDataSource(attachedFile);

		// create the second message part
		MimeBodyPart mbp = new MimeBodyPart();

		// attach the file to the message
		mbp.setDataHandler(new DataHandler(fds));
		String fileName = fds.getName();
		mbp.setFileName(fileName); // +"\""

		//
		// make classification of dicom files stronger
		// other types are classified automatically using file extensions
		//
		if (FileTools.isDICMfile(fds.getInputStream())) {
			mbp.setHeader("Content-Type", "application/dicom;\n\tname=\""
					+ fileName + "\"");
		} else {
			mbp
					.setHeader("Content-Type", mbp.getDataHandler()
							.getContentType());
			mbp.setHeader(DBConsts.XTAG_STUDYID, xTag);
		}

		return mbp;
	}

	
	/**
	 * Build Message
	 * 
	 * @param sAttachedFile
	 * @param session
	 * @param tmpFolder
	 * @param xTag
	 * @return msgPGP
	 */
	public MimeMessage BuildMessage(String sAttachedFile, Session session,
			String tmpFolder, String xTag) {
		boolean bOk = false;
		MimeMessage msgPGP = null;
		MimeBodyPart mbp = null;
		logger.info("CBaseMessageBuilder - BuildMessage - sAttachedFile: "
						+ sAttachedFile);

		logger.info("CBaseMessageBuilder - BuildMessage - TMPFolder: "
				+ tmpFolder);
		
		String sFileNameGpg = tmpFolder + "/" + FileTools.makeUniqName() + "."
				+ DBConsts.ASC_TMP_EXT;
		logger.info("CBaseMessageBuilder - BuildMessage - sFileNameGpg: "
				+ sFileNameGpg);
		// vfilesRecord v = new vfilesRecord(); // db record
		// dmtRecord r = new dmtRecord(); // db record
		try {
			//
			// Part 1: build main part
			if ((sAttachedFile != null) && (sAttachedFile.length() != 0)) {
				// v.m_name = new String(sAttachedFile);
				// v.m_number = 1;

				mbp = BuildPlainDataMessage(sAttachedFile, xTag);
			} else {
				mbp = BuildPlainCommentMessage();
			}

			//
			// Part 2: Build MIME/PGP Message
			MimeMultipart tmpRoot = new MimeMultipart();

			tmpRoot.addBodyPart(mbp);

			MimeBodyPart tmpPart = MakeTempBodyPart(tmpRoot);

			MimeMultipart mpRoot = BuildMimePgpMultipart(tmpPart, sFileNameGpg);

			Properties props = System.getProperties();

			msgPGP = new MimeMessage(session);

			msgPGP.setContent(mpRoot);

			SetDNHeaders(msgPGP, true);
			msgPGP.saveChanges();

			FileOutputStream fosPGP = new FileOutputStream(tmpFolder + "/"
					+ "EncryptedMail1.eml", false);
			msgPGP.writeTo(fosPGP);

			fosPGP.close();

			bOk = true;
		} catch (Exception ex) {
			logger.error("CBaseMessageBuilder - Message building error: " + ex);
			
		}
		mFilesToDel.add(sFileNameGpg);

		if (bOk) {
			// Disposition Notification
			// Two methods are currently supported: RFC and MIME/PGP
			if (this.mMethodDN != DBConsts.MDN_NONE) {
				// RFC Disposition Notification
				// here a record in DB tables has to be created
				// Message-Id is used as a transaction Id
				// One record is created in both tables per the file
				String message_id = "null";

				try {

					msgPGP.setSubject(Configuration.getMainConfig()
							.getProperty("defaultSubject"));
					message_id = msgPGP.getMessageID();

				} catch (MessagingException mex) {
					logger.error("CBaseMessageBuilder - Messaging Exception: " + mex);
					
				}
				
			} // end if NONE

			return msgPGP;
		} else {
			throw (new RuntimeException("Build message failed\n"));
		}
	}

	/**
	 * Delete Files 
	 * 
	 */
	public void ClearResources() {
		for (int fileCount = 0; fileCount < mFilesToDel.size(); fileCount++) {
			String sFileToDel = (String) mFilesToDel.get(fileCount);
			if ((sFileToDel != null) && (sFileToDel.length() != 0)) {
				(new File(sFileToDel)).delete();
			}
		}
	}

	/**
	 * Gets the secret key
	 * 
	 * @return mSecretKey
	 */
	public String getSecretKey() {
		return mSecretKey;
	}

	/**
	 * Sets the secret key
	 * 
	 * @param sKeyID
	 */
	public void setSecretKey(String sKeyID) {
		this.mSecretKey = sKeyID;
	}
	/**
	 * Gets the Pass
	 * 
	 * @return mPass
	 */
	public String getPass() {
		return mPass;
	}

	/**
	 * Sets the Pass
	 * 
	 * @param sPass
	 */
	public void setPass(String sPass) {
		this.mPass = sPass;
	}

	/**
	 * Gets the ID
	 * 
	 * @return mID
	 */
	public String getID() {
		return mID;
	}
	/**
	 * Sets the ID
	 * 
	 * @param sID
	 */
	public void setID(String sID) {
		this.mID = sID;
	}

	/**
	 * Gets the Part
	 * 
	 * @return mPart
	 */
	public int getPart() {
		return mPart;
	}

	/**
	 * Sets the part
	 * 
	 * @param iPart
	 */
	public void setPart(int iPart) {
		this.mPart = iPart;
	}

	/**
	 * Gets the total size
	 * 
	 * @return mTotal
	 */
	public int getTotal() {
		return mTotal;
	}

	/**
	 * Sets the total size
	 * 
	 * @param iTotal
	 */
	public void setTotal(int iTotal) {
		this.mTotal = iTotal;
	}

	/**
	 * Gets the Method DN
	 * 
	 * @return mMethodDN
	 */
	public int getMethodDN() {
		return mMethodDN;
	}

	/**
	 * Sets the Method DN
	 * 
	 * @param iMethodDN
	 */
	public void setMethodDN(int iMethodDN) {
		this.mMethodDN = iMethodDN;
	}

	/**
	 * Gets the Method sign
	 *  
	 * @return mMethodSign
	 */
	public int getMethodSign() {
		return mMethodSign;
	}

	/**
	 * Sets the Method sign
	 * 
	 * @param iMethodSign
	 */
	public void setMethodSign(int iMethodSign) {
		this.mMethodSign = iMethodSign;
	}

	/**
	 * Gets the Replay to DN
	 * 
	 * @return mReplayToDN
	 */
	public String getReplayToDN() {
		return mReplayToDN;
	}

	/**
	 * Sets the Replay to DN
	 * 
	 * @param sReplayToDN
	 */
	public void setReplayToDN(String sReplayToDN) {
		this.mReplayToDN = sReplayToDN;
	}

	/**
	 * Gets the Key IDDN
	 * 
	 * @return mKeyIDDN
	 */
	public String getKeyIDDN() {
		return mKeyIDDN;
	}

	/**
	 * Sets the Key IDDN
	 * 
	 * @param sKeyIDDN
	 */
	public void setKeyIDDN(String sKeyIDDN) {
		this.mKeyIDDN = sKeyIDDN;
	}


	protected Vector mFilesToDel;
	protected String smXTag;
	protected String mPublicKey;
	protected String mComments;

	protected String mSecretKey;
	protected String mPass;

	protected String mID;
	protected int mPart;
	protected int mTotal;
	protected int mMethodDN;
	protected int mMethodSign;
	protected String mReplayToDN;
	protected String mKeyIDDN;

}
