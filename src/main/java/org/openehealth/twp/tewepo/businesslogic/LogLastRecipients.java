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
package org.openehealth.twp.tewepo.businesslogic;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.apache.log4j.Logger;
import org.openehealth.tewepo.twp.dmp.dmc.server.email.xml.DicomMailRecipient;
import org.openehealth.twp.tewepo.database.DatabaseException;
import org.openehealth.twp.tewepo.database.PersistenceService;


/**
 * {@link ILogLastRecipients}
 * 
 * 
 */
@Entity
@javax.persistence.Table(name = "log_lastrecipients")
public class LogLastRecipients extends AbstractPersistentObject implements
		ILogLastRecipients {

	private static Logger logger = Logger.getLogger("webportal");
	/** User/Person */
	@OneToOne(targetEntity = Person.class)
	private IPerson person = null;
	private Date date = new Date();
	private String recipientsLocation = "";
	private String recipientsInstitution = "";
	private String recipientsRecipient = "";
	private String recipientsPublicKeyId = "";
	private String recipientsMailAddress1 = "";
	private String recipientsMailAddress2 = "";

	private String recipientsMailAddress3 = "";

	/**
	 * Creates a new log for the last recipients.
	 * 
	 * @param person
	 *            logged user. Must not be null.
	 * @param dicomMailRecipient
	 *            last selected DICOM mail recipient. Must not be null.
	 *            
	 * @throws BusinesslogicException
	 *             {@link BusinesslogicException}
	 */
	public LogLastRecipients(IPerson person,
			DicomMailRecipient dicomMailRecipient)
			throws BusinesslogicException {
		if (person != null && dicomMailRecipient != null) {
			this.person = person;
			this.setDicomMailRecipient(dicomMailRecipient);
			this.date = new Date();
		} else {
			String message = "LogLastRecipients.class - Constructur: person and dicomMailRecipient must not be null!";
			logger.error(message);
			new BusinesslogicException(message);
		}
	}

	/**
	 * Use, if there's an already existing log with last recipients.
	 */
	public LogLastRecipients() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	public Date getDate() {

		return this.date;
	}

	/**
	 * {@inheritDoc}
	 */
	public IPerson getPerson() {

		return this.person;
	}

	/**
	 * {@inheritDoc}
	 */
	public long save() throws DatabaseException {
		long neuID = PersistenceService.getService().store(this);
		return neuID;
	}

	/**
	 * {@inheritDoc} and validates the input.
	 */
	public void setDate(Date date) throws BusinesslogicException {
		if (date.before(new Date())) {
			this.date = date;
		} else {
			String message = "LogLastRecipients.class - The date must be in past!";
			logger.error(message);
			throw new BusinesslogicException(message);
		}
	}

	/**
	 * {@inheritDoc}, if not null.
	 */
	public void setDicomMailRecipient(DicomMailRecipient dicomMailRecipient)
			throws BusinesslogicException {

		if (dicomMailRecipient != null) {
			this.recipientsLocation = dicomMailRecipient.getLocation();
			this.recipientsInstitution = dicomMailRecipient.getInstitution();
			this.recipientsRecipient = dicomMailRecipient.getRecipient();
			this.recipientsPublicKeyId = dicomMailRecipient.getPublicKeyId();
			this.recipientsMailAddress1 = dicomMailRecipient.getMailAddress1();
			this.recipientsMailAddress2 = dicomMailRecipient.getMailAddress2();
			this.recipientsMailAddress3 = dicomMailRecipient.getMailAddress3();
		} else {
			String message = "LogLastRecipients.class - DicomMailRecipient must not be null!";
			logger.error(message);
			throw new BusinesslogicException(message);
		}
	}

	/**
	 * {@inheritDoc}, if not null.
	 */
	public void setPerson(Person person) throws BusinesslogicException {

		if (person != null)
			this.person = person;
		else {
			String message = "LogLastRecipients.class - Person must not be null!";
			logger.error(message);
			throw new BusinesslogicException(message);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public String getRecipientsLocation() {
		return recipientsLocation;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setRecipientsLocation(String recipientsLocation) {
		this.recipientsLocation = recipientsLocation;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getRecipientsInstitution() {
		return recipientsInstitution;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setRecipientsInstitution(String recipientsInstitution) {
		this.recipientsInstitution = recipientsInstitution;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getRecipientsRecipient() {
		return recipientsRecipient;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setRecipientsRecipient(String recipientsRecipient) {
		this.recipientsRecipient = recipientsRecipient;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getRecipientsPublicKeyId() {
		return recipientsPublicKeyId;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setRecipientsPublicKeyId(String recipientsPublicKeyId) {
		this.recipientsPublicKeyId = recipientsPublicKeyId;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getRecipientsMailAddress1() {
		return recipientsMailAddress1;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setRecipientsMailAddress1(String recipientsMailAddress1) {
		this.recipientsMailAddress1 = recipientsMailAddress1;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getRecipientsMailAddress2() {
		return recipientsMailAddress2;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setRecipientsMailAddress2(String recipientsMailAddress2) {
		this.recipientsMailAddress2 = recipientsMailAddress2;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getRecipientsMailAddress3() {
		return recipientsMailAddress3;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setRecipientsMailAddress3(String recipientsMailAddress3) {
		this.recipientsMailAddress3 = recipientsMailAddress3;
	}

	/**
	 * Searches for the last n log entries of the last recipient for the
	 * referred user from the database.
	 * 
	 * @param userId
	 *            ID of the user
	 * 
	 * @return log list or null, if not found.
	 * 
	 * @throws DatabaseException
	 *             {@link DatabaseException}
	 */
	public static List<ILogLastRecipients> getLogLastRecipients(String userId)
			throws DatabaseException {

		return PersistenceService.getService().getLogLastRecipients(userId);

	}

}
