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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="org.openehealth.twp.tewepo.businesslogic.LockedIP"%>
<%@page import="org.openehealth.twp.tewepo.businesslogic.ILogLastFiles"%>
<%@page import="java.util.List"%>
<%@page import="org.openehealth.twp.tewepo.helper.IPUtils"%>
<%@page import="java.text.SimpleDateFormat"%>

<html>
	<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%
	
	//String ip = request.getRemoteAddr();
	String ip = IPUtils.getIpFromRequest(request);

	if (!LockedIP.isActive(ip)) {
		String errorPath = "errorpage.jsp?message=Ihr Rechner ist gesperrt! Versuchen Sie es später noch einmal!";

		%>
			<jsp:forward page="<%=errorPath%>"/>
		<%
	}

	List<ILogLastFiles> allLastFilesInDB = null;
	try {//for safety
		allLastFilesInDB = (List<ILogLastFiles>) request.getAttribute("allLastFilesInDB");
	} catch (Exception e) {
		//allLastFilesInDB = new List<ILogLastFiles>();
	}

	String path = request.getContextPath().toString();
	String message = (String) request.getAttribute("message");
	if (message == null){
		message = "";
	}
	boolean debugMode = false;

	String debugModeString=(String) request.getAttribute("debugMode");
	if(debugModeString !=null)
		debugMode= Boolean.parseBoolean(debugModeString);

%>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="Pragma" content="no-cache">
<!--                                           -->
<!-- Smart GWT configs						   -->
<!--                                           -->
		<script>
			function toggleBlockToInfo() {
				
						document.getElementById('infoBlock').style.display = 'block';
					
						document.getElementById('historyBlock').style.display = 'none';
					

					
						document.getElementById('navig_right_button_infoBlock').style.backgroundColor ='#efefef';
						document.getElementById('navig_right_button_infoBlock_a').style.fontWeight ='bold';

						document.getElementById('navig_right_button_historyBlock').style.backgroundColor ='#ffffff';
						document.getElementById('navig_right_button_historyBlock_a').style.fontWeight ='normal';
						
		
				
			}
			function toggleBlockToHistory() {
				
				document.getElementById('historyBlock').style.display = 'block';
			
				document.getElementById('infoBlock').style.display = 'none';
			
				document.getElementById('navig_right_button_historyBlock').style.backgroundColor ='#efefef';
				document.getElementById('navig_right_button_historyBlock_a').style.fontWeight ='bold';

				document.getElementById('navig_right_button_infoBlock').style.backgroundColor ='#ffffff';
				document.getElementById('navig_right_button_infoBlock_a').style.fontWeight ='normal';
				}
		
			
			var isomorphicDir = "DICOM_Mail_Client/sc/";
			
		</script>

<!--                                           -->
<!-- This script loads your compiled module.   -->
<!-- If you add any GWT meta tags, they must   -->
<!-- be added before this line.                -->
<!--                                           -->
		<script type="text/javascript" language="javascript"
		src="DICOM_Mail_Client/DICOM_Mail_Client.nocache.js">
		</script>
		<title>
			Teleradiologie Webportal
		</title>
		<link rel="stylesheet" href="<%=path%>/style/style.css" type="text/css">
		<style type="text/css" media="all">
