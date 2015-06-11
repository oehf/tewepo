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
	
	DicomMailRecipient dicomMailRecipient=(DicomMailRecipient)request.getAttribute("recipient");
	
	String path = request.getContextPath().toString();
	String message = (String) request.getAttribute("message");
	
	if (message == null){
		message = "";
	}
		
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.openehealth.twp.tewepo.businesslogic.LockedIP"%>
<%@page import="org.openehealth.tewepo.twp.dmp.dmc.server.email.xml.DicomMailRecipient"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
			<title>Teleradiologie Webportal</title>
			<link rel="stylesheet" href="<%=path%>/style/style.css" type="text/css">
			<script language="JavaScript" src="<%=path%>/js/inputcheck.js"
				type="text/javascript"></script>
			<script type="text/javascript">
			</script>
			<style  type="text/css" media="all">
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
				
				#content a#horizontalmenuetable_a_showlistofcontactpersonspage {
					font-weight: bold;
				}
				
				#content td#horizontalmenuetable_td_showlogfilepage {
					border-bottom: 2px solid #990000
				}

				#content td#horizontalmenuetable_td_showlistofcontactpersonspage {
					background-image: url(images/tabs/tab_active.gif)
				}
				
				#content td#horizontalmenuetable_td_systemconfigurationpage {
					border-bottom: 2px solid #990000
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
				<img src="images/horizontaldashedseperator.gif"	style="margin-top: 5px; margin-bottom: 15px;">
				<h2>Neuer Empfänger</h2>

				<p>Diese Seite beinhaltet die Systemverwaltung des Webportals.</p>
				<br>
			<%
				if (ADMINISTRATOR) {
			%> <%@ include file="include/submenusystemadministration.jsp"%>
			<%
				} else {
					Dispatcher.invalidateSession(request, response,
							"addnewrecipientpage.jsp", "Unerlaubter Zugriff");
				}
			%> <br>

				<div id="message" style="display: inline; color: #990000;"><%=message%></div>

				<%if(dicomMailRecipient==null){%>
				
					<h2>Neuer Empfänger:</h2>
				<%}else{%>
					<h2>Empfängerdaten ändern:</h2>
				<%}%>
				
				<form action="<%=path%>/Dispatcher" method="post" name="registerform"
					onsubmit="return check_emails(check_form(this),1)">
					<%if(dicomMailRecipient==null){%>
					<input type="hidden"
					name="identity" value="addnewrecipient">
					<%}else{%>
					<input type="hidden"
					name="identity" value="editrecipient">
					<input type="hidden" name="recipientid" value="<%if(dicomMailRecipient.getId() !=null){%><%=dicomMailRecipient.getId()%><%}%>">
					<input type="hidden" name="recipientedited" value="true"> 
					<%}%>
	
					<!-- <table class="tableuser">  -->	
					<table class="table">
						<tr>
							<th colspan="3" rowspan="1">Neuen DICOM E-Mail Empfänger hinzufügen</th>
						</tr>
					
						<tr>
							<td id="institution"><img src="images/punkt.gif"></td>
							<td>Einrichtung:</td>
							<td id="tableTdInput"><input type="text" name="institution" value="<%if(dicomMailRecipient!=null && dicomMailRecipient.getInstitution() !=null){%><%=dicomMailRecipient.getInstitution().toString().trim()%><%}%>"
								maxlength="100" style="width: 99%;"></td>
						</tr>
						<tr>
							<td id="rec"><img src="images/punkt.gif"></td>
							<td>Empfänger:</td>
							<td id="tableTdInput"><input type="text" name="rec" value="<%if(dicomMailRecipient!=null && dicomMailRecipient.getRecipient()!=null){%><%=dicomMailRecipient.getRecipient().toString().trim()%><%}%>"
								maxlength="100" style="width: 99%;"></td>
						</tr>
							<tr>
							<td id="location"><img src="images/punkt.gif"></td>
							<td>Ort:</td>
							<td id="tableTdInput">
								<input type="text" name="location" value="<%if(dicomMailRecipient!=null && dicomMailRecipient.getLocation() !=null){%><%=dicomMailRecipient.getLocation().toString().trim()%><%}%>"
								maxlength="100" style="width: 99%;">
							</td>
						</tr>
						<tr>
							<td id="publicKeyId">
								<img src="images/punkt.gif">
							</td>
							<td>Schüssel-ID:</td>
							<td id="tableTdInput">
								<input type="text" name="publicKeyId" value="<%if(dicomMailRecipient!=null && dicomMailRecipient.getPublicKeyId() !=null){%><%=dicomMailRecipient.getPublicKeyId().toString().trim()%><%}%>"
								maxlength="100" style="width: 99%;">
							</td>
						</tr>
						<tr>
							<td id="mail_1">
								<img src="images/punkt.gif">
							</td>
							<td>Postfach (HD):</td>
							<td id="tableTdInput">
								<input type="text" name="mail_1" value="<%if(dicomMailRecipient!=null && dicomMailRecipient.getMailAddress1() !=null){%><%=dicomMailRecipient.getMailAddress1().toString().trim()%><%}%>"
								maxlength="100" style="width: 99%;">
							</td>
						</tr>
						<tr>
							<td id="mail_2">
								<img src="images/punkt.gif">
							</td>
							<td>Postfach (MA):</td>
							<td id="tableTdInput">
								<input type="text" name="mail_2" value="<%if(dicomMailRecipient!=null && dicomMailRecipient.getMailAddress2() !=null){%><%=dicomMailRecipient.getMailAddress2().toString().trim()%><%}%>"
								maxlength="100" style="width: 99%;">	
							</td>
						</tr>
						<tr>
							<td id="mail_3">
								<img src="images/punkt.gif">
							</td>
							<td>Postfach (KA):</td>
							<td id="tableTdInput">
								<input type="text" name="mail_3" value="<%if(dicomMailRecipient!=null && dicomMailRecipient.getMailAddress3() !=null){%><%=dicomMailRecipient.getMailAddress3().toString().trim()%><%}%>"
								maxlength="100" style="width: 99%;">
							</td>
						</tr>
					</table>
					<br>
					<table class="table">
 						<tr>
					        <td id="tableTdInput">
						        <div style="float:right;">
						        	<input name="reset" type="button" value="Abbrechen" class="myimagebutton" style="border: none; margin-right: 1px;"
						            	onclick="parent.location='<%=request.getContextPath().toString()%>/Dispatcher?identity=showlistofcontactpersonspage'" title="Vorgang abbrechen"> 
						      		 <input name="save" type="submit" value="Speichern" class="myimagebutton"  style="border: none; margin-left: 1px;" 
						           		 onclick="if(check_emails(check_form(this),1)){parent.location='<%=request.getContextPath().toString()%>/Dispatcher?identity=editrecipient'}" 
						            	title="&Auml;nderungen speichern">
						        </div>
					         </td>
					    </tr>	
					</table>
				</form>
				<br>
				<img src="images/horizontalerTenner.gif" style="margin-top: 15px; margin-bottom: 15px;">

				<div id="guideline">

					<p>
						<b>Hinweis:</b>
					</p>
					Alle Felder m&uuml;ssen ausgefüllt werden!<br><br>
					Zum Speichern der Empfängerdaten auf "Speichern" klicken.<br>
				</div>
				<!-- /hinweis--> <br clear="all" />
			</div>

			<%@ include file="include/footer.jsp"%>
		</div>

	</body>
</html>