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

package org.openehealth.tewepo.twp.dmp.dmc.server.email;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.ValidationException;
import org.openehealth.tewepo.twp.dmp.dmc.client.data.ReferencePortalDataSet;
import org.openehealth.tewepo.twp.dmp.dmc.server.config.Configuration;
import org.openehealth.tewepo.twp.dmp.dmc.server.email.dicommail.SendMail;
import org.openehealth.tewepo.twp.dmp.dmc.server.email.xml.DicomMailRecipient;
import org.openehealth.tewepo.twp.dmp.dmc.server.email.xml.DicomMailRecipients;
import org.openehealth.twp.tewepo.businesslogic.BusinesslogicException;
import org.openehealth.twp.tewepo.businesslogic.ILogLastRecipients;
import org.openehealth.twp.tewepo.businesslogic.IPerson;
import org.openehealth.twp.tewepo.businesslogic.LogLastFiles;
import org.openehealth.twp.tewepo.businesslogic.LogLastRecipients;
import org.openehealth.twp.tewepo.businesslogic.Person;
import org.openehealth.twp.tewepo.database.DatabaseException;


/**
 * This class is a helper for sending an e-mail.
 * 
 * @author Nilay Yueksekogul
 * 
 */
public class SendDicomMailHelper {

	private Logger logger = Logger.getLogger(SendDicomMailHelper.class);

	private String rootFolderPath = "";
	private String sessionID = "";

	// Has to be accessed by the getDicomMailRecipients method.
	private DicomMailRecipients dicomMailRecipients = null;

	public SendDicomMailHelper(String rootFolderPath, String sessionId) {
		this.rootFolderPath = rootFolderPath;
		this.sessionID = sessionId;
	}

	/**
	 * Gets the temporary folder where uploaded files are stored. A folder will
	 * be create, if it not exists
	 * 
	 * @return tmpFolder created temporary folder
	 */
	public File getTmpFolder() {

		String folderPath = rootFolderPath
				+ Configuration.getMainConfig().getProperty("tmpFolderPath")
				+ "/" + this.sessionID;

		File tmpFolder = new File(folderPath);
		if (!tmpFolder.isDirectory()) {
			tmpFolder.mkdirs();
			logger.info("SendDicomMailsServlet - getTmpFolder - Der Ordner: "
					+ folderPath + " - wurde erstellt!");
		}
		return tmpFolder;

	}

	/**
	 * Gets the temporary folder where uploaded files are stored. A folder will
	 * be create, if it not exists
	 * 
	 * @return tmpFolder if it exists, else return null
	 */
	private File getTmpFolderIfExist() {

		String folderPath = rootFolderPath
				+ Configuration.getMainConfig().getProperty("tmpFolderPath")
				+ "/" + this.sessionID;

		File tmpFolder = new File(folderPath);
		if (!tmpFolder.exists()) {
			return null;
		} else if (!tmpFolder.isDirectory()) {
			return null;
		}
		return tmpFolder;

	}

