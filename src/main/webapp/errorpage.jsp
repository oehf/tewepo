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
<%-- 
Diese Seite zeigt Error Nachrichten vom Request

request erfordert:
(Typ/Klasse			Attr./Para.	bezeichnung)
String				Attribute	message

--%>
<%
	String path = request.getContextPath().toString();
	String message = (String) request.getParameter("message");
	if (message == null) {
		message = (String) request.getAttribute("message");
		if (message == null) {
			message = "Die Sitzung wurde aus unbestimmten Gr&uuml;nden beendet!";
		}
	}
	request.getSession().invalidate();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
			<title>Teleradiologie Webportal</title>
			<link rel="stylesheet" href="<%=path%>/style/style.css" type="text/css">
	</head>

	<body>

		<%@ include file="include/header.jsp"%>
		
		<div id="page">
			<%@ include file="include/mainmenu.jsp"%>
			<div id="content">

				<table align="center" style="margin: 150px 20px 100px;">
					<tr>
						<td align="left"><img src="images/warning.png" alt="ERROR">
						</td>
						<td align="left"><%=message%></td>
					</tr>
				</table>
				<table>
					<tr>
						<td><img src="images/horizontalerTenner.gif"
							class="horizontaletrennlinie" style="padding: 150px 10px 10px;"></td>
					</tr>
					<tr>
						<td>
						<div id="guideline" style="padding: 15px 10px 15px;">
						<p><b>Hinweis:</b></p>
						Es ist ein Fehler aufgetreten. Bitte wiederholen Sie den Vorgang.<br>
						Tritt dieser Fehler wiederholt auf, wenden Sie sich bitte an den <a
							href="<%=request.getContextPath().toString()%>/Dispatcher?identity=contactpage"
							title="Kontakt">Administrator.</a></div>
						</td>
					</tr>
				</table>
			</div>
			<%@ include file="include/footer.jsp"%>
		</div>
	</body>
</html>
