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
			<script language="JavaScript" src="<%=path%>/js/inputcheck.js" type="text/javascript"></script>
			<script type="text/javascript">
				function showPassword(){
					document.getElementById("password_1").innerHTML =
						'<table class="table">'
						+'<tr>'
						+'<td id="tableTdPoint"><img src="images/punkt.gif"></td>'
						+'<td style="width: 25%;"><b>Passwort:</b></td>'
						+'<td>*****</td>'
						+'<td style="width: 1%; background-color: transparent;">'
						+'<a href="javascript:editPassword()">'
						+'<img src="images/pencil.gif" alt="Person &auml;ndern"></a></td>' 
						+'</tr>'
						+'</table>';
						
					document.getElementById("password_2").innerHTML= '';		
					document.getElementById("submitButton").innerHTML='<br>'
						+'<br>' 
						+'<br>'
						+'<img src="images/horizontalerTenner.gif" style="margin-top: 15px; margin-bottom: 15px;">'
						+'<div id="guideline">'
						+'<p>' + '<b>Hinweis:</b>' + '</p>'
						+'Zum &Auml;ndern des Passworts auf <img src="images/pencil.gif" alt="Passwort &auml;ndern" title="Passwort &auml;ndern"> klicken.'
						+'<br>'
						+'<br>'
						+'</div>'
				}

				function editPassword() {
					document.getElementById("password_1").innerHTML = 
						'<table class="table">'
						+'<tr>'
						+'<td id="tableTdPoint"><img src="images/punkt.gif"></td>'
						+'<td style="width: 25%;"><b>Passwort:</b></td>'
						+ '<td>'
						+'<input type="password" id="password_1" name="password_1" '
						+ 'value="" maxlength="100" style="width: 99%; margin:1px;" >'
						+'</td>'
						+'</table>'; 
					document.getElementById("password_2").innerHTML= 
						'<table class="table">'
						+'<tr>'
						+'<td id="tableTdPoint"><img src="images/punkt.gif"></td>'
						+'<td style="width: 25%;"><b>Passwort wiederholen:</b></td>'
						+ '<td>'
						+'<input type="password" id="password_2" name="password_2" value="" maxlength="100" style="width: 99%; margin:1px;">'
						+'</td>'
						+'</tr>'
						+'</table>';		
					document.getElementById("submitButton").innerHTML='<br>'
						+'<table class="table">'
						+'<tr>'
						+'<td id="tableTdInput">'
						+'<div style="float:right;">'
						+'<input name="reset" type="button" value="Abbrechen" class="myimagebutton"'
						+'style="border: none; margin-right: 1px;" '
						+'onclick="showPassword()">'
						+'<input name="button_absenden" type="submit" style="border: none;"'
						+'class="myimagebutton" value="&Auml;ndern">'
						+'</div>'
						+'</td>'
						+'</tr>'
						+'</table>'
						+'</form>'
						+'<br>' +'<br>'
						+'<img src="images/horizontalerTenner.gif" style="margin-top: 15px; margin-bottom: 15px;">'
						+'<div id="guideline">'
						+'<p>' + '<b>Hinweis:</b>' + '</p>'
						+'Zum Speichern des Passworts auf "&Auml;ndern" klicken.'
						+'<br>' 
						+'<br>'
						+'<br>'
						+'</div>'
				}

	//-->
			</script>

			<style  type="text/css" media="all">
<!--
				#navigation a#id_a_de_useradministrationpage{font-weight:bold;}
				#navigation a#id_a_de_useradministrationpage{font-weight:bold;}
				#navigation li#id_li_de_useradministrationpage{background-color:#efefef;}
				#navigation li#id_li_de_useradministrationpage{background-color:#efefef;}
				
				#content a#horizontalmenuetable_a_showuserdatapage{font-weight:bold;}
				#content td#horizontalmenuetable_td_showuserdatapage{background-image:url(images/tabs/tab_active.gif)}
				#content td#horizontalmenuetable_td_useradministrationpage{border-bottom: 2px solid #990000}
				#content td#horizontalmenuetable_td_adduserpage{border-bottom: 2px solid #990000}
				-->
			</style>
</head>

