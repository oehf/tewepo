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
import org.openehealth.twp.tewepo.businesslogic.IPerson;
import org.openehealth.twp.tewepo.businesslogic.Person;
import org.openehealth.twp.tewepo.database.DatabaseException;


/**
 * Servlet implementation for requesting the username.
 * 
 * @author Benjamin Schneider
 * 
 */
@SuppressWarnings("serial")
public class UsernameRequestServlet extends HttpServlet {

	private Logger logger = Logger.getLogger(UsernameRequestServlet.class);//"webportal"

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 *      , javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * Checks the input of the user. If the user exists in the system, temporary
	 * keyword will be created and sent to the stored e-mail address.
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
		String message = "";
		request.setAttribute("message", message);// remove old message

		if (identity.equals("usernamerequest")) { //passwordrequest

			String surname = request.getParameter("surname");
			String forename = request.getParameter("forename");
			String email = request.getParameter("email");

			if (surname != null && !surname.equals("") && forename != null
					&& !forename.equals("") && email != null
					&& !email.equals("")) {
				try {
					IPerson requestedPerson = Person.getPerson(surname,
							forename, email);

					/*
					 * if the person is present, generate a password and send
					 * this to the contained email
					 */
					if (requestedPerson != null) {
					//	requestedPerson.sendPasswordRequestMail();
						requestedPerson.sendUsernameRequestMail();
						request
								.setAttribute("message",
										"Ihr Benutzername wurde an Ihre E-Mail Adresse gesendet.");
						rd = request.getRequestDispatcher("loginpage.jsp");
					}
				} catch (DatabaseException e) {
					logger.error("UsernameRequestServlet - " + e.getMessage());
					message = "Die angegebenen Daten stimmen nicht überein, bitte versuchen Sie es erneut!";
					request.setAttribute("message", message);
					rd = request.getRequestDispatcher("usernamepage.jsp");
				}
			} else {
				/*
				 * the entered data are incomplete--> display error
				 */
				message = "Bitte füllen Sie das Formular aus!";
				logger
						.error("UsernameRequestServlet - Unvollst&auml;ndige Daten");
				request.setAttribute("message", message);
				rd = request.getRequestDispatcher("usernamepage.jsp");

			}
		} else {
			/*
			 * display error
			 */
			message = "Es ist ein Fehler aufgetreten, bitte versuchen Sie es erneut!";
			logger
					.error("UsernameRequestServlet - Es ist ein Fehler aufgetreten");
			request.setAttribute("message", message);
			rd = request.getRequestDispatcher("usernamepage.jsp");

		}
		rd.forward(request, response);
	}

}
