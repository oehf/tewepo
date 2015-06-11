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
import org.openehealth.twp.tewepo.helper.SessionGuard;


/**
 * Servlet implementation to show saved DICOM mail recipients.
 * 
 * @author Benjamin Schneider
 * 
 */
@SuppressWarnings("serial")
public class ShowListOfDicomMailRecipientsServlet extends HttpServlet {

	private Logger logger = Logger.getLogger("webportal");//"webportal"

	/** die Konfiguration */
	private final static Configuration conf = Configuration.getMainConfig("");

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
	 * Shows the page with all DICOM mail recipients which are saved in the
	 * recipients list.
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

		DicomMailRecipients dicomMailRecipients = new DicomMailRecipients(
				getServletContext().getRealPath("/"));
		List<DicomMailRecipient> listDicomMailRecipients = dicomMailRecipients
				.getRecipients();

		request.setAttribute("listrecipients", listDicomMailRecipients);

		rd = request.getRequestDispatcher("showlistofcontactpersonspage.jsp");
		rd.forward(request, response);

	}

}
