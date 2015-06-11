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
package org.openehealth.twp.tewepo.configuration;

import org.openehealth.tewepo.twp.dmp.dmc.server.email.xml.DicomMailRecipient;
import org.openehealth.twp.tewepo.businesslogic.BusinesslogicException;
import org.openehealth.twp.tewepo.businesslogic.ILogLastRecipients;
import org.openehealth.twp.tewepo.businesslogic.IPerson;
import org.openehealth.twp.tewepo.businesslogic.IRole;
import org.openehealth.twp.tewepo.businesslogic.LogLastRecipients;
import org.openehealth.twp.tewepo.businesslogic.Person;
import org.openehealth.twp.tewepo.businesslogic.Role;
import org.openehealth.twp.tewepo.businesslogic.Role.Roles;
import org.openehealth.twp.tewepo.database.DatabaseException;
import org.openehealth.twp.tewepo.database.PersistenceService;
import org.openehealth.twp.tewepo.helper.BCrypt;


/**
 * Initial configuration for the webportal.
 * 
 * @author Benjamin Schneider
 * 
 */
public class InitialConfiguration {

	/**
	 * Initializes the database; test entry.
	 */
	public static void initializeDatabase() {
		try {
			IRole roleAdmin = new Role(Roles.ADMINISTRATOR,
					"Systemadministrator");
			IRole rolePhysician = new Role(Roles.PHYSICIAN, "Arzt");
			IRole rolePatient = new Role(Roles.PATIENT, "Patient");

			PersistenceService.getService().store(roleAdmin);
			PersistenceService.getService().store(rolePhysician);
			PersistenceService.getService().store(rolePatient);
			IRole tmpRole = PersistenceService.getService().getRole(
					Roles.ADMINISTRATOR);
			IPerson sysadmin = new Person("sysadmin", tmpRole, BCrypt.hashpw(
					"123456", BCrypt.gensalt()),
					"max.mustermann@test.de", "Max",
					"Mustermann", "Testunternehmen",
					"Abteilung Test",
					"Medizininformatiker", "Teststraße", "15", "12345",
					"Teststadt");
			sysadmin.setAccountActive(true);

			long personId = PersistenceService.getService().store(sysadmin);

			DicomMailRecipient dicomMailRecipient = new DicomMailRecipient();
			dicomMailRecipient.setInstitution("Testunternehmen");
			dicomMailRecipient.setLocation("Teststadt");
			dicomMailRecipient.setMailAddress1("test@test.de");
			dicomMailRecipient.setPublicKeyId("1B10D61A");
			dicomMailRecipient.setRecipient("Mustermann");

			ILogLastRecipients logLastRecipients = new LogLastRecipients(Person
					.getPerson(personId), dicomMailRecipient);
			logLastRecipients.save();

		} catch (DatabaseException e) {

			e.printStackTrace();
		} catch (BusinesslogicException e) {

			e.printStackTrace();
		}
	}

}
