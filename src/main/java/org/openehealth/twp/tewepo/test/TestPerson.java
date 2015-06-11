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

import java.util.List;

import org.apache.log4j.Logger;
import org.openehealth.twp.tewepo.businesslogic.BusinesslogicException;
import org.openehealth.twp.tewepo.businesslogic.IPerson;
import org.openehealth.twp.tewepo.businesslogic.Person;
import org.openehealth.twp.tewepo.businesslogic.Role;
import org.openehealth.twp.tewepo.businesslogic.Role.Roles;
import org.openehealth.twp.tewepo.database.DatabaseException;
import org.openehealth.twp.tewepo.database.PersistenceService;
import org.openehealth.twp.tewepo.helper.BCrypt;


/**
 * Tests a person object. {@link Person}
 * 
 * @author Benjamin Schneider
 * 
 */
public class TestPerson {

	private static Logger logger = Logger.getLogger("webportal");

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Start des Tests:");

		TestPerson testperson = new TestPerson();
		// testperson.saveNewPerson();

		testperson.loadExistingPerson();

		System.out.println("Ende des Tests!");

	}

	/**
	 * Test to save a new person in the database.
	 */
	private void saveNewPerson() {

		System.out.println("Start Save new Patient...");

		try {
			IPerson p1 = new Person("admin", new Role(Roles.ADMINISTRATOR,
					"Hauptadministrator"), "Passwort", "te@te.te", "Vorname",
					"Nachname", "Organisation", "Abteilung", "Fachrichtung",
					"Strasse", "3e", "54321", "Ort");

			p1.save();
		} catch (BusinesslogicException e) {

			e.printStackTrace();

		} catch (DatabaseException e) {

			e.printStackTrace();
		}

		System.out.println("New Patient saved!!");
	}

	/**
	 * Test to load an existing person from the database.
	 */
	private void loadExistingPerson() {

		System.out.println("Start Load existing Patient...");

		try {
			IPerson p1 = new Person("admin", new Role(Roles.ADMINISTRATOR,
					"Hauptadministrator"), BCrypt.hashpw("admin", BCrypt
					.gensalt()), "te@te.te", "Vorname", "Nachname",
					"Organisation", "Abteilung", "Fachrichtung", "Strasse",
					"3e", "54321", "Ort");

			List<IPerson> possiblePersons = PersistenceService.getService()
					.getPersonsFromDB("admin");
			IPerson p2 = null;
			for (IPerson p : possiblePersons) {
				if (BCrypt.checkpw("admin", p.getPassword()))
					p2 = p;
			}

			// Person p2=Person.getPerson(3);

			System.out.println("Person p1: " + p1.toString());
			if (p2 != null)
				System.out.println("Person p2: " + p2.toString());
			else
				System.out.println("Person p2 nicht geladen.");
			p1.save();
		} catch (BusinesslogicException e) {

			e.printStackTrace();

		} catch (DatabaseException e) {

			e.printStackTrace();
		}

		System.out.println("Patient loaded!!");
	}
}
