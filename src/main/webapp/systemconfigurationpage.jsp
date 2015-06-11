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
	String ip = IPUtils.getIpFromRequest(request);
	//String ip = request.getRemoteAddr();

	if (!LockedIP.isActive(ip)) {
		String errorPath = "errorpage.jsp?message=Ihr Rechner ist gesperrt! Versuchen Sie es später noch einmal!";
%>
<jsp:forward page="<%=errorPath%>" />
<%
	}
	Configuration conf = (Configuration) request
			.getAttribute("showsystemconfigurationpage");
	String path = request.getContextPath().toString();

	boolean debugMode = false;
	debugMode = Boolean.parseBoolean((String) request
			.getAttribute("server_debugMode"));

	boolean mailSmtpStarttlsEnable = false;
	mailSmtpStarttlsEnable = Boolean.parseBoolean((String) request
			.getAttribute("server_mailSmtpStarttlsEnable"));

	boolean client_mailserver_hd_MailSmtpStarttlsEnable = false;
	client_mailserver_hd_MailSmtpStarttlsEnable = Boolean
			.parseBoolean((String) request
					.getAttribute("client_mailserver_hd_mailSmtpStarttlsEnable"));
	
	
	boolean client_mailserver_ma_MailSmtpStarttlsEnable = false;
	client_mailserver_ma_MailSmtpStarttlsEnable = Boolean
			.parseBoolean((String) request
					.getAttribute("client_mailserver_ma_mailSmtpStarttlsEnable"));
	

	boolean client_mailserver_ka_MailSmtpStarttlsEnable = false;
	client_mailserver_ka_MailSmtpStarttlsEnable = Boolean
			.parseBoolean((String) request
					.getAttribute("client_mailserver_ka_mailSmtpStarttlsEnable"));
	

	String message = (String) request.getAttribute("message");
	if (message == null)
		message = "";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.openehealth.twp.tewepo.businesslogic.LockedIP"%>
<%@page import="org.openehealth.twp.tewepo.configuration.Configuration"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Teleradiologie Webportal</title>

<link rel="stylesheet" href="<%=path%>/style/style.css" type="text/css">

<script type="text/javascript" src="<%=path%>/js/pruefung.js"></script>
<script type="text/javascript">
//<!-- 
				function toggleDebugMode( elementID_1,elementID_2){
	
					if (document.getElementById(elementID_1).checked) {
						document.getElementById(elementID_2).value ='true';
					} else {
						document.getElementById(elementID_2).value ='false';
					}
				}
//-->
			</script>

<style type="text/css" media="all">
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
%> <%@ include file="include/menuadmin.jsp"%>
<%
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
 %> <%@ include file="include/mainmenu.jsp"%>
<%
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
				"systemconfigurationpage.jsp", "Unerlaubter Zugriff");
	}
%> <br>

<div id="message" style="display: inline; color: #990000;"><%=message%></div>

<h2>Systemkonfiguration:</h2>



<%
	String block = request.getParameter("block");
	if (block == null) {
		block = "";
	}

	if (block.equals("server_pfad")) {
%>

<form action="<%=path%>/Dispatcher" method="post"
	name="systemadministrationEditPath"
	onsubmit="return pruefeSysConfPfad() && confirm('Vorsicht, Ihre Konfigurationseinstellungen werden geändert!')">

<input type="hidden" name="identity" value="editsystemconf"> <input
	type="hidden" name="block" value="server_pfad">

<table class="table">
	<tr>
		<th colspan="5" rowspan="1">Server - Pfadeinstellungen</th>
	</tr>
	<tr>
		<td><img src="images/punkt.gif"></td>
		<td><b>Logdateipfad:</b></td>
		<td id="tableTdInput"><input type="text"
			name="server_pathLogFiles"
			value="<%if (request != null
						&& request.getAttribute("server_pathLogFiles") != null) {%><%=(String) request.getAttribute(
							"server_pathLogFiles").toString()%><%}%>"
			maxlength="100" style="width: 99%;"></td>
	</tr>
	<tr>
		<td><img src="images/punkt.gif"></td>
		<td><b>Client Konfig.-Dateipfad:</b></td>
		<td id="tableTdInput"><input type="text"
			name="server_pathClientConfigFile"
			value="<%if (request != null
						&& request.getAttribute("server_pathClientConfigFile") != null) {%><%=(String) request
							.getAttribute("server_pathClientConfigFile")%><%}%>"
			maxlength="100" style="width: 99%;"></td>
	</tr>
	<tr>
		<td><img src="images/punkt.gif"></td>
		<td><b>Dateiname Empfängerliste:</b></td>
		<td id="tableTdInput"><input type="text"
			name="server_filenameXMLFileRecipients"
			value="<%if (request != null
						&& request
								.getAttribute("server_filenameXMLFileRecipients") != null) {%><%=(String) request
							.getAttribute("server_filenameXMLFileRecipients")%><%}%>"
			maxlength="100" style="width: 99%;"></td>
	</tr>
	<tr>
		<td><img src="images/punkt.gif"></td>
		<td><b>Debug Mode:</b></td>
		<td id="tableTdInput"><input type="checkbox"
			name="server_debugModeCheckBox" id="server_debugModeCheckBox"
			value="true" <%if (debugMode) {%> checked="checked" <%}%>
			style="width: 15px; border: none;"
			onchange="toggleDebugMode('server_debugModeCheckBox','server_debugMode')">

		<input type="hidden" name="server_debugMode" id="server_debugMode"
			value="<%if (request != null
						&& request.getAttribute("server_debugMode") != null) {%><%=(String) request
									.getAttribute("server_debugMode")%><%}%>">
		</td>
	</tr>

</table>
<table class="table">
	<tr>
		<td id="tableTdInput">
		<div style="float: right;"><input name="abort" type="button"
			value="Abbrechen" class="myimagebutton"
			style="border: none; margin-right: 1px;"
			onclick="parent.location='<%=request.getContextPath().toString()%>/Dispatcher?identity=showsystemconf'"
			title="Vorgang abbrechen"> </input> <input name="save" type="submit"
			value="Speichern" class="myimagebutton"
			style="border: none; margin-right: 1px;"
			title="&Auml;nderungen speichern"> </input></div>
		</td>
	</tr>
</table>
</form>

<br>
<%
	} else {
%>

<table class="tablenew">
	<tr>
		<th colspan="5" rowspan="1">Server - Pfadeinstellungen</th>
	</tr>
	<tr>
		<td><img src="images/punkt.gif"></td>
		<td style="width: 200px;"><b>Logdateipfad:</b></td>
		<td><%=(String) request.getAttribute("server_pathLogFiles")%> <input
			type="hidden" name="server_pathLogFiles"
			value="<%if (request != null
						&& request.getAttribute("server_pathLogFiles") != null) {%><%=(String) request.getAttribute(
							"server_pathLogFiles").toString()%><%}%>">
		</td>
		<td width="40px"><a
			href="<%=request.getContextPath().toString()%>/Dispatcher?identity=systemconf&amp;block=server_pfad">
		<img src="images/pencil.gif" alt="bearbeiten" title="bearbeiten">
		</a></td>
	</tr>
	<tr>
		<td><img src="images/punkt.gif"></td>
		<td style="width: 200px;"><b>Client Konfig.-Dateipfad:</b></td>
		<td><%=(String) request
						.getAttribute("server_pathClientConfigFile")%>

		<input type="hidden" name="server_pathClientConfigFile"
			value="<%if (request != null
						&& request.getAttribute("server_pathClientConfigFile") != null) {%><%=(String) request
							.getAttribute("server_pathClientConfigFile")%><%}%>">
		</td>
	</tr>
	<tr>
		<td><img src="images/punkt.gif"></td>
		<td style="width: 200px;"><b>Dateiname Empfängerliste:</b></td>
		<td><%=(String) request
						.getAttribute("server_filenameXMLFileRecipients")%>

		<input type="hidden" name="server_filenameXMLFileRecipients"
			value="<%if (request != null
						&& request
								.getAttribute("server_filenameXMLFileRecipients") != null) {%><%=(String) request
							.getAttribute("server_filenameXMLFileRecipients")%><%}%>">
		</td>
	</tr>
	<tr>
		<td><img src="images/punkt.gif"></td>
		<td style="width: 200px;"><b>Debug Mode:</b></td>
		<td id="tableTdInput"><input type="checkbox"
			name="server_debugMode" value="server_debugMode" <%if (debugMode) {%>
			checked="checked" <%}%> cstyle="width: 15px; border:none;"
			disabled="disabled"> <input type="hidden"
			name="server_debugMode"
			value="<%if (request != null
						&& request.getAttribute("server_debugMode") != null) {%><%=(String) request
									.getAttribute("server_debugMode")%><%}%>">
		</td>
	</tr>
