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

import org.openehealth.twp.tewepo.database.DatabaseException;

/**
 * Describes the address of a user.
 * 
 * @author Benjamin Schneider
 * 
 */
public interface IAddress extends PersistentObject {

	/**
	 * This method stores the address in the database by calling the relevant
	 * database method
	 * 
	 * @return ID of the address
	 * @throws DatabaseException
	 *             {@link DatabaseException}
	 */
	public abstract long save() throws DatabaseException;

	/**
	 * This method checks if the object is changed. If it's changed, it must be
	 * stored in the database.
	 * 
	 * @return true, if changed false, else
	 */
	public abstract boolean isChanged();

	/**
	 * This method sets a changed flag relevant to the given parameter.
	 * 
	 * @param isChanged
	 */
	public abstract void setChanged(boolean isChanged);

	/**
	 * Gets the street of the address object.
	 * 
	 * @return street
	 */
	public abstract String getStreet();

	/**
	 * Sets the street to the address object.
	 * 
	 * @param street
	 * 
	 * @throws {@link BusinesslogicException}
	 */
	public abstract void setStreet(String street) throws BusinesslogicException;

	/**
	 * Gets the street number of the address object.
	 * 
	 * @return street number
	 */
	public abstract String getNumber();

	/**
	 * Sets the street number to the address object.
	 * 
	 * @param number
	 * 
	 * @throws {@link BusinesslogicException}
	 */
	public abstract void setNumber(String number) throws BusinesslogicException;

	/**
	 * Gets the zipcode of the address object.
	 * 
	 * @return zipcode
	 */
	public abstract String getZipcode();

	/**
	 * Sets the zipcode to the address object.
	 * 
	 * @param zipcode
	 * 
	 * @throws {@link BusinesslogicException}
	 */
	public abstract void setZipcode(String zipcode)
			throws BusinesslogicException;

	/**
	 * Gets the location of the address.
	 * 
	 * @return location
	 */
	public abstract String getLocation();

	/**
	 * Sets the location to the address.
	 * 
	 * @param location
	 * 
	 * @throws {@link BusinesslogicException}
	 */
	public abstract void setLocation(String location)
			throws BusinesslogicException;

	/**
	 * Deletes the address.
	 * 
	 * @throws DatabaseException
	 *             {@link DatabaseException}
	 */
	public abstract void delete() throws DatabaseException;

}