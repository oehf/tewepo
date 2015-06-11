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


/**
 * Servlet implementation for registering as a new user to the webportal.
 * 
 * @author Benjamin Schneider
 * 
 */
@SuppressWarnings("serial")
public class RegistrationServlet extends HttpServlet {

	private Logger logger = Logger.getLogger(RegistrationServlet.class);// "webportal"

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 *      , javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("message", "This is not possible!");
		request.getSession().invalidate();
		RequestDispatcher rd = request
				.getRequestDispatcher("registrationpage.jsp");
		rd.forward(request, response);
	}

	/**
	 * Registers a new user to the webportal. The user or the admin should enter
	 * all required data. username, password (2x), forname, surname, e-mail
	 * address, street, streetnumber, zipcode and location must not be null.
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

		response.setContentType("text/html");
		RequestDispatcher rd = null;
		String identity = request.getParameter("identity");
		
		String title = request.getParameter("title");
		String username = request.getParameter("username");
		String password_1 = request.getParameter("password_1");
		String password_2 = request.getParameter("password_2");
		String forename = request.getParameter("forename");
		String surname = request.getParameter("surname");
		String email = request.getParameter("email");
		String role = request.getParameter("role");
		String organisation = request.getParameter("organisation");
		String street = request.getParameter("street");
		String number = request.getParameter("number");
		String zipcodeString = request.getParameter("zipcode");
		String location = request.getParameter("location");
		String occupationgroup = request.getParameter("occupationgroup");
		String department = request.getParameter("department");

		request.setAttribute("title", title);
		request.setAttribute("username", username);
		request.setAttribute("forename", forename);
		request.setAttribute("surname", surname);
		request.setAttribute("email", email);
		request.setAttribute("role", role);
		request.setAttribute("organisation", organisation);
		request.setAttribute("street", street);
		request.setAttribute("number", number);
		request.setAttribute("zipcodeString", zipcodeString);
		request.setAttribute("location", location);
		request.setAttribute("occupationgroup", occupationgroup);
		request.setAttribute("department", department);

		request.setAttribute("message", "");// delete old message

		if (identity.equals("registration")) {
			// check if all data are set (lastname, forenname, e-Mail,
			// Loginname, Password
			IPerson registrationPerson = new Person();
			try {
				// set flag, that is the account of the person is not yet
				// activated

				registrationPerson.setAccountActive(false);
				registrationPerson.setTitle(title);
				registrationPerson.setLoginname(username);
				registrationPerson.setSurname(surname);
				registrationPerson.setForename(forename);
				registrationPerson.setEmailaddress(email);

				if (department != null && !department.equals("")) {
					registrationPerson.setDepartment(department);
				}
				if (occupationgroup != null && !occupationgroup.equals("")) {
					registrationPerson.setOccupationgroup(occupationgroup);
				}
				if (organisation != null && !organisation.equals("")) {
					registrationPerson.setOrganisation(organisation);
				}
				registrationPerson.setAddress(new Address(street, number,
						zipcodeString, location));

				// set role
				if (request.getParameter("role").equals("rolePatient")) {
					registrationPerson.addRole(Roles.PATIENT);
				}
				if (request.getParameter("role").equals("rolePhysician")) {
					registrationPerson.addRole(Roles.PHYSICIAN);
				}

			} catch (BusinesslogicException e) {
				request
						.setAttribute(
								"message",
								e.getMessage()
										+ " Bitte versuchen Sie es erneut. Wenden Sie sich an den Administrator falls der Fehler wiederholt auftritt.");
				logger.error("RegistrationServlet - " + e.getMessage());
				request.setAttribute("registrationPerson", registrationPerson);
				rd = request.getRequestDispatcher("registrationpage.jsp");
				rd.forward(request, response);
				return;
			}

			// all fileds contain data
			if (!password_1.trim().equals(password_2.trim())) {
				// password does not match the repetition of identical password

				request
						.setAttribute(
								"message",
								"Passworteingabe fehlerhaft! Das Passwort muss aus mindestens 6 Zeichen bestehen. "
										+ "<br>Das Passwort muss mindestens eine Zahl und einen Buchstaben enthalten!");
				// parameters passed back to the form can be filled

				request.setAttribute("registrationPerson", registrationPerson);

				rd = request.getRequestDispatcher("registrationpage.jsp");
				rd.forward(request, response);
				return;

			}

			/*
			 * Dates are entered correctly, now needs to be tested, whether the
			 * person would like to register as a patient o physician and must
			 * then be guided accordingly further and must then be routed
			 */
			try {
				/*
				 * set known terms in the object
				 */
				registrationPerson.setPassword(BCrypt.hashpw(password_1, BCrypt
						.gensalt()));
				/*
				 * check whether the login is already used
				 */

				if (Person.existsLogin(username)) {
					request
							.setAttribute(
									"message",
									"Benutzername wird bereits verwendet! Bitte wählen Sie einen anderen Benutzernamen.");

					request.setAttribute("registrationPerson",
							registrationPerson);

					logger
							.info("RegistrationServlet - Es wurde versucht sich mit einem bereits registrierten Login erneut zu registrieren! Benutzername: "
									+ registrationPerson.getLoginname()
									+ "E-Mail: "
									+ registrationPerson.getEmailaddress());
					rd = request.getRequestDispatcher("registrationpage.jsp");
					rd.forward(request, response);
					return;

				} else{
					try {
						if (Person.existMailadress(email)) {
							request
									.setAttribute(
											"message",
											"E-Mail Adresse existiert bereits! Bitte melden Sie sich mit einer anderen Adresse.");

							request.setAttribute("registrationPerson",
									registrationPerson);

							logger
									.info("RegistrationServlet - Es wurde versucht sich mit einem bereits registrierten E-Mail Adresse erneut zu registrieren! E-Mail: "
											+ registrationPerson.getEmailaddress());
											
							rd = request.getRequestDispatcher("registrationpage.jsp");
							rd.forward(request, response);
							return;
						}
					} catch (DatabaseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				try {
					// save person in the database
					registrationPerson.setObjectID(PersistenceService
							.getService().store(registrationPerson));
					// send message with the confirmation of the user data to
					// the user
					registrationPerson.sendAcivationEMail();

					request
							.setAttribute(
									"message",
									"Sie erhalten in K&uuml;rze eine Aktivierungsmail an die angegebene E-Mail Adresse.<br>"
											+ " Sobald Sie den enthaltenen Link ge&ouml;ffnet haben, k&ouml;nnen Sie sich am Portal anmelden.");
					rd = request.getRequestDispatcher("loginpage.jsp");
					rd.forward(request, response);
					return;

				} catch (DatabaseException e) {
					request.setAttribute("message",
							"Fehler beim Registrieren<br>" + e.getMessage());
					logger.error("RegistrationServlet - " + e.getMessage());
					request.setAttribute("registrationPerson",
							registrationPerson);
					rd = request.getRequestDispatcher("registrationpage.jsp");
					rd.forward(request, response);
					return;
				}

			} catch (BusinesslogicException e) {
				request
						.setAttribute(
								"message",
								e.getMessage()
										+ " Bitte versuchen Sie es erneut. Wenden Sie sich an den Administrator falls der Fehler wiederholt auftritt.");
				logger.error("RegistrationServlet - " + e.getMessage());
				request.setAttribute("registrationPerson", registrationPerson);
				/*
				 * Error when creating the new person object, return to the
				 * registration page
				 */
				rd = request.getRequestDispatcher("registrationpage.jsp");
				rd.forward(request, response);
				return;
			}

		}

		// __________________________
		//
		// Admin adds new Person
		//
		// __________________________

		else if (identity.equals("registration_AdminAddsNewPerson")) {

			IPerson newPerson = new Person();

			try {
				newPerson.setAccountActive(true);
				newPerson.setLoginname(username);
				newPerson.setForename(forename);
				newPerson.setSurname(surname);
				newPerson.setPassword(BCrypt.hashpw(password_1, BCrypt
						.gensalt()));
				newPerson.setEmailaddress(email);
				newPerson.setAddress(new Address(street, number, zipcodeString,
						location));
				if (occupationgroup != null)
					newPerson.setOccupationgroup(occupationgroup);
				if (organisation != null)
					newPerson.setOrganisation(organisation);
				if (department != null)
					newPerson.setDepartment(department);

				// sets role
				if (request.getParameter("rolePatient") != null) {
					newPerson.addRole(Roles.PATIENT);
				} else {
					newPerson.removeRole(Roles.PATIENT);
				}
				if (request.getParameter("rolePhysician") != null) {
					newPerson.addRole(Roles.PHYSICIAN);
				} else {
					newPerson.removeRole(Roles.PHYSICIAN);
				}
				if (request.getParameter("roleAdministrator") != null) {
					newPerson.addRole(Roles.ADMINISTRATOR);
				} else {
					newPerson.removeRole(Roles.ADMINISTRATOR);
				}

			} catch (BusinesslogicException e) {
				request.setAttribute("message", "Fehler beim Registrieren<br>"
						+ e.getMessage());
				request.setAttribute("newPerson", newPerson);
				rd = request.getRequestDispatcher("adduserpage.jsp");
				logger.error("RegistrationServlet - Admin add user "
						+ e.getMessage());

				rd.forward(request, response);
				return;
			}

			// all fields contain data
			if (!password_1.trim().equals(password_2.trim())) {
				// password does not match the repetition of identical passowrd
				// W
				request
						.setAttribute(
								"message",
								"Passworteingabe fehlerhaft! Das Passwort muss aus mindestens 6 Zeichen bestehen. "
										+ "<br>Das Passwort muss mindestens eine Zahl und ein Sonderzeichen enthalten!");
				// Loggen
				request.setAttribute("newPerson", newPerson);

				rd = request.getRequestDispatcher("adduserpage.jsp");
				rd.forward(request, response);
				return;

			}

			if (Person.existsLogin(newPerson.getLoginname())) {
				/*
				 * check whether the login is already used
				 */
				request
						.setAttribute(
								"message",
								"Benutzername wird bereits verwendet! Bitte w&auml;hlen Sie einen anderen Benutzernamen.");

				request.setAttribute("newPerson", newPerson);

				logger
						.info("Es wurde versucht sich mit einem bereits registrierten Login erneut zu registrieren! Benutzername: "
								+ newPerson.getLoginname());
				rd = request.getRequestDispatcher("adduserpage.jsp");

				rd.forward(request, response);
				return;
			}

			try {
				newPerson.setObjectID(PersistenceService.getService().store(
						newPerson));

				request.setAttribute("message",
						"Neuer Benutzer wurde hinzugef&uuml;gt.<br>");
				
				rd = request
						.getRequestDispatcher("/Dispatcher?identity=useradministrationpage");
				rd.forward(request, response);
				return;

			} catch (DatabaseException e) {
				request.setAttribute("message",
						"Fehler beim Hinzuf&uuml;gen des neuen Benutzers.<br>"
								+ e.getMessage());
				request.setAttribute("newPerson", newPerson);
				rd = request.getRequestDispatcher("adduserpage.jsp");
				logger.error("RegistrationServlet - " + e.getMessage());

				rd.forward(request, response);
				return;
			}

		}
		rd = request.getRequestDispatcher("errorpage.jsp");
		rd.forward(request, response);
	}
}