</table>
<%
	}
%> <%
 	if (block.equals("server_mail")) {
 %> <br>

<form action="<%=path%>/Dispatcher" method="post"
	name="systemadministrationEditMail"
	onsubmit="return pruefeSysConfMail() && confirm('Vorsicht, Ihre Konfigurationseinstellungen werden geändert!')">

<input type="hidden" name="identity" value="editsystemconf"> <input
	type="hidden" name="block" value="server_mail">

<table class="table">
	<tr>
		<th colspan="5" rowspan="1">Server - Maileinstellungen</th>
	</tr>
	<tr>
		<td><img src="images/punkt.gif"></td>
		<td><b>Benutzername:</b></td>
		<td id="tableTdInput"><input type="text"
			name="server_mailUsername"
			value="<%if (request != null
						&& request.getAttribute("server_mailUsername") != null) {%><%=(String) request
							.getAttribute("server_mailUsername")%><%}%>"
			maxlength="100" style="width: 99%;"></td>
	</tr>
	<tr>
		<td><img src="images/punkt.gif"></td>
		<td><b>Passwort:</b></td>
		<td id="tableTdInput"><input type="password"
			name="server_mailPassword"
			value="<%if (request != null
						&& request.getAttribute("server_mailPassword") != null) {%><%=(String) request
							.getAttribute("server_mailPassword")%><%}%>"
			maxlength="100" style="width: 99%;"></td>
	</tr>
	<tr>
		<td><img src="images/punkt.gif"></td>
		<td><b>Senderadresse:</b></td>
		<td id="tableTdInput"><input type="text"
			name="server_mailSenderAddress"
			value="<%if (request != null
						&& request.getAttribute("server_mailSenderAddress") != null) {%><%=(String) request
							.getAttribute("server_mailSenderAddress")%><%}%>"
			maxlength="100" style="width: 99%;"></td>
	</tr>
	<tr>
		<td><img src="images/punkt.gif"></td>
		<td><b>SMTP Host:</b></td>
		<td id="tableTdInput"><input type="text"
			name="server_mailSmtpHost"
			value="<%if (request != null
						&& request.getAttribute("server_mailSmtpHost") != null) {%><%=(String) request
							.getAttribute("server_mailSmtpHost")%><%}%>"
			maxlength="100" style="width: 99%;"></td>
	</tr>
	<tr>
		<td><img src="images/punkt.gif"></td>
		<td><b>Default Mailempfänger:</b></td>
		<td id="tableTdInput"><input type="text"
			name="server_defaultMailRecipient"
			value="<%if (request != null
						&& request.getAttribute("server_defaultMailRecipient") != null) {%><%=(String) request
							.getAttribute("server_defaultMailRecipient")%><%}%>"
			maxlength="100" style="width: 99%;"></td>
	</tr>
	<tr>
		<td><img src="images/punkt.gif"></td>
		<td><b>SMTP Port:</b></td>
		<td id="tableTdInput"><input type="text"
			name="server_mailSmtpPort"
			value="<%if (request != null
						&& request.getAttribute("server_mailSmtpPort") != null) {%><%=(String) request
							.getAttribute("server_mailSmtpPort")%><%}%>"
			maxlength="100" style="width: 99%;"></td>
	</tr>
	<tr>
		<td><img src="images/punkt.gif"></td>
		<td><b>Starttls:</b></td>
		<td id="tableTdInput"><input type="checkbox"
			name="server_mailSmtpStarttlsEnableCheckBox"
			id="server_mailSmtpStarttlsEnableCheckBox" value="true"
			<%if (mailSmtpStarttlsEnable) {%> checked="checked" <%}%>
			style="width: 15px; border: none;"
			onchange="toggleDebugMode('server_mailSmtpStarttlsEnableCheckBox','server_mailSmtpStarttlsEnable')">
		<input type="hidden" name="server_mailSmtpStarttlsEnable"
			id="server_mailSmtpStarttlsEnable"
			value="<%if (request != null
						&& request
								.getAttribute("server_mailSmtpStarttlsEnable") != null) {%><%=(String) request
							.getAttribute("server_mailSmtpStarttlsEnable")%><%}%>">
		</td>
	</tr>
</table>

<table class="table">
	<tr>
		<td id="tableTdInput">
		<div style="float: right;"><input name="abort" type="button"
			value="Abbrechen" value="" class="myimagebutton"
			style="border: none; margin-right: 1px;"
			onclick="parent.location='<%=request.getContextPath().toString()%>/Dispatcher?identity=showsystemconf'"
			title="Vorgang abbrechen"> </input> <input name="save" type="submit"
			value="Speichern" class="myimagebutton"
			style="border: none; margin-right: 1px;"
			title="&Auml;nderungen speichern" " title="Änderungen speichern">
		</input></div>
		</td>
	</tr>
