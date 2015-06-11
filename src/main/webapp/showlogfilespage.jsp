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

	String message = (String) request.getAttribute("message");
	if (message == null){
		message = "";
	}
	
	if (!LockedIP.isActive(ip)) {
		String errorPath = "errorpage.jsp?message=Ihr Rechner ist gesperrt! Versuchen Sie es später noch einmal!";

		%>
			<jsp:forward page="<%=errorPath%>"/>
		<%
	}
	String path = request.getContextPath().toString();
	//String Array with all Nodes of the Log file tree
	String[] allNodes = (String[]) request.getAttribute("treenodes");
	if(allNodes==null)
		allNodes=new String[0];
	// Number of elements of the tree
	int allNodesArrayLength = allNodes.length;
	/*
	 * filename of the logfile to be opened
	 */
	String logfilename = request.getParameter("logfilename");
	if(logfilename==null)
		logfilename="info.log";
	//Vector with the contents of the file to be displayed
	Vector<String> logfile = (Vector<String>) request
			.getAttribute("logfile");
	if(logfile==null)
		logfile=new Vector<String>();
	
	logfile.trimToSize();
	if(logfile.size()==0)
		logfile.add(0,"Keine Logeintr&auml;ge vorhanden!");
	
	//currently displayed logfile
	int currentLogFileNodeNumber=0;
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.openehealth.twp.tewepo.businesslogic.LockedIP"%>
<%@page import="java.util.Vector"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
			<title>Teleradiologie Webportal</title>
				<link rel="stylesheet" href="<%=path%>/style/style.css" type="text/css">
				<link rel="StyleSheet" href="<%=path%>/style/tree.css" type="text/css">
				
				<script type="text/javascript" src="<%=path%>/js/tree.js"></script>
				<script type="text/javascript">
		
					var Tree = new Array(<%=allNodesArrayLength%>);
					
					<%for (int i = 0; i < allNodesArrayLength; i++) {%>
					Tree[<%=i%>]="<%=allNodes[i]%>";
					<%
					 /*
					  * Determine the ID of the currently displayed log file
					  * so that the tree can display the current node 
					  * 
					  */
					  logfilename.trim();
						if(!logfilename.equals("")){
							//disassemble current tree element in the items
							// Muster String: "1|0|2009|#" --> nodeId | parentNodeId | nodeName |
							// nodeUrl
							String[] nodeElements=allNodes[i].split("\\|");
							if(nodeElements.length == 3 && nodeElements[3]!=null && !nodeElements[3].equals("#")&& logfilename.equals(nodeElements[2])){
								currentLogFileNodeNumber=Integer.parseInt(nodeElements[0]);
							}
						}
					}%>
					Tree
			
				</script>
				
				<style  type="text/css" media="all">
					<!--
					#navigation a#id_a_de_systemadministrationpage{font-weight:bold;}
					#navigation a#id_a_de_systemadministrationpage{font-weight:bold;}
					#navigation li#id_li_de_systemadministrationpage{background-color:#efefef;}
					#navigation li#id_li_de_systemadministrationpage{background-color:#efefef;}
					
					
					#content a#horizontalmenuetable_a_showlogfilepage{font-weight:bold;}
					#content td#horizontalmenuetable_td_showlogfilepage{background-image:url(images/tabs/tab_active.gif)}
					#content td#horizontalmenuetable_td_showlistofcontactpersonspage{border-bottom:  2px solid #990000}
					#content td#horizontalmenuetable_td_systemconfigurationpage{border-bottom:  2px solid #990000}
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

					<h1>Systemverwaltung</h1>
					<img src="images/horizontaldashedseperator.gif"	style="margin-top: 5px; margin-bottom: 15px;">
					<h2>Log-Dateien anzeigen</h2>
					
					<p>Diese Seite beinhaltet die Systemverwaltung des Webportals.</p>
					<br>
					<%
						if (ADMINISTRATOR) {
					%> <%@ include file="include/submenusystemadministration.jsp"%>
					<%
						} else {
							Dispatcher.invalidateSession(request, response,
									"showlogfile.jsp", "Unerlaubter Zugriff");
						}
					%> <br>

					<div id="message" style="display: inline; color: #990000;"><%=message%></div>
					
					<h2>Log-Dateien:</h2>
					<table class="table tableuseroverview" style="font-size:1.0em;">
						<tr>
							<th colspan="1" rowspan="1" style="text-align:center;"><%=logfilename %></th>
						</tr>
					</table>
					<!-- Test Baumstruktur Start--> <!-- CSS sollte ausgelagert werden, funktioniert aber hier nicht richtig! -->
					<div id="logfileframe" style="font-size:0.8em;height: 300px; overflow:auto;">
					<%
						if (logfile != null && logfile.size() > 0) { //check if logfile exists
					%>  
					<br>
					<%
							for (String s : logfile) {
					%> 			<%=s%><br>
					
					<%
							}
						}
					
					%>
					</div>

					<div class="tree" style="float: left;">
						<div style="font-weight:bold; font-size:1.1em;">Aktuelle Logdateien:</div>
						<li style="margin-left:15px;">
							<a href="Dispatcher?identity=openLogFile&logfilename=info.log">Info-Log</a>
						</li>
						<li style="margin-left:15px;">
							<a href="Dispatcher?identity=openLogFile&logfilename=error.log">Error-Log</a>
						</li>
						<br></br>
						
						<script type="text/javascript">

							createTree(Tree,0,<%=currentLogFileNodeNumber%>);//Parameter --> (Baum-Array ,start node,opened node)

	

						</script>
					</div>
			
					
					<!-- Test Baumstruktur Ende--> <!--  
					<table class="tableuser">
					        <tr>
					                <th>Datum:</th>
					                <th>System-Log</th>
					                <th>Benutzer-Log</th>
					        </tr>
					        <tr>
					                <td>01.08.2009</td>
					                <td><a href="#"> Dateiname X</a></td>
					                <td><a href="#">Dateiname Y</a></td>
					        </tr>
					        <tr>
					                <td>01.08.2009</td>
					                <td><a href="#">Dateiname X</a></td>
					                <td><a href="#">Dateiname Y</a></td>
					        </tr>
					        <tr>
					                <td>01.08.2009</td>
					                <td><a href="#">Dateiname X</a></td>
					                <td><a href="#">Dateiname Y</a></td>
					        </tr>
					        <tr>
					                <td>01.08.2009</td>
					                <td><a href="#">Dateiname X</a></td>
					                <td><a href="#">Dateiname Y</a></td>
					        </tr>
					
					</table>

--> 
					<br>
					<img src="images/horizontalerTenner.gif" style="margin-top: 15px; margin-bottom: 15px;">
					
					<div id="guideline">
						<p>
							<b>Hinweis:</b>
						</p>

						Zum Anzeigen der gew&uuml;nschten Logdatei die Datei unter "<img src="images/tree_img/base.gif"></img>Archiv Log-Dateien" ausw&auml;hlen. <br />
					</div>
					<!-- /hinweis--> <br clear="all" />

				</div>

				<%@ include file="include/footer.jsp"%>
			</div>

		</body>
</html>