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

import org.openehealth.twp.tewepo.businesslogic.Mail;

/**
 * Test class for sending an e-mail.
 * 
 * @author Benjamin Schneider
 * 
 */
public class TestMail {

	public static void main(String[] args) {

		
		String recipientsAddress = "max.mustermanntest.de";
		String subject = "Test";
		String text = "Das ist eine neue Testnachricht mit voreingestellten Einstellungen!";
		// String smtpHost = "smtp.test.de";

		try {
			new Mail().sendMail(recipientsAddress, subject, text);
		} catch (Exception e) {

			e.printStackTrace();
		}

		System.out.println("Ende");
	}

}