</table>
</form>
<br>
<%
	} else {
%>

<table class="table">
	<tr>
		<th colspan="5" rowspan="1">Server - Maileinstellungen</th>
	</tr>
	<tr>
		<td><img src="images/punkt.gif"></td>
		<td style="width: 200px;"><b>Benutzername:</b></td>
		<td><%=(String) request.getAttribute("server_mailUsername")%> <input
			type="hidden" name="server_mailUsername"
			value="<%if (request != null
						&& request.getAttribute("server_mailUsername") != null) {%><%=(String) request
							.getAttribute("server_mailUsername")%><%}%>">
		</td>
		<td width="40px"><a
			href="<%=request.getContextPath().toString()%>/Dispatcher?identity=systemconf&amp;block=server_mail">
		<img src="images/pencil.gif" alt="Daten bearbeiten"
			title="Daten bearbeiten"> </a></td>
	</tr>
	<tr>
		<td><img src="images/punkt.gif"></td>
		<td style="width: 200px;"><b>Passwort:</b></td>
		<td><%="*****"%> <input type="hidden" name="server_mailPassword"
			value="<%if (request != null
						&& request.getAttribute("server_mailPassword") != null) {%><%=(String) request
							.getAttribute("server_mailPassword")%><%}%>">
		</td>
	</tr>
	<tr>
		<td><img src="images/punkt.gif"></td>
		<td style="width: 200px;"><b>Senderadresse:</b></td>
		<td><%=(String) request
						.getAttribute("server_mailSenderAddress")%>

		<input type="hidden" name="server_mailSenderAddress"
			value="<%if (request != null
						&& request.getAttribute("server_mailSenderAddress") != null) {%><%=(String) request
							.getAttribute("server_mailSenderAddress")%><%}%>">
		</td>
	</tr>
	<tr>
		<td><img src="images/punkt.gif"></td>
		<td style="width: 200px;"><b>SMTP Host:</b></td>
		<td><%=(String) request.getAttribute("server_mailSmtpHost")%> <input
			type="hidden" name="server_mailSmtpHost"
			value="<%if (request != null
						&& request.getAttribute("server_mailSmtpHost") != null) {%><%=(String) request
							.getAttribute("server_mailSmtpHost")%><%}%>">
		</td>
	</tr>
	<tr>
		<td><img src="images/punkt.gif"></td>
		<td style="width: 200px;"><b>Default Mailempfänger:</b></td>
		<td><%=(String) request
						.getAttribute("server_defaultMailRecipient")%>

		<input type="hidden" name="server_defaultMailRecipient"
			value="<%if (request != null
						&& request.getAttribute("server_defaultMailRecipient") != null) {%><%=(String) request
							.getAttribute("server_defaultMailRecipient")%><%}%>">
		</td>
	</tr>
	<tr>
		<td><img src="images/punkt.gif"></td>
		<td style="width: 200px;"><b>SMTP Port:</b></td>
		<td><%=(String) request.getAttribute("server_mailSmtpPort")%> <input
			type="hidden" name="server_mailSmtpPort"
			value="<%if (request != null
						&& request.getAttribute("server_mailSmtpPort") != null) {%><%=(String) request
							.getAttribute("server_mailSmtpPort")%><%}%>">
		</td>
	</tr>
	<tr>
		<td><img src="images/punkt.gif"></td>
		<td style="width: 200px;"><b>Starttls:</b></td>
		<td id="tableTdInput"><input type="checkbox"
			name="server_mailSmtpStarttlsEnableCheckbox"
			value="server_mailSmtpStarttlsEnableCheckbox"
			<%if (mailSmtpStarttlsEnable) {%> checked="checked" <%}%>
			style="width: 15px; border: none;" disabled="disabled"> <input
			type="hidden" name="server_mailSmtpStarttlsEnable"
			value="<%if (request != null
						&& request
								.getAttribute("server_mailSmtpStarttlsEnable") != null) {%><%=(String) request
							.getAttribute("server_mailSmtpStarttlsEnable")%><%}%>">
		</td>
	</tr>
	<tr>
	</tr>
</table>
<%
	}
%> <%
 	if (block.equals("client_pfad")) {
 %>

<form action="<%=path%>/Dispatcher" method="post"
	name="systemadministrationEditPath"
	onsubmit="return pruefeSysConfPfad() && confirm('Vorsicht, Ihre Konfigurationseinstellungen werden geändert!')">

<input type="hidden" name="identity" value="editsystemconf"> <input
	type="hidden" name="block" value="client_pfad">

<table class="table">
	<tr>
		<th colspan="5" rowspan="1">Client - Pfadeinstellungen</th>
	</tr>
	<tr>
		<td><img src="images/punkt.gif"></td>
		<td><b>Temp. Ordnerpfad:</b></td>
		<td id="tableTdInput"><input type="text"
			name="client_tmpFolderPath"
			value="<%if (request != null
						&& request.getAttribute("client_tmpFolderPath") != null) {%><%=(String) request.getAttribute(
							"client_tmpFolderPath").toString()%><%}%>"
			maxlength="100" style="width: 99%;"></td>
	</tr>
	<tr>
		<td><img src="images/punkt.gif"></td>
		<td><b>Pfad Logdatei:</b></td>
		<td id="tableTdInput"><input type="text"
			name="client_pathLogFiles"
			value="<%if (request != null
						&& request.getAttribute("client_pathLogFiles") != null) {%><%=(String) request
							.getAttribute("client_pathLogFiles")%><%}%>"
			maxlength="100" style="width: 99%;"></td>
	</tr>
	<tr>
		<td><img src="images/punkt.gif"></td>
		<td><b>GnupG Anwendungspfad:</b></td>
		<td id="tableTdInput"><input type="text"
			name="client_gnupgApplicationPath"
			value="<%if (request != null
						&& request.getAttribute("client_gnupgApplicationPath") != null) {%><%=(String) request
							.getAttribute("client_gnupgApplicationPath")%><%}%>"
			maxlength="100" style="width: 99%;"></td>
	</tr>
</table>


<table class="table">
	<tr>
		<td id="tableTdInput">
		<div style="float: right;"><input name="abort" type="button"
			value="Abbrechen" class="myimagebutton"
			style="border: none; margin-right: 1px;"
			onclick="parent.location='<%=request.getContextPath().toString()%>/Dispatcher?identity=showsystemconf'"
			title="Vorgang abbrechen"> </input> <input name="save" type="submit"
			value="Speichern" class="myimagebutton"
			style="border: none; margin-right: 1px;"
			title="&Auml;nderungen speichern"> </input></div>
		</td>
	</tr>
</table>
</form>

<br>
<%
	} else {
%>

<table class="table">
	<tr>
		<th colspan="5" rowspan="1">Client - Pfadeinstellungen</th>
	</tr>
	<tr>
		<td id="tableTdPoint"><img src="images/punkt.gif"></td>
		<td style="width: 200px;"><b>Temp. Ordnerpfad:</b></td>
		<td><%=(String) request
								.getAttribute("client_tmpFolderPath")%> <input
			type="hidden" name="client_tmpFolderPath"
			value="<%if (request != null
						&& request.getAttribute("client_tmpFolderPath") != null) {%><%=(String) request.getAttribute(
							"client_tmpFolderPath").toString()%><%}%>">
		</td>
		<td width="40px"><a
			href="<%=request.getContextPath().toString()%>/Dispatcher?identity=systemconf&amp;block=client_pfad">
		<img src="images/pencil.gif" alt="bearbeiten" title="bearbeiten">
		</a></td>
	</tr>

	<tr>
	</tr>

	<tr>
		<td id="tableTdPoint"><img src="images/punkt.gif"></td>
		<td style="width: 200px;"><b>Pfad Logdatei:</b></td>
		<td><%=(String) request.getAttribute("client_pathLogFiles")%> <input
			type="hidden" name="client_pathLogFiles"
			value="<%if (request != null
						&& request.getAttribute("client_pathLogFiles") != null) {%><%=(String) request
							.getAttribute("client_pathLogFiles")%><%}%>">
		</td>
	</tr>
	<tr>
		<td id="tableTdPoint"><img src="images/punkt.gif"></td>
		<td style="width: 200px;"><b>GnupG Anwendungspfad:</b></td>
		<td><%=(String) request
						.getAttribute("client_gnupgApplicationPath")%>

		<input type="hidden" name="client_gnupgApplicationPath"
			value="<%if (request != null
						&& request.getAttribute("client_gnupgApplicationPath") != null) {%><%=(String) request
							.getAttribute("client_gnupgApplicationPath")%><%}%>">
		</td>
	</tr>
</table>
<%
	}
