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
package org.openehealth.tewepo.twp.dmp.dmc.server.email.xml;

/**
 * This class represents a DICOM e-mail recipient with those attributes: ID,
 * location, institution, recipient, publicKeyID, first mail address, second
 * mail address, third mail address
 * 
 * @author Benjamin Schneider
 * 
 */
public class DicomMailRecipient {

	private String id = "";
	private String location = "";
	private String institution = "";
	private String recipient = "";
	private String publicKeyId = "";
	private String mailAddress1 = "";
	private String mailAddress2 = "";
	private String mailAddress3 = "";
	private boolean recipientedited = false;

	/**
	 * Checks if the recipient is edited.
	 * 
	 * @return true, if recipient is edited false, else
	 */
	public boolean isRecipientedited() {
		return recipientedited;
	}

	/**
	 * Sets recipient edited.
	 * 
	 * @param recipientedited
	 */
	public void setRecipientedited(boolean recipientedited) {
		this.recipientedited = recipientedited;
	}

	/**
	 * Gets the unique ID of the DICOM e-mail recipient.
	 * 
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets a unique ID for the new DICOM e-mail recipient.
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the location of the DICOM e-mail recipient.
	 * 
	 * @return location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Sets the location of the DICOM e-mail recipient.
	 * 
	 * @param location
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * Gets the institution of the DICOM e-mail recipient.
	 * 
	 * @return institution
	 */
	public String getInstitution() {
		return institution;
	}

	/**
	 * Sets the institution of the DICOM e-mail recipient.
	 * 
	 * @param institution
	 */
	public void setInstitution(String institution) {
		this.institution = institution;
	}

	/**
	 * Gets the recipient of the DICOM e-mail recipient.
	 * 
	 * @return recipient
	 */
	public String getRecipient() {
		return recipient;
	}

	/**
	 * Sets the recipient of the DICOM e-mail recipient.
	 * 
	 * @param recipient
	 */
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	/**
	 * Gets the generated publicKeyID of the DICOM e-mail recipient.
	 * 
	 * @return publicKeyID
	 */
	public String getPublicKeyId() {
		return publicKeyId;
	}

	/**
	 * Sets the publicKeyID generated for the new DICOM e-mail recipient.
	 * 
	 * @param publicKeyID
	 */
	public void setPublicKeyId(String publicKeyId) {
		this.publicKeyId = publicKeyId;
	}

	/**
	 * Gets the first e-mail address of the DICOM e-mail recipient.
	 * 
	 * @return mailAdress1
	 */
	public String getMailAddress1() {
		return mailAddress1;
	}

	/**
	 * Sets the first e-mail address of the new DICOM e-mail recipient.
	 * 
	 * @param mailAdress1
	 */
	public void setMailAddress1(String mailAddress1) {
		this.mailAddress1 = mailAddress1;
	}

	/**
	 * Gets the second e-mail address of the DICOM e-mail recipient.
	 * 
	 * @return mailAdress2
	 */
	public String getMailAddress2() {
		return mailAddress2;
	}

	/**
	 * Sets the second e-mail address of the new DICOM e-mail recipient.
	 * 
	 * @param mailAdress2
	 */
	public void setMailAddress2(String mailAddress2) {
		this.mailAddress2 = mailAddress2;
	}

	/**
	 * Gets the third e-mail address of the DICOM e-mail recipient.
	 * 
	 * @return mailAdress3
	 */
	public String getMailAddress3() {
		return mailAddress3;
	}

	/**
	 * Sets the third e-mail address of the new DICOM e-mail recipient.
	 * 
	 * @param mailAdress3
	 */
	public void setMailAddress3(String mailAddress3) {
		this.mailAddress3 = mailAddress3;
	}
}
