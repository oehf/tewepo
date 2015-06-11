/*
 * Copyright 2012 Benjamin Schneider
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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.openehealth.tewepo.twp.dmp.dmc.server.config.Configuration;
import org.openehealth.tewepo.twp.dmp.dmc.server.email.xml.DicomMailRecipient;


/**
 * This class is for sending an e-mail.
 * 
 * @author Benjamin Schneider
 * 
 *         revised and updated from
 * @author Nilay Yueksekogul 2013
 * 
 */
@SuppressWarnings("restriction")
public class SendMail {

	private Session session;
	private String recipientMailAddress = "";
	private Logger logger = Logger.getLogger(SendMail.class);

	// XTag:
	private XTag mXTag = new XTag(); // 23.07.05 it will assign a default tag

	/**
	 * Creates a new sending mail process with the given attribute.
	 * 
	 * @param recipientMailAddress
	 *            e-mail address of the recipient
	 */
	public SendMail(String recipientMailAddress) {

		logger.info("SendMail - " + "" + "setup started");

		this.recipientMailAddress = recipientMailAddress;

		logger.info("SendMail - setup finished");
	}

	/**
	 * 
	 */
	public void splitAttachment() {

	}

	/**
	 * 
	 * @param param
	 * @return
	 */
	public String trim(String param) {
		return param.substring(0, param.indexOf('.'));
	}

