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
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.openehealth.tewepo.twp.dmp.dmc.server.email.xml.DicomMailRecipient;
import org.openehealth.tewepo.twp.dmp.dmc.server.email.xml.DicomMailRecipients;
import org.openehealth.twp.tewepo.configuration.Configuration;


/**
 * Servlet implementation for adding a new recipient (and also editing an
 * existing recipient)
 * 
 * @author Benjamin Schneider
 * 
 */
@SuppressWarnings("serial")
public class AddRecipientServlet extends HttpServlet {

	private Logger logger = Logger.getLogger(AddRecipientServlet.class);//"webportal"

	/** configuration */
	private final static Configuration conf = Configuration.getMainConfig("");

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
	 * Adds a new recipient which finally will be shown in the recipients' list.
	 * Or edits the data of an already existing recipient. The changes will be
	 * visible after saving. {@link DicomMailRecipient}
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

		String location = request.getParameter("location");
		String institution = request.getParameter("institution");
		String publicKeyId = request.getParameter("publicKeyId");
		String rec = request.getParameter("rec"); // recipient
		String mail_1 = request.getParameter("mail_1");
		String mail_2 = request.getParameter("mail_2");
		String mail_3 = request.getParameter("mail_3");

		request.setAttribute("message", "");// remove old message
		String identity = request.getParameter("identity");
		String idRecipientToEdit = request.getParameter("recipientid");
		request.setAttribute("recipientid", null);
		String recipientedited = request.getParameter("recipientedited");
		// request.setAttribute("recipientedited", true);
		String idRecipientToDelete = request.getParameter("deleterecipient");
		request.setAttribute("deleterecipient", null);

		DicomMailRecipients dicomMailRecipients = new DicomMailRecipients(getServletContext().getRealPath("/"));
		List<DicomMailRecipient> listDicomMailRecipients = dicomMailRecipients
				.getRecipients();

		// check if request param "editrecipient" is set;
		// if yes jump to the attribute "editrecipient" below!
		if (idRecipientToEdit != null) {
			if (recipientedited != null) {
				if (!recipientedited.equals("true")) {
					for (DicomMailRecipient recipient : listDicomMailRecipients) {
						if (recipient.getId().trim().equals(
								idRecipientToEdit.trim())) {
							request.setAttribute("recipient", recipient);
							rd = request
									.getRequestDispatcher("Dispatcher?identity=addnewrecipientpage");
							rd.forward(request, response);
							return;
						}
					}
					request
							.setAttribute("message",
									"Es ist ein Fehler aufgetreten, bitte versuchen Sie es erneut!");
					rd = request
							.getRequestDispatcher("showlistofcontactpersonspage.jsp");
					rd.forward(request, response);
					logger.error("AddRecipientServlet - Fehler aufgetreten");
					return;
				}
			}
		} else if (idRecipientToDelete != null) {
			for (DicomMailRecipient recipient : listDicomMailRecipients) {
				if (recipient.getId().trim().equals(idRecipientToDelete.trim())) {
					listDicomMailRecipients.remove(recipient);
					dicomMailRecipients.setRecipients(listDicomMailRecipients);
					dicomMailRecipients.serialize();
					request.setAttribute("message",
							"Empf&auml;nger gel&ouml;scht!");
					rd = request
							.getRequestDispatcher("Dispatcher?identity=showlistofcontactpersonspage");
					rd.forward(request, response);
					return;
				}
			}
			request
					.setAttribute("message",
							"Es ist ein Fehler aufgetreten, bitte versuchen Sie es erneut!");
			rd = request
					.getRequestDispatcher("showlistofcontactpersonspage.jsp");
			rd.forward(request, response);
			logger.error("AddRecipientServlet - Fehler aufgetreten");
			return;
		}

		// -------------------
		// new recipient
		// -------------------

