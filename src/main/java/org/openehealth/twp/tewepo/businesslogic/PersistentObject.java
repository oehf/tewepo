/*
 * Copyright 2009 Manuel Carrasco Moñino. (manuel_carrasco at users.sourceforge.net) 
 * http://code.google.com/p/gwtupload
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.openehealth.twp.tewepo.businesslogic;

/**
 * 
 * Describes the general interface of a persistent object.
 * 
 * @version 1.0 09.11.2008
 * @author Markus Birkle
 */
public interface PersistentObject {

	/**
	 * Returns a numeric value as identifier associatet with an object.
	 * 
	 * @return The primary key associated with an object.
	 */
	public abstract long getObjectID();

	/**
	 * Sets the identifing id of an object.
	 * 
	 * @param objectID
	 *            The objectid as primarykey.
	 */
	public void setObjectID(long objectID);

	/**
	 * Returns a numeric value as version identifier for the object.
	 * 
	 * @return The version identifier.
	 */
	public int getVersion();
}