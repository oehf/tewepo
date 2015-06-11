
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
<%
	String path = request.getContextPath().toString();
	String message = (String) request.getAttribute("message");
	if (message == null)
		message = "";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

			<title>Teleradiologie Webportal</title>
			<link rel="stylesheet" href="<%=path%>/style/style.css" type="text/css">
			<script language="JavaScript" src="<%=path%>/js/inputcheck.js"
				type="text/javascript"></script>
			<style  type="text/css" media="all">
				<!--
				#navigation a#id_a_de_contactpage{font-weight:bold;}
				#navigation a#id_a_de_contactpage{font-weight:bold;}
				#navigation li#id_li_de_contactpage{background-color:#efefef;}
				#navigation li#id_li_de_contactpage{background-color:#efefef;}
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

			<h1>Kontakt</h1>
			
			<img src="images/horizontaldashedseperator.gif"	style="margin-top: 5px; margin-bottom: 15px;">
			<p></p>
		
			<div id="message" style="display: inline; color: #990000;"><%=message%></div>
		
			<h2>Kontaktformular:</h2>
				Falls Sie Fragen zum Portal haben verwenden Sie das folgende
				Formular.<br>
				Ihre Anfragen werden schnellst m&ouml;glich bearbeitet.<br></br>
		
			<form action="<%=path%>/Dispatcher" method="post" name="FormKontakt" id="FormKontakt" onsubmit="return check_form(this)">
				<input type="hidden" name="identity" value="sendmail">
		
					<table class="table tablecontact">

				<!--	<tr>
						<td id="tableTdPoint"><img src="images/punkt.gif"></td>
						<td><strong>Anrede:</strong>&nbsp;</td>
						<td id="tableTdInput"><select name="title" title="Anrede ausw&auml;hlen"  style="border:1px solid #990000;">
							<option selected="selected"></option>
							<option>Frau</option>
							<option>Herr</option>
							<option>Dr.</option>
							<option>Dr. med.</option>
							<option>PD.</option>
							<option>Prof.</option>
						</select></td>
						<td></td>
				
					</tr> -->
	 				<tr>
						<td id="tableTdPoint">
							<img src="images/punkt.gif">
						</td>
						<td>
							<strong>Akademischer Grad:</strong>&nbsp;
						</td>
						<td id="tableTdInput">
							<%if (user != null ) {%>
								<%= user.getTitle() %>
						
								<input type="hidden" name="title" id="title" value="<%=user.getTitle()%>" size="100" maxlength="100" style="width: 99%; border:1px solid #990000;">
								
							<%} else {%>
								<input type="text" name="title" id="title" value="" size="100" maxlength="100" style="width: 99%; border:1px solid #990000;"> <%}%>
						</td>
						<td></td>
	 				<tr>
						<td id="tableTdPoint">
							<img src="images/punkt.gif">
						</td>
						<td>
							<strong>Vorname:*</strong>&nbsp;
						</td>
						<td id="tableTdInput">
							<%if (user != null && user.getForename() != null
								&& !user.getForename().equals("")) {%>
								<%=user.getForename()%>
						
								<input type="hidden" name="forename" id="forename" value="<%=user.getForename()%>" size="100" maxlength="100" style="width: 99%; border:1px solid #990000;">
						
							<%} else {%>
								<input type="text" name="forename" id="forename" value="" size="100" maxlength="100" style="width: 99%; border:1px solid #990000;"> <%}%>
						</td>
						<td></td>
					</tr>  
					<tr>
						<td id="tableTdPoint">
							<img src="images/punkt.gif">
						</td>
						<td>
							<strong>Nachname:*</strong>&nbsp;
						</td>
						<td id="tableTdInput">
							<%if (user != null && user.getSurname() != null
								&& !user.getSurname().equals("")) {%>
								<%=user.getSurname()%>
				
								<input type="hidden" name="surname" id="surname" value="<%=user.getSurname()%>" size="100" maxlength="100" style="width: 99%; border:1px solid #990000;">
							<%} else {%>
								<input type="text" name="surname" id="surname" value="" size="100" maxlength="100" style="width: 99%; border:1px solid #990000;"><%}%>
						</td>
						<td></td>
	
					</tr>
					<tr>
						<td id="tableTdPoint">
							<img src="images/punkt.gif">
						</td>
						<td>
							<strong>E-Mail:*</strong>&nbsp;
						</td>
						<td id="tableTdInput">
							<%if (user != null && user.getEmailaddress() != null
								&& !user.getEmailaddress().equals("")) {%>
								<%=user.getEmailaddress()%>
				
							<input type="hidden" name="email" id="email" value="<%=user.getEmailaddress()%>" size="100" maxlength="100" style="width: 99%; border:1px solid #990000;">
				
							<%} else {%>
								<input type="text" name="email" id="email" value="" size="100" maxlength="100" style="width: 99%; border:1px solid #990000;"><%}%>
						</td>
						<td></td>
					</tr>
					<tr>
						<td id="tableTdPoint">
							<img src="images/punkt.gif">
						</td>
						<td>
							<strong>Anliegen:</strong>
						</td>
						<td id="tableTdInput">
							<select name="subject" title="Anliegen ausw&auml;hlen" style="border:1px solid #990000;">
								<option selected="selected">Anfrage</option>
								<option>Probleme</option>
								<option>Partner</option>
								<option>Sonstiges</option>
							</select>
						</td>
						<td></td>
					</tr>
					<tr>
						<%
							if (user != null) {
						%>
							<span style="color:#990000;">Die mit einem * gekennzeichneten Felder k&ouml;nnen nicht editiert werden!</style></span>
						<%
							} else {
						%>
							<span style="color:#990000;">Die mit einem * gekennzeichneten Felder m&uuml;ssen ausgefüllt werden!</style></span>
						<%
							}
						%>
					</tr>
					<tr>
						<td id="tableTdPoint">
							<img src="images/punkt.gif">
						</td>
						<td valign="top">
							<strong>Nachricht:</strong>
						</td>
						<td>
							<textarea name="message" rows="10" cols="50"></textarea>
						</td>
						<td></td>
					</tr>
				</table>
				<br>
				<table class="table">
					<tr>
						<td id="tableTdInput">
							<div style="float:right;">
								<input name="button_absenden" type="submit" value="Senden" class="myimagebutton" style="border: none; margin-left: 1px;" title="Nachricht versenden">
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
				Bitte Anliegen aus der Liste wählen und Nachricht eingeben.
	
			</div>
		</div>

		<%@ include file="include/footer.jsp"%></div>
	</body>
</html>