	/**
	 * Sending DICOM E-Mails.
	 * 
	 * @param object
	 *            a {@link ReferencePortalDataSet} object
	 * 
	 * @return result result message
	 */
	public String sendDicomMails(ReferencePortalDataSet object) {
		String result = "";
		String errorMessage = "Fehler beim Versenden, bitte versuchen sie es noch einmal!";
		DicomMailRecipient recipientMailAddress = null;
		File tmpfolder = getTmpFolder();
		String[] tmpArrayRecipientMailServer = null;

		// serialize textual description
		if (object != null) {
			if (object instanceof ReferencePortalDataSet) {
				String[] tmpArrayRecipient = object.getBasicDataSet()
						.getRecipient().split(";");

				String tmpArrayRecipientString = tmpArrayRecipient[2]
						.toString();
				tmpArrayRecipientMailServer = tmpArrayRecipientString
						.split("\\(");

				recipientMailAddress = this.getRecipientObject(
						tmpArrayRecipient[0].trim(), tmpArrayRecipient[1]
								.trim(), tmpArrayRecipientMailServer[0].trim());// tmpArrayRecipient[2].trim());

				ReferencePortalDataSet dataSet = (ReferencePortalDataSet) object;
				generateXMLDescriptionFiles(dataSet);
			}
		}

		int countFiles = tmpfolder.listFiles().length;
		String reciepientCity = "";

		if (tmpfolder != null && tmpfolder.listFiles() != null
				&& tmpfolder.listFiles().length > 0) {

			SendMail sendMail = null;

			try {

				String locationMailserver = "hd";

				if (tmpArrayRecipientMailServer != null
						&& tmpArrayRecipientMailServer.length == 2
						&& tmpArrayRecipientMailServer[1].length() == 3) {

					if (tmpArrayRecipientMailServer[1].equals("HD)")) {

						sendMail = new SendMail(recipientMailAddress
								.getMailAddress1());
						reciepientCity = " (HD)";
						locationMailserver = "hd";

					} else if (tmpArrayRecipientMailServer[1].equals("MA)")) {

						sendMail = new SendMail(recipientMailAddress
								.getMailAddress2());
						reciepientCity = " (MA)";
						locationMailserver = "ma";
					} else if (tmpArrayRecipientMailServer[1].equals("KA)")) {

						sendMail = new SendMail(recipientMailAddress
								.getMailAddress3());
						reciepientCity = " (KA)";
						locationMailserver = "ka";

					} else {
						locationMailserver = "hd";
						sendMail = new SendMail(recipientMailAddress
								.getMailAddress1());
					}
				} else {
					// send DICOM Mails
					locationMailserver = "hd";
					sendMail = new SendMail(recipientMailAddress
							.getMailAddress1());
				}

				sendMail.sendMail(tmpfolder, recipientMailAddress,
						locationMailserver);

			} catch (MessagingException e) {

				result = errorMessage;
				logger
						.error("SendDicomMailsServlet - sendDicomMails -  MessagingException e Adresse: "
								+ e);

				// remove .eml + .asc_tmp Files
				deleteSpecificTmpFiles();

				return errorMessage;

			} catch (Exception e) {

				result = errorMessage;
				logger
						.error("SendDicomMailsServlet - sendDicomMails - Exception e : "
								+ e);
				return errorMessage;
			}

			IPerson senderUser;
			try {
				senderUser = Person.getPerson(object.getBasicDataSet()
						.getSenderUserId());

				// reciever is complemented by the mailbox
				recipientMailAddress.setRecipient(recipientMailAddress
						.getRecipient()
						+ reciepientCity);

				// save last recipient in db
				new LogLastRecipients(senderUser, recipientMailAddress).save();
				logger
						.info("SendDicomMailHelper - sendDicomMails - Letzter Empfänger in DB gespeichert");

				String sFileName = "";
				long size;
				String fileSize = "";

				for (File f : tmpfolder.listFiles()) {
					sFileName = (String) f.getName();
					size = f.length();
					// f.getAbsolutePath();
					logger
							.info("SendDicomMailHelper - sendDicomMails - Dateiname: "
									+ sFileName);

					if (sFileName.endsWith(".asc_tmp")
							|| sFileName.endsWith(".eml")
							|| sFileName
									.equals("textualDescriptionAdvancedData.xml")) {
						// do not save
					} else {

						fileSize = readableFileSize(size);
						new LogLastFiles(senderUser, recipientMailAddress,
								sFileName, fileSize).save();
					}

				}

			} catch (BusinesslogicException e1) {
				result = errorMessage;
				logger.error("SendDicomMailsServlet - sendDicomMails: "
						+ e1.getMessage());
				return errorMessage;
			} catch (DatabaseException e2) {
				result = errorMessage;
				logger.error("SendDicomMailsServlet - sendDicomMails: "
						+ e2.getMessage());
				return errorMessage;
			}

			if (result.equals("")) {
				result = countFiles - 1
						+ " Datei(en) und die Beschreibung wurde(n) gesendet!";

				deleteAllSessionTmpFiles();
				try {

					LogLastRecipients logLastRecipients = new LogLastRecipients(
							new Person(), recipientMailAddress);
				} catch (BusinesslogicException e) {
					logger
							.error("SendDicomMailsServlet - sendDicomMails - letzte Senderinformationen"
									+ e.getMessage());
				}
			}
			logger.debug(result);
			return result;
		} else {
			result = "Es ist keine Datei zum Versenden ausgewählt!";
			logger.debug("SendDicomMailsServlet - sendDicomMails - " + result);
			return result;
		}
	}

