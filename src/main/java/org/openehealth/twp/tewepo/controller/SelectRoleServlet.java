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
import org.openehealth.twp.tewepo.businesslogic.Role.Roles;
import org.openehealth.twp.tewepo.helper.SessionGuard;


/**
 * Servlet implementation to select a role for the user in the webportal.
 * 
 * @author Benjamin Schneider
 * 
 */
@SuppressWarnings("serial")
public class SelectRoleServlet extends HttpServlet {

	private Logger logger = Logger.getLogger(SelectRoleServlet.class);//"webportal"

	/**
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
	 * HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);

	}

	/**
	 * Sets one or more roles to the user.
	 * 
	 * @param request
	 * @param response
	 * 
	 * @throws ServletException
	 * @throws {@link IOException}
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// validate request
		SessionGuard.INSTANCE.validate(request);

		response.setContentType("text/html");
		RequestDispatcher rd = null;

		String roleString = request.getParameter("role");
		Roles role = Roles.PATIENT;
		if (roleString.equals("physician"))
			role = Roles.PHYSICIAN;
		else if (roleString.equals("administrator"))
			role = Roles.ADMINISTRATOR;

		// set role
		SelectRoleServlet.setAuthorisation(request, response, role);

		// forwarded to the appropriate home
		rd = request.getRequestDispatcher("/Dispatcher?identity=homepage");
		rd.forward(request, response);
	}

	/**
	 * Changes the authorisation in the session.
	 * 
	 * @param request
	 * @param response
	 * @param role
	 *            the role to set
	 */
	public static void setAuthorisation(HttpServletRequest request,
			HttpServletResponse response, Roles role) {
		HttpSession session = request.getSession();
		boolean administrator = false;
		boolean physician = false;
		boolean patient = false;

		if (role.equals(Roles.ADMINISTRATOR)) {
			administrator = true;
			physician = false;
			patient = false;

		} else if (role.equals(Roles.PHYSICIAN)) {
			physician = true;
			administrator = false;
			patient = false;
		} else if (role.equals(Roles.PATIENT)) {
			patient = true;
			administrator = false;
			physician = false;
		}

		// set attributes in the session
		session.setAttribute("patient", patient);
		session.setAttribute("physician", physician);
		session.setAttribute("administrator", administrator);
	}

}
