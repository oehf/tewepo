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
import org.openehealth.twp.tewepo.businesslogic.Mail;


/**
 * Servlet implementation of the contact page for users and visitors of the
 * webportal.
 * 
 * @author Benjamin Schneider
 * 
 */
@SuppressWarnings("serial")
public class ContactServlet extends HttpServlet {

	private Logger logger = Logger.getLogger(ContactServlet.class);

	/**
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("message", "This is not possible!");
		request.getSession().invalidate();
		RequestDispatcher rd = request.getRequestDispatcher("errorpage.jsp");
		rd.forward(request, response);
	}

	/**
	 * Sends the contact request of a user or visitor as an e-mail to the admin.
	 * 
	 * @param request
	 * @param response
	 * 
	 * @throws ServletException
	 * @throws IOException
	 *             {@link IOException}
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		RequestDispatcher rd = null;
		String identity = request.getParameter("identity");
		String message = "";
		if (identity != null && identity.equals("sendmail")) {

			String defaultSubject = "Kontaktformular des Teleradiologieportals";

			String userTitle = request.getParameter("title");
			String userForename = request.getParameter("forename");
			String userSurname = request.getParameter("surname");		
			String userEMailAddress = request.getParameter("email");
			String mailSubject = request.getParameter("subject");
			String mailMessage = request.getParameter("message");

			String text = "";

			if (userTitle != null)
				text = userTitle + " ";

			text = text + userForename + " " + userSurname + " (mailto:'"
					+ userEMailAddress + "')"
					+ " hat eine Anfrage gestellt. \n\n";
			text = text + "Betreff: " + mailSubject + " \n\n";
			text = text + "Nachricht: " + mailMessage;
			text = text
					+ "\n\n\n Diese Nachricht wurde automatisch vom Teleradiologie-Webportal RND generiert.";

			try {
				new Mail().sendMail(defaultSubject, text);
				message = "Die Nachricht wurde erfolgreich an den Administrator gesendet.";
			} catch (Exception e) {
				logger.error("ContactServlet - E-Mail Versendefehler: "
						+ e.getMessage());
				message = "Beim Versenden einer Kontakt e-Mail ist ein Fehler aufgetreten. Die Nachricht konnte nicht versendet werden, bitte versuchen Sie es noch einmal!";
			}

			request.setAttribute("message", message);

			rd = request.getRequestDispatcher("contactpage.jsp");
			rd.forward(request, response);
		} else {

			logger
					.error("ContactServlet - Beim versenden einer Kontakt e-Mail ist ein Fehler aufgetreten!");
			message = "Es ist ein Fehler aufgetreten, bitte versuchen Sie es erneut!";

			request.setAttribute("message", message);

			rd = request.getRequestDispatcher("contactpage.jsp");
			rd.forward(request, response);

		}

	}

}
