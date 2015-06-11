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
			
			<style type="text/css" media="all">
			<!--
				#navigation a#id_a_de_homepage {
					font-weight: bold;
				}
				
				#navigation a#id_a_de_homepage {
					font-weight: bold;
				}
				
				#navigation li#id_li_de_homepage {
					background-color: #efefef;
				}
				
				#navigation li#id_li_de_homepage {
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
				<h1>Startseite</h1>
				<img src="images/horizontaldashedseperator.gif"	style="margin-top: 5px; margin-bottom: 15px;">
				
				<div id="message" style="display: inline; color: #990000;"><%=message%></div>
				<br />
				<h2>Portal des Teleradiologienetzwerks Rhein-Neckar Dreieck</h2>
				<br>
				<h2>
					<font color="#990000">Das Teleradiologieportal läuft zunächst in einer Pilotphase <br>und ist nicht für den  
				produktiven Einsatz zugelassen. </font>
				</h2><br>
				
				Seit einigen Jahren existiert in der Metropolregion 
				Rhein-Neckar das Teleradiologienetzwerk RND für den
				einrichtungsübergreifenden Austausch von medizinischen Daten wie
				beispielsweise Bilddaten unterschiedlicher Formate. Die Kommunikation
				innerhalb des Netzwerks basiert auf dem DICOM E-Mail Standard und
				erfordert entsprechende DICOM E-Mail Clients bei den einzelnen
				Kommunikationspartnern. Speziell die Installation, Konfiguration sowie
				die Wartung dieser Clients verlangt technisches Verständnis. Dieses
				haben gerade Anwender mit geringer Nutzung des Netzwerks, wie
				beispielsweise niedergelassene Ärzte, selten und können aus diesem Grund
				nicht in das Netzwerk eingebunden werden. Um dieser Problematik zu
				begegnen und damit die Ausweitung des Teleradiologienetzwerks weiter
				voranzutreiben wurde dieses Open Source Teleradiologie Webportal für den
				Versand von DICOM E-Mails entwickelt. Das Portal stellt eine ebenso
				sichere wie einfache Alternative zu den vorhandenen DICOM E-Mail Clients
				dar. <br>
				<br>
				Das Webportal eröffnet die Möglichkeit, medizinische Daten (Bilder)
				mittels DICOM E-Mail an Partner innerhalb des Teleradiologienetzwerks zu
				versenden, ohne dass eine vorherige Softwareinstallation beim Absender
				notwendig ist. Der Versendeworkflow für ein Bild gestaltet sich wie
				folgt: Der Anwender ruft die URL des Teleradiologie Webportals auf. Ist
				er bereits am Portal registriert, kann er sich direkt anmelden und die
				Funktionen des Portals nutzen. Andernfalls ist zuerst eine Registrierung
				mit anschließender Überprüfung der angegebenen Daten des Anwenders,
				durch den Administrator des Portals, notwendig. Zusätzlich bietet das Portal die Möglichkeit
				einen LDAP Server für die Benutzerverwaltung einzubinden. Ein
				eingeloggter Anwender kann den Empfänger für die DICOM E-Mail innerhalb
				des Teleradiologienetzwerks auswählen und anschließend beliebige Bilder
				für den Versand zum Webserver, welcher sich in der demilitarisierten
				Zone (DMZ) des Universitätsklinikums Heidelberg befindet, hochladen. Vor
				der Übertragung der Dateien wird durch ein Java Applet sichergestellt,
				dass diese signiert und verschlüsselt sind. Der Webserver erstellt dann
				eine standardkonforme DICOM E-Mail und versendet diese entsprechend des
				SMTP (Simple Mail Transfer Protocol) Protokolls. Das Teleradiologie
				Webportal wird den Projektpartnern unter einer Open Source Lizenz zur
				freien Nutzung zur Verfügung gestellt. <br>
				<br>
				Das Webportal des Teleradiologienetzwerks ermöglicht niedergelassenen
				Ärzten in der Metropolregion Rhein-Neckar, sowie anderen Anwendern mit
				geringem Datentransferbedarf, die Möglichkeit kostenlos die Vorteile des
				Netzwerks zu nutzen und somit ohne eigenen Aufwand mit allen Partnern
				des Netzwerks in Verbindung zu stehen. Das Portal stellt einen wichtigen
				Meilenstein bei der Ausweitung des Teleradiologienetzwerks dar, es
				verbessert damit die Patientenversorgung in der Metropolregion
				Rhein-Neckar. Das Konzept, ebenso wie die Open Source Software des
				Webportals sind jedoch nicht nur in dieser Region anwendbar, sondern
				können auch in andere Regionen exportiert werden. <br>
				<br>

			</div>

			<%@ include file="include/footer.jsp"%>
		</div>

	</body>
</html>