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
import org.openehealth.twp.tewepo.businesslogic.BusinesslogicException;
import org.openehealth.twp.tewepo.businesslogic.IPerson;
import org.openehealth.twp.tewepo.database.DatabaseException;
import org.openehealth.twp.tewepo.helper.BCrypt;
import org.openehealth.twp.tewepo.helper.SessionGuard;


/**
 * Servlet implementation for users to edit their own user data.
 * 
 * @author Benjamin Schneider
 * 
 */
@SuppressWarnings("serial")
public class EditUserDataServlet extends HttpServlet {

	private Logger logger = Logger.getLogger(EditUserDataServlet.class);

	/**
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("message", "This is not possible!");
		request.getSession().invalidate();
		RequestDispatcher rd = request.getRequestDispatcher("error.jsp");
		rd.forward(request, response);
	}

	/**
	 * Allows users to edit theis own user data. Changes will be visible after
	 * saving.
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
		request.getSession();

		IPerson user = (IPerson) request.getSession().getAttribute("loginBean");

		String pw1 = request.getParameter("password_1");
		String pw2 = request.getParameter("password_2");

		request.setAttribute("message", "");// remove old message

		if (user != null && pw1 != null && pw1 != null && !pw2.equals("")
				&& !pw2.equals("")) {

			pw1 = pw1.trim();
			pw2 = pw2.trim();

			// check if password and repeat password are the same
			if (pw1.equals(pw2)) {
				try {
					user.setPassword(BCrypt.hashpw(pw1, BCrypt.gensalt())); // password
					// encrypt
					// and
					// save
					user.save();
					String message = "Passwort des Patienten mit der ID:"
							+ user.getObjectID() + " wurde ge&auml;ndert!";
					request.setAttribute("message",
							"Passwort wurde erfolgreich ge&auml;ndert.");
					rd = request.getRequestDispatcher("showuserdatapage.jsp");
					logger.info("EditUserDataServlet - " + message);
				} catch (BusinesslogicException e) {
					request.setAttribute("message",
							"Fehler beim &Auml;ndern des Passworts<br>"
									+ e.getMessage());
					rd = request.getRequestDispatcher("showuserdatapage.jsp");
					logger
							.error("EditUserDataServlet - Fehler beim Passwort ändern: "
									+ e.getMessage());
				} catch (DatabaseException e) {
					request.setAttribute("message",
							"Fehler beim &Auml;ndern des Passworts in der DB<br>"
									+ e.getMessage());
					rd = request.getRequestDispatcher("showuserdatapage.jsp");
					logger
							.error("EditUserDataServlet - Fehler beim Ändern des Passworts in der DB: "
									+ e.getMessage());
				}
			} else {
				// error, because the passwords aren't the same
				String error = "Passwort stimmt nicht mit der Passwortwiederholung &uuml;berein!";
				request.setAttribute("message", error);
				rd = request.getRequestDispatcher("showuserdatapage.jsp");
				logger.error("EditUserDataServlet - " + error);
			}

		} else {
			// error in the input!!! or person in the Session is null
			String error = "Fehlerhafte Eingabe! / Session ung&uuml;ltig!";
			request.setAttribute("message", error);
			rd = request.getRequestDispatcher("showuserdatapage.jsp");
			logger.error("EditUserDataServlet -  " + error);
		}

		rd.forward(request, response);
	}

}
