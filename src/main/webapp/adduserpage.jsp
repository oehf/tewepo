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
	
	Person newPerson = (Person) request.getAttribute("newPerson");
	
	String stringBoolNewProfessionalRole = request.getParameter("boolprofessional");
	String stringNewProfessionalRole = request.getParameter("professionalrole");

	boolean boolProfessionalRolePhysician = false;
	boolean boolProfessionalRoleAdmin = false;
	
	if(newPerson != null && newPerson.getRoles() != null) {
	for (IRole r : newPerson.getRoles()) {
		if (r.getRole().equals(Role.Roles.PHYSICIAN)) {
			boolProfessionalRolePhysician = true;
		} else if (r.getRole().equals(Role.Roles.ADMINISTRATOR)) {
			boolProfessionalRoleAdmin = true;
		}
	}
	}
	
	if (stringBoolNewProfessionalRole != null
			&& stringNewProfessionalRole != null) {
		/*
		 * here is trying to parse the parameters stringBoolProfessional into a boolean
		 */
		try {
			boolean tmpBoolNewProfessionalRole = Boolean
					.parseBoolean(stringBoolNewProfessionalRole);
			if (stringNewProfessionalRole
					.equals(Role.Roles.PHYSICIAN.toString()))
				boolProfessionalRolePhysician = tmpBoolNewProfessionalRole;
			else if (stringNewProfessionalRole
					.equals(Role.Roles.ADMINISTRATOR.toString()))
				boolProfessionalRoleAdmin = tmpBoolNewProfessionalRole;
		} catch (Exception e) {
			//failed to parse the string into a boolean value
		}
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.openehealth.twp.tewepo.businesslogic.LockedIP"%>
<%@page import="org.openehealth.twp.tewepo.businesslogic.Role"%>
<%@page import="org.openehealth.twp.tewepo.businesslogic.Role.Roles"%>
<%@page import="org.openehealth.twp.tewepo.businesslogic.IRole"%>
<%@page import="org.openehealth.twp.tewepo.businesslogic.IPerson"%><html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Teleradiologie Webportal</title>
		<link rel="stylesheet" href="<%=path%>/style/style.css" type="text/css">
		<script language="JavaScript" src="<%=path%>/js/inputcheck.js"
			type="text/javascript"></script>
		<script type="text/javascript">
		<!-- 
			function showprofessionalfields(){
				
				if ( document.registerform.rolePhysician.checked || document.registerform.roleAdministrator.checked ) {
					/*
					 * Benutzer ist Arzt oder Administrator, also muss er einer Ogranisation angehören und die Abteilung und Berufsgruppe muss ausgefüllt sein
					 */
						 document.getElementById("professional_2").innerHTML = 
						 '<table class="table">'
						+'<tr><th colspan="3" rowspan="1">Organisation</th></tr>'
						+'<tr><td id="tableTdPoint"><img src="images/punkt.gif"></td><td>Berufsgruppe:</td><td id="tableTdInput"><input type="text" name="occupationgroup" value="<%if (newPerson != null && newPerson.getOccupationgroup() != null) {%><%=newPerson.getOccupationgroup().trim()%><%}%>" maxlength="100" style="width: 99%;"></td></tr>'
						+'<tr><td id="tableTdPoint"><img src="images/punkt.gif"></td><td>Organisation:</td><td id="tableTdInput"><input type="text" name="organisation" value="<%if (newPerson != null && newPerson.getOrganisation() != null) {%><%=newPerson.getOrganisation().trim()%><%}%>" maxlength="100" style="width: 99%;"></td></tr>'
						+'<tr><td id="tableTdPoint"><img src="images/punkt.gif"></td><td>Abteilung:</td><td id="tableTdInput"><input type="text" name="department" value="<%if (newPerson != null && newPerson.getDepartment() != null) {%><%=newPerson.getDepartment().trim()%><%}%>" maxlength="100" style="width: 99%;"></td></tr>'
						+'</table>';
				} else {
					document.getElementById("professional_2").innerHTML = "";
				}
			
				}
			//-->
		</script>

		<style  type="text/css" media="all">
			<!--
			#navigation a#id_a_de_useradministrationpage{font-weight:bold;}
			#navigation a#id_a_de_useradministrationpage{font-weight:bold;}
			#navigation li#id_li_de_useradministrationpage{background-color:#efefef;}
			#navigation li#id_li_de_useradministrationpage{background-color:#efefef;}
			
			#content a#horizontalmenuetable_a_useradministrationpage{font-weight:bold;}
			#content td#horizontalmenuetable_td_showuserdatapage{border-bottom:  2px solid #990000}
			#content td#horizontalmenuetable_td_useradministrationpage{background-image:url(images/tabs/tab_active.gif)}
			#content td#horizontalmenuetable_td_adduserpage{border-bottom:  2px solid #990000}
			
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

				<h1>Benutzerverwaltung</h1>
				<img src="images/horizontaldashedseperator.gif"	style="margin-top: 5px; margin-bottom: 15px;">

				<h2>Benutzer anlegen</h2>

				<p>Diese Seite beinhaltet die Benutzerverwaltung des Webportals.</p>
				<br>
			<%
				if (ADMINISTRATOR) {
				%> <%@ include file="include/submenuuseradministration.jsp"%>
			<%
				} else {
						Dispatcher.invalidateSession(request, response,"adduserpage.jsp", "Unerlaubter Zugriff");
				}
			%> <br>

				<div id="message" style="display: inline; color: #990000;"><%=message%></div>

				<form action="<%=path%>/Dispatcher" method="post" name="registerform" onsubmit="return check_form(this)">
					<input type="hidden" name="identity" value="registration_AdminAddsNewPerson">	

					<table class="table">
						<tr>
							<th colspan="3" rowspan="1">Kennung</th>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td>Benutzername:</td>
							<td id="tableTdInput">
								<input type="text" name="username"  value="<%if(newPerson!=null && newPerson.getLoginname() !=null){%><%=newPerson.getLoginname().trim()%><%}%>"
									maxlength="100" style="width: 99%;">
							</td>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td>Passwort:</td>
							<td id="tableTdInput">
								<input type="password" name="password_1" maxlength="100" style="width: 99%;">
							</td>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td>Passwort wiederholen:</td>
							<td id="tableTdInput">
								<input type="password" name="password_2" maxlength="100" style="width: 99%;">
							</td>
						</tr>
					</table>

					<table class="table">
						<tr>
							<th colspan="3" rowspan="1">Personenbezogene Daten</th>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td>Akademischer Grad:</td>
							<td id="tableTdInput">
								<input type="text" name="title"  value="<%if(newPerson!=null && newPerson.getTitle() !=null){%><%=newPerson.getTitle().trim()%><%}%>"
									maxlength="100" style="width: 99%;">
							</td>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td>Vorname:</td>
							<td id="tableTdInput">
								<input type="text" name="forename"  value="<%if(newPerson!=null && newPerson.getForename() !=null){%><%=newPerson.getForename().trim()%><%}%>"
									maxlength="100" style="width: 99%;">
							</td>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td>Nachname:</td>
							<td id="tableTdInput">
								<input type="text" name="surname"  value="<%if(newPerson!=null && newPerson.getSurname() !=null){%><%=newPerson.getSurname().trim()%><%}%>"
									maxlength="100" style="width: 99%;">
							</td>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td>E-Mail:</td>
							<td id="tableTdInput">
								<input type="text" name="email"  value="<%if(newPerson!=null && newPerson.getEmailaddress() !=null){%><%=newPerson.getEmailaddress().trim()%><%}%>"
									maxlength="100" style="width: 99%;">
							</td>
						</tr>
					</table>

				<%
					boolean tmpBoolPatient = false;
					boolean tmpBoolPhysician = false;
					boolean tmpBoolAdmin = false;
					
					/*
					 * Vorhandene Rollen der Person setzen.
					 */
					if(newPerson != null && newPerson.getRoles() != null) {
					for (IRole r : newPerson.getRoles()) {
						 if (r.getRole().equals(Role.Roles.PHYSICIAN)) {
							tmpBoolPhysician = true;
						} else if (r.getRole().equals(Role.Roles.PATIENT)) {
							tmpBoolPatient = true;
						} else if (r.getRole().equals(Role.Roles.ADMINISTRATOR)) {
							tmpBoolAdmin = true;
						}
					}
					}
				/*
				 * set currently changed role
				 */
				tmpBoolPhysician = boolProfessionalRolePhysician;
				tmpBoolAdmin = boolProfessionalRoleAdmin;
			%>

				<table class="table">
					<tr>
						<th colspan="3" rowspan="1">Rollen</th>
					</tr>
					<tr>
						<td id="tableTdPoint">
							<img src="images/punkt.gif">
						</td>
						<td>Patient:</td>
						<td id="tableTdInput">
							<input type="checkbox" name="rolePatient" value="rolePatient" <%if (tmpBoolPatient) {%> checked="checked"<%}%> 
							onchange="showprofessionalfields()" style="width: 15px;"> &nbsp;(Rolle Patient nicht aktiv)
						</td>
					</tr>
					<tr>
						<td id="tableTdPoint">
							<img src="images/punkt.gif">
						</td>
						<td>Arzt:</td>
						<td id="tableTdInput">
							<input type="checkbox" name="rolePhysician" value="rolePhysician" <%if (tmpBoolPhysician) {%> checked="checked" <%}%>
							onchange="showprofessionalfields()" style="width: 15px;">
						</td>
					</tr>
					<tr>
						<td id="tableTdPoint">
							<img src="images/punkt.gif">
						</td>
						<td>Administrator:</td>
						<td id="tableTdInput">
							<input type="checkbox" name="roleAdministrator" <%if (tmpBoolAdmin) {%> checked="checked" <%}%>
							onchange="showprofessionalfields()" style="width: 15px;">
						</td>
					</tr>
				</table>

				<div id="professional_2"></div>
				<%
					if (boolProfessionalRolePhysician || boolProfessionalRoleAdmin) {
				%>
						<table class="table">
							<tr>
								<th colspan="3" rowspan="1">Organisation</th>
							</tr>
							<tr>
								<td id="tableTdPoint">
									<img src="images/punkt.gif">
								</td>
								<td>Berufsgruppe:</td>
								<td id="tableTdInput">
									<input type="text" name="occupationgroup" value="<%if (newPerson.getOccupationgroup() != null) {%><%=newPerson.getOccupationgroup().trim()%><%}%>"
									maxlength="100" style="width: 99%;">
								</td>
							</tr>
							<tr>
								<td id="tableTdPoint">
									<img src="images/punkt.gif">
								</td>
								<td>Organisation:</td>
								<td id="tableTdInput">
									<input type="text" name="organisation" value="<%if (newPerson.getOrganisation() != null) {%><%=newPerson.getOrganisation().trim()%><%}%>"
									maxlength="100" style="width: 99%;">
								</td>
							</tr>
							<tr>
								<td id="tableTdPoint">
									<img src="images/punkt.gif">
								</td>
								<td>Abteilung:</td>
								<td id="tableTdInput">
									<input type="text" name="department" value="<%if (newPerson.getDepartment() != null) {%><%=newPerson.getDepartment().trim()%><%}%>"
									maxlength="100" style="width: 99%;">
								</td>
							</tr>
						</table>
					<%
						}
					%>

						<table class="table">
							<tr>
								<th colspan="3" rowspan="1">Adresse</th>
							</tr>
							<tr>
								<td id="tableTdPoint">
									<img src="images/punkt.gif">
								</td>
								<td>Straße/Nr.:</td>
								<td id="tableTdInput">
									<input type="text" name="street" value="<%if(newPerson!=null && newPerson.getAddress() != null && newPerson.getAddress().getStreet() !=null){%><%=newPerson.getAddress().getStreet().trim()%><%}%>"
										style="width: 80%;" maxlength="33" > <!-- style="width: 99%;" -->
									<input type="text" name="number" value="<%if(newPerson!=null && newPerson.getAddress() != null && newPerson.getAddress().getNumber() !=null){%><%=newPerson.getAddress().getNumber().trim()%><%}%>"
										style="width: 16%;" maxlength="5" /> <!-- style="width: 99%;" -->
								</td>
							</tr>
							<tr>
								<td id="tableTdPoint">
									<img src="images/punkt.gif">
								</td>
								<td>PLZ/Ort:</td>
								<td id="tableTdInput">
									<input type="text" name="zipcode" value="<%if(newPerson!=null && newPerson.getAddress() != null && newPerson.getAddress().getZipcode() !=null){%><%=newPerson.getAddress().getZipcode().trim()%><%}%>"
										style="width: 16%;" maxlength="5" ><!--  -->
									 <input type="text" name="location" value="<%if(newPerson!=null && newPerson.getAddress() != null && newPerson.getAddress().getLocation() !=null){%><%=newPerson.getAddress().getLocation().trim()%><%}%>"
										style="width: 80%;" maxlength="33"/><!-- style="width: 99%;" -->
								</td>
							</tr>
					</table>
			
					<br>
					<table class="table">
						<tr>
							<td id="tableTdInput">
								<div style="float:right;">
								<input name="reset" type="button" value="Abbrechen"
									   class="myimagebutton"
									   style="border: none; margin-right: 1px;"
									   onclick="parent.location='<%=request.getContextPath().toString()%>/Dispatcher?identity=useradministrationpage'" title="Vorgang abbrechen"></input>
								<input name="save" type="submit" value="Speichern"
									   class="myimagebutton" 
									   style="border: none; margin-left: 1px;" 
									   title="Benutzer hinzuf&uuml;gen">
								</input>
								</div>
							</td>
						</tr>
					</table>
				</form>

				<br>
				<img src="images/horizontalerTenner.gif"
					style="margin-top: 15px; margin-bottom: 15px;">
			
				<div id="guideline">
					<p>
						<b>Hinweis:</b>
					</p>
						Alle Felder m&uuml;ssen ausgefüllt werden!<br><br>
						Zum Speichern der Daten auf "Speichern" klicken.<br>
						Zum Abbrechen auf "Abbrechen" klicken.<br>
				
				</div>
			<!-- /hinweis--> <br clear="all" />

			</div>

		<%@ include file="include/footer.jsp"%>
		</div>

	</body>
</html>