		try {
			if (identity.equals("addnewrecipient")) {
				if (location != null && !location.trim().equals("")
						&& institution != null
						&& !institution.trim().equals("")
						&& publicKeyId != null
						&& !publicKeyId.trim().equals("") && rec != null
						&& !rec.trim().equals("") || mail_1 != null
						&& mail_1.trim() != null
						&& mail_2.trim() != null
						&& mail_3.trim() != null
						&& ((!mail_1.trim().equals("")) || (!mail_2.trim().equals("")) || (!mail_3.trim().equals("")))) {

					// new recipient, create a new entry
					DicomMailRecipient newDicomMailRecipient = new DicomMailRecipient();

					newDicomMailRecipient.setLocation(location.trim());
					newDicomMailRecipient.setInstitution(institution.trim());
					newDicomMailRecipient.setPublicKeyId(publicKeyId.trim());
					newDicomMailRecipient.setRecipient(rec.trim());
					newDicomMailRecipient.setMailAddress1(mail_1.trim());
					newDicomMailRecipient.setMailAddress2(mail_2.trim());
					newDicomMailRecipient.setMailAddress3(mail_3.trim());
					newDicomMailRecipient.setId(String
							.valueOf(GregorianCalendar.getInstance()
									.getTimeInMillis()));

					listDicomMailRecipients.add(newDicomMailRecipient);
					dicomMailRecipients.setRecipients(listDicomMailRecipients);
					dicomMailRecipients.serialize();

					request.setAttribute("message",
							"Empf&auml;nger gespeichert!");

					rd = request
							.getRequestDispatcher("Dispatcher?identity=showlistofcontactpersonspage");
					rd.forward(request, response);

				} else {
					// input incomplete - display systemconfigurationpage.jsp


					request.setAttribute("message",
							"Bitte Daten vollst&auml;ndig eingeben! Es muss mindestens eine Mailadresse angegeben werden.");


					DicomMailRecipient recipient = new DicomMailRecipient();

					recipient.setInstitution(institution.trim());
					recipient.setLocation(location.trim());
					recipient.setPublicKeyId(publicKeyId.trim());
					recipient.setRecipient(rec.trim());
					recipient.setMailAddress1(mail_1.trim());
					recipient.setMailAddress2(mail_2.trim());
					recipient.setMailAddress3(mail_3.trim());

					request.setAttribute("recipient", recipient);

					// listDicomMailRecipients.add(newDicomMailRecipient);
					rd = request
							.getRequestDispatcher("Dispatcher?identity=editrecipient");
					rd.forward(request, response);

				}
			}

			// -------------------
			// edit recipient
			// -------------------

			else if (identity.equals("editrecipient")) {
				if (request.getParameter("recipientid").trim() != null
						&& !request.getParameter("recipientid").trim().equals(
								"")) {
					int i = 0;
					// the recipient exists already --> update the dates
					for (DicomMailRecipient recipient : listDicomMailRecipients) {
						if (location != null && !location.equals("")
								&& institution != null
								&& !institution.equals("")
								&& publicKeyId != null
								&& !publicKeyId.equals("") && rec != null
								&& !rec.equals("") && mail_1 != null
								&& mail_1.trim() != null
								&& mail_2.trim() != null
								&& mail_3.trim() != null
								&&  ((!mail_1.trim().equals("")) || (!mail_2.trim().equals("")) || (!mail_3.trim().equals("")))) {
							if (recipient.getId().trim().equals(
									request.getParameter("recipientid").trim())) {

								DicomMailRecipient changedRecipient = new DicomMailRecipient();
								changedRecipient
										.setId(recipient.getId().trim());
								listDicomMailRecipients.remove(recipient);

								changedRecipient.setLocation(location.trim());
								changedRecipient.setInstitution(institution
										.trim());
								changedRecipient.setPublicKeyId(publicKeyId
										.trim());
								changedRecipient.setRecipient(rec.trim());
								changedRecipient.setMailAddress1(mail_1.trim());
								changedRecipient.setMailAddress2(mail_2.trim());
								changedRecipient.setMailAddress3(mail_3.trim());

								listDicomMailRecipients
										.add(i, changedRecipient);
								dicomMailRecipients
										.setRecipients(listDicomMailRecipients);
								dicomMailRecipients.serialize();

								request.setAttribute("message",
										"&Auml;nderungen gespeichert!");

								rd = request
										.getRequestDispatcher("Dispatcher?identity=showlistofcontactpersonspage");
								rd.forward(request, response);

								return;
							}
							i++;
						}

						else {
							request.setAttribute("message",
									"Bitte Daten vollst&auml;ndig eingeben! Es muss mindestens eine Mailadresse angegeben werden.");

							DicomMailRecipient changedRecipient = new DicomMailRecipient();

							changedRecipient.setLocation(location.trim());
							changedRecipient.setInstitution(institution.trim());
							changedRecipient.setPublicKeyId(publicKeyId.trim());
							changedRecipient.setRecipient(rec.trim());
							changedRecipient.setMailAddress1(mail_1.trim());
							changedRecipient.setMailAddress2(mail_2.trim());
							changedRecipient.setMailAddress3(mail_3.trim());

							request.setAttribute("recipient", changedRecipient);

							// listDicomMailRecipients.add(newDicomMailRecipient);
							rd = request
									.getRequestDispatcher("Dispatcher?identity=addnewrecipientpage");
							rd.forward(request, response);
							return;
						}
					}

				}

			}

		} catch (Exception e) {
			request
					.setAttribute(
							"message",
							"Es ist ein Fehler aufgetreten. Bitte wiederholen Sie den Vorgang. Tritt dieser Fehler wiederholt auf, wenden Sie sich bitte an den Administrator.");
			rd = request
					.getRequestDispatcher("Dispatcher?identity=showlistofcontactpersonspage");
			rd.forward(request, response);
			logger.error("AddRecipientServlet - Fehler aufgetreten: "+e.getMessage());
		}
		// Ende doPost
	}
}
