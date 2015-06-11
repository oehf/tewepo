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
 * An object of this class represents the mail receiver.
 * 
 * @author Benjamin Schneider
 * 
 */
public class MailReceiver {

	String senderAddress =  "someone@test.de";
	private MailAccount mailAccount = null;
	String recipientsAddress = "somereceiver@test.de";
	String subject = "DICOM-eMail";


	/**
	 * Creates a new mail receiver with all required attributes.
	 * 
	 * @param senderAddress
	 *            ; e-mail address of the sender. Must not be null and "".
	 * @param mailAccount
	 *            ; mail account {@link MailAccount}. Must not be null.
	 * @param recipientsAddress
	 *            e-mail address of the recipient. Must not be null and "".
	 * @param subject
	 *            subject of the e-mail. Must not be null and "".
	 */
	public MailReceiver(String senderAddress, MailAccount mailAccount,
			String recipientsAddress, String subject) {
		if (!(senderAddress != null && !senderAddress.equals("")
				&& mailAccount != null && recipientsAddress != null
				&& !recipientsAddress.equals("") && subject != null && !subject
				.equals(""))) {
			throw new InvalidParameterException();
		}

		this.senderAddress = senderAddress;
		this.mailAccount = mailAccount;
		this.recipientsAddress = recipientsAddress;
		this.subject = subject;
	}

	/**
	 * Gets the mail account.
	 * 
	 * @return mail account {@link MailAccount}
	 */
	public MailAccount getMailAccount() {
		return mailAccount;
	}

	/**
	 * Gets the e-mail address of the sender.
	 * 
	 * @return e-mail address of the sender.
	 */
	public String getSenderAddress() {
		return senderAddress;
	}

	/**
	 * Gets the e-mail address of the recipient.
	 * 
	 * @return e-mail address of the recipient
	 */
	public String getRecipientsAddress() {
		return recipientsAddress;
	}

	/**
	 * Gets the subject of the e-mail.
	 * 
	 * @return subject
	 */
	public String getSubject() {
		return subject;
	}

}
