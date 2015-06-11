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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

/**
 * 
 * Provides annotaded fields for an objectID and a version number. Every entity
 * object will extend this class.
 * 
 * @version 1.0 12.11.2008
 * @author Markus Birkle
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractPersistentObject implements PersistentObject {

	@Id
	@TableGenerator(name = "puma_gen", table = "primary_keys")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "puma_gen")
	@Column(name = "id")
	protected Long objectID = new Long(-1);
	@Version
	private int version;

	/**
	 * Gets the Object ID
	 */
	public long getObjectID() {
		return objectID;
	}

	/**
	 * Sets the Object ID
	 * @param objectID
	 */
	public void setObjectID(long objectID) {
		this.objectID = objectID;
	}

	/**
	 * Gets the version
	 */
	public final int getVersion() {
		return version;
	}
}
