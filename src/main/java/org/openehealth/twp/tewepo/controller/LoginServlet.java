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
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.openehealth.twp.tewepo.businesslogic.BusinesslogicException;
import org.openehealth.twp.tewepo.businesslogic.IPerson;
import org.openehealth.twp.tewepo.businesslogic.LockedIP;
import org.openehealth.twp.tewepo.businesslogic.Person;
import org.openehealth.twp.tewepo.businesslogic.Role.Roles;
import org.openehealth.twp.tewepo.database.DatabaseException;
import org.openehealth.twp.tewepo.helper.IPUtils;
import org.openehealth.twp.tewepo.helper.SessionGuard;


/**
 * This class checks if the given loginname and password belongs to a person.
 * 
 * @author Benjamin Schneider
 * 
 */
@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {

	private Logger logger = Logger.getLogger(LoginServlet.class);

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
		RequestDispatcher rd = request.getRequestDispatcher("errorpage.jsp");
		rd.forward(request, response);
	}

	/**
	 * Logs the user in the system and saves it in the session "loginBean". If
	 * the user cannot be logged in, the login page will be opened with an error
	 * message.
	 * 
	 * @param request
	 * @param response
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String message = "";
		
		response.setContentType("text/html");
		RequestDispatcher rd = null;
		// invalidate Session
		request.getSession().invalidate();

		String username = request.getParameter("name");
		String userpass = request.getParameter("password");

		request.setAttribute("message", message);// remove old message
		if (username != null && userpass != null && !username.equals("")
				&& !userpass.equals("")) {

			username = username.trim();
			userpass = userpass.trim();

			// search person
			IPerson user = null;
			try {
				user = Person.login(username, userpass);
				// if(user == null){
				// user = LdapVerbindung.ldapLogin(username, userpass);
				// }
			} catch (BusinesslogicException e) {
				request.setAttribute("message", "Error while login<br>"
						+ e.getMessage());
				rd = request.getRequestDispatcher("loginpage.jsp");

				// rd.forward(request, response);
				// return;
			} catch (DatabaseException e) {
				request.setAttribute("message", "Error while login<br>"
						+ e.getMessage());
				rd = request.getRequestDispatcher("loginpage.jsp");

				logger.error("LoginServlet - Error while login: "
						+ e.getMessage());
				// rd.forward(request, response);
				// return;
			}
			if (user != null) {
				// user.getRoles().trimToSize();
				/*
				 * check if person has roles, is this not the case, the person
				 * is either disabled or not for the use of portal unlocked
				 */
				if (user.isAccountActive()) {
					// evrything is ok, the informations match a user
					// create new Session
					HttpSession session = request.getSession();
					// set login bean
					session.setAttribute("loginBean", user);
					logger.info("Login Servlet - User: " + user.getObjectID()
							+ " is logged in.");

					setAuthorisation(request, response);

					// bind session to client
					session.setAttribute(SessionGuard.MARKER,
							SessionGuard.INSTANCE.getMarker(request));

					/*
					 * check how many roles are available, if the user has more
					 * than one role, redirect to a page specially for role
					 * selection
					 */

					if (user.getRoles().size() == 1) {
						rd = request
								.getRequestDispatcher("/Dispatcher?identity=homepage");
					} else {
						rd = request.getRequestDispatcher("selectrolepage.jsp");
					}
				} else {

					request
							.setAttribute(
									"message",
									"Ihr Account wurde gesperrt bzw. ist noch nicht f&uuml;r die Nutzung des Protals freigeschalten.<br> "
											+ "Eine Anmeldung ist nicht m&ouml;glich!");
					rd = request.getRequestDispatcher("loginpage.jsp");
					rd.forward(request, response);
					logger
							.error("LoginServlet - Account gesperrt / nicht freigeschaltet");
					return;
				}
			} else {
				
				
				String ip = IPUtils.getIpFromRequest(request);
				
			//	 String ip = request.getRemoteAddr();
				
				LockedIP.lockIP(ip);
				logger.warn("LoginServlet - The IP: " + ip
						+ " failed to login.");

				request.setAttribute("message", "Anmeldung fehlgeschlagen!");
				rd = request.getRequestDispatcher("loginpage.jsp");
			}

		} else {
			// no name or password entered - login.jsp show again
			request.setAttribute("message",
					"Bitte Benutzername und Passwort eingeben!");
			rd = request.getRequestDispatcher("loginpage.jsp");

		}

		rd.forward(request, response);
	}

	/**
	 * Sets/Changes the authorisation in the session.
	 * 
	 * @param request
	 * @param response
	 */
	public static void setAuthorisation(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		IPerson benutzer = (IPerson) session.getAttribute("loginBean");
		boolean administrator = false;
		boolean physician = false;
		boolean patient = false;

		for (int x = 0; x < benutzer.getRoles().size(); x++) {

			if (benutzer.getRoles().get(x).getRole()
					.equals(Roles.ADMINISTRATOR)) {
				administrator = true;
				physician = false;
				patient = false;

			} else if (benutzer.getRoles().get(x).getRole().equals(
					Roles.PHYSICIAN)) {
				physician = true;
				administrator = false;
				patient = false;
			} else if (benutzer.getRoles().get(x).getRole().equals(
					Roles.PATIENT)) {
				patient = true;
				administrator = false;
				physician = false;
			}

		}

		// set attributes in the session
		session.setAttribute("patient", patient);
		session.setAttribute("physician", physician);
		session.setAttribute("administrator", administrator);
	}

}
