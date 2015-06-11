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
package org.openehealth.tewepo.twp.dmp.dmc.client.rpc;

import org.openehealth.tewepo.twp.dmp.dmc.client.data.ReferencePortalDataSet;

import com.google.gwt.user.client.rpc.RemoteService;


/**
 * Servlet implementation interface SendDicomMailsService.
 * 
 * @author devmis
 * 
 */
public interface SendDicomMailsService extends RemoteService {
	
	/**
	 * 
	 * Send Dicom Mails
	 * 
	 * @param object
	 * @return
	 */
	public String sendDicomMails(ReferencePortalDataSet object);

	/**
	 * Gets List of files
	 * 
	 * @return file list
	 */
	public String getFileListString();

	/**
	 * 
	 * Deletes a file on the temp Folder
	 * 
	 * @param filename
	 * @return true when delete file on tmp folder, false when doesn't delete file
	 */
	public boolean deleteFileOnTmpFolder(String filename);

	/**
	 * 
	 * Deletes all session temp files
	 * 
	 * @return true when delete all session temp files, false when doesn't delete the files.
	 */
	public boolean deleteAllSessionTmpFiles();

	/**
	 * 
	 * Gets the Dicom Mail Recent Recipient
	 * 
	 * @param userId
	 * @return recent recipient
	 */
	public String getDicomMailRecipientsRecentRecipients(String userId);

	/**
	 * 
	 * Gets the Dicom Mail Recipient Location
	 * 
	 * @return recipient location
	 */
	public String getDicomMailRecipientsLocation();

	/**
	 * 
	 * Gets the Dicom Mail Recipient Institution
	 * 
	 * 
	 * @param location
	 * @return recipient Institution
	 */
	public String getDicomMailRecipientsInstitution(String location);

	/**
	 * 
	 * Gets the Dicom Mail Recipient 
	 * 
	 * @param location
	 * @param institution
	 * @return recipient  
	 */
	public String getDicomMailRecipientsRecipient(String location,String institution);

	/**
	 * 
	 * Gets the Size of the file
	 * 
	 * @return size 
	 */
	public String getFileSize();

}
