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

import com.google.gwt.user.client.rpc.AsyncCallback;


	/**
	 * Servlet implementation interface SendDicomMailsServiceAsync.
	 * 
	 * @author devmis
	 * 
	 */
	public interface SendDicomMailsServiceAsync {
	
	
	/**
	 * 
	 * Deletes all session temp file
	 * 
	 * @param callback
	 */
	public void deleteAllSessionTmpFiles(AsyncCallback<Boolean> callback);

	/**
	 * 
	 *  Deletes a file on the temp Folder
	 * 
	 * @param filename
	 * @param callback
	 */
	public void deleteFileOnTmpFolder(String filename,
			AsyncCallback<Boolean> callback);

	/**
	 * 
	 * Gets List of files
	 * 
	 * @param callback
	 */
	public void getFileListString(AsyncCallback<String> callback);
	
	
	/**
	 * 
	 * Gets the Size of the file
	 * 
	 * @param callback
	 */
	public void getFileSize(AsyncCallback<String> callback);
	

	/**
	 * 
	 * Send Dicom Mails
	 * 
	 * @param object
	 * @param callback
	 */
	public void sendDicomMails(ReferencePortalDataSet object,
			AsyncCallback<String> callback);

	/**
	 * 
	 * Gets the Dicom Mail Recent Recipient
	 * 
	 * @param userId
	 * @param callback
	 */
	public void getDicomMailRecipientsRecentRecipients(String userId,
			AsyncCallback<String> callback);

	/**
	 * 
	 * Gets the Dicom Mail Recipient Institution
	 * 
	 * @param location
	 * @param callback
	 */
	public void getDicomMailRecipientsInstitution(String location,
			AsyncCallback<String> callback);

	/**
	 * 
	 * Gets the Dicom Mail Recipient Location
	 * 
	 * @param callback
	 */
	public void getDicomMailRecipientsLocation(AsyncCallback<String> callback);

	/**
	 * 
	 * Gets the Dicom Mail Recipient 
	 * 
	 * @param location
	 * @param institution
	 * @param callback
	 */
	public void getDicomMailRecipientsRecipient(String location,
			String institution, AsyncCallback<String> callback);

}