%>
<%
	if (block.equals("client_ip")) {
%>

<form action="<%=path%>/Dispatcher" method="post"
	name="systemadministrationEditPath"
	onsubmit="return pruefeSysConfPfad() && confirm('Vorsicht, Ihre Konfigurationseinstellungen werden geändert!')">

<input type="hidden" name="identity" value="editsystemconf"> 
<input type="hidden" name="block" value="client_ip">

<table class="table">
	<tr>
		<th colspan="5" rowspan="1">Allgemeine DICOM Mail Portaleinstellungen</th>
	</tr>
	<tr>
		<td>
			<img src="images/punkt.gif">
		</td>
		<td>
			<b>Anzahl Login-Versuche:</b>	
		</td>
		<td id="tableTdInput">
			<input type="text" name="client_numberOfAllowedAffords"
			value="<%if (request != null
						&& request
								.getAttribute("client_numberOfAllowedAffords") != null) {%><%=(String) request.getAttribute(
							"client_numberOfAllowedAffords").toString()%><%}%>"
			maxlength="100" style="width: 99%;">
		</td>
	</tr>
	<tr>
		<td>
			<img src="images/punkt.gif">
		</td>
		<td>
			<b>IP Sperrzeit (ms):</b>
		</td>
		<td id="tableTdInput">
			<input type="text" name="client_lockedIPwaitingTime"
			value="<%if (request != null
						&& request.getAttribute("client_lockedIPwaitingTime") != null) {%><%=(String) request
							.getAttribute("client_lockedIPwaitingTime")%><%}%>"
			maxlength="100" style="width: 99%;">
		</td>
	</tr>
	<tr>
		<td>
			<img src="images/punkt.gif">
		</td>
		<td>
			<b>Letzte Empfänger (Anz.):</b>
		</td>
		<td id="tableTdInput">
			<input type="text" name="client_countViewLastRecipients"
			value="<%if (request != null
						&& request
								.getAttribute("client_countViewLastRecipients") != null) {%><%=(String) request
							.getAttribute("client_countViewLastRecipients")%><%}%>"
			maxlength="100" style="width: 99%;">
		</td>
	</tr>
	<tr>
		<td>
			<img src="images/punkt.gif">
		</td>
		<td>
			<b>Splitgröße (Byte):</b>
		</td>
		<td id="tableTdInput">
			<input type="text" name="client_splitSize" value="<%if (request != null
						&& request.getAttribute("client_splitSize") != null) {%><%=(String) request
									.getAttribute("client_splitSize")%><%}%>"
			maxlength="100" style="width: 99%;">
		</td>
	</tr>
</table>

<table class="table">
	<tr>
		<td id="tableTdInput">
			<div style="float: right;">
				<input name="abort" type="button" value="Abbrechen" class="myimagebutton"
					style="border: none; margin-right: 1px;"
					onclick="parent.location='<%=request.getContextPath().toString()%>/Dispatcher?identity=showsystemconf'"
					title="Vorgang abbrechen"> 
				</input> 
				<input name="save" type="submit"
					value="Speichern" class="myimagebutton"
					style="border: none; margin-right: 1px;"
					title="&Auml;nderungen speichern"> 
				</input>
			</div>
		</td>
	</tr>
</table>
</form>

<br>
<%
	} else {
%>
<table class="table">
	<tr>
		<th colspan="5" rowspan="1">Allgemeine DICOM Mail Portaleinstellungen</th>
	</tr>
	<tr>
		<td id="tableTdPoint"><img src="images/punkt.gif"></td>
		<td style="width: 200px;">
			<b>Anzahl Login-Versuche:</b>
		</td>
		<td><%=(String) request
						.getAttribute("client_numberOfAllowedAffords")%>

		<input type="hidden" name="client_numberOfAllowedAffords"
			value="<%if (request != null
						&& request
								.getAttribute("client_numberOfAllowedAffords") != null) {%><%=(String) request.getAttribute(
							"client_numberOfAllowedAffords").toString()%><%}%>">
		</td>
		<td width="40px">
			<a href="<%=request.getContextPath().toString()%>/Dispatcher?identity=systemconf&amp;block=client_ip">
				<img src="images/pencil.gif" alt="bearbeiten" title="bearbeiten">
			</a>
		</td>
	</tr>
	<tr>
		<td id="tableTdPoint">
			<img src="images/punkt.gif">
		</td>
		<td style="width: 200px;">
			<b>IP Sperrzeit (ms):</b>
		</td>
		<td><%=(String) request
						.getAttribute("client_lockedIPwaitingTime")%>
		<input type="hidden" name="client_lockedIPwaitingTime"
			value="<%if (request != null
						&& request.getAttribute("client_lockedIPwaitingTime") != null) {%><%=(String) request
							.getAttribute("client_lockedIPwaitingTime")%><%}%>">
		</td>
	</tr>
	<tr>
		<td id="tableTdPoint">
			<img src="images/punkt.gif">
		</td>
		<td style="width: 200px;">
			<b>Letzte Empfänger (Anz.):</b>
		</td>
		<td><%=(String) request
						.getAttribute("client_countViewLastRecipients")%>

		<input type="hidden" name="client_countViewLastRecipients"
			value="<%if (request != null
						&& request
								.getAttribute("client_countViewLastRecipients") != null) {%><%=(String) request
							.getAttribute("client_countViewLastRecipients")%><%}%>">
		</td>
	</tr>
	<tr>
		<td id="tableTdPoint">
			<img src="images/punkt.gif">
		</td>
		<td style="width: 200px;">
			<b>Splitgröße (Byte):</b>
		</td>
		<td><%=(String) request.getAttribute("client_splitSize")%> 
			<input type="hidden" name="client_splitSize"
			value="<%if (request != null
						&& request.getAttribute("client_splitSize") != null) {%><%=(String) request
									.getAttribute("client_splitSize")%><%}%>">
		</td>
	</tr>
</table>
<%
	}
%> <%
 	if (block.equals("client_dicom")) {
 %>

<form action="<%=path%>/Dispatcher" method="post"
	name="systemadministrationEditPath"
	onsubmit="return pruefeSysConfPfad() && confirm('Vorsicht, Ihre Konfigurationseinstellungen werden geändert!')">

<input type="hidden" name="identity" value="editsystemconf"> 
<input type="hidden" name="block" value="client_dicom">

<table class="table">
	<tr>
		<th colspan="5" rowspan="1">Client - DICOM Mail PGP-Key</th>
	</tr>
	<tr>
		<td>
			<img src="images/punkt.gif">
		</td>
		<td>
			<b>ID-Portal private key:</b>
		</td>
		<td id="tableTdInput">
			<input type="text" name="client_userPrivateKey"
			value="<%if (request != null
						&& request.getAttribute("client_userPrivateKey") != null) {%><%=(String) request.getAttribute(
							"client_userPrivateKey").toString()%><%}%>"
			maxlength="100" style="width: 99%;">
		</td>
	</tr>
	<tr>
		<td>
			<img src="images/punkt.gif">
		</td>
		<td>
			<b>Passwort private key:</b>
		</td>
		<td id="tableTdInput">
			<input type="password" name="client_passwordPrivateKey"
			value="<%if (request != null
						&& request.getAttribute("client_passwordPrivateKey") != null) {%><%=(String) request
							.getAttribute("client_passwordPrivateKey")%><%}%>"
			maxlength="100" style="width: 99%;">
		</td>
	</tr>
</table>

<table class="table">
	<tr>
		<td id="tableTdInput">
			<div style="float: right;">
				<input name="abort" type="button"
				value="Abbrechen" class="myimagebutton"
				style="border: none; margin-right: 1px;"
				onclick="parent.location='<%=request.getContextPath().toString()%>/Dispatcher?identity=showsystemconf'"
				title="Vorgang abbrechen"> 
				</input> 
				<input name="save" type="submit"
				value="Speichern" class="myimagebutton"
				style="border: none; margin-right: 1px;"
				title="&Auml;nderungen speichern"> 
				</input>
			</div>
		</td>
	</tr>
</table>
</form>

