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
			<style  type="text/css" media="all">
				<!--
				#navigation a#id_a_de_legalnoticepage{font-weight:bold;}
				#navigation a#id_a_de_legalnoticepage{font-weight:bold;}
				#navigation li#id_li_de_legalnoticepage{background-color:#efefef;}
				#navigation li#id_li_de_legalnoticepage{background-color:#efefef;}
				-->
			</style>
	</head>
	<body>

		<%@ include file="include/header.jsp"%>
		<div id="page">

			<%
				if (ADMINISTRATOR) {
			%> <%@ include file="include/menuadmin.jsp"%>
			<%
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
			 %> <%@ include
				file="include/mainmenu.jsp"%> <%
			 	}
			 %>

			<div id="content">
				<h1>Impressum</h1>
				<img src="images/horizontaldashedseperator.gif"	style="margin-top: 5px; margin-bottom: 15px;">
				<div id="message" style="display: inline; color: #990000;"><%=message%></div>

				<br/>
				<h2>Anbieter</h2>
			
				<p class="bodytext">&nbsp;</p>
				<p class="bodytext">Vertreten durch
				</p>
				<p class="bodytext">E-Mail: 
				</p>
				<p class="bodytext">&nbsp;</p>
				<h3>Kontakt</h3>
				
				<p class="bodytext">&nbsp;</p>
				<h3>Webdesign und Content Management</h3>
				
				<p class="bodytext">&nbsp;</p>
				<h3>Datenschutz</h3>
				<p class="bodytext">Beim Zugriff auf die allgemeinen Informationen der Medizinischen Fakultät werden Informationen über Nutzerzugriffe zu statistischen Zwecken ausschließlich anonymisiert in Protokolldateien gespeichert. Erfasst werden dabei nur die Tatsache des Zugriffs und das aufgerufene Dokument. Ein Bezug zur Person des Nutzers ist auf jeden Fall ausgeschlossen. Es werden keine Einzelnutzerprofile erstellt, auch nicht pseudonymisiert. 
				</p>
				<p class="bodytext">&nbsp;</p>
				<h3>Seiten Dritter</h3>
				<p class="bodytext">Auf den Seiten der Medizinischen Fakultät Heidelberg finden Sie zahlreiche Links (=Querverweise), auch über Banner zu klinikumsexternen Seiten Dritter. Diese sind als solche gekennzeichnet. Bei der Aktivierung dieser Links wird ein neues Fenster geöffnet und der Nutzer wird zum Angebot des Drittanbieters weitergeleitet.
				</p>
				<p class="bodytext">&nbsp;</p>
				<p class="bodytext">Bei direkten oder indirekten Verweisen auf fremde Internetseiten, die außerhalb des Verantwortungsbereiches der Fakultät liegen, würde eine Haftungsverpflichtung ausschließlich in dem Fall in Kraft treten, in dem daie Medizinische Fakultät Heidelberg von den Inhalten Kenntnis hat und es ihm technisch möglich und zumutbar wäre, die Nutzung im Falle rechtswidriger Inhalte zu verhindern. Die medizinische Fakultät erklärt daher ausdrücklich, dass zum Zeitpunkt der Linksetzung die entsprechenden verlinkten Seiten frei von illegalen Inhalten waren. Es hat keinerlei Einfluss auf die aktuelle und zukünftige Gestaltung und auf die Inhalte der gelinkten/verknüpften Seiten dieser Drittanbieter. Deshalb distanziert es sich ausdrücklich von allen Inhalten aller gelinkten /verknüpften Seiten, die nach der Linksetzung verändert wurden.
				</p>
				<p class="bodytext">&nbsp;</p>
					<!--  Text: [end] -->
						
					<!--  CONTENT ELEMENT, uid:1978/text [end] -->
					
					<!--  CONTENT ELEMENT, uid:20450/text [begin] -->
				<a id="c20450"></a>
				<!--  Header: [begin] -->
				<div class="csc-header csc-header-n2"><h1>Haftungsausschluss</h1></div>
				<!--  Header: [end] -->
					
				<!--  Text: [begin] -->
				<h3>1. Inhalt des Onlineangebotes </h3>
				<p class="bodytext">Das Internetportal der Medizinischen Fakultät Heidelberg stellt seinen Patientinnen und Patienten sowie weiteren Interessierten vielfältige und qualitativ hochstehende Informationen zur Verfügung.<br />Die Medizinische Fakultät übernimmt keinerlei Gewähr für die Aktualität, Korrektheit, Vollständigkeit oder Qualität der bereitgestellten Informationen. Haftungsansprüche gegen die Fakultät, welche sich auf Schäden materieller oder ideeller Art beziehen, die durch die Nutzung oder Nichtnutzung der dargebotenen Informationen bzw. durch die Nutzung fehlerhafter und unvollständiger Informationen verursacht wurden, sind grundsätzlich ausgeschlossen, sofern seitens der Fakultät kein nachweislich vorsätzliches oder grob fahrlässiges Verschulden vorliegt. <br />Alle Angebote sind freibleibend und unverbindlich. Die Medizinische Fakultät Heidelberg behält es sich ausdrücklich vor, Teile der Seiten oder das gesamte Angebot ohne gesonderte Ankündigung zu verändern, zu ergänzen, zu löschen oder die Veröffentlichung zeitweise oder endgültig einzustellen.
				</p>
				<p class="bodytext">&nbsp;</p>
				<h3>2. Verweise und Links </h3>
				<p class="bodytext">Bei direkten oder indirekten Verweisen auf fremde Webseiten (&quot;Hyperlinks&quot;), die außerhalb des Verantwortungsbereiches der Medizinische Fakultät Heidelberg liegen, würde eine Haftungsverpflichtung ausschließlich in dem Fall in Kraft treten, in dem die Medizinische Fakultät Heidelberg von den Inhalten Kenntnis hat und es ihm technisch möglich und zumutbar wäre, die Nutzung im Falle rechtswidriger Inhalte zu verhindern. <br />Die Medizinische Fakultät Heidelberg erklärt hiermit ausdrücklich, dass zum Zeitpunkt der Linksetzung keine illegalen Inhalte auf den zu verlinkenden Seiten erkennbar waren. Auf die aktuelle und zukünftige Gestaltung, die Inhalte oder die Urheberschaft der verlinkten/verknüpften Seiten hat die Medizinische Fakultät Heidelberg keinerlei Einfluss. Deshalb distanziert es sich hiermit ausdrücklich von allen Inhalten aller verlinkten /verknüpften Seiten, die nach der Linksetzung verändert wurden. Diese Feststellung gilt für alle innerhalb des eigenen Internetangebotes gesetzten Links und Verweise sowie für Fremdeinträge in von der Medizinische Fakultät Heidelberg eingerichteten Gästebüchern, Diskussionsforen, Linkverzeichnissen, Mailinglisten und in allen anderen Formen von Datenbanken, auf deren Inhalt externe Schreibzugriffe möglich sind. Für illegale, fehlerhafte oder unvollständige Inhalte und insbesondere für Schäden, die aus der Nutzung oder Nichtnutzung solcherart dargebotener Informationen entstehen, haftet allein der Anbieter der Seite, auf welche verwiesen wurde, nicht derjenige, der über Links auf die jeweilige Veröffentlichung lediglich verweist.
				</p>
				<p class="bodytext">&nbsp;</p>
				<h3>3. Urheber- und Kennzeichenrecht </h3>
				<p class="bodytext">Die Medizinische Fakultät Heidelberg ist bestrebt, in allen Publikationen die Urheberrechte der verwendeten Bilder, Grafiken, Tondokumente, Videosequenzen und Texte zu beachten, von ihm selbst erstellte Bilder, Grafiken, Tondokumente, Videosequenzen und Texte zu nutzen oder auf lizenzfreie Grafiken, Tondokumente, Videosequenzen und Texte zurückzugreifen. <br />Alle innerhalb des Internetangebotes genannten und ggf. durch Dritte geschützten Marken- und Warenzeichen unterliegen uneingeschränkt den Bestimmungen des jeweils gültigen Kennzeichenrechts und den Besitzrechten der jeweiligen eingetragenen Eigentümer. Allein aufgrund der bloßen Nennung ist nicht der Schluss zu ziehen, dass Markenzeichen nicht durch Rechte Dritter geschützt sind! <br />Das Copyright für veröffentlichte, von der Medizinische Fakultät Heidelberg selbst erstellte Objekte bleibt allein beim Autor der Seiten. Eine Vervielfältigung oder Verwendung solcher Grafiken, Tondokumente, Videosequenzen und Texte in anderen elektronischen oder gedruckten Publikationen ist ohne ausdrückliche Zustimmung der Medizinischen Fakultät Heidelberg nicht gestattet.
				</p>
				<p class="bodytext">&nbsp;</p>
				<h3>4. Datenschutz </h3>
				<p class="bodytext">Sofern innerhalb des Internetangebotes die Möglichkeit zur Eingabe persönlicher oder geschäftlicher Daten (Emailadressen, Namen, Anschriften) besteht, so erfolgt die Preisgabe dieser Daten seitens des Nutzers auf ausdrücklich freiwilliger Basis. Die Inanspruchnahme und Bezahlung aller angebotenen Dienste ist - soweit technisch möglich und zumutbar - auch ohne Angabe solcher Daten bzw. unter Angabe anonymisierter Daten oder eines Pseudonyms gestattet. Die Nutzung der im Rahmen des Impressums oder vergleichbarer Angaben veröffentlichten Kontaktdaten wie Postanschriften, Telefon- und Faxnummern sowie Emailadressen durch Dritte zur Übersendung von nicht ausdrücklich angeforderten Informationen ist nicht gestattet. Rechtliche Schritte gegen die Versender von sogenannten Spam-Mails bei Verstössen gegen dieses Verbot sind ausdrücklich vorbehalten.
				</p>
				<p class="bodytext">&nbsp;</p>
				<h3>5. Rechtswirksamkeit dieses Haftungsausschlusses </h3>
				<p class="bodytext">Dieser Haftungsausschluss ist als Teil des Internetangebotes zu betrachten, von dem aus auf diese Seite verwiesen wurde. Sofern Teile oder einzelne Formulierungen dieses Textes der geltenden Rechtslage nicht, nicht mehr oder nicht vollständig entsprechen sollten, bleiben die übrigen Teile des Dokumentes in ihrem Inhalt und ihrer Gültigkeit davon unberührt. </p>
				<br />
				<br />
			</div>

			<%@ include file="include/footer.jsp"%></div>

		</body>
</html>