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

import javax.persistence.Entity;

import org.openehealth.twp.tewepo.database.DatabaseException;
import org.openehealth.twp.tewepo.database.PersistenceService;
import org.openehealth.twp.tewepo.helper.Utility;


/**
 * {@link IAddress}
 * 
 * @author Benjamin Schneider
 * 
 */
@Entity
@javax.persistence.Table(name = "address")
public class Address extends AbstractPersistentObject implements IAddress {


	private String street;

	private String number;

	private String zipcode;

	private String location;
	/**
	 * this flag shows if the object was changed since the last save
	 */
	private boolean isChanged;

	/**
	 * Constructor with the ID. This will only be called from the database (if
	 * the object already exists), because the changed-flag is not set here.
	 * 
	 * 
	 * @param id
	 * @param street
	 * @param number
	 * @param zipcode
	 * @param location
	 * 
	 * @throws BusinesslogicException
	 *             {@link BusinesslogicException}
	 */
	public Address(int id, String street, String number, String zipcode,
			String location) throws BusinesslogicException {

		this.setStreet(street);
		this.setNumber(number);
		this.setZipcode(zipcode);
		this.setLocation(location);
		this.setChanged(false);
	}

	/**
	 * Constructor without ID. This will be called when a new address object is
	 * created.
	 * 
	 * @param street
	 * @param number
	 * @param zipcode
	 * @param location
	 * 
	 * @throws BusinesslogicException
	 *             {@link BusinesslogicException}
	 */
	public Address(String street, String number, String zipcode, String location)
			throws BusinesslogicException {

		this.setStreet(street);
		this.setNumber(number);
		this.setZipcode(zipcode);
		this.setLocation(location);
		this.setChanged(true);
	}

	/**
	 * Default constructor
	 */
	public Address() {

	}

	/**
	 * {@inheritDoc}
	 */
	public long save() throws DatabaseException {

		long neuID = PersistenceService.getService().store(this);

		this.setChanged(false);
		return neuID;

	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isChanged() {
		return isChanged;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setChanged(boolean isChanged) {
		this.isChanged = isChanged;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * {@inheritDoc} and validates the input.
	 */
	public void setStreet(String street) throws BusinesslogicException {
		if (Utility.isRightAddressString(street)) {
			this.street = street;
			this.setChanged(true); // set true  if data change about setter
			//  is needed for the DB update
		} else
			throw new BusinesslogicException(
					"Ungültige Straße. Die Bezeichnung muss aus min. 3 Zeichen bestehen, Sonderzeichen sind nicht zugelassen.");
	}

	/**
	 * {@inheritDoc}
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * {@inheritDoc} and validates the input.
	 */
	public void setNumber(String number) throws BusinesslogicException {
		this.number = number;
		this.setChanged(true); // set true  if data change about setter
		//  is needed for the DB update
	}

	/**
	 * {@inheritDoc}
	 */
	public String getZipcode() {
		return zipcode;
	}

	/**
	 * {@inheritDoc} and validates the input.
	 */
	public void setZipcode(String zipcode) throws BusinesslogicException {
		if (Utility.isRightZipcode(zipcode)) {
			this.zipcode = zipcode;
			this.setChanged(true); // set true  if data change about setter
			//  is needed for the DB update
		} else
			throw new BusinesslogicException(
					"Ungültige Postleitzahl. Die Postleitzahl muss aus genau 5 Ziffern bestehen.");

	}

	/**
	 * {@inheritDoc}
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * {@inheritDoc} and validates the input.
	 */
	public void setLocation(String location) throws BusinesslogicException {
		if (Utility.isRightAddressString(location)) {
			this.location = location;
			this.setChanged(true); // set true  if data change about setter
			//  is needed for the DB update
		} else
			throw new BusinesslogicException(
					"Ungültiger Ort. Die Bezeichnung muss aus min. 3 Zeichen bestehen, Sonderzeichen sind nicht zugelassen.");
	}

	/**
	 * {@inheritDoc}
	 */
	public void delete() throws DatabaseException {

		// DBAddress.deleteAddress(getId());

	}

}