<br>
<%
	} else {
%>
<table class="table">
	<tr>
		<th colspan="5" rowspan="1">Client - DICOM Mail PGP-Key</th>
	</tr>
	<tr>
		<td id="tableTdPoint">
			<img src="images/punkt.gif">
		</td>
		<td style="width: 200px;">
			<b>ID-Portal private key:</b>
		</td>
		<td><%=(String) request
						.getAttribute("client_userPrivateKey")%> 
			<input type="hidden" name="client_userPrivateKey"
			value="<%if (request != null
						&& request.getAttribute("client_userPrivateKey") != null) {%><%=(String) request.getAttribute(
							"client_userPrivateKey").toString()%><%}%>">
		</td>
		<td width="40px">
			<a href="<%=request.getContextPath().toString()%>/Dispatcher?identity=systemconf&amp;block=client_dicom">
				<img src="images/pencil.gif" alt="bearbeiten" title="bearbeiten">
			</a>
		</td>
	</tr>
	<tr>
		<td id="tableTdPoint">
			<img src="images/punkt.gif">
		</td>
		<td style="width: 200px;">
			<b>Passwort private key:</b>
		</td>
		<td>***** 
			<input type="hidden" name="client_passwordPrivateKey"
			value="<%if (request != null
						&& request.getAttribute("client_passwordPrivateKey") != null) {%><%=(String) request
							.getAttribute("client_passwordPrivateKey")%><%}%>">
		</td>
	</tr>
</table>
<%
	}
%> <%
 	if (block.equals("client_mailserver_hd_mail")) {
 %>

<form action="<%=path%>/Dispatcher" method="post"
	name="systemadministrationEditPath"
	onsubmit="return pruefeSysConfPfad() && confirm('Vorsicht, Ihre Konfigurationseinstellungen werden geändert!')">

<input type="hidden" name="identity" value="editsystemconf"> 
<input type="hidden" name="block" value="client_mailserver_hd_mail">

<table class="table">
	<tr>
		<th colspan="3" rowspan="1">DICOM Mail - Einstellungen Mailserver HD</th>
	</tr>
	<tr>
		<td>
			<img src="images/punkt.gif">
		</td>
		<td>
			<b>Benutzername:</b>
		</td>
		<td id="tableTdInput">
			<input type="text" name="client_mailserver_hd_mailUsername"
			value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_hd_mailUsername") != null) {%><%=(String) request.getAttribute(
							"client_mailserver_hd_mailUsername").toString()%><%}%>"
			maxlength="100" style="width: 99%;">
		</td>
	</tr>
	<tr>
		<td>
			<img src="images/punkt.gif">
		</td>
		<td>
			<b>Passwort:</b>
		</td>
		<td id="tableTdInput">
			<input type="password" name="client_mailserver_hd_mailPassword"
			value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_hd_mailPassword") != null) {%><%=(String) request
							.getAttribute("client_mailserver_hd_mailPassword")%><%}%>"
			maxlength="100" style="width: 99%;">
		</td>
	</tr>
	<tr>
		<td>
			<img src="images/punkt.gif">
		</td>
		<td>
			<b>Senderadresse:</b>
		</td>
		<td id="tableTdInput">
			<input type="text" name="client_mailserver_hd_mailSenderAddress"
			value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_hd_mailSenderAddress") != null) {%><%=(String) request
									.getAttribute("client_mailserver_hd_mailSenderAddress")%><%}%>"
			maxlength="100" style="width: 99%;">
		</td>
	</tr>
	<tr>
		<td>
			<img src="images/punkt.gif">
		</td>
		<td>
			<b>SMTP Host:</b>
		</td>
		<td id="tableTdInput">
			<input type="text" name="client_mailserver_hd_mailSmtpHost"
			value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_hd_mailSmtpHost") != null) {%><%=(String) request
							.getAttribute("client_mailserver_hd_mailSmtpHost")%><%}%>"
			maxlength="100" style="width: 99%;">
		</td>
	</tr>
	<tr>
		<td>
			<img src="images/punkt.gif">
		</td>
		<td>
			<b>Default Mailempfänger:</b>
		</td>
		<td id="tableTdInput">
			<input type="text" name="client_mailserver_hd_defaultMailRecipient"
			value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_hd_defaultMailRecipient") != null) {%><%=(String) request
									.getAttribute("client_mailserver_hd_defaultMailRecipient")%><%}%>"
			maxlength="100" style="width: 99%;">
		</td>
	</tr>
	<tr>
		<td>
			<img src="images/punkt.gif">
		</td>
		<td>
			<b>SMTP Port:</b>
		</td>
		<td id="tableTdInput">
			<input type="text" name="client_mailserver_hd_mailSmtpPort"
			value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_hd_mailSmtpPort") != null) {%><%=(String) request
							.getAttribute("client_mailserver_hd_mailSmtpPort")%><%}%>"
			maxlength="100" style="width: 99%;">
		</td>
	</tr>
	<tr>
		<td>
			<img src="images/punkt.gif">
		</td>
		<td>
			<b>Starttls:</b>
		</td>
		<td id="tableTdInput">
			<input type="checkbox" name="client_mailserver_hd_mailSmtpStarttlsEnableCheckbox"
			id="client_mailserver_hd_mailSmtpStarttlsEnableCheckbox" value="true"
			<%if (client_mailserver_hd_MailSmtpStarttlsEnable) {%>
			checked="checked" <%}%> style="width: 15px; border: none;"
			onchange="toggleDebugMode('client_mailserver_hd_mailSmtpStarttlsEnableCheckbox','client_mailserver_hd_mailSmtpStarttlsEnable')">

		<input type="hidden" name="client_mailserver_hd_mailSmtpStarttlsEnable"
			id="client_mailserver_hd_mailSmtpStarttlsEnable"
			value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_hd_mailSmtpStarttlsEnable") != null) {%><%=(String) request
									.getAttribute("client_mailserver_hd_mailSmtpStarttlsEnable")%><%}%>">
		</td>
	</tr>
</table>


<table class="table">
	<tr>
		<td id="tableTdInput">
			<div style="float: right;">
				<input name="abort" type="button"
				value="Abbrechen" class="myimagebutton"
				style="border: none; margin-right: 1px;"
				onclick="parent.location='<%=request.getContextPath().toString()%>/Dispatcher?identity=showsystemconf'"
				title="Vorgang abbrechen"> 
				</input> 
				<input name="save" type="submit"
				value="Speichern" class="myimagebutton"
				style="border: none; margin-right: 1px;"
				title="&Auml;nderungen speichern"> 
				</input>
			</div>
		</td>
	</tr>
</table>
</form>