<body onload="showPassword()">

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
			<h1>Benutzerverwaltung</h1>
			<img src="images/horizontaldashedseperator.gif"	style="margin-top: 5px; margin-bottom: 15px;">
			<h2>Meine Benutzerdaten</h2>

			<p>Diese Seite beinhaltet die Benutzerverwaltung des Webportals.</p>
			<br>
			<%
				if (ADMINISTRATOR) {
			%> <%@ include file="include/submenuuseradministration.jsp"%>
				<br>
			<%
				}
				if (!ADMINISTRATOR && !PHYSICIAN & !PATIENT) {
					Dispatcher.invalidateSession(request, response,"showuserdatapage.jsp", "Unerlaubter Zugriff");
				}
			%> 
		

			<div id="message" style="display: inline; color: #990000;"><%=message%></div>


			<form name="showUserDataForm" action="<%=path%>/Dispatcher" method="post" name="registerform" onsubmit="return check_form(this)">
				
				
				<input type="hidden" name="identity" value="editperson"/>

				<h2>Passwort bearbeiten:</h2>
				<div id="subject">
					<table class="table">
						<tr>
							<th colspan="3" rowspan="1">Benutzerdaten&uuml;berblick</th>
						</tr>
					</table>
				</div>
				
				<div id="username">
					<table class="table">
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td style="width: 25%;"><b>Benutzername:</b></td><!-- style="width: 32%;" -->
							<td><%=user.getLoginname()%></td>
						</tr>
					</table>
				</div>
				
				<div id="password_1">
					<table class="table">
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td ><b>Passwort:</b></td><!-- style="width: 32%;" -->
							<td>*****</td>
							<td style=" width:16px;">
								<a href="javascript:editPassword()">
									<img src="images/pencil.gif" title="Passwort &auml;ndern" alt="Passwort &auml;ndern">
								</a>
							</td>
						</tr>
					</table>
				</div>
				
				<div id="password_2"></div>
				
				<div id="userdata_1">		
					<table class="table">	
					
					<tr>
						<td id="tableTdPoint">
								<img src="images/punkt.gif">
						</td>
						<td style="width: 25%;"><b>Akademischer Grad:</b></td>
						<td><%=user.getTitle()%></td>
					</tr>	
					<tr>
						<td id="tableTdPoint">
								<img src="images/punkt.gif">
						</td>
						<td style="width: 25%;"><b>Vorname:</b></td>
						<td><%=user.getForename()%></td>
					</tr>
					<tr>
					<td id="tableTdPoint">
								<img src="images/punkt.gif">
						</td>
						<td><b>Nachname:</b></td>
						<td><%=user.getSurname()%></td>
					</tr>
					<tr>
					<td id="tableTdPoint">
								<img src="images/punkt.gif">
						</td>
						<td><b>E-Mail:</b></td>
						<td><%=user.getEmailaddress()%></td>
					</tr>
						<%
							if (user.getOccupationgroup() != null && !user.getOccupationgroup().trim().equals("")) {
						%>
								<tr>
								<td id="tableTdPoint">
									<img src="images/punkt.gif">
								</td>
									<td><b>Berufsgruppe:</b></td>
									<td><%=user.getOccupationgroup()%></td>
								</tr>
						<%
							}
							if (user.getOrganisation() != null && !user.getOrganisation().trim().equals("")) {
						%>
								<tr>
									<td id="tableTdPoint">
										<img src="images/punkt.gif">
									</td>
									<td><b>Organisation:</b></td>
									<td><%=user.getOrganisation()%></td>
								</tr>
						<%
							}
							if (user.getDepartment() != null && !user.getDepartment().trim().equals("")) {
						%>
								<tr>
									<td id="tableTdPoint">
										<img src="images/punkt.gif">
									</td>
									<td><b>Abteilung:</b></td>
									<td><%=user.getDepartment()%></td>
								</tr>
						<%
							}
						%>
						<%
							//	if (user.getAddress() != null) {
						%>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td><b>Straße/Nr.:</b></td>
							<td><%=user.getAddress().getStreet()%>&nbsp;<%=user.getAddress().getNumber()%></td>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td><b>PLZ/Ort:</b></td>
							<td><%=user.getAddress().getZipcode()%>&nbsp;<%=user.getAddress().getLocation()%></td>
						</tr>
						<%
							//	}
						%>
					</table>
				</div>				
					
				<div id="submitButton"></div>
				<br>
			</form>
				<!-- /hinweis--> <br clear="all" />

			</div>

			<%@ include file="include/footer.jsp"%>
		</div>

	</body>
</html>