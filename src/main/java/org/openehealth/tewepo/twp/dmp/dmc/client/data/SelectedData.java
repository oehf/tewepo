/*
 * Copyright 2012 the original author or authors.
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
package org.openehealth.tewepo.twp.dmp.dmc.client.data;

import java.util.Vector;

/**
 * An object of this class represents the selected data, in this case
 * recipients' addresses.
 * 
 * @author devmis
 * 
 */
public class SelectedData {

	private static SelectedData instance = null;

	/**
	 * Singleton-Pattern
	 * 
	 * @return an instance of the class
	 */
	public static SelectedData getInstance() {
		if (instance == null) {
			instance = new SelectedData();
		}
		return instance;
	}

	private Vector<String> addresses = new Vector<String>();

	/**
	 * Sets the adresses of the recipients.
	 * 
	 * @param addresses
	 */
	public void setAddresses(Vector<String> add) {
		addresses = add;
	}

	/**
	 * Gets the addresses of the recipients.
	 * 
	 * @return addresses
	 */
	public Vector<String> getAddresses() {
		return addresses;
	}

}