<%
	} else {
%>

<table class="table">
	<tr>
		<th colspan="5" rowspan="1">DICOM Mail - Einstellungen Mailserver HD</th>
	</tr>
	<tr>
		<td id="tableTdPoint">
			<img src="images/punkt.gif">
		</td>
		<td style="width: 200px;">
			<b>Benutzername:</b>
		</td>
		<td><%=(String) request
						.getAttribute("client_mailserver_hd_mailUsername")%>

		<input type="hidden" name="client_mailserver_hd_mailUsername"
			value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_hd_mailUsername") != null) {%><%=(String) request.getAttribute(
							"client_mailserver_hd_mailUsername").toString()%><%}%>">
		</td>
		<td width="40px">
			<a href="<%=request.getContextPath().toString()%>/Dispatcher?identity=systemconf&amp;block=client_mailserver_hd_mail">
				<img src="images/pencil.gif" alt="Daten bearbeiten"
			title="Daten bearbeiten"> 
			</a>
		</td>
	</tr>
	<tr>
		<td id="tableTdPoint">
			<img src="images/punkt.gif">
		</td>
		<td style="width: 200px;">
			<b>Passwort:</b>
		</td>
		<td>***** 
			<input type="hidden" name="client_mailserver_hd_mailPassword"
			value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_hd_mailPassword") != null) {%><%=(String) request
							.getAttribute("client_mailserver_hd_mailPassword")%><%}%>">
		</td>
	</tr>
	<tr>
		<td id="tableTdPoint">
			<img src="images/punkt.gif">
		</td>
		<td style="width: 200px;">
			<b>Senderadresse:</b>
		</td>
		<td><%=(String) request
								.getAttribute("client_mailserver_hd_mailSenderAddress")%>

		<input type="hidden" name="client_mailserver_hd_mailSenderAddress"
			value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_hd_mailSenderAddress") != null) {%><%=(String) request
									.getAttribute("client_mailserver_hd_mailSenderAddress")%><%}%>">
		</td>
	</tr>
	<tr>
		<td id="tableTdPoint">
			<img src="images/punkt.gif">
		</td>
		<td style="width: 200px;">
			<b>SMTP Host:</b>
		</td>
		<td><%=(String) request
						.getAttribute("client_mailserver_hd_mailSmtpHost")%>

		<input type="hidden" name="client_mailserver_hd_mailSmtpHost"
			value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_hd_mailSmtpHost") != null) {%><%=(String) request
							.getAttribute("client_mailserver_hd_mailSmtpHost")%><%}%>">
		</td>
	</tr>
	<tr>
		<td id="tableTdPoint">
			<img src="images/punkt.gif">
		</td>
		<td style="width: 200px;">
			<b>Default Mailempfänger:</b>
		</td>
		<td><%=(String) request
								.getAttribute("client_mailserver_hd_defaultMailRecipient")%>

		<input type="hidden" name="client_mailserver_hd_defaultMailRecipient"
			value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_hd_defaultMailRecipient") != null) {%><%=(String) request
									.getAttribute("client_mailserver_hd_defaultMailRecipient")%><%}%>">
		</td>
	</tr>
	<tr>
		<td id="tableTdPoint">
			<img src="images/punkt.gif">
		</td>
		<td style="width: 200px;">
			<b>SMTP Port:</b>
		</td>
		<td><%=(String) request
						.getAttribute("client_mailserver_hd_mailSmtpPort")%>
		<input type="hidden" name="client_mailserver_hd_mailSmtpPort"
			value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_hd_mailSmtpPort") != null) {%><%=(String) request
							.getAttribute("client_mailserver_hd_mailSmtpPort")%><%}%>">
		</td>
	</tr>
	<tr>
		<td id="tableTdPoint">
			<img src="images/punkt.gif">
		</td>
		<td style="width: 200px;">
			<b>Starttls:</b>
		</td>
		<td id="tableTdInput">
			<input type="checkbox" name="client_mailserver_hd_mailSmtpStarttlsEnableCheckbox"
			value="client_mailserver_hd_mailSmtpStarttlsEnableCheckbox"
			<%if (client_mailserver_hd_MailSmtpStarttlsEnable) {%>
			checked="checked" <%}%> style="width: 15px; border: none;"
			disabled="disabled"> 
			<input type="hidden"
			name="client_mailserver_hd_mailSmtpStarttlsEnable"
			value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_hd_mailSmtpStarttlsEnable") != null) {%><%=(String) request
									.getAttribute("client_mailserver_hd_mailSmtpStarttlsEnable")%><%}%>">
		</td>
	</tr>
</table>
<%
	}
%> 

<%
 	if (block.equals("client_mailserver_ma_mail")) {
 %>
 
 <form action="<%=path%>/Dispatcher" method="post"
	name="systemadministrationEditPath"
	onsubmit="return pruefeSysConfPfad() && confirm('Vorsicht, Ihre Konfigurationseinstellungen werden geändert!')">

<input type="hidden" name="identity" value="editsystemconf"> 
<input type="hidden" name="block" value="client_mailserver_ma_mail">
 
 <table class="table">
	<tr>
		<th colspan="3" rowspan="1">DICOM Mail - Einstellungen Mailserver MA</th>
	</tr>
	<tr>
		<td>
			<img src="images/punkt.gif">
		</td>
		<td>
			<b>Benutzername:</b>
		</td>
		<td id="tableTdInput">
			<input type="text"
			name="client_mailserver_ma_mailUsername"
			value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_ma_mailUsername") != null) {%><%=(String) request.getAttribute(
							"client_mailserver_ma_mailUsername").toString()%><%}%>"
			maxlength="100" style="width: 99%;">
		</td>
	</tr>
	<tr>
		<td>
			<img src="images/punkt.gif">
		</td>
		<td>
			<b>Passwort:</b>
		</td>
		<td id="tableTdInput">
			<input type="password"
			name="client_mailserver_ma_mailPassword"
			value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_ma_mailPassword") != null) {%><%=(String) request
							.getAttribute("client_mailserver_ma_mailPassword")%><%}%>"
			maxlength="100" style="width: 99%;">
		</td>
	</tr>
	<tr>
		<td>
			<img src="images/punkt.gif">
		</td>
		<td>
			<b>Senderadresse:</b>
		</td>
		<td id="tableTdInput">
			<input type="text"
			name="client_mailserver_ma_mailSenderAddress"
			value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_ma_mailSenderAddress") != null) {%><%=(String) request
									.getAttribute("client_mailserver_ma_mailSenderAddress")%><%}%>"
			maxlength="100" style="width: 99%;">
		</td>
	</tr>
	<tr>
		<td>
			<img src="images/punkt.gif">
		</td>
		<td>
			<b>SMTP Host:</b>
		</td>
		<td id="tableTdInput">
			<input type="text" name="client_mailserver_ma_mailSmtpHost"
			value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_ma_mailSmtpHost") != null) {%><%=(String) request
							.getAttribute("client_mailserver_ma_mailSmtpHost")%><%}%>"
			maxlength="100" style="width: 99%;">
		</td>
	</tr>
	<tr>
		<td>
			<img src="images/punkt.gif">
		</td>
		<td>
			<b>Default Mailempfänger:</b>
		</td>
		<td id="tableTdInput">
			<input type="text" name="client_mailserver_ma_defaultMailRecipient"
			value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_ma_defaultMailRecipient") != null) {%><%=(String) request
									.getAttribute("client_mailserver_ma_defaultMailRecipient")%><%}%>"
			maxlength="100" style="width: 99%;">
		</td>
	</tr>
	<tr>
		<td>
			<img src="images/punkt.gif">
		</td>
		<td>
			<b>SMTP Port:</b>
		</td>
		<td id="tableTdInput">
			<input type="text"
			name="client_mailserver_ma_mailSmtpPort"
			value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_ma_mailSmtpPort") != null) {%><%=(String) request
							.getAttribute("client_mailserver_ma_mailSmtpPort")%><%}%>"
			maxlength="100" style="width: 99%;">
		</td>
	</tr>
	<tr>
		<td>
			<img src="images/punkt.gif">
		</td>
		<td>
			<b>Starttls:</b>
		</td>
		<td id="tableTdInput">
			<input type="checkbox"
			name="client_mailserver_ma_mailSmtpStarttlsEnableCheckbox"
			id="client_mailserver_ma_mailSmtpStarttlsEnableCheckbox" value="true"
			<%if (client_mailserver_ma_MailSmtpStarttlsEnable) {%>
			checked="checked" <%}%> style="width: 15px; border: none;"
			onchange="toggleDebugMode('client_mailserver_ma_mailSmtpStarttlsEnableCheckbox','client_mailserver_ma_mailSmtpStarttlsEnable')">

		<input type="hidden"
			name="client_mailserver_ma_mailSmtpStarttlsEnable"
			id="client_mailserver_ma_mailSmtpStarttlsEnable"
			value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_ma_mailSmtpStarttlsEnable") != null) {%><%=(String) request
									.getAttribute("client_mailserver_ma_mailSmtpStarttlsEnable")%><%}%>">
		</td>
	</tr>
</table>

