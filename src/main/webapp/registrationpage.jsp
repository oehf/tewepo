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

	if(ip  == null){
		ip = request.getRemoteAddr();
	}
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
	
	
	String department_ = (String) request.getParameter("department");
	if (department_ == null) {
		department_ = "";
	}
	String occupationgroup_ = (String) request.getParameter("occupationgroup");
	if (occupationgroup_ == null) {
		occupationgroup_ = "";
	}
	String organisation_ = (String) request.getParameter("organisation");
	if (organisation_ == null) {
		organisation_ = "";
	}
	
	
	

	Person registrationPerson = (Person) request.getAttribute("registrationPerson");

	String stringBoolNewProfessionalRole = request.getParameter("boolprofessional");
	String stringNewProfessionalRole = request.getParameter("professionalrole");

	boolean boolProfessionalRolePatient = false;
	boolean boolProfessionalRolePhysician = true;
	
	if(registrationPerson != null && registrationPerson.getRoles() != null) {
	for (IRole r : registrationPerson.getRoles()) {
		if (r.getRole().equals(Role.Roles.PATIENT)) {
			boolProfessionalRolePatient = true;
		} else if (r.getRole().equals(Role.Roles.PHYSICIAN)) {
			boolProfessionalRolePhysician = true;
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
					.equals(Role.Roles.PATIENT.toString()))
				boolProfessionalRolePatient = tmpBoolNewProfessionalRole;
			else if (stringNewProfessionalRole
					.equals(Role.Roles.PHYSICIAN.toString()))
				boolProfessionalRolePhysician = tmpBoolNewProfessionalRole;

		} catch (Exception e) {
			//error while parsing the string to a boolean value
		}
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.openehealth.twp.tewepo.businesslogic.LockedIP"%>
<%@page import="org.openehealth.twp.tewepo.businesslogic.Role"%>
<%@page import="org.openehealth.twp.tewepo.businesslogic.Role.Roles"%>
<%@page import="org.openehealth.twp.tewepo.businesslogic.IRole"%>
<%@page import="org.openehealth.twp.tewepo.businesslogic.IPerson"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

			<title>Teleradiologie Webportal</title>
			<link rel="stylesheet" href="<%=path%>/style/style.css" type="text/css">
			<script language="JavaScript" src="<%=path%>/js/differentmethods.js" type="text/javascript"></script>
			<script language="JavaScript" src="<%=path%>/js/inputcheck.js" type="text/javascript"></script>
			<script type="text/javascript">

			var department=<%if (request.getParameter("department") != null ) {%><%=request.getParameter("department")%><%}else{%>""<%}%>;
			var department1="";
			//	<!-- 
				function registrationshowprofessionalfields(){
	
					if ( document.registerform.role[1].checked) {
						/*
						 * User is physician or administrator, so he must belong to a organisation and the department and profession must be filled
						 */
						 document.getElementById("professional_2").innerHTML = 
						 '<table class="table">'
			 			+'<tr><th colspan="3" rowspan="1">Organisation</th></tr>'
						+'<tr><td id="tableTdPoint"><img src="images/punkt.gif"></td><td>Berufsgruppe*:</td><td id="tableTdInput"><input type="text" name="occupationgroup" value="<%if (registrationPerson != null && registrationPerson.getOccupationgroup() != null) {%><%=registrationPerson.getOccupationgroup().trim()%><%}%>" maxlength="100" style="width: 99%;"></td></tr>'
						+'<tr><td id="tableTdPoint"><img src="images/punkt.gif"></td><td>Organisation*:</td><td id="tableTdInput"><input type="text" name="organisation" value="<%if (registrationPerson != null && registrationPerson.getOrganisation() != null) {%><%=registrationPerson.getOrganisation().trim()%><%}%>" maxlength="100" style="width: 99%;"></td></tr>'
						+'<tr><td id="tableTdPoint"><img src="images/punkt.gif"></td><td>Abteilung*:</td><td id="tableTdInput"><input type="text" name="department" value="<%if (registrationPerson != null && registrationPerson.getDepartment() != null) {%><%=registrationPerson.getDepartment().trim()%><%}%>" maxlength="100" style="width: 99%;"></td></tr>'
						+'</table>';
					} else {
						document.getElementById("professional_2").innerHTML = "";
					}

				}
				//-->
			</script>
			<style  type="text/css" media="all">
			<!--
				#navigation a#id_a_de_registrationpage{font-weight:bold;}
				#navigation a#id_a_de_registrationpage{font-weight:bold;}
				#navigation li#id_li_de_registrationpage{background-color:#efefef;}
				#navigation li#id_li_de_registrationpage{background-color:#efefef;}
				
				
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
		
		boolean tmpBoolPatient = false;
		boolean tmpBoolPhysician = false;
 		%>

		<div id="content">
			
			<h1>Registrieren</h1>
			<img src="images/horizontaldashedseperator.gif"	style="margin-top: 5px; margin-bottom: 15px;">
			
			<h2>Registrierungsformular</h2>
			Auf dieser Seite k&ouml;nnen Sie sich am Portal registrieren.<br></br>
			Damit Sie das Teleradiologieportal nutzen k&ouml;nnen,
			m&uuml;ssen Sie eine Registrierung durchf&uuml;hren.<br>
			Bitte f&uuml;llen Sie hierf&uuml;r das Registrierungsformular aus.<br></br>
			<i>
				<span>Alle * Felder m&uuml;ssen ausgefüllt werden!</style>
				</span>
			</i><br>
			<div id="message" style="display: inline; color: #990000;"><%=message%></div>

			<!--<div id="guideline">-->

			<form action="<%=path%>/Dispatcher" method="post" name="registerform" onsubmit="return check_form(this)">
				<input type="hidden" name="identity" value="registration"> 

				<!-- <table class="tableuser">  -->
				<table class="table">
					<tr>
						<th colspan="3" rowspan="1">Kennung</th>
					</tr>
					<tr>
						<td id="tableTdPoint">
							<img src="images/punkt.gif">
						</td>
						<td>
							<strong>Benutzername*:</strong>&nbsp;
						</td>
						<td id="tableTdInput">
							<input type="text" name="username" value="<%if(registrationPerson!=null && registrationPerson.getLoginname() !=null){%><%=registrationPerson.getLoginname().trim()%><%}else if(request.getParameter("username")!=null){%> <%=request.getParameter("username")%><%} %>"
								maxlength="100" style="width: 99%;">					
						</td>
					</tr>
					<tr>
						<td id="tableTdPoint">
							<img src="images/punkt.gif">
						</td>
						<td></td>
						<td style="font-size:0.9em; color:#808080">
							<font >Mind. 6 Zeichen lang. Keine Umlaute oder Sonderzeichen einfügen.</font>
						</td>
					</tr>
					<tr>
						<td id="tableTdPoint">
							<img src="images/punkt.gif">
						</td>
						<td>
							<strong>Passwort*:</strong>&nbsp;
						</td>
						<td id="tableTdInput">
							<input type="password" name="password_1" maxlength="100" style="width: 99%;">
						</td>
					</tr>
					<tr>
						<td id="tableTdPoint">
							<img src="images/punkt.gif">
						</td>
						<td></td>
						<td style="font-size:0.9em; color:#808080"> 
							<font >Mind. aus 6 Zeichen und eine Zahl und ein Sonderzeichen (@#!?*$%^&§+=.,).</font>
						</td>
					</tr>
					<tr>
						<td id="tableTdPoint">
							<img src="images/punkt.gif">
						</td>
						<td>
							<strong>Passwort wiederholen*:</strong>&nbsp;
						</td>
						<td id="tableTdInput">
							<input type="password" name="password_2" maxlength="100" style="width: 99%;">
						</td>
					</tr>
					
				</table>

				<!-- <table class="tableuser">  -->
				<table class="table">
					<tr>
						<th colspan="3" rowspan="1">Personenbezogene Daten</th>
					</tr>
					<tr>
						<td id="tableTdPoint">
							<img src="images/punkt.gif">
						</td>
						<td>
							<strong>Akademischer Grad:</strong>&nbsp;
						</td>
						<td id="tableTdInput">
							<input type="text" name="title" value="<%if(registrationPerson!=null && registrationPerson.getTitle() !=null){%><%=registrationPerson.getTitle().trim()%><%}else if(request.getParameter("title")!=null){%> <%=request.getParameter("title")%><%}%>"
								maxlength="100" style="width: 99%;">
						</td>
					</tr>
					<tr>
						<td id="tableTdPoint">
							<img src="images/punkt.gif">
						</td>
						<td>
							<strong>Vorname*:</strong>&nbsp;
						</td>
						<td id="tableTdInput">
							<input type="text" name="forename" value="<%if(registrationPerson!=null && registrationPerson.getForename() !=null){%><%=registrationPerson.getForename().trim()%><%}else if(request.getParameter("forename")!=null){%> <%=request.getParameter("forename")%><%}%>"
								maxlength="100" style="width: 99%;">
						</td>
					</tr>
					<tr>
						<td id="tableTdPoint">
							<img src="images/punkt.gif">
						</td>
						<td>
							<strong>Nachname*:</strong>&nbsp;
						</td>
						<td id="tableTdInput">
							<input type="text" name="surname" value="<%if(registrationPerson!=null && registrationPerson.getSurname() !=null){%><%=registrationPerson.getSurname().trim()%><%}else if(request.getParameter("surname")!=null){%> <%=request.getParameter("surname")%><%}%>"
								maxlength="100" style="width: 99%;">
						</td>
					</tr>
					<tr>
						<td id="tableTdPoint">
							<img src="images/punkt.gif">
						</td>
						<td>
							<strong>E-Mail*:</strong>&nbsp;
						</td>
						<td id="tableTdInput">
							<input type="text" name="email" value="<%if(registrationPerson!=null && registrationPerson.getEmailaddress() !=null){%><%=registrationPerson.getEmailaddress().trim()%><%}else if(request.getParameter("email")!=null){%> <%=request.getParameter("email")%><%}%>"
								maxlength="100" style="width: 99%;">
						</td>
					</tr>
				</table>


				<%
					
	
				/*
	 			* set existing roles of the person
				*/
					if(registrationPerson != null && registrationPerson.getRoles() != null) {
						for (IRole r : registrationPerson.getRoles()) {
							if (r.getRole().equals(Role.Roles.PHYSICIAN)) {
								tmpBoolPhysician = true;
							} else if (r.getRole().equals(Role.Roles.PATIENT)) {
								tmpBoolPatient = true;
							}
						}
					}
				/*
	 			* set curently changed roles
	 			*/
					tmpBoolPatient = boolProfessionalRolePatient;
					tmpBoolPhysician = boolProfessionalRolePhysician;
				%>

				<!-- <table class="tableuser">  -->
				<table class="table">
					<tr>
						<th colspan="3" rowspan="1">Rolle</th>
					</tr>
					<tr>
						<td id="tableTdPoint">
							<img src="images/punkt.gif">
						</td>
						<td style="color:#990000;" colspan="3" rowspan="1">
							Bitte eine Auswahl treffen!
						</td>
	
					</tr>
					<tr>
						<td id="tableTdPoint">
							<img src="images/punkt.gif">
						</td>
						<td>
							<strong>Patient:</strong>&nbsp;
						</td>
						<td id="tableTdInput">
							<input type="radio" name="role" value="rolePatient"  <%if(tmpBoolPatient){%>checked="checked"<%}%>
								onchange="registrationshowprofessionalfields()" style="width: 15px; border: none;">&nbsp;<!--(Rolle Patient nicht aktiv)  -->
						</td>
					</tr>
					<tr>
						<td id="tableTdPoint">
							<img src="images/punkt.gif">
						</td>
						<td>
							<strong>Arzt:</strong>&nbsp;
						</td>
						<td id="tableTdInput">
							<input type="radio" name="role" value="rolePhysician"  <%if(tmpBoolPhysician){%>checked="checked"<%}%>
								onchange="registrationshowprofessionalfields()" style="width: 15px; border: none;">
						</td>
					</tr>
				</table>

				<div id="professional_2">
				<%
					if (boolProfessionalRolePhysician) {
				%>
					<table class="table">
						<tr>
							<th colspan="3" rowspan="1">Organisation</th>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>	
							<td>Berufsgruppe*:</td>
							<td id="tableTdInput">
								<input type="text" name="occupationgroup"
									value="<%if (registrationPerson !=null && registrationPerson.getOccupationgroup() != null){%><%=registrationPerson.getOccupationgroup().trim()%><%}else {%><%=occupationgroup_%><%}%>"
									maxlength="100" style="width: 99%;">
							</td>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td>Organisation*:</td>
							<td id="tableTdInput">
								<input type="text" name="organisation"
									value="<%if (registrationPerson !=null && registrationPerson.getOrganisation() != null){%><%=registrationPerson.getOrganisation().trim()%><%}else {%><%=organisation_%><%}%>"
									maxlength="100" style="width: 99%;">
							</td>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td>Abteilung*:</td>
							<td id="tableTdInput">
								<input type="text" name="department"
									value="<%if (registrationPerson !=null && registrationPerson.getDepartment() != null){%><%=registrationPerson.getDepartment().trim()%><%}else {%><%=department_%><%}%>"
									maxlength="100" style="width: 99%;">
								
							</td>
						</tr>
					</table>
				<%
					}
				%>
</div>

					<!-- <table class="tableuser">  -->
					<table class="table">
						<tr>
							<th colspan="3" rowspan="1">Adresse</th>
						</tr>
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td>
								<strong>Straße/Nr.*:</strong>&nbsp;
							</td>
							<td id="tableTdInput">
								<input type="text" name="street" value="<%if(registrationPerson!=null && registrationPerson.getAddress() != null && registrationPerson.getAddress().getStreet() !=null){%><%=registrationPerson.getAddress().getStreet().trim()%><%}else if(request.getParameter("street")!=null){%> <%=request.getParameter("street")%><%}%>"
									 maxlength="33" style="width: 80%;"/> <!--size="33"  -->
									 
								<input type="text" name="number" value="<%if(registrationPerson!=null && registrationPerson.getAddress() != null && registrationPerson.getAddress().getNumber() !=null){%><%=registrationPerson.getAddress().getNumber().trim()%><%}else if(request.getParameter("number")!=null){%> <%=request.getParameter("number")%><%}%>"
									 maxlength="5" style="width: 16%;"/> <!-- size="5"" -->							
							</td>
						</tr>						
						<tr>
							<td id="tableTdPoint">
								<img src="images/punkt.gif">
							</td>
							<td>
								<strong>PLZ/Ort*:</strong>&nbsp;
							</td>
							<td id="tableTdInput" >
								<input type="text" name="zipcode" value="<%if(registrationPerson!=null && registrationPerson.getAddress() != null && registrationPerson.getAddress().getZipcode() !=null){%><%=registrationPerson.getAddress().getZipcode().trim()%><%}else if(request.getParameter("zipcode")!=null){%> <%=request.getParameter("zipcode")%><%}%>"
									 maxlength="5" style="width: 16%;"/><!--size="5"  -->
									 
								 <input type="text" name="location" value="<%if(registrationPerson!=null && registrationPerson.getAddress() != null && registrationPerson.getAddress().getLocation() !=null){%><%=registrationPerson.getAddress().getLocation().trim()%><%}else if(request.getParameter("location")!=null){%> <%=request.getParameter("location")%><%}%>"
									 maxlength="33" style="width: 80%;" /><!--size="33"  -->
							</td>
						</tr>
					</table>
					<br>

					<!-- <table class="tableuser">  -->
					<table class="table">
						<tr>
							<td id="tableTdInput">
								<div style="float:right;">
									<input name="submit" type="submit" value="Registrieren" class="myimagebutton" style="border: none; margin-left: 1px;"
										onclick="if(return check_form(this))parent.location='<%=request.getContextPath().toString()%>/Dispatcher?identity=registration'">
									</input>
								</div>
							</td>
						</tr>
					</table>
				</form>
				<br>
				<img src="images/horizontalerTenner.gif" style="margin-top: 15px; margin-bottom: 15px;">

				<div id="hinweis">
					<p>
						<b>Hinweis:</b>
					</p>

<!--<%-->
<!--	if (message != null && !message.equals("")) {-->
<!--%>-->
<!--<div style="color: #990000;" id="message" style="display:inline"><%=message%></div>-->
<!--<%-->
<!--	}-->
<!--%> -->

	Für die Eingabekontrolle muss JavaScript aktiviert sein!
	
										
			</div>

		</div>

		<%@ include file="include/footer.jsp"%>
		
	</div>

	</body>
</html>