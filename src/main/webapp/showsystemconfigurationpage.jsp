<%--
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
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.openehealth.twp.tewepo.helper.IPUtils"%>
<%
	//String ip = request.getRemoteAddr();
	String ip = IPUtils.getIpFromRequest(request);	

	if (!LockedIP.isActive(ip)) {
		String errorPath = "errorpage.jsp?message=Ihr Rechner ist gesperrt! Versuchen Sie es später noch einmal!";

	%>
		<jsp:forward page="<%=errorPath%>"/>
	<%
	}
	Configuration configuration = (Configuration) request
			.getAttribute("showsystemconf");

	String path = request.getContextPath().toString();
	
	boolean debugMode= false;
	debugMode = Boolean.parseBoolean((String) request.getAttribute("server_debugMode"));
	
	boolean mailSmtpStarttlsEnable= false;
	mailSmtpStarttlsEnable = Boolean.parseBoolean((String) request.getAttribute("server_mailSmtpStarttlsEnable"));
	
	boolean client_mailserver_hd_MailSmtpStarttlsEnable= false;											
	client_mailserver_hd_MailSmtpStarttlsEnable = Boolean.parseBoolean((String) request.getAttribute("client_mailserver_hd_mailSmtpStarttlsEnable"));
	
	boolean client_mailserver_ma_MailSmtpStarttlsEnable= false;
	client_mailserver_ma_MailSmtpStarttlsEnable = Boolean.parseBoolean((String) request.getAttribute("client_mailserver_ma_mailSmtpStarttlsEnable"));
	
	boolean client_mailserver_ka_MailSmtpStarttlsEnable= false;
	client_mailserver_ka_MailSmtpStarttlsEnable = Boolean.parseBoolean((String) request.getAttribute("client_mailserver_ka_mailSmtpStarttlsEnable"));
	
	String message = (String) request.getAttribute("message");
	if (message == null)
		message = "";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.openehealth.twp.tewepo.businesslogic.LockedIP"%>
<%@page import="org.openehealth.twp.tewepo.configuration.Configuration"%>
<%@page
	import="org.openehealth.tewepo.twp.dmp.dmc.server.email.xml.DicomMailRecipient"%>
<%@page import="java.util.ArrayList"%>


<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

			<title>Teleradiologie Webportal</title>
			<link rel="stylesheet" href="<%=path%>/style/style.css" type="text/css">
			<style type="text/css" media="all">
<!--
				#navigation a#id_a_de_systemadministrationpage {
					font-weight: bold;
				}
				
				#navigation a#id_a_de_systemadministrationpage {
					font-weight: bold;
				}
				
				#navigation li#id_li_de_systemadministrationpage {
					background-color: #efefef;
				}
				
				#navigation li#id_li_de_systemadministrationpage {
					background-color: #efefef;
				}
				
				#content a#horizontalmenuetable_a_systemconfigurationpage {
					font-weight: bold;
				}
				
				#content td#horizontalmenuetable_td_showlogfilepage {
					border-bottom: 2px solid #990000
				}
				
				#content td#horizontalmenuetable_td_showlistofcontactpersonspage {
					border-bottom: 2px solid #990000
				}
				
				#content td#horizontalmenuetable_td_systemconfigurationpage {
					background-image: url(images/tabs/tab_active.gif)
				}
