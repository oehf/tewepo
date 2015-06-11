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
import org.openehealth.twp.tewepo.businesslogic.Person;
import org.openehealth.twp.tewepo.database.DatabaseException;


/**
 * Servlet implementation for activating a registered user.
 * 
 * @author Benjamin Schneider
 * 
 */
@SuppressWarnings("serial")
public class ActivationServlet extends HttpServlet {

	private Logger logger = Logger.getLogger(ActivationServlet.class);

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
	 * Activates the account of a user so that this is now able to log in the
	 * system.
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
		request.setAttribute("message", "");// remove old message

		if (identity.equals("activateaccount")) {

			String code = request.getParameter("code");

			try {
				Person.activateAccount(code);
			} catch (DatabaseException e) {
				logger.error("Account activation failed: " + e.getMessage());
				request.setAttribute("message",
						"Beim Freischalten Ihres Accounts ist ein Fehler aufgetreten,<br>"
								+ "bitte melden Sie sich beim Administrator.");
				rd = request.getRequestDispatcher("contactpage.jsp");
			} catch (BusinesslogicException e) {
				logger.error("Account activation failed: " + e.getMessage());
				request.setAttribute("message",
						"Beim Freischalten Ihres Accounts ist ein Fehler aufgetreten,<br>"
								+ "bitte melden Sie sich beim Administrator.");
				rd = request.getRequestDispatcher("contactpage.jsp");
			}

			request.setAttribute("message",
					"Ihr Account wurde freigeschaltet,<br>"
							+ "Sie k&ouml;nnen sich nun anmelden!");
			rd = request.getRequestDispatcher("loginpage.jsp");

		}

		rd.forward(request, response);
	}

}
