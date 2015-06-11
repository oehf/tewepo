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

/**
 * This class is for handling exceptions. It will be thrown, if an invalid value
 * is given. (f.e. in constructors or in set-methods)
 * 
 * @author Benjamin Schneider
 * 
 */
@SuppressWarnings("serial")
public class InvalidValueException extends BusinesslogicException {

	/**
	 * Creates a new invalid value exception object with the given error message
	 * 
	 * @param error
	 *            message the message of the exception
	 */
	public InvalidValueException(String fehlertext) {
		super(fehlertext);
	}

}