<table class="table">
	<tr>
		<td id="tableTdInput">
			<div style="float: right;">
				<input name="abort" type="button"
				value="Abbrechen" class="myimagebutton"
				style="border: none; margin-right: 1px;"
				onclick="parent.location='<%=request.getContextPath().toString()%>/Dispatcher?identity=showsystemconf'"
				title="Vorgang abbrechen"> 
				</input> 
				<input name="save" type="submit"
				value="Speichern" class="myimagebutton"
				style="border: none; margin-right: 1px;"
				title="&Auml;nderungen speichern"> 
				</input>
			</div>
		</td>
	</tr>
</table>
</form>

<%
	} else {
%>
<table class="table">
	<tr>
		<th colspan="5" rowspan="1">DICOM Mail - Einstellungen Mailserver MA</th>
	</tr>
	<tr>
		<td id="tableTdPoint">
			<img src="images/punkt.gif">
		</td>
		<td style="width: 200px;">
			<b>Benutzername:</b>
		</td>
		<td><%=(String) request
						.getAttribute("client_mailserver_ma_mailUsername")%>

		<input type="hidden" name="client_mailserver_ma_mailUsername"
			value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_ma_mailUsername") != null) {%><%=(String) request.getAttribute(
							"client_mailserver_ma_mailUsername").toString()%><%}%>">
		</td>
		<td width="40px">
			<a href="<%=request.getContextPath().toString()%>/Dispatcher?identity=systemconf&amp;block=client_mailserver_ma_mail">
				<img src="images/pencil.gif" alt="Daten bearbeiten"
				title="Daten bearbeiten"> 
			</a>
		</td>
	</tr>
	<tr>
		<td id="tableTdPoint">
			<img src="images/punkt.gif">
		</td>
		<td style="width: 200px;">
			<b>Passwort:</b>
		</td>
		<td>***** 
			<input type="hidden"
				name="client_mailserver_ma_mailPassword"
			value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_ma_mailPassword") != null) {%><%=(String) request
							.getAttribute("client_mailserver_ma_mailPassword")%><%}%>">
		</td>
	</tr>
	<tr>
		<td id="tableTdPoint">
			<img src="images/punkt.gif">
		</td>
		<td style="width: 200px;">
			<b>Senderadresse:</b>
		</td>
		<td><%=(String) request
								.getAttribute("client_mailserver_ma_mailSenderAddress")%>

		<input type="hidden" name="client_mailserver_ma_mailSenderAddress"
			value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_ma_mailSenderAddress") != null) {%><%=(String) request
									.getAttribute("client_mailserver_ma_mailSenderAddress")%><%}%>">
		</td>
	</tr>
	<tr>
		<td id="tableTdPoint">
			<img src="images/punkt.gif">
		</td>
		<td style="width: 200px;">
			<b>SMTP Host:</b>
		</td>
		<td><%=(String) request
						.getAttribute("client_mailserver_ma_mailSmtpHost")%>

		<input type="hidden" name="client_mailserver_ma_mailSmtpHost"
			value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_ma_mailSmtpHost") != null) {%><%=(String) request
							.getAttribute("client_mailserver_ma_mailSmtpHost")%><%}%>">
		</td>
	</tr>
	<tr>
		<td id="tableTdPoint">
			<img src="images/punkt.gif">
		</td>
		<td style="width: 200px;"><b>Default Mailempfänger:</b></td>
		<td><%=(String) request
								.getAttribute("client_mailserver_ma_defaultMailRecipient")%>

		<input type="hidden" name="client_mailserver_ma_defaultMailRecipient"
			value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_ma_defaultMailRecipient") != null) {%><%=(String) request
									.getAttribute("client_mailserver_ma_defaultMailRecipient")%><%}%>">
		</td>
	</tr>
	<tr>
		<td id="tableTdPoint">
			<img src="images/punkt.gif">
		</td>
		<td style="width: 200px;">
			<b>SMTP Port:</b>
		</td>
		<td><%=(String) request
						.getAttribute("client_mailserver_ma_mailSmtpPort")%>
		<input type="hidden" name="client_mailserver_ma_mailSmtpPort"
			value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_ma_mailSmtpPort") != null) {%><%=(String) request
							.getAttribute("client_mailserver_ma_mailSmtpPort")%><%}%>">
		</td>
	</tr>
	<tr>
		<td id="tableTdPoint">
			<img src="images/punkt.gif">
		</td>
		<td style="width: 200px;">
			<b>Starttls:</b>
		</td>
		<td id="tableTdInput">
			<input type="checkbox" name="client_mailserver_ma_mailSmtpStarttlsEnableCheckbox"
			value="client_mailserver_ma_mailSmtpStarttlsEnableCheckbox"
			<%if (client_mailserver_ma_MailSmtpStarttlsEnable) {%>
			checked="checked" <%}%> style="width: 15px; border: none;"
			disabled="disabled"> <input type="hidden"
			name="client_mailserver_ma_mailSmtpStarttlsEnable"
			value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_ma_mailSmtpStarttlsEnable") != null) {%><%=(String) request
									.getAttribute("client_mailserver_ma_mailSmtpStarttlsEnable")%><%}%>">
		</td>
	</tr>
</table>
<%
	}
%> 

<%
 	if (block.equals("client_mailserver_ka_mail")) {
 %>
 <form action="<%=path%>/Dispatcher" method="post"
	name="systemadministrationEditPath"
	onsubmit="return pruefeSysConfPfad() && confirm('Vorsicht, Ihre Konfigurationseinstellungen werden geändert!')">

<input type="hidden" name="identity" value="editsystemconf"> 
<input type="hidden" name="block" value="client_mailserver_ka_mail">
 
 <table class="table">
	<tr>
		<th colspan="3" rowspan="1">DICOM Mail - Einstellungen Mailserver
		KA</th>
	</tr>
	<tr>
		<td>
			<img src="images/punkt.gif">
		</td>
		<td>	
			<b>Benutzername:</b>
		</td>
		<td id="tableTdInput">
			<input type="text" name="client_mailserver_ka_mailUsername"
			value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_ka_mailUsername") != null) {%><%=(String) request.getAttribute(
							"client_mailserver_ka_mailUsername").toString()%><%}%>"
			maxlength="100" style="width: 99%;">
		</td>
	</tr>
	<tr>
		<td>
			<img src="images/punkt.gif">
		</td>
		<td>
			<b>Passwort:</b>
		</td>
		<td id="tableTdInput">
			<input type="password" name="client_mailserver_ka_mailPassword"
			value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_ka_mailPassword") != null) {%><%=(String) request
							.getAttribute("client_mailserver_ka_mailPassword")%><%}%>"
			maxlength="100" style="width: 99%;"></td>
	</tr>
	<tr>
		<td>
			<img src="images/punkt.gif">
		</td>
		<td>
			<b>Senderadresse:</b>
		</td>
		<td id="tableTdInput">
			<input type="text"	name="client_mailserver_ka_mailSenderAddress"
			value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_ka_mailSenderAddress") != null) {%><%=(String) request
									.getAttribute("client_mailserver_ka_mailSenderAddress")%><%}%>"
			maxlength="100" style="width: 99%;">
		</td>
	</tr>
	<tr>
		<td>
			<img src="images/punkt.gif">
		</td>
		<td>
			<b>SMTP Host:</b>
		</td>
		<td id="tableTdInput">
			<input type="text" name="client_mailserver_ka_mailSmtpHost"
			value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_ka_mailSmtpHost") != null) {%><%=(String) request
							.getAttribute("client_mailserver_ka_mailSmtpHost")%><%}%>"
			maxlength="100" style="width: 99%;">
		</td>
	</tr>
	<tr>
		<td>
			<img src="images/punkt.gif">
		</td>
		<td>
			<b>Default Mailempfänger:</b>
		</td>
		<td id="tableTdInput">
			<input type="text" name="client_mailserver_ka_defaultMailRecipient"
			value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_ka_defaultMailRecipient") != null) {%><%=(String) request
									.getAttribute("client_mailserver_ka_defaultMailRecipient")%><%}%>"
			maxlength="100" style="width: 99%;">
		</td>
	</tr>
	<tr>
		<td>
			<img src="images/punkt.gif">
		</td>
		<td>
			<b>SMTP Port:</b>
		</td>
		<td id="tableTdInput">
			<input type="text" name="client_mailserver_ka_mailSmtpPort"
			value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_ka_mailSmtpPort") != null) {%><%=(String) request
							.getAttribute("client_mailserver_ka_mailSmtpPort")%><%}%>"
			maxlength="100" style="width: 99%;">
		</td>
	</tr>
	<tr>
		<td>
			<img src="images/punkt.gif">
		</td>
		<td>
			<b>Starttls:</b>
		</td>
		<td id="tableTdInput">
			<input type="checkbox" name="client_mailserver_ka_mailSmtpStarttlsEnableCheckbox"
			id="client_mailserver_ka_mailSmtpStarttlsEnableCheckbox" value="true"
			<%if (client_mailserver_ka_MailSmtpStarttlsEnable) {%>
			checked="checked" <%}%> style="width: 15px; border: none;"
			onchange="toggleDebugMode('client_mailserver_ka_mailSmtpStarttlsEnableCheckbox','client_mailserver_ka_mailSmtpStarttlsEnable')">

		<input type="hidden"
			name="client_mailserver_ka_mailSmtpStarttlsEnable"
			id="client_mailserver_ka_mailSmtpStarttlsEnable"
			value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_ka_mailSmtpStarttlsEnable") != null) {%><%=(String) request
									.getAttribute("client_mailserver_ka_mailSmtpStarttlsEnable")%><%}%>">
		</td>
	</tr>
