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

import org.openehealth.tewepo.twp.dmp.dmc.server.email.xml.DicomMailRecipient;
import org.openehealth.twp.tewepo.database.DatabaseException;


/**
 * Describes the last recipients' list.
 * 
 * @author Benjamin Schneider
 * 
 */
public interface ILogLastRecipients extends PersistentObject {

	/**
	 * Sets the user, last recipients will be showed for.
	 * 
	 * @param person
	 * 
	 * @throws BusinesslogicException
	 *             {@link BusinesslogicException}
	 */
	public abstract void setPerson(Person person) throws BusinesslogicException;

	/**
	 * Gets the user, last recipients will be showed for.
	 * 
	 * @return user
	 */
	public abstract IPerson getPerson();

	/**
	 * Sets the DICOM mail recipient.
	 * 
	 * @param dicomMailRecipient
	 *            {@link DicomMailRecipient}
	 * 
	 * @throws BusinesslogicException
	 *             {@link BusinesslogicException}
	 */
	public abstract void setDicomMailRecipient(
			DicomMailRecipient dicomMailRecipient)
			throws BusinesslogicException;

	/**
	 * Sets the date.
	 * 
	 * @param date
	 * 
	 * @throws BusinesslogicException
	 *             {@link BusinesslogicException}
	 */
	public abstract void setDate(Date date) throws BusinesslogicException;

	/**
	 * Gets the date.
	 * 
	 * @return the date
	 */
	public abstract Date getDate();

	/**
	 * Gets the location of the recipient.
	 * 
	 * @return the location
	 */
	public abstract String getRecipientsLocation();

	/**
	 * Gets the institution of the recipient.
	 * 
	 * @return the institution
	 */
	public abstract String getRecipientsInstitution();

	/**
	 * Gets the recipient of the recipient.
	 * 
	 * @return the recipient
	 */
	public abstract String getRecipientsRecipient();

	/**
	 * Gets the publickey ID of the recipient.
	 * 
	 * @return the public key
	 */
	public abstract String getRecipientsPublicKeyId();

	/**
	 * Gets the first e-mail address of the recipient.
	 * 
	 * @return the first e-mail address
	 */
	public abstract String getRecipientsMailAddress1();

	/**
	 * Gets the second e-mail address of the recipient.
	 * 
	 * @return the second e-mail address
	 */
	public abstract String getRecipientsMailAddress2();

	/**
	 * Gets the third e-mail address of the recipient.
	 * 
	 * @return the third e-mail address
	 */
	public abstract String getRecipientsMailAddress3();

	/**
	 * Stores the log entry in the database by calling the relevant
	 * database-method.
	 * 
	 * @return ID if the log
	 * 
	 * @throws DatabaseException
	 *             {@link DatabaseException}
	 */
	public abstract long save() throws DatabaseException;

}
