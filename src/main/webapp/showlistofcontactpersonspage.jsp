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
	List<DicomMailRecipient> listRecipients;
	try {//for safety
		listRecipients = (List<DicomMailRecipient>) request
				.getAttribute("listrecipients");
	} catch (Exception e) {
		listRecipients = new ArrayList<DicomMailRecipient>();
	}

	String path = request.getContextPath().toString();
	String message = (String) request.getAttribute("message");
	if (message == null)
		message = "";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.openehealth.twp.tewepo.businesslogic.LockedIP"%>
<%@page import="java.util.List"%>
<%@page
	import="org.openehealth.tewepo.twp.dmp.dmc.server.email.xml.DicomMailRecipient"%>
<%@page import="java.util.ArrayList"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
			<title>Teleradiologie Webportal</title>
			<link rel="stylesheet" href="<%=path%>/style/style.css" type="text/css">
			<style type="text/css" media="all">
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
	
			<div id="content" >
			
				<h1>Systemverwaltung</h1>
				<img src="images/horizontaldashedseperator.gif"
					style="margin-top: 5px; margin-bottom: 15px;">
				<h2>Partnerliste anzeigen</h2>
				
				<p>Diese Seite beinhaltet die Systemverwaltung des Webportals.</p>
				<br>
				<%
					if (ADMINISTRATOR) {
				%> <%@ include file="include/submenusystemadministration.jsp"%>
				<%
					} else {
						Dispatcher.invalidateSession(request, response,
								"showlistofcontactpersonspage.jsp",
								"Unerlaubter Zugriff");
					}
				%> <br>
	
				<div id="message" style="display: inline; color: #990000;"><%=message%></div>
				
				<h2>Partnerliste:</h2> 
				
				
				
				<br/>
					<a href="#" class="add_link" onclick="parent.location='<%=request.getContextPath().toString()%>/Dispatcher?identity=addnewrecipientpage'" title="Empf&auml;nger hinzuf&uuml;gen" >
						Empfänger hinzufügen
					</a>
					<br></br>
					<div id="partner" style="height: 350px; overflow:auto">
					<%
						if (listRecipients != null) {
							int counter = 0;
							for (DicomMailRecipient d : listRecipients) {
					%>
	
	
						<table class="table">
							<tr>
								<th colspan="5" rowspan="1"><%=d.getInstitution()%></th>
							</tr>
						
							<tr>
								<td id="tableTdPoint">
									<img src="images/punkt.gif">
								</td>
								<td style="width: 160px;">
									<b>Einrichtung:</b>
								</td>
								<td><%=d.getInstitution()%></td>
							</tr>
							<tr>
								<td id="tableTdPoint">
									<img src="images/punkt.gif">
								</td>
								<td style="width: 160px;">
									<b>Empf&auml;nger:</b>
								</td>
								<td><%=d.getRecipient()%></td>
							</tr>
							<tr>
								<td id="tableTdPoint">
									<img src="images/punkt.gif">
								</td>
								<td style="width: 160px;">
									<b>Ort:</b>
								</td>
								<td><%=d.getLocation()%></td>
								<td width="40px">
									<a href="<%=request.getContextPath().toString()%>/Dispatcher?identity=editrecipient&amp;recipientid=<%=d.getId()%>&amp;recipientedited=false"><img
									src="images/pencil.gif" alt="bearbeiten" title="bearbeiten">
									</a>
								</td>
							</tr>
							<tr>
								<td id="tableTdPoint">
									<img src="images/punkt.gif">
								</td>
								<td style="width: 160px;">
									<b>Schl&uuml;ssel-ID:</b>
								</td>
								<td><%=d.getPublicKeyId()%></td>
							</tr>
							<tr>
								<td id="tableTdPoint">
									<img src="images/punkt.gif">
								</td>
								<td style="width: 160px;">
									<b>Postfach (HD):</b>
								</td>
								<td><%=d.getMailAddress1()%></td>
								<td width="40px">
									<a href="<%=request.getContextPath().toString()%>/Dispatcher?identity=addnewrecipient&amp;deleterecipient=<%=d.getId()%>"><img
									src="images/delete.gif" alt="l&ouml;schen" title="l&ouml;schen" onClick="
										if (confirm('Wollen Sie den Empfänger wirklich löschen?'))
											this.form.submit()
									; else return false;">
									</a>
								</td>
							</tr>
							<tr>
								<td id="tableTdPoint">
									<img src="images/punkt.gif">
								</td>
								<td style="width: 160px;">
									<b>Postfach (MA):</b>
								</td>
								<td><%=d.getMailAddress2()%></td>
							</tr>
							<tr>
								<td id="tableTdPoint">
									<img src="images/punkt.gif">
								</td>
								<td style="width: 160px;"><b>Postfach (KA):</b></td>
								<td><%=d.getMailAddress3()%></td>
							</tr>
						</table>
						
						<br>
						
						<%
								}
							}
						%> <br>
						</div>
						<img src="images/horizontalerTenner.gif"
							style="margin-top: 15px; margin-bottom: 15px;">
						<div id="guideline">
						
						<p>
							<b>Hinweis:</b>
						</p>
						Zum Bearbeiten der Empfängerdaten auf <img src="images/pencil.gif" alt="Daten berbeiten" title="Daten bearbeiten"> klicken.<br>
						Zum L&ouml;schen eines Empfängers auf <img src="images/delete.gif" alt="Empfänger l&ouml;schen" title="Empfänger l&ouml;schen"> klicken.<br>
						<br>
					</div>
					<!-- /hinweis--> <br clear="all" />
	
				</div>

			<%@ include file="include/footer.jsp"%>
		</div>
	
	</body>
</html>