	/**
	 * Sets all content and sends the e-mail.
	 * 
	 * @param content
	 *            encrypted MIME multipart to set
	 * @param totalCountFilesToSend
	 *            Anzahl der zu versendenen Dateien
	 * @param recipient
	 *            Empfänger der Dateien
	 * @throws MessagingException
	 * @throws {@link IOException}
	 */
	public void sendMail(/* MimeMultipartEncrypted content, */File tmpFolder,
			DicomMailRecipient recipient, String mailServer)
			throws MessagingException, IOException {

		int nFiles = tmpFolder.listFiles().length;

		logger.info("SendMail - sendMail -  TMP-Folder Anzahl Dateien: "
				+ tmpFolder.listFiles().length);

		boolean m_stop = false;
		String sEmsg = ""; // send report

		SplittingSender splittingSender = new SplittingSender(Integer
				.parseInt(Configuration.getMainConfig()
						.getProperty("splitSize")));

		int total = 0; // indicates a total number of files to send
		int part = 0; // indicates a current number of a file to send

		MailAuthenticator auth = null;
		MailReceiver mailReceiverContainer = null;
		Properties properties = new Properties();
		String mailSenderAdressProp= "";
		
		if (mailServer.equalsIgnoreCase("hd")) {
			
			auth = new MailAuthenticator(Configuration.getMainConfig()
					.getProperty("mailserver_hd_mailUsername"), Configuration
					.getMainConfig().getProperty("mailserver_hd_mailPassword"));
			
			mailSenderAdressProp = Configuration.getMainConfig().getProperty("mailserver_hd_mailSenderAddress");
			mailReceiverContainer = new MailReceiver(mailSenderAdressProp,
					new MailAccount(Configuration.getMainConfig().getProperty(
							"mailserver_hd_mailUsername"), Configuration
							.getMainConfig().getProperty(
									"mailserver_hd_mailPassword"),
							Configuration.getMainConfig().getProperty(
									"mailserver_hd_mailSmtpHost")),
					recipientMailAddress, Configuration.getMainConfig()
							.getProperty("defaultSubject"));

			properties.put("mail.smtp.host", Configuration.getMainConfig()
					.getProperty("mailserver_hd_mailSmtpHost"));
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", Configuration
					.getMainConfig().getProperty(
							"mailserver_hd_mailSmtpStarttlsEnable"));
			properties.put("mail.smtp.port", Configuration.getMainConfig()
					.getProperty("mailserver_hd_mailSmtpPort"));
			
			
		} else if (mailServer.equalsIgnoreCase("ma")) {
			
			mailSenderAdressProp = Configuration.getMainConfig().getProperty("mailserver_ma_mailSenderAddress");
			
			auth = new MailAuthenticator(Configuration.getMainConfig()
					.getProperty("mailserver_ma_mailUsername"), Configuration
					.getMainConfig().getProperty("mailserver_ma_mailPassword"));

			mailReceiverContainer = new MailReceiver(mailSenderAdressProp,
					new MailAccount(Configuration.getMainConfig().getProperty(
							"mailserver_ma_mailUsername"), Configuration
							.getMainConfig().getProperty(
									"mailserver_ma_mailPassword"),
							Configuration.getMainConfig().getProperty(
									"mailserver_ma_mailSmtpHost")),
					recipientMailAddress, Configuration.getMainConfig()
							.getProperty("defaultSubject"));

			properties.put("mail.smtp.host", Configuration.getMainConfig()
					.getProperty("mailserver_ma_mailSmtpHost"));
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", Configuration
					.getMainConfig().getProperty(
							"mailserver_ma_mailSmtpStarttlsEnable"));
			properties.put("mail.smtp.port", Configuration.getMainConfig()
					.getProperty("mailserver_ma_mailSmtpPort"));
			
			
		} else if (mailServer.equalsIgnoreCase("ka")) {
			
			mailSenderAdressProp = Configuration.getMainConfig().getProperty("mailserver_ka_mailSenderAddress");
			auth = new MailAuthenticator(Configuration.getMainConfig()
					.getProperty("mailserver_ka_mailUsername"), Configuration
					.getMainConfig().getProperty("mailserver_ka_mailPassword"));

			mailReceiverContainer = new MailReceiver(mailSenderAdressProp,
					new MailAccount(Configuration.getMainConfig().getProperty(
							"mailserver_ka_mailUsername"), Configuration
							.getMainConfig().getProperty(
									"mailserver_ka_mailPassword"),
							Configuration.getMainConfig().getProperty(
									"mailserver_ka_mailSmtpHost")),
					recipientMailAddress, Configuration.getMainConfig()
							.getProperty("defaultSubject"));

			properties.put("mail.smtp.host", Configuration.getMainConfig()
					.getProperty("mailserver_ka_mailSmtpHost"));
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", Configuration
					.getMainConfig().getProperty(
							"mailserver_ka_mailSmtpStarttlsEnable"));
			properties.put("mail.smtp.port", Configuration.getMainConfig()
					.getProperty("mailserver_ka_mailSmtpPort"));
		} else {

			throw new MessagingException(
					"SendMail.java - Error - Could not load account on the right mailserver.");
		}

		session = Session.getInstance(properties, auth);

		String currentTimestamp = FileTools.makeUniqueNameFormatted();

		String xTag = "";
		String sFileName1 = "";

		String xTagSetID = currentTimestamp;

		// ---- create sign and encrypted Message
		CBaseMessageBuilder builder = null;

		builder = new CSignedEncryptedMessageBuilder("", mXTag.toString(),
				recipient.getPublicKeyId());
		builder.setSecretKey(Configuration.getMainConfig().getProperty(
				"userPrivateKey"));
		builder.setPass(Configuration.getMainConfig().getProperty(
				"passwordPrivateKey"));

		// set the DN method assigned for the recipient
		// Todo check the parameter in DBBank
		builder.setMethodDN(2);
		builder.setID(xTagSetID); // "X-TELEMEDICINE-SETID"
		builder.setReplayToDN("-"); // DN_RFC="Disposition-Notification-To"

		// builder.setKeyIDDN("todo"); //
		// "X-TELEMEDICINE-DISPOSITION-NOTIFICATION-KEYID" s.o.

		total = nFiles;
		builder.setTotal(total);// "X-TELEMEDICINE-SETTOTAL
		part = 0;

		//
		// SEND FILES
		//

		// reconnect conditions
		int last_before_reconnect = DBConsts.SMTP_RECONNECT_NFILES;
		Date start_time = new Date();

		InputStream is;

		for (File f : tmpFolder.listFiles()) {
			boolean bProcessedOk = false;
			String sFileName = (String) f.getAbsolutePath();
			logger.info("SendMail - sendMail -  Dateiname: " + sFileName);
			logger.info("SendMail - sendMail - part: " + part);
			logger.info("SendMail - sendMail - builder: " + builder.toString());
			String sFileOriginal = sFileName;
			part++;
			builder.setPart(part); // "X-TELEMEDICINE-SETPART

			if (xTag.isEmpty()) {

				sFileName1 = (String) f.getAbsolutePath();
				is = new FileInputStream(f.getAbsolutePath());

				if (!FileTools.isDICMfile(is)) {

					// create StudyID for non-Dicom-File
					xTag = Configuration.getMainConfig().getProperty("oid")
							+ "." + currentTimestamp;

					// Dicom File
				} else {
					xTag = FileTools.getStudyInstanceUID(sFileName1);
				}
			}

			// It is not allowed to send any files started with ".", ".."
			// Function FileTools.GetNotNiddenFilesFromDir(dir) takes care of it
			int j = 0;
			boolean dicom_noname_remove_flag = false;
			try {
				logger.info("SendMail - sendMail - tmpFolder "
						+ tmpFolder.getAbsolutePath());
				logger.info("SendMail - sendMail - sFileName " + sFileName);

				MimeMessage msg = builder.BuildMessage(sFileName, session,
						tmpFolder.getAbsolutePath(), xTag);

				String old_mid = msg.getMessageID();

				msg.setFrom(new InternetAddress(mailSenderAdressProp)); //Configuration.getMainConfig().getProperty("mailSenderAddress")
				msg.setRecipients(Message.RecipientType.TO, InternetAddress
						.parse(recipientMailAddress, false));

				msg.setHeader(DBConsts.DN_RFC, mailSenderAdressProp); //Configuration.getMainConfig().getProperty("mailSenderAddress")

				splittingSender.sendMessage(msg, session);

				bProcessedOk = true;

			} catch (MessagingException e) {

				String msg = e.getMessage();
				if (msg != null) {
					int indexOfEndLine = msg.indexOf('\n');
					if (indexOfEndLine != -1) {
						sEmsg += msg.substring(0, indexOfEndLine) + "\n";
					} else {
						sEmsg += msg + "\n";
					}
				} else {
					sEmsg += " Unknown error \n";
				}

				logger.error(sEmsg);
				throw e;
			}

			logger.info("SendMail - Starting Removing temporary files");

			if (dicom_noname_remove_flag) {
				boolean delete_dicom_success = (new File(sFileName)).delete();
				if (!delete_dicom_success) {
					logger.error("SendMail - sendMail - Dicom file "
							+ sFileName + "cannot be removed");

				} else {
					logger.error("SendMail - sendMail - Dicom file "
							+ sFileName + "has been removed");
				}
				sFileName = sFileOriginal; // restore the original file name
				logger.info("SendMail - sendMail - Original file " + sFileName);

				dicom_noname_remove_flag = false;
			}

		}

	}

	/**
	 * An object of this class represents an e-mail authenticator.
	 * logger.info("Dateien durchsucht: DICOM FILE vorhanden");
	 * 
	 * @author Benjamin Schneider
	 * 
	 */
	class MailAuthenticator extends Authenticator {

		private final String user;

		private final String password;

		/**
		 * constructor creates MailAuthenticator Object<br>
		 * from the paramaeters user und password.
		 * 
		 * @param user
		 *            String, Username for the Mailaccount.
		 * @param password
		 *            String, Password for the Mailaccount
		 */
		public MailAuthenticator(String user, String password) {
			this.user = user;
			this.password = password;
		}

		/**
		 * This method returns new PasswortAuthentication Object
		 * 
		 * @see javax.mail.Authenticator#getPasswordAuthentication()
		 */
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(this.user, this.password);
		}
	}

}