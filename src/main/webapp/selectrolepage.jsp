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
			<jsp:forward page="<%=errorPath%>"/>
		<%
	}
	String path = request.getContextPath().toString();
	String message = (String) request.getAttribute("message");
	if (message == null) {
		message = request.getParameter("message");
		if (message == null) {
			message = "";
		}
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.openehealth.twp.tewepo.businesslogic.LockedIP"%>
<%@page import="org.openehealth.twp.tewepo.businesslogic.Role"%>
<%@page import="org.openehealth.twp.tewepo.businesslogic.Role.Roles"%>
<%@page import="org.openehealth.twp.tewepo.businesslogic.IRole"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
			<title>Teleradiologie Webportal</title>
			<link rel="stylesheet" href="<%=path%>/style/style.css" type="text/css">
			<script type="text/javascript" src="<%=path%>/js/pruefung.js">
			</script>
			<style  type="text/css" media="all">
				<!--
				#navigation a#id_a_de_loginpage{font-weight:bold;}
				#navigation a#id_a_de_loginpage{font-weight:bold;}
				#navigation li#id_li_de_loginpage{background-color:#efefef;}
				#navigation li#id_li_de_loginpage{background-color:#efefef;}
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
					<h1>Anmeldung am Teleradiologieportal Rhein-Neckar Dreieck</h1>
					<img src="images/horizontaldashedseperator.gif"	style="margin-top: 5px; margin-bottom: 15px;">
						<h2>Rollenauswahlmen&uuml;</h2>
						<div id="message" style="display: inline; color: #990000;"><%=message%></div>
						<p>Zum Einloggen w&auml;hlen Sie bitte die gew&uuml;nschte Rolle aus.</p>
						<br>
				<%
						/*
						 * check wich role exists and put these
						 */
						boolean roleAdminExists = false;
						boolean rolePhysicianExists = false;
						boolean rolePatientExists = false;
					
						for (IRole r : user.getRoles()) {
					
							if (r.getRole().equals(Role.Roles.ADMINISTRATOR))
								roleAdminExists = true;
							else if (r.getRole().equals(Role.Roles.PHYSICIAN))
								rolePhysicianExists = true;
							else if (r.getRole().equals(Role.Roles.PATIENT))
								rolePatientExists = true;
					
						}
					%>
						<form action="<%=path%>/Dispatcher" method="post" name="selectroleform">
						<input type="hidden" name="identity" value="selectrole">
						<table class="table">
							<tr>
								<th colspan="2">Verf&uuml;gbare Rollen:</th>
							</tr>
							<%
								if (rolePatientExists) {
							%>
									<tr>
										<td>
											<strong>Patient</strong>
										</td>
										<%
										//check the current role and preselecting
									if (PATIENT) {
										%>
										<td style="width: 60%; height: 40px; text-align: center;">
											<input type="radio" name="role" value="patient" checked="checked">
										</td>
										<%
									} else {
										%>
										<td style="width: 60%; height: 40px; text-align: center;">
											<input type="radio" name="role" value="patient">
										</td>
										<%
									}
								%>
							</tr>
							<%
								}
								if (rolePhysicianExists) {
							%>
									<tr>
										<td>
											<strong>Arzt</strong>
										</td>
								<%
											///check the current role and preselecting
									if (PHYSICIAN) {
								%>
										<td style="width: 60%; height: 40px; text-align: center;">
											<input type="radio" name="role" value="physician" checked="checked">
										</td>
								<%
									} else {
								%>
										<td style="width: 60%; height: 40px; text-align: center;">
											<input type="radio" name="role" value="physician">
										</td>
								<%
									}
								%>
									</tr>
							<%
								}
								if (roleAdminExists) {
							%>
									<tr>
										<td>
											<strong>Administrator</strong>
										</td>
								<%
										//check the current role and preselecting
									if (ADMINISTRATOR) {
								%>
										<td style="width: 60%; height: 40px; text-align: center;">
											<input type="radio" name="role" value="administrator" checked="checked">
										</td>
								<%
									} else {
								%>
										<td style="width: 60%; height: 40px; text-align: center;">
											<input type="radio" name="role" value="administrator">
										</td>
								<%
									}
								%>
									</tr>
							<%
								}
							%>
								<tr>
									<td colspan="3" align="left" style="color: red">
										<div id="message" style="display: inline"><%=message%>
										</div>
									</td>
								</tr>
							</table>
							<br>
							<table class="table">
								<tr>
									<td id="tableTdInput">
										<div style="float:right;">
											<input type="submit" value="Weiter" title="Weiter" style="border: none;" class="myimagebutton">
										</div>
									</td>
								</tr>
							</table>
						</form>
						<br></br>

						<img src="images/horizontalerTenner.gif" class="horizontaletrennlinie">
						<div id="guideline">
							<p>
								<b>Hinweis:</b>
							</p>
						Bitte w&auml;hlen Sie eine Rolle aus und klicken Sie auf "Weiter" um die Auswahl zu übernehmen. 
						</div>


					</div>

				<%@ include file="include/footer.jsp"%></div>

			</body>
</html>