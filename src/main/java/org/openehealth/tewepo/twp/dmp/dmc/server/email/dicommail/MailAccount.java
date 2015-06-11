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
package org.openehealth.tewepo.twp.dmp.dmc.server.email.dicommail;

import java.security.InvalidParameterException;

/**
 * An object of this class represents a mail account.
 * 
 * @author Benjamin Schneider
 * 
 */
public class MailAccount {
	private String username = "someone@test.de";
	private String password = "test";
	private String smtpHost = "smtp.test.de";

	/**
	 * Creates a new mail account with all required parameters.
	 * 
	 * @param username
	 *            ; must not be null and ""
	 * @param password
	 *            ; must not be null and ""
	 * @param smtpHost
	 *            must not be null and ""
	 * 
	 * @throws InvalidParameterException
	 *             , if parameters null or ""
	 */
	public MailAccount(String username, String password, String smtpHost) {
		/*
		 * check parameters
		 */
		if (!(username != null && !username.equals("") && password != null
				&& !password.equals("") && smtpHost != null && !smtpHost
				.equals(""))) {
			throw new InvalidParameterException();
		}
		this.username = username;
		this.password = password;
		this.smtpHost = smtpHost;
	}

	/**
	 * Gets the SMTP Host's name.
	 * 
	 * @return SMTP host
	 */
	public String getSmtpHost() {
		return smtpHost;
	}

	/**
	 * Gets the username.
	 * 
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Gets the password.
	 * 
	 * @return password
	 */
	public String getPassword() {
		return password;
	}
}
