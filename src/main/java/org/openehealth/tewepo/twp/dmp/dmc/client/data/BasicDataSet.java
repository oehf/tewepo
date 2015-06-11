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
package org.openehealth.tewepo.twp.dmp.dmc.client.data;

import java.io.Serializable;

/**
 * An object of this class represents a basic data set, consisting of those
 * attributes: forename, surname, sex, birth date of the patient; recipient's
 * e-mail address; sender's ID.
 * 
 * @author devmis
 * 
 */
public class BasicDataSet implements Serializable {

	private static final long serialVersionUID = 1L;
	private String forename = "";
	private String surname = "";
	private BasicDataSet.SEX sex = BasicDataSet.SEX.UNKNOWN;
	private String birthdate = null;

	public static enum SEX {
		MALE, FEMALE, UNKNOWN
	};

	private String recipient = "";
	private long senderUserId = -1;

	/**
	 * Default Constructor
	 */
	public BasicDataSet() {
	}

	/**
	 * Creates a new basic data set with all required attributes.
	 * 
	 * @param forename
	 *            first name of the patient
	 * @param surname
	 *            last name of the patient
	 * @param sex
	 *            sex of the patient
	 * @param birthdate
	 *            birth date of the patient
	 * @param recipient
	 *            e-mail address of the recipient
	 * @param senderUserId
	 *            user ID of the sender
	 */
	public BasicDataSet(String forename, String surname, BasicDataSet.SEX sex,
			String birthdate, String recipient, long senderUserId) {
		super();
		this.forename = forename;
		this.surname = surname;
		this.sex = sex;
		this.birthdate = birthdate;
		this.recipient = recipient;
		this.senderUserId = senderUserId;
	}

	/**
	 * Gets the user ID of the DICOM mail sender.
	 * 
	 * @return senderUserId
	 */
	public long getSenderUserId() {
		return senderUserId;
	}

	/**
	 * Sets the user ID for the DICOM mail sender.
	 * 
	 * @param senderUserId
	 */
	public void setSenderUserId(long senderUserId) {
		this.senderUserId = senderUserId;
	}

	/**
	 * Gets the first name of the patient.
	 * 
	 * @return forename
	 */
	public String getForename() {
		return forename;
	}

	/**
	 * Sets the first name of the patient.
	 * 
	 * @param forename
	 */
	public void setForename(String forename) {
		this.forename = forename;
	}

	/**
	 * Gets the last name of the patient.
	 * 
	 * @return surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * Sets the last name of the patient.
	 * 
	 * @param surname
	 */
	public void setSurename(String surname) {
		this.surname = surname;
	}

	/**
	 * Gets the sex of the patient.
	 * 
	 * @return sex
	 */
	public BasicDataSet.SEX getSex() {
		return this.sex;
	}

	/**
	 * Sets the sex of the patient.
	 * 
	 * @param sex
	 */
	public void setSex(BasicDataSet.SEX sex) {
		this.sex = sex;
	}

	/**
	 * Gets the birth date of the patient.
	 * 
	 * @return birthdate
	 */
	public String getBirthdate() {
		return birthdate;
	}

	/**
	 * Sets the birth date of the patient.
	 * 
	 * @param birthdate
	 */
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	/**
	 * Gets the e-mail address of the recipient.
	 * 
	 * @return recipient
	 */
	public String getRecipient() {
		return recipient;
	}

	/**
	 * Sets the e-mail address of the recipient.
	 * 
	 * @param recipient
	 */
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

}
