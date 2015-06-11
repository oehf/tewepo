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
package org.openehealth.twp.tewepo.database;

/**
 * Exceptions for exceptional situations from the database
 * 
 * @author Felix Fischer
 * @version $Id: DatenbankException.java 1697 2007-03-13 12:07:57Z crabe $
 */
@SuppressWarnings("serial")
public class DatabaseException extends Exception {

	/**
	 * Creates a database exception with error text 
	 * 
	 * @param errordescription
	 *            Fehlertext
	 */
	public DatabaseException(String errordescription) {
		super(errordescription);
	}
}
