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
import javax.persistence.OneToOne;

/**
 * 
 * {@link IActivationDatabaseEntry}
 * 
 * @author Benjamin Schneider
 * 
 */
@Entity
@javax.persistence.Table(name = "activationdatabaseentry")
public class ActivationDatabaseEntry extends AbstractPersistentObject implements
		IActivationDatabaseEntry {

	private String activationCode;

	@OneToOne(targetEntity = Person.class, cascade = {})
	private IPerson person;

	/**
	 * {@inheritDoc}
	 */
	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setPerson(IPerson person) {
		this.person = person;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getActivationCode() {
		return activationCode;
	}

	/**
	 * {@inheritDoc}
	 */
	public IPerson getPerson() {
		return person;
	}

}