-->
			</style>
	</head>

	<body>

		<%@ include file="include/header.jsp"%>
		<div id="page">
		<%
			if (ADMINISTRATOR) {
		%> <%@ include file="include/menuadmin.jsp"%> <%
		 	}
		 %> <%
		 	if (PHYSICIAN) {
		 %> <%@ include file="include/menuphysician.jsp"%>
		<%
			}
		%> <%
		 	if (PATIENT) {
		 %> <%@ include file="include/menupatient.jsp"%>
		<%
			}
		%> <%
		 	if (!PATIENT && !PHYSICIAN && !ADMINISTRATOR) {
		 %> <%@ include file="include/mainmenu.jsp"%> <%
		 	}
		 %>

			<div id="content">

				<h1>Systemverwaltung</h1>
				<img src="images/horizontaldashedseperator.gif"
					style="margin-top: 5px; margin-bottom: 15px;">
				<h2>Systemkonfiguration</h2>
				
				<p>Diese Seite beinhaltet die Systemverwaltung des Webportals.</p>
				<br>
				<%
					if (ADMINISTRATOR) {
				%> <%@ include file="include/submenusystemadministration.jsp"%>
				<%
					} else {
						Dispatcher.invalidateSession(request, response,
								"showsystemconfigurationpage.jsp",
								"Unerlaubter Zugriff");
					}
				%> <br>

					<div id="message" style="display: inline; color: #990000;"><%=message%></div>
					
					<h2>Systemkonfiguration:</h2>
					<p>Zur Überahme der Konfigurationseinstellungen muss der Webserver neugestartet werden.</p><br>
					
					<table class="tablenew">
						<tr>
							<th colspan="5" rowspan="1">Allgemeine Server Einstellungen</th>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>Pfad für Logdateien:</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								<%=(String) request.getAttribute("server_pathLogFiles")%>
							</td>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>Client Konfig.-Dateipfad:</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								<%=(String) request.getAttribute("server_pathClientConfigFile")%>
							</td>
							<td width="40px">
								<a href="<%=request.getContextPath().toString()%>/Dispatcher?identity=systemconf&amp;block=server_pfad">
									<img src="images/pencil.gif" alt="bearbeiten" title="bearbeiten">
								</a>
							</td>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>Empfängerliste:</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								<%=(String) request.getAttribute("server_filenameXMLFileRecipients")%>
							</td>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>Debug Mode:</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								<input type="checkbox" name="server_debugMode" value="server_debugMode" <%if (debugMode) {%> checked="checked" <%}%>
										style="width: 15px; border:none;" disabled="disabled">
							</td>
						</tr>
					</table>
					<br>
					<table class="table">
						<tr>
							<th colspan="5" rowspan="1">Support Mail Einstellungen</th>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>Benutzername:</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								<%=(String) request.getAttribute("server_mailUsername")%>
							</td>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>Passwort:</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								<%="*****"%>
							</td>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>Senderadresse:</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								<%=(String) request.getAttribute("server_mailSenderAddress")%>
							</td>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>SMTP Host:</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								<%=(String) request.getAttribute("server_mailSmtpHost")%>
							</td>
							<td width="40px">
								<a href="<%=request.getContextPath().toString()%>/Dispatcher?identity=systemconf&amp;block=server_mail">
									<img src="images/pencil.gif" alt="Daten bearbeiten" title="Daten bearbeiten">
								</a>
							</td>	
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>Default Mailempfänger:</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								<%=(String) request.getAttribute("server_defaultMailRecipient")%>
							</td>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>SMTP Port:</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								<%=(String) request.getAttribute("server_mailSmtpPort")%>
							</td>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>Starttls:</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								<input type="checkbox" name="server_mailSmtpStarttlsEnable" value="server_mailSmtpStarttlsEnable" 
									<%if (mailSmtpStarttlsEnable) {%> checked="checked" <%}%>
										style="width: 15px; border:none;" disabled="disabled">
							</td>
						</tr>
					</table>
					<br>
					<table class="table">
						<tr>
							<th colspan="5" rowspan="1">Allgemeine Client Pfadeinstellungen</th>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>Temp. Ordnerpfad:</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								<%=(String) request.getAttribute("client_tmpFolderPath")%>
							</td>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>Pfad für Logdateien:</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								<%=(String) request.getAttribute("client_pathLogFiles")%>
							</td>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>GnupG Anwendungspfad:</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								<%=(String) request.getAttribute("client_gnupgApplicationPath")%>
							</td>
						    <td width="40px">
						    	<a	href="<%=request.getContextPath().toString()%>/Dispatcher?identity=systemconf&amp;block=client_pfad">
						    		<img src="images/pencil.gif" alt="bearbeiten" title="bearbeiten">	
								</a>
							</td>
						</tr>
					</table>
					<br>
					<table class="table">
						<tr>
							<th colspan="5" rowspan="1">Allgemeine DICOM Mail Portal Einstellungen</th>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>Anzahl Login-Versuche:</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								<%=(String) request.getAttribute("client_numberOfAllowedAffords")%>
							</td>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>IP Sperrzeit (ms):</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								<%=(String) request.getAttribute("client_lockedIPwaitingTime")%>
							</td>
							<td width="40px">
						    	<a	href="<%=request.getContextPath().toString()%>/Dispatcher?identity=systemconf&amp;block=client_ip">
						    		<img src="images/pencil.gif" alt="bearbeiten" title="bearbeiten">	
								</a>
							</td>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>Letzte Empfänger (Anz.):</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								<%=(String) request.getAttribute("client_countViewLastRecipients")%>
							</td>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>Splitgröße (Byte):</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								<%=(String) request.getAttribute("client_splitSize")%>
							</td>
						</tr>
					</table>
					<br>
					<table class="table">
						<tr>
							<th colspan="5" rowspan="1">DICOM Mail PGP-Key Einstellungen</th>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>ID-Portal private key:</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								<%=(String) request.getAttribute("client_userPrivateKey")%>
							</td>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>Passwort private key:</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								*****
							</td>
							<td width="40px">
								<a href="<%=request.getContextPath().toString()%>/Dispatcher?identity=systemconf&amp;block=client_dicom">
									<img src="images/pencil.gif" alt="bearbeiten" title="bearbeiten">
								</a>
							</td>
						</tr>
					</table>
					<br>
					<table class="table">
						<tr>
							<th colspan="5" rowspan="1">DICOM Mail - Einstellungen Mailserver HD</th>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>Benutzername:</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								<%=(String) request.getAttribute("client_mailserver_hd_mailUsername")%>
							</td>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>Passwort:</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								<%="*****"%>
							</td>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>Senderadresse:</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								<%=(String) request.getAttribute("client_mailserver_hd_mailSenderAddress")%>
							</td>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>SMTP Host:</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								<%=(String) request.getAttribute("client_mailserver_hd_mailSmtpHost")%>
							</td>
							<td width="40px">
								<a href="<%=request.getContextPath().toString()%>/Dispatcher?identity=systemconf&amp;block=client_mailserver_hd_mail">
									<img src="images/pencil.gif" alt="Daten bearbeiten" title="Daten bearbeiten">
								</a>
							</td>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>Default Mailempfänger:</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								<%=(String) request.getAttribute("client_mailserver_hd_defaultMailRecipient")%>
							</td>
						</tr>
						
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>SMTP Port:</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								<%=(String) request.getAttribute("client_mailserver_hd_mailSmtpPort")%>
							</td>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>Starttls:</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								<input type="checkbox" name="client_mailserver_hd_mailSmtpStarttlsEnable" value="client_mailserver_hd_mailSmtpStarttlsEnable" 
									<%if (client_mailserver_hd_MailSmtpStarttlsEnable) {%> checked="checked" <%}%>
										style="width: 15px; border:none;" disabled="disabled">
							</td>
						</tr>
					</table>
					<table class="table">
						<tr>
							<th colspan="5" rowspan="1">DICOM Mail - Einstellungen Mailserver MA</th>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>Benutzername:</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								<%=(String) request.getAttribute("client_mailserver_ma_mailUsername")%>
							</td>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>Passwort:</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								<%="*****"%>
							</td>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>Senderadresse:</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								<%=(String) request.getAttribute("client_mailserver_ma_mailSenderAddress")%>
							</td>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>SMTP Host:</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								<%=(String) request.getAttribute("client_mailserver_ma_mailSmtpHost")%>
							</td>
							<td width="40px">
								<a href="<%=request.getContextPath().toString()%>/Dispatcher?identity=systemconf&amp;block=client_mailserver_ma_mail">
									<img src="images/pencil.gif" alt="Daten bearbeiten" title="Daten bearbeiten">
								</a>
							</td>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>Default Mailempfänger:</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								<%=(String) request.getAttribute("client_mailserver_ma_defaultMailRecipient")%>
							</td>
						</tr>
						
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>SMTP Port:</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								<%=(String) request.getAttribute("client_mailserver_ma_mailSmtpPort")%>
							</td>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>Starttls:</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								<input type="checkbox" name="client_mailserver_ma_mailSmtpStarttlsEnable" value="client_mailserver_ma_mailSmtpStarttlsEnable" 
									<%if (client_mailserver_ma_MailSmtpStarttlsEnable) {%> checked="checked" <%}%>
										style="width: 15px; border:none;" disabled="disabled">
							</td>
						</tr>
					</table>
					<table class="table">
						<tr>
							<th colspan="5" rowspan="1">DICOM Mail - Einstellungen Mailserver KA</th>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>Benutzername:</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								<%=(String) request.getAttribute("client_mailserver_ka_mailUsername")%>
							</td>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>Passwort:</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								<%="*****"%>
							</td>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>Senderadresse:</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								<%=(String) request.getAttribute("client_mailserver_ka_mailSenderAddress")%>
							</td>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>SMTP Host:</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								<%=(String) request.getAttribute("client_mailserver_ka_mailSmtpHost")%>
							</td>
							<td width="40px">
								<a href="<%=request.getContextPath().toString()%>/Dispatcher?identity=systemconf&amp;block=client_mailserver_ka_mail">
									<img src="images/pencil.gif" alt="Daten bearbeiten" title="Daten bearbeiten">
								</a>
							</td>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>Default Mailempfänger:</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								<%=(String) request.getAttribute("client_mailserver_ka_defaultMailRecipient")%>
							</td>
						</tr>
						
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>SMTP Port:</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								<%=(String) request.getAttribute("client_mailserver_ka_mailSmtpPort")%>
							</td>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 40%;">
								<b>Starttls:</b>
							</td>
							<td style="table-layout: fixed; word-break:break-all; word-wrap:break-word; width: 75%;">
								<input type="checkbox" name="client_mailserver_ka_mailSmtpStarttlsEnable" value="client_mailserver_ka_mailSmtpStarttlsEnable" 
									<%if (client_mailserver_ka_MailSmtpStarttlsEnable) {%> checked="checked" <%}%>
										style="width: 15px; border:none;" disabled="disabled">
							</td>
						</tr>
					</table>
					<br>
					<img src="images/horizontalerTenner.gif"
						style="margin-top: 15px; margin-bottom: 15px;">
					<div id="guideline">
					
						<p>
							<b>Hinweis:</b>
						</p>
						Zum Bearbeiten der Konfigurationsdaten auf <img src="images/pencil.gif"
						alt="Daten berbeiten" title="Daten bearbeiten"> klicken.<br>
						Um den Debug Mode einzuschalten müssen Sie unter "Systemverwaltung"<br> 
						den Reiter "Systemkonfiguration" wählen und die Konfiguration "Debug Mode"<br> 
						unter "Server - Pfadeinstellungen" aktivieren.
					</div>
					<!-- /hinweis--> <br clear="all" />

				</div>

			<%@ include file="include/footer.jsp"%>
		</div>

	</body>
</html>