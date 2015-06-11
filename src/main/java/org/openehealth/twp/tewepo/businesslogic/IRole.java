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

import org.openehealth.twp.tewepo.businesslogic.Role.Roles;

/**
 * Describes roles for a user in the webportal.
 * 
 * @author Benjamin Schneider
 * 
 */
public interface IRole extends PersistentObject {

	/**
	 * Gets the current set role.
	 * 
	 * @return Enum-Element
	 */
	public abstract Roles getRole();

	/**
	 * Sets the current role.
	 * 
	 * @param role
	 *            Enum-Element
	 */
	public abstract void setRole(Roles role);

	/**
	 * Sets the current role.
	 * 
	 * @param role
	 *            String
	 */
	public abstract void setRole(String role);

	// /**
	// * returns  ID
	// *
	// * @return
	// */
	// public abstract int getId();
	//
	// /**
	// * set ID
	// *
	// * @param id
	// */
	// public abstract void setId(int id);

	/**
	 * Gets the description.
	 * 
	 * @return description
	 */
	public abstract String getDescription();

	/**
	 * Sets the description.
	 * 
	 * @param description
	 */
	public abstract void setDescription(String description);

	/**
	 * Overrides the toString()-method.
	 */
	public abstract String toString();

}