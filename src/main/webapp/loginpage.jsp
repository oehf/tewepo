
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
--%><%@ page language="java" contentType="text/html; charset=UTF-8"
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
			<style type="text/css" media="all">
			<!--
				#navigation a#id_a_de_loginpage {
					font-weight: bold;
				}

				#navigation a#id_a_de_loginpage {
					font-weight: bold;
				}

				#navigation li#id_li_de_loginpage {
					background-color: #efefef;
				}

				#navigation li#id_li_de_loginpage {
					background-color: #efefef;
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

			<h2>Anmeldung am Teleradiologieportal Rhein-Neckar Dreieck</h2>

			<p>
				Herzlich Willkommen beim Teleradiologieportal Rhein-Neckar Dreieck.
				<br></br>
				Auf dieser Seite können Sie sich am Portal anmelden. Falls Sie noch
				keine Kennung haben, k&ouml;nnen Sie sich 
				<a href="<%=request.getContextPath().toString()%>/Dispatcher?identity=registrationpage"
					title="Registrieren">hier
				</a> 
				registrieren.
				
			</p>
			<br>
			<!--  <%@ include file="include/submenulogin.jsp"%> <br>-->

			<div id="message" style="display: inline; color: #990000;"><%=message%></div>

			<h2>Login</h2>

			<strong>Bitte geben Sie Ihren Benutzernamen und Ihr Kennwort ein:</strong>
			<br>

			<form action="<%=path%>/Dispatcher" method="post" name="loginform" onsubmit="return pruefeLogin()">
				<input type="hidden" name="identity" value="login">
					<table class="tablenew">
						<tr>
							<td>
								<b>Benutzername:</b>&nbsp;
							</td>
							<td id="tableTdInput">
								<input type="text" name="name" value="" size="100" maxlength="100" style="width: 99%;" tabindex="1">	
								<a style="text-decoration: none;" href="<%=request.getContextPath().toString()%>/Dispatcher?identity=usernamepage" title="Benutzernameanfordern">
									<font size="3" color="#808080">
										Benutzername vergessen?
									</font>
								</a>							
							</td>

						</tr>
						<tr>
							<td>
								<b>Passwort:</b>
							</td>
							<td id="tableTdInput">
								<input type="password" name="password" size="100" maxlength="100" style="width: 99%;" tabindex="2">
								<a style="text-decoration: none;" href="<%=request.getContextPath().toString()%>/Dispatcher?identity=keywordpage" title="Benutzernameanfordern">
									<font size="3" color="#808080">
										Passwort vergessen?
									</font>
								</a>						
							</td>
						</tr>
						<tr>
							<td> </td>
							<td>
								
							</td>
						</tr>
					</table>
					<br>

					<table class="table">
						<tr>
							<td id="tableTdInput">
								<div style="float:right;">
									<input type="submit" value="Login"style="border: none;" class="myimagebutton">
								</div>
							</td>
						</tr>
					</table>
				</form>
				<br>

				<!--  <img src="images/horizontalerTenner.gif" class="horizontaletrennlinie">-->
	
					<!--  <div id="guideline">
						<p>
							<b>Hinweis:</b>
			
						</p>-->

						<!-- Falls Sie eine Kennung beim medizinischen Authentifizierungsservice
						DocCheck besitzen, k&ouml;nnen Sie sich mit dieser am Portal
						anmelden. Hierf&uuml;r w&auml;hlen Sie den Reiter "DocCheck-Anmeldung"
						und geben Ihre Kennung ein.-->
					</div>
						<%@ include file="include/footer.jsp"%>
				</div>

		</body>
	</html>