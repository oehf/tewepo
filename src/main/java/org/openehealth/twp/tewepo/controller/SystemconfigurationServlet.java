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
import org.openehealth.twp.tewepo.configuration.Configuration;


/**
 * Servlet implementation for editing systemconfiguration.
 * 
 * @author Benjamin Schneider
 */
@SuppressWarnings("serial")
public class SystemconfigurationServlet extends HttpServlet {

	private Logger logger = Logger.getLogger("webportal");

	// configuration for server component
	private final static Configuration confPortalServer = Configuration
			.getMainConfig("");
	// configuration for the client component
	private static Configuration confPortalClient = null;

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
	 * Loads, and enables to change the systemconfiguration. Changes will be
	 * visible and valid after saving.
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		RequestDispatcher rd = null;

		String server_pathClientConfigFile = confPortalServer
				.getProperty("pathClientConfigFile");
		try {
			confPortalClient = Configuration.getMainConfig(getServletContext()
					.getRealPath("/")
					+ server_pathClientConfigFile);
			
		} catch (Error e) {
			logger.error("SystemconfigurationServlet - Clientkonfiguration konnte nicht geladen werden, bitte prüfen Sie den Pfad der Datei!");
			logger.error("SystemconfigurationServlet - " + e.getStackTrace());
			confPortalClient = null;
		}

		String block = request.getParameter("block");

		String client_tmpFolderPath = request
				.getParameter("client_tmpFolderPath");
		String client_countViewLastRecipients = request
				.getParameter("client_countViewLastRecipients");
		String client_splitSize = request.getParameter("client_splitSize");
		String client_pathLogFiles = request
				.getParameter("client_pathLogFiles");
		String client_gnupgApplicationPath = request
				.getParameter("client_gnupgApplicationPath");
		String client_numberOfAllowedAffords = request
				.getParameter("client_numberOfAllowedAffords");
		String client_lockedIPwaitingTime = request
				.getParameter("client_lockedIPwaitingTime");

		String client_mailserver_hd_mailUsername = request
				.getParameter("client_mailserver_hd_mailUsername");
		String client_mailserver_hd_mailPassword = request
				.getParameter("client_mailserver_hd_mailPassword");
		String client_mailserver_hd_mailSenderAddress = request
				.getParameter("client_mailserver_hd_mailSenderAddress");
		String client_mailserver_hd_mailSmtpHost = request
				.getParameter("client_mailserver_hd_mailSmtpHost");
		String client_mailserver_hd_defaultMailRecipient = request
				.getParameter("client_mailserver_hd_defaultMailRecipient");
		String client_mailserver_hd_mailSmtpPort = request
				.getParameter("client_mailserver_hd_mailSmtpPort");
		String client_mailserver_hd_mailSmtpStarttlsEnable = request
				.getParameter("client_mailserver_hd_mailSmtpStarttlsEnable");

		String client_mailserver_ma_mailUsername = request
				.getParameter("client_mailserver_ma_mailUsername");
		String client_mailserver_ma_mailPassword = request
				.getParameter("client_mailserver_ma_mailPassword");
		String client_mailserver_ma_mailSenderAddress = request
				.getParameter("client_mailserver_ma_mailSenderAddress");
		String client_mailserver_ma_mailSmtpHost = request
				.getParameter("client_mailserver_ma_mailSmtpHost");
		String client_mailserver_ma_defaultMailRecipient = request
				.getParameter("client_mailserver_ma_defaultMailRecipient");
		String client_mailserver_ma_mailSmtpPort = request
				.getParameter("client_mailserver_ma_mailSmtpPort");
		String client_mailserver_ma_mailSmtpStarttlsEnable = request
				.getParameter("client_mailserver_ma_mailSmtpStarttlsEnable");

		String client_mailserver_ka_mailUsername = request
				.getParameter("client_mailserver_ka_mailUsername");
		String client_mailserver_ka_mailPassword = request
				.getParameter("client_mailserver_ka_mailPassword");
		String client_mailserver_ka_mailSenderAddress = request
				.getParameter("client_mailserver_ka_mailSenderAddress");
		String client_mailserver_ka_mailSmtpHost = request
				.getParameter("client_mailserver_ka_mailSmtpHost");
		String client_mailserver_ka_defaultMailRecipient = request
				.getParameter("client_mailserver_ka_defaultMailRecipient");
		String client_mailserver_ka_mailSmtpPort = request
				.getParameter("client_mailserver_ka_mailSmtpPort");
		String client_mailserver_ka_mailSmtpStarttlsEnable = request
				.getParameter("client_mailserver_ka_mailSmtpStarttlsEnable");

