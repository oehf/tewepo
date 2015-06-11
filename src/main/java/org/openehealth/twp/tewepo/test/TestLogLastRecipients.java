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
package org.openehealth.twp.tewepo.test;

import org.openehealth.tewepo.twp.dmp.dmc.server.email.xml.DicomMailRecipient;
import org.openehealth.twp.tewepo.businesslogic.BusinesslogicException;
import org.openehealth.twp.tewepo.businesslogic.LogLastRecipients;
import org.openehealth.twp.tewepo.businesslogic.Person;
import org.openehealth.twp.tewepo.database.DatabaseException;


/**
 * Tests a log with last recipients. {@link LogLastRecipients}
 * 
 * @author Benjamin Schneider
 * 
 */
public class TestLogLastRecipients {

	public static void main(String[] args) {
		System.out.println("Start des Tests:");

		TestLogLastRecipients testLogLastRecipient = new TestLogLastRecipients();

		testLogLastRecipient.saveLogLastRecipients();

		System.out.println("Ende des Tests!");

	}

	/**
	 * Tests to save log with last chosen recipients.
	 */
	private void saveLogLastRecipients() {

		DicomMailRecipient dicomMailRecipient = new DicomMailRecipient();
		dicomMailRecipient.setInstitution("Testunternehmen");
		dicomMailRecipient.setLocation("Teststadt");
		dicomMailRecipient.setMailAddress1("test@test.de");
		dicomMailRecipient.setPublicKeyId("1B10D61A");
		dicomMailRecipient.setRecipient("Mustermann");
		try {
			new LogLastRecipients(Person.getPerson(5), dicomMailRecipient)
					.save();
		} catch (DatabaseException e) {

			e.printStackTrace();
		} catch (BusinesslogicException e) {

			e.printStackTrace();
		}
	}
}
