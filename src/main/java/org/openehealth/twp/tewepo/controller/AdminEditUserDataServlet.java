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
package org.openehealth.twp.tewepo.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.openehealth.twp.tewepo.businesslogic.Address;
import org.openehealth.twp.tewepo.businesslogic.BusinesslogicException;
import org.openehealth.twp.tewepo.businesslogic.IPerson;
import org.openehealth.twp.tewepo.businesslogic.Person;
import org.openehealth.twp.tewepo.businesslogic.Role.Roles;
import org.openehealth.twp.tewepo.database.DatabaseException;
import org.openehealth.twp.tewepo.database.PersistenceService;
import org.openehealth.twp.tewepo.helper.BCrypt;
import org.openehealth.twp.tewepo.helper.SessionGuard;


/**
 * Servlet implementation for the administrator to edit user data.
 * 
 * @author Benjamin Schneider
 * 
 */
@SuppressWarnings("serial")
public class AdminEditUserDataServlet extends HttpServlet {
	private Logger logger = Logger.getLogger(AdminEditUserDataServlet.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * Allows administrator to edit the user data. The changes will be visible
	 * after saving.
	 * 
	 * @param request
	 * @param response
	 * 
	 * @throws ServletException
	 * @throws {@link IOException}
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// validate request
		SessionGuard.INSTANCE.validate(request);

		response.setContentType("text/html");
		RequestDispatcher rd = null;
		// id of the edit user
		int userID = Integer.parseInt(request.getParameter("id"));
		// user to be edited
		IPerson user = null;
		request.setAttribute("message", "");// remove old message
		String identity = request.getParameter("identity");

		if (identity == null
				|| (!identity.equals("saveadminedituserdata") && !identity
						.equals("deleteperson"))) {

			// user is loaded from the database based on the id
			//and written to the session
			if (userID >= 0) {

				try {
					user = PersistenceService.getService().getPerson(userID);

					if (user != null) {
						request.setAttribute("personToEdit", user);
						rd = request
								.getRequestDispatcher("adminedituserdatapage.jsp");

					}
				} catch (DatabaseException e) {
					request.setAttribute("message",
							"Fehler beim Laden der zu editierenden Person aus der DB<br>"
									+ e.getMessage());
					rd = request
							.getRequestDispatcher("useradministrationpage.jsp");
					logger
							.error("AdminEditUserDataServlet - Ladefehler der zu editierenden Person aus der DB: "
									+ e.getMessage());
				}

			} else {
				// faulty User ID
				String error = "Fehlerhafte User ID!";
				request.setAttribute("message", error);
				rd = request.getRequestDispatcher("useradministrationpage.jsp");
				logger.error("sAdminEditUserDataServlet - " + error);
			}

		} else {

			try {
				IPerson person = PersistenceService.getService().getPerson(
						userID);
				// if (person == null)
				// person = new Person();
				String username = request.getParameter("username");
				if (username != null && !username.equals(""))
					person.setLoginname(username);
				// passwords can not be set, since not yet checked if match 
				String password_1 = request.getParameter("password_1");
				String password_2 = request.getParameter("password_2");
				
				String title = request.getParameter("title");
				if (title != null )
					person.setTitle(title);
				
				String forename = request.getParameter("forename");
				if (forename != null && !forename.equals(""))
					person.setForename(forename);
				String surname = request.getParameter("surname");
				if (surname != null && !surname.equals(""))
					person.setSurname(surname);
				String email = request.getParameter("email");
				if (email != null && !email.equals(""))
					person.setEmailaddress(email);
				if (request.getParameter("accountActive") != null) {
					person.setAccountActive(true);
				} else {
					person.setAccountActive(false);
				}
				String organisation = request.getParameter("organisation");
				if (organisation != null && !organisation.equals(""))
					person.setOrganisation(organisation);
				if (person.getAddress() == null)
					person.setAddress(new Address());
				String street = request.getParameter("street");
				if (street != null && !street.equals(""))
					person.getAddress().setStreet(street);
				String number = request.getParameter("number");
				if (number != null && !number.equals(""))
					person.getAddress().setNumber(number);
				String zipcodeString = request.getParameter("zipcode");
				if (zipcodeString != null && !zipcodeString.equals(""))
					person.getAddress().setZipcode(zipcodeString);
				String location = request.getParameter("location");
				if (location != null && !location.equals(""))
					person.getAddress().setLocation(location);
				String occupationgroup = request
						.getParameter("occupationgroup");
				if (occupationgroup != null && !occupationgroup.equals(""))
					person.setOccupationgroup(occupationgroup);
				String department = request.getParameter("department");
				if (department != null && !department.equals(""))
					person.setDepartment(department);
				if (request.getParameter("rolePatient") != null) {
					person.addRole(Roles.PATIENT);
				} else {
					person.removeRole(Roles.PATIENT);
				}
				if (request.getParameter("rolePhysician") != null) {
					person.addRole(Roles.PHYSICIAN);
				} else {
					person.removeRole(Roles.PHYSICIAN);
				}
				if (request.getParameter("roleAdministrator") != null) {
					person.addRole(Roles.ADMINISTRATOR);
				} else {
					person.removeRole(Roles.ADMINISTRATOR);
				}

				if (identity.equals("saveadminedituserdata")) {

					request.setAttribute("message", "");// remove old message
					try {
						if (username != null && !username.equals("")
								&& password_1 != null && !password_1.equals("")
								&& password_2 != null && !password_2.equals("")
								&& forename != null && !forename.equals("")
								&& surname != null && !surname.equals("")
								&& email != null && !email.equals("")
						// && organisation != null && !organisation.equals("")
						// && occupationgroup != null
						// && !occupationgroup.equals("") && department != null
						// && !department.equals("")
						) {
							// all fields contain data

							if (!password_1.trim().equals(password_2.trim())
									&& !password_1.trim().equals("passwort1$")) {
								// password does not match the repetition

								request
										.setAttribute(
												"message",
												"Passworteingabe fehlerhaft! Das Passwort muss aus mindestens 6 Zeichen bestehen. "
														+ "<br>Das Passwort muss mindestens eine Zahl und einen Buchstaben enthalten! (z.B. passwort1$)");

								request.setAttribute("personToEdit", person);
	

								rd = request
										.getRequestDispatcher("adminedituserdatapage.jsp");
							} else {

								// check whether the password has changed, 
								// if not
								// --> not encrypt, otherwise encrypt
								String newPassword = "";
								if (password_1.equals("passwort1$")) {
									newPassword = (PersistenceService
											.getService().getPerson(userID))
											.getPassword();
								} else {
									newPassword = BCrypt.hashpw(password_1,
											BCrypt.gensalt());
								}
								person.setPassword(newPassword);
								/*
								 * check if person has the role  from a physician/ administrator
								 * if not, than remove the fields Abteilung, Berufsgruppe, Organisation
								 */
								if (!person.isProfessional()) {
									person.clearProfessionalFields();
								}
								PersistenceService.getService().store(person);
								request
										.setAttribute(
												"message",
												"Benutzerdaten wurden"
														+ " erfolgreich ge&auml;ndert.");
								rd = request
										.getRequestDispatcher("/Dispatcher?identity=useradministrationpage");

							}
						} else {
							// input incomplete - display registrationpage.jsp

							request.setAttribute("message",
									"Bitte Daten vollst&auml;ndig eingeben");
							request.setAttribute("personToEdit",
									PersistenceService.getService().getPerson(
											userID));
							rd = request
									.getRequestDispatcher("adminedituserdatapage.jsp");

						}
					} catch (BusinesslogicException e) {
						request
								.setAttribute(
										"message",
										"Fehler beim Editieren der Benutzerdaten!<br>"
												+ "Bitte versuchen Sie es erneut oder kontaktieren Sie den Administrator!");
						rd = request
								.getRequestDispatcher("/Dispatcher?identity=useradministrationpage");
						logger
								.error("AdminEditUserDataServlet - Fehler beim Editieren der Benutzerdaten: "
										+ e.getMessage());
					} catch (DatabaseException e) {
						request
								.setAttribute(
										"message",
										"Fehler beim &auml;ndern der Daten!<br>"
												+ "Bitte versuchen Sie es erneut oder kontaktieren Sie den Administrator!");
						rd = request
								.getRequestDispatcher("/Dispatcher?identity=useradministrationpage");
						logger
								.error("AdminEditUserDataServlet - Fehler beim &auml;ndern der Daten: "
										+ e.getMessage());
					}

				} else if (identity.equals("deleteperson")) {
					// remove a person

					try {
						IPerson deletePerson = Person.getPerson(userID);
						
						String userString = deletePerson.getSurname() + " "
								+ deletePerson.getForename();
						PersistenceService.getService().remove(deletePerson);

						request.setAttribute("message", "Der Benutzer "
								+ userString + " wurde"
								+ " erfolgreich gel&ouml;scht!");
						rd = request
								.getRequestDispatcher("/Dispatcher?identity=useradministrationpage");

					} catch (DatabaseException e) {
						request
								.setAttribute(
										"message",
										"Fehler beim l&ouml;schen der Benutzerdaten!<br>"
												+ "Bitte versuchen Sie es erneut oder kontaktieren Sie den Administrator");
						rd = request
								.getRequestDispatcher("/Dispatcher?identity=useradministrationpage");
						logger
								.error("AdminEditUserDataServlet - Fehler beim l&ouml;schen der Benutzerdaten:"
										+ e.getMessage());
					}

				}
			} catch (BusinesslogicException e) {
				request
						.setAttribute(
								"message",
								"Fehler beim Editieren der Benutzerdaten!<br>"
										+ "Bitte versuchen Sie es erneut oder kontaktieren Sie den Administrator");
				rd = request
						.getRequestDispatcher("/Dispatcher?identity=useradministrationpage");
				logger
						.error("AdminEditUserDataServlet - Fehler beim Editieren der Benutzerdaten:"
								+ e.getMessage());
			} catch (DatabaseException e) {
				request
						.setAttribute(
								"message",
								"Fehler beim Editieren der Benutzerdaten!<br>"
										+ "Bitte versuchen Sie es erneut oder kontaktieren Sie den Administrator");
				rd = request
						.getRequestDispatcher("/Dispatcher?identity=useradministrationpage");
				logger
						.error("AdminEditUserDataServlet - Fehler beim Editieren der Benutzerdaten:"
								+ e.getMessage());
			}
		}// Ende else
		rd.forward(request, response);
	}// Ende doPost
}