		String client_userPrivateKey = request
				.getParameter("client_userPrivateKey");
		String client_passwordPrivateKey = request
				.getParameter("client_passwordPrivateKey");
		String server_pathLogFiles = request
				.getParameter("server_pathLogFiles");
		String server_filenameXMLFileRecipients = request
				.getParameter("server_filenameXMLFileRecipients");
		String server_mailUsername = request
				.getParameter("server_mailUsername");
		String server_mailPassword = request
				.getParameter("server_mailPassword");
		String server_mailSenderAddress = request
				.getParameter("server_mailSenderAddress");
		String server_mailSmtpHost = request
				.getParameter("server_mailSmtpHost");
		String server_defaultMailRecipient = request
				.getParameter("server_defaultMailRecipient");
		String server_mailSmtpPort = request
				.getParameter("server_mailSmtpPort");
		String server_mailSmtpStarttlsEnable = request
				.getParameter("server_mailSmtpStarttlsEnable");
		String server_debugMode = request.getParameter("server_debugMode");

		request.setAttribute("message", "");// remove old message
		String identity = request.getParameter("identity");

		if (identity.equals("systemconf") || identity.equals("showsystemconf")) {
			request.setAttribute("block", block);

			request.setAttribute("server_filenameXMLFileRecipients",
					confPortalServer.getProperty("filenameXMLFileRecipients"));
			request.setAttribute("server_pathLogFiles", confPortalServer
					.getProperty("pathLogFiles"));
			request.setAttribute("server_pathClientConfigFile",
					confPortalServer.getProperty("pathClientConfigFile"));
			
			request.setAttribute("server_debugMode", confPortalClient
					.getProperty("debugMode"));
			
			
			request.setAttribute("server_mailUsername", confPortalServer
					.getProperty("mailUsername"));
			request.setAttribute("server_mailPassword", confPortalServer
					.getProperty("mailPassword"));
			request.setAttribute("server_mailSenderAddress", confPortalServer
					.getProperty("mailSenderAddress"));
			request.setAttribute("server_mailSmtpHost", confPortalServer
					.getProperty("mailSmtpHost"));
			request.setAttribute("server_defaultMailRecipient",
					confPortalServer.getProperty("defaultMailRecipient"));
			request.setAttribute("server_mailSmtpPort", confPortalServer
					.getProperty("mailSmtpPort"));
			request.setAttribute("server_mailSmtpStarttlsEnable",
					confPortalServer.getProperty("mailSmtpStarttlsEnable"));

			if (confPortalClient != null) {
				try {
					request.setAttribute("client_tmpFolderPath",
							confPortalClient.getProperty("tmpFolderPath"));
					request.setAttribute("client_pathLogFiles",
							confPortalClient.getProperty("pathLogFiles"));
					request.setAttribute("client_gnupgApplicationPath",
							confPortalClient
									.getProperty("gnupgApplicationPath"));

					request.setAttribute("client_numberOfAllowedAffords",
							confPortalClient
									.getProperty("numberOfAllowedAffords"));
					request
							.setAttribute("client_lockedIPwaitingTime",
									confPortalClient
											.getProperty("lockedIPwaitingTime"));
					request.setAttribute("client_userPrivateKey",
							confPortalClient.getProperty("userPrivateKey"));
					request.setAttribute("client_passwordPrivateKey",
							confPortalClient.getProperty("passwordPrivateKey"));

					
					request.setAttribute("client_mailserver_hd_mailUsername",
							confPortalClient
									.getProperty("mailserver_hd_mailUsername"));

					request.setAttribute("client_mailserver_hd_mailPassword",
							confPortalClient
									.getProperty("mailserver_hd_mailPas"
											+ "sword"));
					request
							.setAttribute(
									"client_mailserver_hd_mailSenderAddress",
									confPortalClient
											.getProperty("mailserver_hd_mailSenderAddress"));
					request.setAttribute("client_mailserver_hd_mailSmtpHost",
							confPortalClient
									.getProperty("mailserver_hd_mailSmtpHost"));
					request
							.setAttribute(
									"client_mailserver_hd_defaultMailRecipient",
									confPortalClient
											.getProperty("mailserver_hd_defaultMailRecipient"));
					request.setAttribute("client_mailserver_hd_mailSmtpPort",
							confPortalClient
									.getProperty("mailserver_hd_mailSmtpPort"));
					String tmp=confPortalClient
					.getProperty("mailserver_hd_mailSmtpStarttlsEnable");
					request
							.setAttribute(
									"client_mailserver_hd_mailSmtpStarttlsEnable",
									confPortalClient
											.getProperty("mailserver_hd_mailSmtpStarttlsEnable"));

					request.setAttribute("client_mailserver_ma_mailUsername",
							confPortalClient
									.getProperty("mailserver_ma_mailUsername"));

					request.setAttribute("client_mailserver_ma_mailPassword",
							confPortalClient
									.getProperty("mailserver_ma_mailPas"
											+ "sword"));
					request
							.setAttribute(
									"client_mailserver_ma_mailSenderAddress",
									confPortalClient
											.getProperty("mailserver_ma_mailSenderAddress"));
					request.setAttribute("client_mailserver_ma_mailSmtpHost",
							confPortalClient
									.getProperty("mailserver_ma_mailSmtpHost"));
					request
							.setAttribute(
									"client_mailserver_ma_defaultMailRecipient",
									confPortalClient
											.getProperty("mailserver_ma_defaultMailRecipient"));
					request.setAttribute("client_mailserver_ma_mailSmtpPort",
							confPortalClient
									.getProperty("mailserver_ma_mailSmtpPort"));
					request
							.setAttribute(
									"client_mailserver_ma_mailSmtpStarttlsEnable",
									confPortalClient
											.getProperty("mailserver_ma_mailSmtpStarttlsEnable"));

					request.setAttribute("client_mailserver_ka_mailUsername",
							confPortalClient
									.getProperty("mailserver_ka_mailUsername"));

					request.setAttribute("client_mailserver_ka_mailPassword",
							confPortalClient
									.getProperty("mailserver_ka_mailPas"
											+ "sword"));
					request
							.setAttribute(
									"client_mailserver_ka_mailSenderAddress",
									confPortalClient
											.getProperty("mailserver_ka_mailSenderAddress"));
					request.setAttribute("client_mailserver_ka_mailSmtpHost",
							confPortalClient
									.getProperty("mailserver_ka_mailSmtpHost"));
					request
							.setAttribute(
									"client_mailserver_ka_defaultMailRecipient",
									confPortalClient
											.getProperty("mailserver_ka_defaultMailRecipient"));
					request.setAttribute("client_mailserver_ka_mailSmtpPort",
							confPortalClient
									.getProperty("mailserver_ka_mailSmtpPort"));
					request
							.setAttribute(
									"client_mailserver_ka_mailSmtpStarttlsEnable",
									confPortalClient
											.getProperty("mailserver_ka_mailSmtpStarttlsEnable"));

					request.setAttribute("client_countViewLastRecipients",
							confPortalClient
									.getProperty("countViewLastRecipients"));
					request.setAttribute("client_splitSize", confPortalClient
							.getProperty("splitSize"));

				} catch (Error e) {
					logger
							.error("Clientkonfiguration konnte nicht geladen werden, bitte pr&uuml;fen Sie den Pfad der Datei!");
					logger.error(e.getStackTrace());
					confPortalClient = null;
				}
			}

			if (identity.equals("systemconf")) {
				rd = request
						.getRequestDispatcher("Dispatcher?identity=systemconfigurationpage");
			} else {
				rd = request
						.getRequestDispatcher("Dispatcher?identity=showsystemconfigurationpage");
			}
			rd.forward(request, response);

		} else if (identity.equals("editsystemconf")) {

			if (block != null) {
				try {
					request.setAttribute("block", block);

					if (server_pathClientConfigFile != null)
						confPortalServer.setProperty("pathClientConfigFile",
								server_pathClientConfigFile);

					// if (server_pathLogFiles != null)
					// confPortalServer.setProperty("pathClientConfigFile",
					// server_pathClientConfigFile);
					if (server_pathLogFiles != null)
						confPortalServer.setProperty("pathLogFiles",
								server_pathLogFiles);
					if (server_filenameXMLFileRecipients != null)
						confPortalServer.setProperty(
								"filenameXMLFileRecipients",
								server_filenameXMLFileRecipients);
					if (server_debugMode != null)
						confPortalClient.setProperty("debugMode",
								server_debugMode);
					if (server_mailUsername != null)
						confPortalServer.setProperty("mailUsername",
								server_mailUsername);
					if (server_mailPassword != null)
						confPortalServer.setProperty("mailPassword",
								server_mailPassword);
					if (server_mailSenderAddress != null)
						confPortalServer.setProperty("mailSenderAddress",
								server_mailSenderAddress);
					if (server_mailSmtpHost != null)
						confPortalServer.setProperty("mailSmtpHost",
								server_mailSmtpHost);
					if (server_defaultMailRecipient != null)
						confPortalServer.setProperty("defaultMailRecipient",
								server_defaultMailRecipient);
					if (server_mailSmtpPort != null)
						confPortalServer.setProperty("mailSmtpPort",
								server_mailSmtpPort);
					if (server_mailSmtpStarttlsEnable != null)
						confPortalServer.setProperty("mailSmtpStarttlsEnable",
								server_mailSmtpStarttlsEnable);
					if (client_tmpFolderPath != null)
						confPortalClient.setProperty("tmpFolderPath",
								client_tmpFolderPath);
					if (server_pathLogFiles != null)
						confPortalClient.setProperty("pathLogFiles",
								server_pathLogFiles);
					if (client_gnupgApplicationPath != null)
						confPortalClient.setProperty("gnupgApplicationPath",
								client_gnupgApplicationPath);

					if (client_numberOfAllowedAffords != null)
						confPortalClient.setProperty("numberOfAllowedAffords",
								client_numberOfAllowedAffords);
					if (client_lockedIPwaitingTime != null)
						confPortalClient.setProperty("lockedIPwaitingTime",
								client_lockedIPwaitingTime);

					if (client_mailserver_hd_mailUsername != null)
						confPortalClient.setProperty("mailserver_hd_mailUsername",
								client_mailserver_hd_mailUsername);
					if (client_mailserver_hd_mailPassword != null)
						confPortalClient.setProperty("mailserver_hd_mailPassword",
								client_mailserver_hd_mailPassword);
					if (client_mailserver_hd_mailSenderAddress != null)
						confPortalClient.setProperty("mailserver_hd_mailSenderAddress",
								client_mailserver_hd_mailSenderAddress);
					if (client_mailserver_hd_mailSmtpHost != null)
						confPortalClient.setProperty("mailserver_hd_mailSmtpHost",
								client_mailserver_hd_mailSmtpHost);
					if (client_mailserver_hd_defaultMailRecipient != null)
						confPortalClient.setProperty("mailserver_hd_defaultMailRecipient",
								client_mailserver_hd_defaultMailRecipient);
					if (client_mailserver_hd_mailSmtpPort != null)
						confPortalClient.setProperty("mailserver_hd_mailSmtpPort",
								client_mailserver_hd_mailSmtpPort);
					if (client_mailserver_hd_mailSmtpStarttlsEnable != null)
						confPortalClient.setProperty("mailserver_hd_mailSmtpStarttlsEnable",
								client_mailserver_hd_mailSmtpStarttlsEnable);

					if (client_mailserver_ma_mailUsername != null)
						confPortalClient.setProperty("mailserver_ma_mailUsername",
								client_mailserver_ma_mailUsername);
					if (client_mailserver_ma_mailPassword != null)
						confPortalClient.setProperty("mailserver_ma_mailPassword",
								client_mailserver_ma_mailPassword);
					if (client_mailserver_ma_mailSenderAddress != null)
						confPortalClient.setProperty("mailserver_ma_mailSenderAddress",
								client_mailserver_ma_mailSenderAddress);
					if (client_mailserver_ma_mailSmtpHost != null)
						confPortalClient.setProperty("mailserver_ma_mailSmtpHost",
								client_mailserver_ma_mailSmtpHost);
					if (client_mailserver_ma_defaultMailRecipient != null)
						confPortalClient.setProperty("mailserver_ma_defaultMailRecipient",
								client_mailserver_ma_defaultMailRecipient);
					if (client_mailserver_ma_mailSmtpPort != null)
						confPortalClient.setProperty("mailserver_ma_mailSmtpPort",
								client_mailserver_ma_mailSmtpPort);
					if (client_mailserver_ma_mailSmtpStarttlsEnable != null)
						confPortalClient.setProperty("mailserver_ma_mailSmtpStarttlsEnable",
								client_mailserver_ma_mailSmtpStarttlsEnable);

					if (client_mailserver_ka_mailUsername != null)
						confPortalClient.setProperty("mailserver_ka_mailUsername",
								client_mailserver_ka_mailUsername);
					if (client_mailserver_ka_mailPassword != null)
						confPortalClient.setProperty("mailserver_ka_mailPassword",
								client_mailserver_ka_mailPassword);
					if (client_mailserver_ka_mailSenderAddress != null)
						confPortalClient.setProperty("mailserver_ka_mailSenderAddress",
								client_mailserver_ka_mailSenderAddress);
					if (client_mailserver_ka_mailSmtpHost != null)
						confPortalClient.setProperty("mailserver_ka_mailSmtpHost",
								client_mailserver_ka_mailSmtpHost);
					if (client_mailserver_ka_defaultMailRecipient != null)
						confPortalClient.setProperty("mailserver_ka_defaultMailRecipient",
								client_mailserver_ka_defaultMailRecipient);
					if (client_mailserver_ka_mailSmtpPort != null)
						confPortalClient.setProperty("mailserver_ka_mailSmtpPort",
								client_mailserver_ka_mailSmtpPort);
					if (client_mailserver_ka_mailSmtpStarttlsEnable != null)
						confPortalClient.setProperty("mailserver_ka_mailSmtpStarttlsEnable",
								client_mailserver_ka_mailSmtpStarttlsEnable);

					if (client_countViewLastRecipients != null)
						confPortalClient.setProperty("countViewLastRecipients",
								client_countViewLastRecipients);
					if (client_splitSize != null)
						confPortalClient.setProperty("splitSize",
								client_splitSize);
					if (client_userPrivateKey != null)
						confPortalClient.setProperty("userPrivateKey",
								client_userPrivateKey);
					if (client_passwordPrivateKey != null)
						confPortalClient.setProperty("passwordPrivateKey",
								client_passwordPrivateKey);

					confPortalServer.serialize();
					confPortalClient.serialize();

					if (block.equals("server_pfad")) {
						request.setAttribute("message",
								"Serverpfadeinstellungen gespeichert!");
						logger
								.info("SystemconfigurationServlet - Serverpfadeinstellungen gespeichert");
						rd = request
								.getRequestDispatcher("Dispatcher?identity=showsystemconf");
						rd.forward(request, response);

					} else if (block.equals("server_mail")) {
						request.setAttribute("message",
								"Servermaileinstellungen gespeichert!");
						logger
								.info("SystemconfigurationServlet - Servermaileinstellungen gespeichert");
						rd = request
								.getRequestDispatcher("Dispatcher?identity=showsystemconf");
						rd.forward(request, response);

					} else if (block.equals("client_pfad")) {
						request.setAttribute("message",
								"Clientpfadeinstellungen gespeichert!");
						rd = request
								.getRequestDispatcher("Dispatcher?identity=showsystemconf");
						logger
								.info("SystemconfigurationServlet - Clientpfadeinstellungen gespeichert");
						rd.forward(request, response);

					} else if (block.equals("client_ip")) {
						request.setAttribute("message",
								"Clientpfadeinstellungen gespeichert!");
						rd = request
								.getRequestDispatcher("Dispatcher?identity=showsystemconf");
						logger
								.info("SystemconfigurationServlet - Clientpfadeinstellungen gespeichert");
						rd.forward(request, response);

					} else if (block.equals("client_mailserver_hd_mail")) {
						request.setAttribute("message",
								"Clientmaileinstellungen gespeichert!");
						rd = request
								.getRequestDispatcher("Dispatcher?identity=showsystemconf");
						logger
								.info("SystemconfigurationServlet - Clientmaileinstellungen gespeichert");
						rd.forward(request, response);
					} else if (block.equals("client_mailserver_ma_mail")) {
						request.setAttribute("message",
								"Clientmaileinstellungen gespeichert!");
						rd = request
								.getRequestDispatcher("Dispatcher?identity=showsystemconf");
						logger
								.info("SystemconfigurationServlet - Clientmaileinstellungen gespeichert");
						rd.forward(request, response);

					} else if (block.equals("client_mailserver_ka_mail")) {
						request.setAttribute("message",
								"Clientmaileinstellungen gespeichert!");
						rd = request
								.getRequestDispatcher("Dispatcher?identity=showsystemconf");
						logger
								.info("SystemconfigurationServlet - Clientmaileinstellungen gespeichert");
						rd.forward(request, response);

					} else if (block.equals("client_dicom")) {
						request.setAttribute("message",
								"DICOM-Maileinstellungen gespeichert!");
						rd = request
								.getRequestDispatcher("Dispatcher?identity=showsystemconf");
						logger
								.info("SystemconfigurationServlet - DICOM-Maileinstellungen gespeichert");
						rd.forward(request, response);
					} else {
						logger
								.error("SystemconfigurationServlet - Failed to save data.");
						rd = request
								.getRequestDispatcher("Dispatcher?identity=showsystemconfigurationpage");
						rd.forward(request, response);
					}

				} catch (Exception e) {
					logger.error("SystemconfigurationServlet - "
							+ e.getMessage());
					rd = request
							.getRequestDispatcher("Dispatcher?identity=showsystemconfigurationpage");
					rd.forward(request, response);
				}
			} else {
				rd = request
						.getRequestDispatcher("Dispatcher?identity=showsystemconfigurationpage");
				rd.forward(request, response);

			}

		}

	}
}
