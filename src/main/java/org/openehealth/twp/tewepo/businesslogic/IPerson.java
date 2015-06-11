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

import java.util.List;

import org.openehealth.twp.tewepo.businesslogic.Role.Roles;
import org.openehealth.twp.tewepo.database.DatabaseException;


/**
 * Describes a person in the system (webportal).
 * 
 * @author Benjamin Schneider
 * 
 */
public interface IPerson extends PersistentObject {

	/**
	 * Checks if the person is a doctor or an administrator.
	 * 
	 * @return true, if Doctor/Administrator false, else
	 */
	public abstract boolean isProfessional();

	/**
	 * Clears the fields of a professional user, if f.e. it's role is changed
	 * from doctor to patient.
	 */
	public abstract void clearProfessionalFields();

	/**
	 * Sends an e-mail to the user with a temporary password, if requested.
	 */
	public abstract void sendPasswordRequestMail();

	
	/**
	 * Sends an e-mail to the user with the username, if requested.
	 */
	public abstract void sendUsernameRequestMail();

	
	/**
	 * Sends an e-mail to the user with an activation link which has to be
	 * clicked to activate the registered account.
	 */
	public abstract void sendAcivationEMail();

	/**
	 * Fuegt der Person eine weitere Rolle hinzu. Adds a person (more) role(s).
	 * 
	 * @param role
	 *            another role
	 * 
	 * @throws BusinesslogicException
	 *             {@link BusinesslogicException}
	 */
	public abstract void addRole(Roles role) throws BusinesslogicException;

	/**
	 * Sets the e-mail address of the person.
	 * 
	 * @param emailaddress
	 *            new e-mail address
	 * 
	 * @throws BusinesslogicException
	 *             {@link BusinesslogicException}
	 */
	public abstract void setEmailaddress(String emailaddress)
			throws BusinesslogicException;

	/**
	 * Sets the title of the person.
	 * 
	 * @param title
	 *            new title
	 * 
	 * @throws BusinesslogicException
	 *             {@link BusinesslogicException}
	 */
	public abstract void setTitle(String title)
			throws BusinesslogicException;
	
	
	/**
	 * Sets the forename of the person.
	 * 
	 * @param forename
	 *            new forename
	 * 
	 * @throws BusinesslogicException
	 *             {@link BusinesslogicException}
	 */
	public abstract void setForename(String forename)
			throws BusinesslogicException;

	/**
	 * Sets the surname of the person.
	 * 
	 * @param surname
	 *            new surname
	 * 
	 * @throws BusinesslogicException
	 *             {@link BusinesslogicException}
	 */
	public abstract void setSurname(String surname)
			throws BusinesslogicException;

	/**
	 * Sets the password of the person.
	 * 
	 * @param passwort
	 *            the new password
	 * 
	 * @throws BusinesslogicException
	 *             {@link BusinesslogicException}
	 */
	public abstract void setPassword(String passwort)
			throws BusinesslogicException;

	/**
	 * Sets the loginname of the person.
	 * 
	 * @param loginname
	 * 
	 * @throws BusinesslogicException
	 *             {@link BusinesslogicException}
	 */
	public abstract void setLoginname(String loginname)
			throws BusinesslogicException;

	// /**
	// * defines the Person-ID 
	// *
	// * @param id
	// * Person-ID
	// *
	// * @throws BusinesslogicException
	// * 
	// */
	// public abstract void setId(int id) throws BusinesslogicException;

	/**
	 * Sets the occupation group of the person.
	 * 
	 * @param occupationgroup
	 */
	public abstract void setOccupationgroup(String occupationgroup)
			throws BusinesslogicException;

	/**
	 * Sets the department of the user.
	 * 
	 * @param department
	 * 
	 * @throws BusinesslogicException
	 *             {@link BusinesslogicException}
	 */
	public abstract void setDepartment(String department)
			throws BusinesslogicException;

	/**
	 * Sets the organisation of the user.
	 * 
	 * @param organisation
	 * 
	 * @throws BusinesslogicException
	 *             {@link BusinesslogicException}
	 */
	public abstract void setOrganisation(String organisation)
			throws BusinesslogicException;

	/**
	 * Sets the address of the user.
	 * 
	 * @param address
	 * 
	 * @throws BusinesslogicException
	 *             {@link BusinesslogicException}
	 */
	public abstract void setAddress(IAddress address)
			throws BusinesslogicException;

	/**
	 * Sets the roles of the person.
	 * 
	 * @param roles
	 */
	public abstract void setRoles(List<IRole> roles);

	/**
	 * Checks if account is active.
	 * 
	 * @return true, if active false, else
	 */
	public abstract boolean isAccountActive();

	/**
	 * Sets/Changes account active/inactive
	 * 
	 * @param accountActive
	 */
	public abstract void setAccountActive(boolean accountActive);

	/**
	 * Stores the person in the database by calling the relevant
	 * database-method.
	 * 
	 * @return ID of the person
	 * 
	 * @throws DatabaseException
	 *             {@link DatabaseException}
	 */
	public abstract long save() throws DatabaseException;

	/**
	 * This method is showing a person.
	 * 
	 * @return the person with all attributes
	 */
	public abstract String toString();

	/**
	 * Deletes the given role of the person.
	 * 
	 * @param role
	 *            the role to delete
	 */
	public abstract void removeRole(Roles role);

	/**
	 * Gets the e-mail address of the person
	 * 
	 * @return the e-mail address
	 */
	public abstract String getEmailaddress();

	/**
	 * Gets the title of the person.
	 * 
	 * @return the forename
	 */
	public abstract String getTitle();

	
	/**
	 * Gets the forename of the person.
	 * 
	 * @return the forename
	 */
	public abstract String getForename();

	/**
	 * Gets the surname of the person.
	 * 
	 * @return the surname
	 */
	public abstract String getSurname();

	// /**
	// * returns Id from Person.
	// *
	// * @return Person-ID
	// */
	// public abstract int getId();

	/**
	 * Gets the roles of the person.
	 * 
	 * @return the roles
	 */
	public abstract List<IRole> getRoles();

	/**
	 * Gets the loginname of the person
	 * 
	 * @return loginname of the person
	 */
	public abstract String getLoginname();

	/**
	 * Gets the occupation group of the person
	 * 
	 * @return the occupation group
	 */

	public abstract String getOccupationgroup();

	/**
	 * Gets the department of the person.
	 * 
	 * @return the department
	 */

	public abstract String getDepartment();

	/**
	 * Gets the organisation of the person.
	 * 
	 * @return the organisation
	 */
	public abstract String getOrganisation();

	/**
	 * Gets the address of the person.
	 * 
	 * @return the address
	 */
	public abstract IAddress getAddress();

	/**
	 * Gets the password of the person.
	 * 
	 * @return password
	 */
	public abstract String getPassword();

	// later probably unnecessary

	/**
	 * Gets the ID of the person.
	 * 
	 * @return the ID
	 */
	public abstract long getId();

}