	/**
	 * Generates XML description files with the contents of the advanced data
	 * set {@link ReferencePortalDataSet}
	 * 
	 * @param advancedDataSet
	 *            {@link ReferencePortalDataSet}
	 */
	private void generateXMLDescriptionFiles(// BasicDataSet basicDataSet,
			ReferencePortalDataSet advancedDataSet) {
		// create File to marshal to
		FileWriter fileWriterAdvancedDataSet = null;
		try {
			File tmpfolder = getTmpFolder();
			if (!tmpfolder.exists())
				tmpfolder.mkdir();

			if (advancedDataSet != null) {
				fileWriterAdvancedDataSet = new FileWriter(tmpfolder.getPath()
						+ "/textualDescriptionAdvancedData.xml");
				Marshaller.marshal(advancedDataSet, fileWriterAdvancedDataSet);
			}

		} catch (IOException e) {
			logger
					.error("SendDicomMailsServlet - generateXMLDescriptionFiles - "
							+ e.getMessage());
		} catch (MarshalException e) {
			logger
					.error("SendDicomMailsServlet - generateXMLDescriptionFiles - "
							+ e.getMessage());
		} catch (ValidationException e) {
			logger
					.error("SendDicomMailsServlet - generateXMLDescriptionFiles - "
							+ e.getMessage());
		} finally {

			try {
				if (fileWriterAdvancedDataSet != null)
					fileWriterAdvancedDataSet.close();

			} catch (IOException e) {
				logger
						.error("SendDicomMailsServlet - generateXMLDescriptionFiles - "
								+ e.getMessage());
			}
		}

	}

	/**
	 * Gets the file size as String.
	 * 
	 * @return 0; if file size <= 0
	 * @author Nilay Yueksekogul
	 */

	public String readableFileSize(long size) {
		if (size <= 0) {
			return "0";
		}
		final String[] units = new String[] { "Byte", "KB", "MB", "GB", "TB" };
		int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
		return new DecimalFormat("#,##0.#").format(size
				/ Math.pow(1024, digitGroups))
				+ " " + units[digitGroups];
	}

	/**
	 * Gets the size of the file
	 * 
	 * @return fileSize
	 * @author Nilay Yueksekogul
	 */
	public String getFileSize() {

		long fileSize = 0;
		String filesize;

		File tmpfolder = getTmpFolderIfExist();
		if (tmpfolder == null) {
			filesize = 0 + "";
			return filesize;
		}

		if (tmpfolder != null && tmpfolder.listFiles() != null
				&& tmpfolder.listFiles().length > 0) {
			for (File f : tmpfolder.listFiles()) {
				fileSize = fileSize + f.length();
			}
			filesize = readableFileSize(fileSize);
			return filesize;
		}
		return 0 + "";
	}

	/**
	 * Gets a list of Files
	 * 
	 * @return fileNameList
	 */
	public String getFileListString() {
		String fileNameList = "";

		File tmpfolder = getTmpFolderIfExist();
		if (tmpfolder == null) {
			return null;
		}

		if (tmpfolder != null && tmpfolder.listFiles() != null
				&& tmpfolder.listFiles().length > 0) {
			// send DICOM Mails
			for (File f : tmpfolder.listFiles()) {

				fileNameList = fileNameList + f.getName() /* name */+ "\r\n";

			}
			if (fileNameList.length() == 0)
				return null;
			return fileNameList;
		} else {
			return null;
		}

	}

	/**
	 * Deletes the temporary file on the server.
	 * 
	 * @return true; if file deleted false; else
	 * @author Nilay Yueksekogul
	 */
	public boolean deleteFileOnTmpFolder(String filename) {
		boolean deleted = false;
		try {

			File tmpFolder = getTmpFolder();
			File fileToDelete = new File(tmpFolder, filename);

			if (fileToDelete.exists()) {
				deleted = fileToDelete.delete();
				if (tmpFolder.isDirectory()
						&& tmpFolder.listFiles().length == 0)
					tmpFolder.delete();

			}

			if (tmpFolder.isDirectory() && tmpFolder.listFiles().length == 0)
				tmpFolder.delete();
		} catch (Exception e) {
			logger.error("SendDicomMailsServlet - deleteFileOnTmpFolder - "
					+ e.getMessage());
			return false;
		}
		return deleted;
	}

