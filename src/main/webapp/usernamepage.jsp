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
	String path = request.getContextPath().toString();
	String message = (String) request.getAttribute("message");
	if (message == null)
		message = "";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.openehealth.twp.tewepo.businesslogic.LockedIP"%><html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

		<title>Teleradiologie Webportal</title>

		<link rel="stylesheet" href="<%=path%>/style/style.css" type="text/css">

		<script type="text/javascript" src="<%=path%>/js/pruefung.js"> </script>
	
		<style  type="text/css" media="all">
		<!--
			#navigation a#id_a_de_usernamepage{
				font-weight:bold;
			}
			#navigation a#id_a_de_usernamepage{
				font-weight:bold;
			}
			#navigation li#id_li_de_usernamepage{
				background-color:#efefef;
			}
			#navigation li#id_li_de_usernamepage{
				background-color:#efefef;
			}
			#content a#horizontalmenuetable_a_usernamepagerequest {
				font-weight: bold;
			}
			#content td#horizontalmenuetable_td_usernamepagerequest {
				background-image: url(images/tabs/tab_active.gif)
			}
			#content td#horizontalmenuetable_td_keywordpagerequest {
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
				
				
				<h1>Benutzerdaten Anforderung</h1>
				
				<img src="images/horizontaldashedseperator.gif"	style="margin-top: 5px; margin-bottom: 15px;">
			
				
				<h2>Benutzerdaten für das  Teleradiologieportal Rhein-Neckar Dreieck anfordern </h2>

				<p>			
				Auf dieser Seite können Sie ihre Benutzeraccount Angaben am Portal anfordern. Sie haben
				die Möglichkeit Ihren Benutzernamen und Passwort anzufordern.
				<br></br>
			</p>
			<br>
				<%@ include file="include/submenuUseraccountRequest.jsp"%> <br>

				<!--  <img src="images/horizontaldashedseperator.gif"	style="margin-top: 5px; margin-bottom: 15px;">-->
				
				<div id="message" style="display: inline; color: #990000;"><%=message%></div>

				<h2>Benutzername-Anfrage</h2>
				
				<strong>Bitte f&uuml;llen Sie folgendes Formular aus und klicken Sie auf absenden.
				Anschließend bekommen Sie auf Ihre registrierte E-Mail Adresse Ihr Benutzername zugeschickt.</strong> 
				<br></br>

				<form action="<%=path%>/Dispatcher" method="post" name="FormUsernameanfrage" id="FormUsernameanfrage"
					onsubmit="return pruefeLogin()">
					<input type="hidden" name="identity" value="usernamerequest">
					
					<table class="table">
						<tr>
							<td>
								<strong>Ihr Nachname:</strong>&nbsp;
							</td>
							<td id="tableTdInput">
								<input type="text" name="surname" id="surname" value="" size="100" maxlength="100" style="width: 99%;">
							</td>
						</tr>
						<tr>
							<td>
								<strong>Ihr Vorname:</strong>&nbsp;
							</td>
							<td id="tableTdInput">
								<input type="text" name="forename" id="forename" value="" size="100" maxlength="100" style="width: 99%;">
							</td>
						</tr>

						<tr>
							<td>
								<strong>Ihre E-Mail Adresse:</strong>
							</td>
							<td id="tableTdInput">
								<input type="text" name="email" value="" size="100" maxlength="100" style="width: 99%;">
							</td>

						</tr>
					</table>
					<br>
					<table class="table">
						<tr>
							<td id="tableTdInput">
								<div style="float:right;">
									<input name="I1" type="submit" value="Anfordern" class="myimagebutton" style="border: none; margin-left: 1px;">
								</div>
							</td>
						</tr>
					</table>
				</form>

				<br>
				<img src="images/horizontalerTenner.gif" style="margin-top: 15px; margin-bottom: 15px;">
				
				<div class="guideline">
					<b>Hinweis:</b><br>

						Bitte achten Sie bei der Eingabe Ihres Nachnamen und Ihrer
						E-Mail Adresse auf die Groß- und Kleinschreibung, da sonst die Benutzername-Anfrage nicht bearbeitet
						werden kann.<br></br>
						Sollten Sie weitere Probleme mit Ihrem Login haben, setzen Sie sich bitte mit dem <a href="<%=request.getContextPath().toString()%>/Dispatcher?identity=contactpage"
						title="Kontakt">Administrator</a> in Verbindung.
				</div>


			</div>

			<%@ include file="include/footer.jsp"%></div>

	</body>
</html>