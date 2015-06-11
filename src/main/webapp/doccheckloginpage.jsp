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

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
			<title>Teleradiologie Webportal</title>
			<link rel="stylesheet" href="<%=path%>/style/style.css" type="text/css">
			<script type="text/javascript" src="<%=path%>/js/pruefung.js">
			</script>
			<style  type="text/css" media="all">
				<!--
				#navigation a#id_a_de_loginpage{
					font-weight:bold;
				}
				#navigation a#id_a_de_loginpage{
					font-weight:bold;
				}
				#navigation li#id_li_de_loginpage{
					background-color:#efefef;
				}
				#navigation li#id_li_de_loginpage{
					background-color:#efefef;
				}
				#content a#horizontalmenuetable_a_doccheckloginpage{
					font-weight:bold;
				}
				#content td#horizontalmenuetable_td_doccheckloginpage{
					background-image:url(images/tabs/tab_active.gif)
				}
				#content td#horizontalmenuetable_td_portalloginpage{
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
				<h1>Anmeldung</h1>
				
				<img src="images/horizontaldashedseperator.gif"	style="margin-top: 5px; margin-bottom: 15px;">
				<h2>Anmeldung am Teleradiologieportal RND</h2>
			
				<p>
					Herzlich Willkommen beim Teleradiologieportal RND.<br>
					Bitte geben Sie Ihre Zugangsdaten ein.
				</p>
				<br>
				<%@ include file="include/submenulogin.jsp"%>
				<br>

				<div id="message" style="display: inline; color: #990000;"><%=message%></div>
				<h2>
					<font color="#990000">Zur Zeit nicht verfügbar!</font>
				</h2>
				<br>
			
				<strong>Bitte geben Sie Ihre DocCheck-Kennung ein:</strong>
				<br>
			
				<center>
					<img src="images/docCheckLoginDummy.jpg" alt="DocCheck Login"></img>
				</center>
				<br>
				<br>
				<img src="images/horizontalerTenner.gif" class="horizontaletrennlinie">
				<div id="guideline">
					<p>
						<b>Hinweis:</b>
					</p>

					Auf dieser Seite können Sie sich mit Ihrer DocCheck-Kennung am Portal anmelden.
					<br></br>
					Weitere Informationen zum externen Authentifizierungsdienst DocCheck entnehmen Sie der Webseite des Anbieters: <a href="http://www.doccheck.com/de/">www.doccheck.de</a>
					<br></br>
					Falls Sie keine DocCheck-Kennung haben, k&ouml;nnen Sie sich 
					<a href="<%=request.getContextPath().toString()%>/Dispatcher?identity=registrationpage"
						title="Registrieren">hier
					</a> 
					auch direkt am Portal registrieren und anschließend am Portal anmelden.
				</div>

			</div>

			<%@ include file="include/footer.jsp"%>
		</div>
	</body>
</html>