</table>

<table class="table">
	<tr>
		<td id="tableTdInput">
			<div style="float: right;">
				<input name="abort" type="button"
					value="Abbrechen" class="myimagebutton"
					style="border: none; margin-right: 1px;"
					onclick="parent.location='<%=request.getContextPath().toString()%>/Dispatcher?identity=showsystemconf'"
					title="Vorgang abbrechen"> 
				</input> 
				<input name="save" type="submit"
					value="Speichern" class="myimagebutton"
					style="border: none; margin-right: 1px;"
					title="&Auml;nderungen speichern"> 
				</input>
			</div>
		</td>
	</tr>
</table>
</form>

<br>
 <%
	} else {
%>
<table class="table">
	<tr>
		<th colspan="5" rowspan="1">DICOM Mail - Einstellungen Mailserver KA</th>
	</tr>
	<tr>
		<td id="tableTdPoint">
			<img src="images/punkt.gif">
		</td>
		<td style="width: 200px;">
			<b>Benutzername:</b>
		</td>
		<td><%=(String) request
						.getAttribute("client_mailserver_ka_mailUsername")%>

			<input type="hidden" name="client_mailserver_ka_mailUsername"
				value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_ka_mailUsername") != null) {%><%=(String) request.getAttribute(
							"client_mailserver_ka_mailUsername").toString()%><%}%>">
		</td>
		<td width="40px">
			<a href="<%=request.getContextPath().toString()%>/Dispatcher?identity=systemconf&amp;block=client_mailserbver_ka_mail">
				<img src="images/pencil.gif" alt="Daten bearbeiten" title="Daten bearbeiten"> 
			</a>
		</td>
	</tr>
	<tr>
		<td id="tableTdPoint">
			<img src="images/punkt.gif">
		</td>
		<td style="width: 200px;">
			<b>Passwort:</b>
		</td>
		<td>***** 
			<input type="hidden" name="client_mailserver_ma_mailPassword"
			value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_ka_mailPassword") != null) {%><%=(String) request
							.getAttribute("client_mailserver_ka_mailPassword")%><%}%>">
		</td>
	</tr>
	<tr>
		<td id="tableTdPoint">
			<img src="images/punkt.gif">
		</td>
		<td style="width: 200px;">
			<b>Senderadresse:</b>
		</td>
		<td><%=(String) request
								.getAttribute("client_mailserver_ka_mailSenderAddress")%>

			<input type="hidden" name="client_mailserver_ka_mailSenderAddress"
				value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_ka_mailSenderAddress") != null) {%><%=(String) request
									.getAttribute("client_mailserver_ka_mailSenderAddress")%><%}%>">
		</td>
	</tr>
	<tr>
		<td id="tableTdPoint">
			<img src="images/punkt.gif">
		</td>
		<td style="width: 200px;">
			<b>SMTP Host:</b>
		</td>
		<td><%=(String) request
						.getAttribute("client_mailserver_ka_mailSmtpHost")%>
	
			<input type="hidden" name="client_mailserver_ka_mailSmtpHost"
				value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_ka_mailSmtpHost") != null) {%><%=(String) request
							.getAttribute("client_mailserver_ka_mailSmtpHost")%><%}%>">
		</td>
	</tr>
	<tr>
		<td id="tableTdPoint">
			<img src="images/punkt.gif">
		</td>
		<td style="width: 200px;">
			<b>Default Mailempfänger:</b>
		</td>
		<td><%=(String) request
								.getAttribute("client_mailserver_ka_defaultMailRecipient")%>

			<input type="hidden" name="client_mailserver_ka_defaultMailRecipient"
				value="<%if (request != null
							&& request
								.getAttribute("client_mailserver_ka_defaultMailRecipient") != null) {%><%=(String) request
									.getAttribute("client_mailserver_ka_defaultMailRecipient")%><%}%>">
		</td>
	</tr>
	<tr>
		<td id="tableTdPoint">
			<img src="images/punkt.gif">
		</td>
		<td style="width: 200px;">
			<b>SMTP Port:</b>
		</td>
		<td><%=(String) request
						.getAttribute("client_mailserver_ka_mailSmtpPort")%>
			<input type="hidden" name="client_mailserver_ka_mailSmtpPort"
				value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_ka_mailSmtpPort") != null) {%><%=(String) request
							.getAttribute("client_mailserver_ka_mailSmtpPort")%><%}%>">
		</td>
	</tr>
	<tr>
		<td id="tableTdPoint">
			<img src="images/punkt.gif">
		</td>
		<td style="width: 200px;">
			<b>Starttls:</b>
		</td>
		<td id="tableTdInput">
			<input type="checkbox" name="client_mailserver_ka_mailSmtpStarttlsEnableCheckbox"
			value="client_mailserver_ka_mailSmtpStarttlsEnableCheckbox"
				<%if (client_mailserver_ka_MailSmtpStarttlsEnable) {%>
				checked="checked" <%}%> style="width: 15px; border: none;"
				disabled="disabled"> 
			<input type="hidden"
				name="client_mailserver_ka_mailSmtpStarttlsEnable"
				value="<%if (request != null
						&& request
								.getAttribute("client_mailserver_ka_mailSmtpStarttlsEnable") != null) {%><%=(String) request
									.getAttribute("client_mailserver_ka_mailSmtpStarttlsEnable")%><%}%>">
		</td>
	</tr>
</table> 
 <%
	}
%> 
<img src="images/horizontalerTenner.gif" style="margin-top: 15px; margin-bottom: 15px;">

<div id="guideline">
	<p>
		<b>Hinweis:</b>
	</p>
Alle Felder m&uuml;ssen ausgefüllt werden!<br>
<br>
Zum Speichern der Daten auf "Speichern" klicken.<br>
Zum Abbrechen auf "Abbrechen" klicken.<br>

</div>
<!-- /hinweis--> <br clear="all" />

</div>

<%@ include file="include/footer.jsp"%>
</div>

</body>
</html>