	/**
	 * Deletes all temporary files from a session.
	 * 
	 * @return true; if files deleted false; else
	 * @author Nilay Yueksekogul
	 */
	public boolean deleteAllSessionTmpFiles() {
		try {
			File tmpFolder = getTmpFolder();
			for (File file : tmpFolder.listFiles()) {
				file.delete();
			}
			tmpFolder.delete();
		} catch (Exception e) {
			logger
					.error("SendDicomMailsServlet - deleteAllSessionTmpFiles - Can't delete all temporary files from session: "
							+ e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * Deletes all temporary files from a session.
	 * 
	 * @return true; if files deleted false; else
	 * @author Nilay Yueksekogul
	 */
	public boolean deleteSpecificTmpFiles() {
		try {
			File tmpFolder = getTmpFolder();
			for (File file : tmpFolder.listFiles()) {
				String filename = file.getName();
				if (filename.endsWith(".eml") || filename.endsWith(".asc_tmp")) {
					deleteFileOnTmpFolder(filename);
				}
			}
		} catch (Exception e) {
			logger
					.error("SendDicomMailsServlet - deleteSpecificTmpFiles - Can't delete specific files from session: "
							+ e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * Gets the DICOM mail recipients, if they are null the method creates a new
	 * object.
	 * 
	 * @return DICOM mail recipients
	 */
	private DicomMailRecipients getDicomMailRecipients() {
		if (this.dicomMailRecipients == null) {
			this.dicomMailRecipients = new DicomMailRecipients(rootFolderPath);
		}
		return this.dicomMailRecipients;
	}

	/**
	 * Gets all locations of the DICOM mail recipients as a String separated by
	 * a ";"
	 * 
	 * @return locaitons of the DICOM mail recipients
	 */
	public String getDicomMailRecipientsLocation() {
		String result = "";
		if (getDicomMailRecipients().getRecipients().size() > 0) {
			result = getDicomMailRecipients().getRecipients().get(0)
					.getLocation();
			if (getDicomMailRecipients().getRecipients().size() > 1) {
				for (DicomMailRecipient recipient : getDicomMailRecipients()
						.getRecipients()) {
					if (!result.contains(recipient.getLocation())) {
						result = result + ";" + recipient.getLocation();
					}
				}
			}
		}
		return result;
	}

	/**
	 * Gets all institutions of the DICOM mail recipients with the given
	 * location as a String separated by a ";"
	 * 
	 * @return institutions of the DICOM mail recipients
	 */
	public String getDicomMailRecipientsInstitution(String location) {
		String result = "";
		List<DicomMailRecipient> tmpList = new ArrayList<DicomMailRecipient>();

		for (DicomMailRecipient recipient : getDicomMailRecipients()
				.getRecipients()) {
			if (recipient.getLocation().equals(location))
				tmpList.add(recipient);
		}

		if (tmpList.size() > 0) {
			result = tmpList.get(0).getInstitution();
			if (tmpList.size() > 1) {
				for (DicomMailRecipient recipient : tmpList) {
					if (recipient.getLocation().equals(location)) {
						if (!result.contains(recipient.getInstitution()))
							result = result + ";" + recipient.getInstitution();
					}
				}
			}
		}
		return result;
	}

	/**
	 * Gets all recipients of the DICOM mail recipients with the given location
	 * and the given institution as a String separated by a ";"
	 * 
	 * @return recipients of the DICOM mail recipient
	 */
	public String getDicomMailRecipientsRecipient(String location,
			String institution) {
		String result = "";
		List<DicomMailRecipient> tmpList = new ArrayList<DicomMailRecipient>();

		for (DicomMailRecipient recipient : getDicomMailRecipients()
				.getRecipients()) {
			if (recipient.getLocation().equals(location)
					&& recipient.getInstitution().equals(institution)) {
				if (!tmpList.contains(recipient))
					tmpList.add(recipient);
			}
		}

		if (tmpList.size() > 0) {
			if (tmpList.get(0).getMailAddress1() != null
					&& !tmpList.get(0).getMailAddress1().equals("")) {
				if (result.trim().length() > 0)
					result = result + ";";

				result = result + tmpList.get(0).getRecipient() + " (HD)";
			}

			if (tmpList.get(0).getMailAddress2() != null
					&& !tmpList.get(0).getMailAddress2().equals("")) {
				if (result.trim().length() > 0)
					result = result + ";";
				result = result + tmpList.get(0).getRecipient() + " (MA)";
			}
			if (tmpList.get(0).getMailAddress3() != null
					&& !tmpList.get(0).getMailAddress3().equals("")) {
				if (result.trim().length() > 0)
					result = result + ";";
				result = result + tmpList.get(0).getRecipient() + " (KA)";
			}
			// result = tmpList.get(0).getRecipient();
			if (tmpList.size() > 1) {
				for (DicomMailRecipient recipient : tmpList) {
					if (!result.contains(recipient.getRecipient() + " (HD)")) {
						if (recipient.getMailAddress1() != null
								&& !recipient.getMailAddress1().equals(""))
							result = result + ";" + recipient.getRecipient()
									+ " (HD)";
					}
					if (!result.contains(recipient.getRecipient() + " (MA)")) {
						if (recipient.getMailAddress2() != null
								&& !recipient.getMailAddress2().equals(""))
							result = result + ";" + recipient.getRecipient()
									+ " (MA)";
					}
					if (!result.contains(recipient.getRecipient() + " (KA)")) {
						if (recipient.getMailAddress3() != null
								&& !recipient.getMailAddress3().equals(""))
							result = result + ";" + recipient.getRecipient()
									+ " (KA)";
					}
				}
			}
		}

		return result;
	}

	/**
	 * Gets the DICOM mail recipient with the given location, institution and
	 * recipient.
	 * 
	 * @return null; if no recipient found or more than one recipient found
	 *         DICOM mail recipient; else
	 */
	public DicomMailRecipient getRecipientObject(String location,
			String institution, String recipientStringers) {
		DicomMailRecipient result = null;

		List<DicomMailRecipient> tmpList = new ArrayList<DicomMailRecipient>();

		for (DicomMailRecipient recipient : getDicomMailRecipients()
				.getRecipients()) {

			if (recipient.getLocation().equals(location.trim())
					&& recipient.getInstitution().equals(institution.trim())
					&& recipient.getRecipient().equals(
							recipientStringers.trim())) {

				if (!tmpList.contains(recipient))
					tmpList.add(recipient);
			}
		}

		if (tmpList.size() == 1) {
			return tmpList.get(0);
		}

		return result;
	}

	/**
	 * Gets the recent DICOM mail recipients of the current user.
	 * 
	 * @return recent DICOM mail recipient(s)
	 */
	public String getDicomMailRecipientsRecentRecipients(String userId) {
		String returnValue = "";
		try {
			List<ILogLastRecipients> listLogLastRecipients = null;
			listLogLastRecipients = LogLastRecipients
					.getLogLastRecipients(userId);

			if (listLogLastRecipients == null) {
				return returnValue;
			}
			String stringMaxEntries = Configuration.getMainConfig()
					.getProperty("countViewLastRecipients");
			int maxEntries = Integer.parseInt(stringMaxEntries);

			if (maxEntries > listLogLastRecipients.size()) {
				maxEntries = listLogLastRecipients.size();
			}

			for (int i = 0; i < maxEntries; i++) {
				returnValue = returnValue
						+ listLogLastRecipients.get(i).getRecipientsLocation()
						+ ";"
						+ listLogLastRecipients.get(i)
								.getRecipientsInstitution() + ";"
						+ listLogLastRecipients.get(i).getRecipientsRecipient()
						+ "-";
			}
		} catch (DatabaseException e) {
			logger
					.error("SendDicomMailsServlet - getDicomMailRecipientsRecentRecipients - Database Exception: SendDicomMailsServlet - "
							+ e.getMessage());
		}
		return returnValue;
	}

}
