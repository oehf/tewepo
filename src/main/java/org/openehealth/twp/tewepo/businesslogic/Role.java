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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.openehealth.twp.tewepo.database.DatabaseException;
import org.openehealth.twp.tewepo.database.PersistenceService;


/**
 * {@link IRole}
 * 
 * @author Benjamin Schneider
 * 
 */
@Entity
@javax.persistence.Table(name = "role")
public class Role extends AbstractPersistentObject implements IRole {

	public static enum Roles {
		ADMINISTRATOR, PHYSICIAN, PATIENT
	};

	@Enumerated(EnumType.ORDINAL)
	private Roles role;

	private String description;

	// private int id = -1;

	/**
	 * Creates a new role for the user.
	 * 
	 * @param role
	 *            chosen role for the user
	 * @param description
	 *            description of the role
	 */
	public Role(Roles role, String description) {

		this.setRole(role);
		this.setDescription(description);
	}

	/**
	 * Default constructor
	 */
	public Role() {

	}

	/**
	 * Looks for a certain role in the database with the given description.
	 * 
	 * @param role
	 * 
	 * @return found role null, if not found.
	 * 
	 * @throws DatabaseException
	 *             will be thrown, if there's a problem with the database
	 *             access.
	 */
	public static IRole getRole(Roles role) throws DatabaseException {

		if (role.equals(Roles.ADMINISTRATOR))
			return PersistenceService.getService().getRole(Roles.ADMINISTRATOR);
		else if (role.equals(Roles.PHYSICIAN))
			return PersistenceService.getService().getRole(Roles.PHYSICIAN);
		else if (role.equals(Roles.PATIENT))
			return PersistenceService.getService().getRole(Roles.PATIENT);
		else
			return null;

	}

	/**
	 * {@inheritDoc}
	 */
	public Roles getRole() {
		return role;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setRole(Roles role) {
		this.role = role;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setRole(String role) {

		if (role.trim().equalsIgnoreCase("Administrator"))
			this.role = Roles.ADMINISTRATOR;
		else if (role.trim().equalsIgnoreCase("Physician"))
			this.role = Roles.PHYSICIAN;
		else
			this.role = Roles.PATIENT;
	}

	// /* (non-Javadoc)
	// * @see org.openehealth.twp.tewepo.businesslogic.IRole#getId()
	// */
	// public int getId() {
	// return id;
	// }
	//
	// /* (non-Javadoc)
	// * @see org.openehealth.twp.tewepo.businesslogic.IRole#setId(int)
	// */
	// public void setId(int id) {
	// this.id = id;
	// }

	/**
	 * {@inheritDoc}
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (this.role.equals(Roles.ADMINISTRATOR))
			return "Administrator";
		else if (this.role.equals(Roles.PHYSICIAN))
			return "Arzt";
		else if (this.role.equals(Roles.PATIENT))
			return "Patient";
		else
			return "";
	}

}