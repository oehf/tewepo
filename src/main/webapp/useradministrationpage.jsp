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
	Vector<IPerson> allPersonsInDB;
	try {//zur Sicherheit
		allPersonsInDB = (Vector<IPerson>) request
				.getAttribute("allPersonsInDB");
	} catch (Exception e) {
		allPersonsInDB = new Vector<IPerson>();
	}

	String path = request.getContextPath().toString();
	String message = (String) request.getAttribute("message");
	if (message == null)
		message = "";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.openehealth.twp.tewepo.businesslogic.LockedIP"%>
<%@page import="java.util.Vector"%>
<%@page import="org.openehealth.twp.tewepo.businesslogic.Role"%>
<%@page import="org.openehealth.twp.tewepo.businesslogic.Role.Roles"%>
<%@page import="org.openehealth.twp.tewepo.controller.Dispatcher"%>
<%@page import="org.openehealth.twp.tewepo.businesslogic.IRole"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Teleradiologie Webportal</title>
		<link rel="stylesheet" href="<%=path%>/style/style.css" type="text/css">
		<style type="text/css" media="all">
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
				
				<h2>Benutzer</h2>

				<p>Diese Seite beinhaltet die Benutzerverwaltung des Webportals.</p>
				<br>
				<%
					if (ADMINISTRATOR) {
				%> <%@ include file="include/submenuuseradministration.jsp"%>
				<%
					} else {
						Dispatcher.invalidateSession(request, response,"useradministrationpage.jsp", "Unerlaubter Zugriff");
					}
				%> <br>

				<div id="message" style="display: inline; color: #990000;"><%=message%></div>
				
				<h2>Benutzer verwalten:</h2>
				<br/>
				<a href="#" class="add_link" onclick="parent.location='<%=request.getContextPath().toString()%>/Dispatcher?identity=adduserpage'" >Benutzer hinzufügen</a>
				
				<table class="table tableuseroverview">
					<tr>
						<th>Nr.</th>
						<th>Benutzername</th>
						<th>Nachname</th>
						<th>Vorname</th>
						<th>E-Mail</th>
						<!--  <th>Arzt</th>-->
						<th>Aktiv</th>
				<!--  	<th>Abteilung</th>-->
						<th>Rollen</th>
				<!--	<th>Berufsgruppe</th>-->
						<th></th>
						<th></th>
					</tr>

				<%
					if (allPersonsInDB != null) {
						int counter=0;
						for (IPerson p : allPersonsInDB) {
							counter++;
							String tmpRoleAdmin = "-";
							String tmpRolePhysician = "-";
							String tmpRolePatient = "-";
							for (IRole r : p.getRoles()) {
			
								if (r.getRole().equals(Role.Roles.ADMINISTRATOR)) {
									tmpRoleAdmin = "S";
			
								} else if (r.getRole().equals(Role.Roles.PHYSICIAN)) {
									tmpRolePhysician = "A";
			
								} else if (r.getRole().equals(Role.Roles.PATIENT)) {
									tmpRolePatient = "P";
			
								}
			
							}
							String roles = tmpRoleAdmin + "/" + tmpRolePhysician + "/"
									+ tmpRolePatient;
				%>
					<tr>
						<td>
							<%=counter%>
						</td>
						<td>
							<%=p.getLoginname()%>
						</td>
						<td>
							<%=p.getSurname()%>
						</td>
						<td>
							<%=p.getForename()%>
						</td>
						<td>
							<%=p.getEmailaddress()%>
						</td>
						<!--  <td style="text-align:center">
							<%if(p.isProfessional()){%><img src="images/hook.gif"><%}%>
						</td>-->
						<td style="text-align:center">
							<%if(p.isAccountActive()){%><img src="images/hook.gif"><%} %>
						</td>
				<!--	<td><%if(p.getDepartment()!=null){%><%=p.getDepartment()%><%} %></td> -->
						<td>
							<%=roles%>
						</td>
				<!-- 	<td style="border-right:none;"><%if(p.getOccupationgroup()!=null){%><%=p.getOccupationgroup()%><%} %></td> -->
						<td>
							<a href="<%=request.getContextPath().toString()%>/Dispatcher?identity=adminedituserdatapage&amp;id=<%=p.getObjectID()%>">
								<img src="images/pencil.gif" alt="Benutzerdaten bearbeiten" title="Benutzerdaten bearbeiten">
							</a>
						</td>
						<td>
							<%
							if (user.getObjectID() != p.getObjectID()) {
							%>
							<a href="<%=request.getContextPath().toString()%>/Dispatcher?identity=deleteperson&amp;id=<%=p.getObjectID()%>">
								<img src="images/delete.gif" alt="Benutzer l&ouml;schen" title="Benutzer l&ouml;schen" onClick="
								if (confirm('Wollen Sie den Benutzer wirklich löschen?'))
									this.form.submit()
								; else return false;">
							</a>
							<%
								}
							%>
						</td>
					</tr>

						<%
							}
						}
						%>

			</table>
			<br>


			<br>
			<img src="images/horizontalerTenner.gif" style="margin-top: 15px; margin-bottom: 15px;">
			
			<div id="guideline">

				<p>
					<b>Hinweis:</b>
				</p>
				Zum Bearbeiten einer Person auf <img src="images/pencil.gif" alt="Benutzerdaten bearbeiten" title="Benutzerdaten bearbeiten"> klicken.<br> 
				Zum L&ouml;schen einer Person auf <img src="images/delete.gif" alt="Benutzer l&ouml;schen" title="Benutzer l&ouml;schen"> klicken.<br>

				S = Systemadministrator<br>
				A = Arzt<br>
				<!--  P = Patient--></br>
				
				<image src="images/hook.gif"> zeigt, dass der Benutzerkonto aktiv ist.</image>
				
			</div>
				<!-- /hinweis--> <br clear="all" />

			</div>

			<%@ include file="include/footer.jsp"%></div>

	</body>
</html>