<!--
			#navigation a#id_a_de_dicomemailpage {
				font-weight: bold;
				}
	
			#navigation a#id_a_de_dicomemailpage {
				font-weight: bold;
				}
	
			#navigation li#id_li_de_dicomemailpage {
				background-color: #efefef;
				}
	
			#navigation li#id_li_de_dicomemailpage {
				background-color: #efefef;
				}
				
			#navig_right #navig_right_button_infoBlock {
				background-color: #efefef;
				}
				
			#navig_right li a#navig_right_button_infoBlock_a{
				font-weight: bold;
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
				<h1>
					Dateien versenden
				</h1>
				<img src="images/horizontaldashedseperator.gif" style="margin-top: 5px; margin-bottom: 15px;">
				
				<h2>DICOM E-Mail</h2>
		
				<p>
					Das Teleradiologieportal Rhein-Neckar Dreieck bietet die M&ouml;glichkeit zum
					Versenden von Dateien per DICOM E-Mail.<br>
					Bitte geben Sie die erforderlichen Daten ein.
				</p>

				<p>
					<font color="#990000"><%=message %></font>
					<br>
				</p>
		
				<% if(!debugMode){ %>

	
						<h2>Neue DICOM E-Mail erstellen:</h2>
					
						<br>	
						<div id="wrapper_dicommail">				
							<div id="hidden_userId" title="<%=user.getId()%>">
							
							</div>
						
							<div id="uploadApplet" class="groupBox" style=" display:none; float:left; margin-left: 5px; padding-top: 0px; padding-bottom: 10px; text-align: center; width: 534px; border-top: none;">
								<img alt="" src="./images/backgroundFileUpload_top.png" style="position: relative; left: -1px; margin-bottom: 10px;"> 
					
									<applet 
										name="JUpload" 
										code="wjhk.jupload2.JUploadApplet" 
										archive="wjhk.jupload.jar" 
										codebase="./applets" 
										width="515" 
										height="254">  <!--400-->
										
										<param name="postURL" value="<%=request.getContextPath().toString()%>/Dispatcher?identity=upload" />
										
										<!--  <param name="maxChunkSize" value="500000" />
										<param name="uploadPolicy" value="PictureUploadPolicy" />-->
										
										
										<param name="showLogWindow" value="false" />
										<param name="showStatusBar" value="true" />
										<!--  <param name="allowedFileExtensions" value="jpg/jpeg/tif/dcm/txt/pdf"/>-->
										<param name="lookAndFeel" value="system" />
										<param name="sslVerifyCert" value="server" />
										<param name="debugLevel" value="100"/>
										<param name="afterUploadURL" value="javascript:refreshFileListJSNI();">
				
				
										Java 1.5 or higher plugin required. 
									</applet>	
							</div>
		
							<div id="navigation" style="float: right;  width: auto; height:auto; margin-left: 5px; margin-top:17px; "><!--border-left: 2px solid #990000;-->
								
								<ul id="navig_right">
									<li id="navig_right_button_infoBlock"  style="border-top:1px solid #F9B000"> <!-- onClick="checklist(this);" -->
										<a href="#infoBlock"  id="navig_right_button_infoBlock_a" onClick="toggleBlockToInfo();" >Anleitung</a>
									</li>
									<li id="navig_right_button_historyBlock">
										<a href="#historyBlock" id="navig_right_button_historyBlock_a" onClick="toggleBlockToHistory();" >Historie</a>
									</li>
								</ul>
							
								<div id="historyBlock" class="info" style=" overflow:auto; float: right; width: 230px; height: 940px;  margin-left: 5px;  font-size:0.8em; color:#404040; margin-top:10px; display: none;">
								
									<h2>Historie</h2>
							<%
								if (allLastFilesInDB != null && allLastFilesInDB.size() !=0) {
									int counter=0;
									for (ILogLastFiles f : allLastFilesInDB) {
										counter++;
										String dateConv="";
										SimpleDateFormat datetime = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
										
										SimpleDateFormat formatter = new SimpleDateFormat();
									
										dateConv = datetime.format(f.getDate());					
							%>			
									<h3>
										<b>Datei <%=counter %>:</b>
									</h3> 				
									<b>Datum:</b>&nbsp;<%=dateConv %><br> 
									<b>Dateiname:&nbsp;</b> <%=f.getFilename() %><br> 
									<b>Dateigröße:&nbsp;</b> <%=f.getSize() %> <br>
									<b>Empfänger:&nbsp;</b> <%=f.getRecipientsRecipient() %> 
									<br></br>
							<% 
									}
								}else{
							%>		
									Keine Dateien verschickt!
							<% 	
								}
							%>	
				
					
								</div>
		
								<div id="infoBlock" class="info" style="float: right;font-weight: normal; width: 230px; margin-left: 5px;  font-size:0.8em;  margin-top:5px; color:#404040;">
									
									<h2>Anleitung zum Versenden von DICOM E-Mails</h2>
									
										Folgen Sie bitte den unten aufgeführten Schritten um eine DICOM E-Mail zu versenden.
									
										<h3><b>Datei auswählen und hochladen</b></h3>
										<h3>Schritt 1:</h3> Drücken Sie auf das "Auswahl" Button, daraufhin
										   wird das Applet gestartet.<br>
										<h3>Schritt 2:</h3> Klicken Sie im Applet auf den "Durchsuchen.." Button
										   und wählen Sie eine Datei aus.<br> 
										   Folgende Dateiformate sind erlaubt: 
										   JPG, JPEG, TIFF, DCM, PDF, TXT				   
										<h3>Schritt 3:</h3> Um die ausgewählte Datei hochzuladen, klicken Sie auf "Hochladen"
										<h3>Schritt 4:</h3> Nachdem der Uploadvorgang beendet wird, schließt das Applet und die Datei wird mit der Dateiname aufgelistet.
										   Hier besteht die Möglichkeit die Datei mit dem "Löschen" Button zu entfernen. 
										   <br><br>
										   Um mehrere Dateien hochzuladen wiederholen Sie die Schritte 1 bis 4.
										<br></br>
										<h3><b>Empfänger wählen</b></h3>
										<h3>Schritt 5:</h3> Um den Empfänger zu wählen wird der Ort, die Einrichtung und der Empfänger aus den gegebenen Listen
										   nacheinander ausgewählt. Zu den Empfängern werden jeweils drei Mailserver angeboten. Diese sind mit HD für Heidelberg, 
										   MA für Mannheim und KA für Karlsruhe gekennzeichnet. 
										   <br></br>
										   Es besteht die Möglichkeit den zuletzt gewählten Empfänger auszusuchen.
										   Hierzu wird aus der Liste "Zuletzt gewählt" der Eintrag angeklickt. Falls das erste Mal Dateien versendet 
										   werden, ist diese Liste leer.
										   <br></br>
										   Der ausgewählte Empfänger wird direkt unten nochmals eingeblendet.
											<br></br>
										<h3><b>Angaben zum Patienten ausfüllen</b></h3>  
										<h3>Schritt 6:</h3> Bitte tragen Sie in diesem Schritt die Angaben zum Befund und dem Patienten. 
										<br></br>
										<h3><b>Abschicken der Dateien</b></h3>
										<h3>Schritt 7:</h3> Durch das Anklicken von "Daten prüfen" werden die eingegebenen Daten überprüft.
										<h3>Schritt 8:</h3> Klicken Sie jetzt auf "Daten senden"
											
								</div>	
							</div>

	
						<!-- OPTIONAL: include this if you want history support --> 
		
						<iframe src="javascript:''" id="__gwt_historyFrame" tabIndex='-1'
							style="position: absolute; width: 0; height: 0; border: 0">
						</iframe>

						<div id="form" style="width: 540px; min-height:940px; float: left;">
						</div>
					</div>
					<img src="images/horizontalerTenner.gif" style="margin-top: 15px; margin-bottom: 15px;">
				<% }
	
				if(debugMode){
				%>
					<div id="Debugging">
						<p>
							<h2>Debug Mode</h2>
						</p>
						<table class="table tableuseroverview" style="font-size:1.0em;">
							<tr>
								<th colspan="3" rowspan="1" style="text-align:center;">DICOM E-Mailversand testen</th>
							</tr>
						</table>
						<br>
						<p>
							Dieser Modus ermöglicht das einfache Debuggen der DICOM E-Mail Funktionalität. Beispieldateien werden zum Testen 
							verwendet, die im cache-Ordner zum Versenden vorhanden sind. Eine DICOM E-Mail mit der Beispieldatei wird aufgebaut, indem sie verschlüsselt und signiert wird.
							An  vordefinierte Empfänger  wird dann diese E-Mail versendet.   
						</p>
						
						<br>
						<p>				
							Der <b>"Testfiles anlegen"</b> Button kopiert aus dem  debug-Ordner die Beispieldateien in den cache-Ordner.<br>
							Das ständige kompilieren der Seite wird somit verhindert.<br>				 
							Durch das Anklicken von <b>"Testmail Senden"</b> Button wird die Beispieldatei als DICOM E-Mail verschickt.<br>
						</p>
						<br>
				
		     		   	<input name="debug_testMailButton" type="button" value="Testfiles anlegen" 
		      	  			class="myimagebutton" style="border: none; margin-right: 1px;"
		            		onclick="parent.location='<%=request.getContextPath().toString()%>/Dispatcher?identity=debug_copyMail'" title="Testfiles anlegen">
		        		</input>
		        		<input name="debug_testButton" type="button" value="Testmail senden" 
							class="myimagebutton" style="border: none; margin-right: 1px;"
		         		   onclick="parent.location='<%=request.getContextPath().toString()%>/Dispatcher?identity=debug_sendMail'" title="Testnachricht verschicken">
		     		   </input>
		     		   <br><br></br>
		  		</div>

				<img src="images/horizontalerTenner.gif" style="margin-top: 15px; margin-bottom: 15px;">

			<% } %>
	
				<div id="guideline">
					<p>
						<b>Hinweis:</b>
					</p>
					Sie können mehrere Dateien hochladen und versenden.<br>
					Die mit * bezeichnete Felder müssen ausgefüllt werden.<br>
					
				</div>

			</div>
			<%@ include file="include/footer.jsp"%>
		</div>
	</body>
</html>