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

/*
 * 
 *
 * Revised and updated from Nilay Yüksekogul
 * 2013
 * 
 * 
 */
package org.openehealth.twp.tewepo.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.openehealth.tewepo.twp.dmp.dmc.server.config.Configuration;
import org.openehealth.tewepo.twp.dmp.dmc.server.email.dicommail.FileTools;
import org.openehealth.twp.tewepo.helper.SessionGuard;


/**
 * This class is the distribution of the requests
 * 
 */
@SuppressWarnings("serial")
public class Dispatcher extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet {

	private final static Logger logger = Logger.getLogger(Dispatcher.class);

	/**
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
	 * HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * Forwards different requests.
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
	 *      HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// initialize Method
		// InitialConfiguration.initializeDatabase();

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		RequestDispatcher rd;
		// HttpSession session = request.getSession();
		// if(request.getSession()==null)
		// {
		// String
		// message="Ihre Session ist Abgelaufen, bitte melden Sie sich erneut am System an.";
		// rd = request.getRequestDispatcher("errorpage.jsp");
		// request
		// .setAttribute(
		// "message",
		// message);
		//
		// rd.forward(request, response);
		// }
		// validate request

		String identity = request.getParameter("identity");

		try {

			if (identity == null) {
				rd = request.getRequestDispatcher("errorpage.jsp");
				request.getSession().invalidate();
				rd.forward(request, response);
				return;
			}

			if (identity.equals("login")) {
				// forward to LoginServlet
				rd = request.getRequestDispatcher("/LoginServlet");
				rd.forward(request, response);
				return;
			} else if (identity.equals("homepage")) {
				logger.info("Dispatcher - Startseite aufgerufen");
				rd = request.getRequestDispatcher("homepage.jsp");
				rd.forward(request, response);
				return;
			} else if (identity.equals("loginpage")) {
				logger.info("Dispatcher - Anmeldeseite aufgerufen");
				rd = request.getRequestDispatcher("loginpage.jsp");
				rd.forward(request, response);
				return;
			/*} else if (identity.equals("doccheckloginpage")) {
				logger.info("Dispatcher - DocCheck Anmeldeseite aufgerufen");
				rd = request.getRequestDispatcher("doccheckloginpage.jsp");
				rd.forward(request, response);
				return;*/
			} else if (identity.equals("registration")) {
				logger.info("Dispatcher - Registierung abschließen!");
				rd = request.getRequestDispatcher("/RegistrationServlet");
				rd.forward(request, response);
				return;
			} else if (identity.equals("registrationpage")) {
				logger.info("Dispatcher - Registrierungsseite aufgerufen");
				rd = request.getRequestDispatcher("registrationpage.jsp");
				rd.forward(request, response);
				return;
			} else if (identity.equals("passwordrequest")) {
				logger.info("Dispatcher - Neues Passwort angefordert");
				rd = request.getRequestDispatcher("/KeywordRequestServlet");
				rd.forward(request, response);
				return;
			} else if (identity.equals("keywordpage")) {
				logger.info("Dispatcher - Passwort anfordern Seite aufgerufen");
				rd = request.getRequestDispatcher("keywordpage.jsp");
				rd.forward(request, response);
				return;
			} else if (identity.equals("usernamerequest")) {
				logger.info("Dispatcher - Benutzername angefordert");
				rd = request.getRequestDispatcher("/UsernameRequestServlet");
				rd.forward(request, response);
				return;
			} else if (identity.equals("usernamepage")) {
				logger.info("Dispatcher - Benutzername anfordern Seite aufgerufen");
				rd = request.getRequestDispatcher("usernamepage.jsp");
				rd.forward(request, response);
				return;
			} else if (identity.equals("sendmail")) {
				logger.info("Dispatcher - Benutzer sendet Kontakt e-Mail");
				rd = request.getRequestDispatcher("ContactServlet");
				rd.forward(request, response);
				return;
			} else if (identity.equals("contactpage")) {
				logger.info("Dispatcher - Kontaktseite aufgerufen");
				rd = request.getRequestDispatcher("contactpage.jsp");
				rd.forward(request, response);
				return;
			} else if (identity.equals("legalnoticepage")) {
				logger.info("Dispatcher - Impressum aufgerufen");
				rd = request.getRequestDispatcher("legalnoticepage.jsp");
				rd.forward(request, response);
				return;
			} else if (identity.equals("activateaccount")) {
				logger
						.info("Dispatcher - Benutzer will seinen Account freischalten");
				rd = request.getRequestDispatcher("/ActivationServlet");
				rd.forward(request, response);
				return;
			}

			SessionGuard.INSTANCE.validate(request);

			if (identity.equals("upload")) {
				logger.info("Dispatcher - Datei wird hochgeladen");
				rd = request.getRequestDispatcher("/UploadFileServlet");
				rd.forward(request, response);
			} else if (identity.equals("selectrole")) {
				logger.info("Dispatcher - Benutzer hat Rolle gew&auml;hlt");
				rd = request.getRequestDispatcher("/SelectRoleServlet");
				rd.forward(request, response);
			} else if (identity.equals("logout")) {
				logger.info("Dispatcher - Benutzer ausgeloggt");
				rd = request.getRequestDispatcher("homepage.jsp");
				
				String path=getServletContext().getRealPath("/"+Configuration.getMainConfig()
						.getProperty("tmpFolderPath")+"/"+request.getSession().getId());
				
				
				FileTools.deleteDir(path);
								
				request.getSession().invalidate();			
				rd.forward(request, response);
				
			} else if (identity.equals("addnewrecipientpage")) {
				logger
						.info("'Dispatcher - Neuer Empf&auml;nger'-Seite aufgerufen.");
				rd = request.getRequestDispatcher("addnewrecipientpage.jsp");
				rd.forward(request, response);
			} else if (identity.equals("addnewrecipient")) {
				logger
						.info("Dispatcher - Administrator fügt neuen Empf&auml;nger hinzu!");
				rd = request.getRequestDispatcher("/AddRecipientServlet");
				rd.forward(request, response);
			} else if (identity.equals("editrecipient")) {
				logger
						.info("Dispatcher - Administrator editiert die Empf&auml;ngerdaten!");
				rd = request.getRequestDispatcher("/AddRecipientServlet");
				rd.forward(request, response);
			} else if (identity.equals("deleterecipient")) {
				logger
						.info("Dispatcher - Administrator l&ouml;scht den Empf&auml;nger!");
				rd = request.getRequestDispatcher("/AddRecipientServlet");
				rd.forward(request, response);
			} else if (identity.equals("registration_AdminAddsNewPerson")) {
				logger
						.info("Dispatcher - Administrator f&uuml;gt neue Person hinzu!");
				rd = request.getRequestDispatcher("/RegistrationServlet");
				rd.forward(request, response);
			} else if (identity.equals("showuserdatapage")) {
				logger.info("Dispatcher - Anzeige der Userdaten aufgerufen");
				rd = request.getRequestDispatcher("showuserdatapage.jsp");
				rd.forward(request, response);
			} else if (identity.equals("useradministrationpage")) {
				logger.info("Dispatcher - Anzeige aller Userdaten aufgerufen");
				rd = request.getRequestDispatcher("/ShowAllUserServlet");
				rd.forward(request, response);
			} else if (identity.equals("adminedituserdatapage")) {
				logger.info("Dispatcher - Admin bearbeitet Benutzerdaten");
				rd = request.getRequestDispatcher("/AdminEditUserDataServlet");
				rd.forward(request, response);
			} else if (identity.equals("deleteperson")) {
				logger.info("Dispatcher - Admin l&ouml;scht Benutzer.");
				rd = request.getRequestDispatcher("/AdminEditUserDataServlet");
				rd.forward(request, response);
			} else if (identity.equals("saveadminedituserdata")) {
				logger.info("Dispatcher - Admin speichert Benutzerdaten");
				rd = request.getRequestDispatcher("/AdminEditUserDataServlet");
				rd.forward(request, response);
			} else if (identity.equals("adduserpage")) {
				logger.info("Dispatcher - Admin legt einen neuen Benutzer an.");
				rd = request.getRequestDispatcher("adduserpage.jsp");
				rd.forward(request, response);
			} else if (identity.equals("editperson")) {
				logger.info("Dispatcher - Benutzerdaten werden bearbeitet!");
				rd = request.getRequestDispatcher("/EditUserDataServlet");
				rd.forward(request, response);
			} else if (identity.equals("showlogfilespage")) {
				logger.info("Dispatcher - Log-Dateien-Anzeige aufgerufen");
				rd = request.getRequestDispatcher("/ShowLogFilesServlet");
				rd.forward(request, response);
			} else if (identity.equals("openLogFile")) {
				logger.info("Dispatcher - Log-Datei aufgerufen");
				rd = request.getRequestDispatcher("/ShowLogFilesServlet");
				rd.forward(request, response);
			} else if (identity.equals("showlistofcontactpersonspage")) {
				logger.info("Dispatcher - Partnerliste-Anzeige aufgerufen");
				rd = request
						.getRequestDispatcher("/ShowListOfDicomMailRecipientsServlet");
				rd.forward(request, response);
			} else if (identity.equals("showlistofcontactpersonspage")) {
				logger.info("Dispatcher - Partnerliste-Anzeige aufgerufen");
				rd = request
						.getRequestDispatcher("showlistofcontactpersonspage.jsp");
				rd.forward(request, response);
			} else if (identity.equals("systemconfigurationpage")) {
				logger.info("Dispatcher - Systemkonfiguration aufgerufen.");
				rd = request
						.getRequestDispatcher("systemconfigurationpage.jsp");
				rd.forward(request, response);
			} else if (identity.equals("showsystemconf")) {
				logger
						.info("Dispatcher - Systemkonfigurationsdaten werden angezeigt.");
				rd = request
						.getRequestDispatcher("/SystemconfigurationServlet");
				rd.forward(request, response);
			} else if (identity.equals("showsystemconfigurationpage")) {
				logger
						.info("Dispatcher - Systemkonfigurationsdaten werden angezeigt.");
				rd = request
						.getRequestDispatcher("showsystemconfigurationpage.jsp");
				rd.forward(request, response);
			} else if (identity.equals("systemconf")) {
				logger
						.info("Dispatcher - Systemkonfigurationsdaten wird angezeigt.");
				rd = request
						.getRequestDispatcher("/SystemconfigurationServlet");
				rd.forward(request, response);
				
			} else if (identity.equals("editsystemconf")) {
				logger.info("Dispatcher - Systemkonfigurationsdaten werden bearbeitet.");
				rd = request.getRequestDispatcher("/SystemconfigurationServlet");
				rd.forward(request, response);
			
			} else if (identity.equals("dicomemailpage")) {
				
				logger.info("Dispatcher - DICOM E-Mail Servlet aufgerufen");
				
System.out.println("DEBUG MODE KONFIGURATION: "+Configuration.getMainConfig().getProperty("debugMode"));
				
				request.setAttribute("debugMode", Configuration.getMainConfig().getProperty("debugMode"));
				
				rd = request.getRequestDispatcher("/DicomEmailServlet");
				rd.forward(request, response);
						
				
			}else if (identity.equals("dicomemailpage.jsp")) {
				
				logger.info("Dispatcher - DICOM E-Mail Seite aufgerufen");
				
				System.out.println("DEBUG MODE KONFIGURATION: "+Configuration.getMainConfig().getProperty("debugMode"));
				
				request.setAttribute("debugMode", Configuration.getMainConfig().getProperty("debugMode"));
				
				rd = request.getRequestDispatcher("dicomemailpage.jsp");
				rd.forward(request, response);
						
				
			} else if (identity.equals("debug_sendMail")) {
				
				logger.info("Dispatcher - Debug SendMail wurde aufgerufen");
				rd = request.getRequestDispatcher("/DebugSendMailServlet");
				rd.forward(request, response);
				
			} else if (identity.equals("debug_copyMail")) {
				
				logger.info("Dispatcher - Debug CreateMail wurde aufgerufen");
				rd = request.getRequestDispatcher("/CopyTestMailsServlet");
				rd.forward(request, response);
			}

			// if identity unknown -> logout
			else {
				rd = request.getRequestDispatcher("errorpage.jsp");
				request.getSession().invalidate();
				rd.forward(request, response);
			}
		} catch (ServletException se) {
			se.printStackTrace();
			logger.error("Dispatcher - Internel Error: " + se.getMessage());
			String message = "Es ist ein interner Fehler aufgetreten, bitte melden Sie sich erneut am System an. Sollte der Fehler wiederholt auftreten, wenden Sie sich bitte an den Administrator.";
			rd = request.getRequestDispatcher("errorpage.jsp");
			request.setAttribute("message", message);

			rd.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Dispatcher - System is not available "
					+ e.getMessage());
			rd = request.getRequestDispatcher("errorpage.jsp");
			request.getSession().invalidate();
			request
					.setAttribute(
							"message",
							"System momentan nicht verf&uuml;gbar.<br>Bitte versuchen Sie es sp&auml;ter noch einmal!");

			rd.forward(request, response);

		}

	}

	/**
	 * Deletes the session of the user, forwards to error page and shows
	 * Utility.UNERLAUBTER_ZUGRIFF.
	 * 
	 * @param request
	 * @param response
	 * 
	 * @throws ServletException
	 * @throws IOException
	 *             {@link IOException}
	 */
	public static void invalidateSession(HttpServletRequest request,
			HttpServletResponse response, String wherefrom, String reason)
			throws ServletException, IOException {
		request.setAttribute("message", "UNERLAUBTER ZUGRIFF");
		// remove Session 
		request.getSession().invalidate();

		logger.warn("Dispatcher - Aussperrung: " + wherefrom + " - " + reason);

		// return to login page
		RequestDispatcher rd = request.getRequestDispatcher("errorpage.jsp");
		rd.forward(request, response);
	}

}