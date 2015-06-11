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
import org.openehealth.twp.tewepo.businesslogic.LogLastFiles;
import org.openehealth.twp.tewepo.businesslogic.Person;
import org.openehealth.twp.tewepo.database.DatabaseException;
import org.openehealth.twp.tewepo.helper.SessionGuard;


/**
 * Servlet implementation to show all users in the system.
 * 
 * @author Nilay Yüksekogul
 * 
 */
@SuppressWarnings("serial")
public class DicomEmailServlet extends HttpServlet {

	private Logger logger = Logger.getLogger(DicomEmailServlet.class);//"webportal"

	/**
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public DicomEmailServlet() {
		super();
	}

	/**
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
	 * HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * Loads and shows all files stored in the database.
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

		RequestDispatcher rd;
		IPerson user = null;
		user= (Person) request.getSession().getAttribute("loginBean");
		
		long userId = user.getId();

		try {
			request.setAttribute("allLastFilesInDB", LogLastFiles.getLogLastFiles(userId));

			rd = request.getRequestDispatcher("dicomemailpage.jsp");
			rd.forward(request, response);
		} catch (DatabaseException e) {
			request.setAttribute("message",
					"Fehler beim laden der Dateien aus der DB! <br>"
							+ e.getMessage());
			rd = request.getRequestDispatcher("registrationpage.jsp");
			logger.error("ShowAllLastFilesServlet - " + e.getMessage());
		}

	}

}
