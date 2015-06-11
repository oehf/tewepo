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

import org.apache.log4j.Logger;
import org.openehealth.tewepo.twp.dmp.dmc.client.data.ReferencePortalDataSet;
import org.openehealth.tewepo.twp.dmp.dmc.client.rpc.SendDicomMailsService;
import org.openehealth.tewepo.twp.dmp.dmc.server.email.xml.DicomMailRecipient;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;


/**
 * This class represents the servlet implementation class for sending DICOM
 * e-mails.
 * 
 * @author Benjamin Schneider
 * 
 */
public class SendDicomMailsServlet extends RemoteServiceServlet implements
		SendDicomMailsService {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(SendDicomMailsServlet.class);

	/**
	 * Sending DICOM E-Mails.
	 * 
	 * @param object
	 *            a {@link ReferencePortalDataSet} object
	 * 
	 * @return result result message
	 */
	public String sendDicomMails(ReferencePortalDataSet object) {

		logger.info("Start send-process");
		SendDicomMailHelper helper = new SendDicomMailHelper(
				getServletContext().getRealPath("/"), getThreadLocalRequest()
						.getSession().getId());

		return helper.sendDicomMails(object);

	}

	/**
	 * Gets the name of the file as String.
	 * 
	 * @return null; if no file exists name of the file
	 */
	public String getFileListString() {

		SendDicomMailHelper helper = new SendDicomMailHelper(
				getServletContext().getRealPath("/"), getThreadLocalRequest()
						.getSession().getId());

		return helper.getFileListString();
	}

	
	/**
	 * Gets the size of the folder.
	 * 
	 * @return 
	 * @author Nilay Yueksekogul
	 */
	public String getFileSize() {
		
		
		SendDicomMailHelper helper = new SendDicomMailHelper(
				getServletContext().getRealPath("/"), getThreadLocalRequest()
						.getSession().getId());
		
		
		return helper.getFileSize();
	}


	/**
	 * Deletes the temporary file on the server.
	 * 
	 * @return true; if file deleted false; else
	 */
	public boolean deleteFileOnTmpFolder(String filename) {

		SendDicomMailHelper helper = new SendDicomMailHelper(
				getServletContext().getRealPath("/"), getThreadLocalRequest()
						.getSession().getId());

		return helper.deleteFileOnTmpFolder(filename);

	}

	/**
	 * Deletes all temporary files from a session.
	 * 
	 * @return true; if files deleted false; else
	 */
	public boolean deleteAllSessionTmpFiles() {

		SendDicomMailHelper helper = new SendDicomMailHelper(
				getServletContext().getRealPath("/"), getThreadLocalRequest()
						.getSession().getId());

		return helper.deleteAllSessionTmpFiles();
	}

	/**
	 * Gets all locations of the DICOM mail recipients as a String separated by
	 * a ";"
	 * 
	 * @return locaitons of the DICOM mail recipients
	 */
	public String getDicomMailRecipientsLocation() {

		SendDicomMailHelper helper = new SendDicomMailHelper(
				getServletContext().getRealPath("/"), getThreadLocalRequest()
						.getSession().getId());

		return helper.getDicomMailRecipientsLocation();

	}

	/**
	 * Gets all institutions of the DICOM mail recipients with the given
	 * location as a String separated by a ";"
	 * 
	 * @return institutions of the DICOM mail recipients
	 */
	public String getDicomMailRecipientsInstitution(String location) {

		SendDicomMailHelper helper = new SendDicomMailHelper(
				getServletContext().getRealPath("/"), getThreadLocalRequest()
						.getSession().getId());

		return helper.getDicomMailRecipientsInstitution(location);

	}

	/**
	 * Gets all recipients of the DICOM mail recipients with the given location
	 * and the given institution as a String separated by a ";"
	 * 
	 * @return recipients of the DICOM mail recipient
	 */
	public String getDicomMailRecipientsRecipient(String location,
			String institution) {

		SendDicomMailHelper helper = new SendDicomMailHelper(
				getServletContext().getRealPath("/"), getThreadLocalRequest()
						.getSession().getId());

		return helper.getDicomMailRecipientsRecipient(location, institution);
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

		SendDicomMailHelper helper = new SendDicomMailHelper(
				getServletContext().getRealPath("/"), getThreadLocalRequest()
						.getSession().getId());

		return helper.getRecipientObject(location, institution,
				recipientStringers);

	}

	/**
	 * Gets the recent DICOM mail recipients of the current user.
	 * 
	 * @return recent DICOM mail recipient(s)
	 */
	public String getDicomMailRecipientsRecentRecipients(String userId) {
		SendDicomMailHelper helper = new SendDicomMailHelper(
				getServletContext().getRealPath("/"), getThreadLocalRequest()
						.getSession().getId());

		return helper.getDicomMailRecipientsRecentRecipients(userId);
	}

	
}
