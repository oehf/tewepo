<%-- 
This page represents the header (include).
--%>
<%@page import="org.openehealth.twp.tewepo.businesslogic.Person"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	boolean ADMINISTRATOR = false;
	boolean PHYSICIAN = false;
	boolean PATIENT = false;
%>

<%@page import="org.openehealth.twp.tewepo.controller.Dispatcher"%>
<%@page import="org.openehealth.twp.tewepo.businesslogic.IPerson"%>

<div id="portal_header">
	<div id="headercontent">
	<%
		IPerson user = (IPerson) request.getSession().getAttribute(
				"loginBean");
		long userId = -1;
	
		if (user == null) {
			//if no user specified -> error -> End session
			ADMINISTRATOR = false;
			PHYSICIAN = false;
			PATIENT = false;
	%> <%
 		} // End - no User
	 	else {
	
	 		// from here the user is not equal to zero
	 		ADMINISTRATOR = (Boolean) request.getSession().getAttribute(
	 				"administrator");
	 		PHYSICIAN = (Boolean) request.getSession().getAttribute(
	 				"physician");
	 		PATIENT = (Boolean) request.getSession()
	 				.getAttribute("patient");
	 %>

		<table class="usercontainer" align="right">
			<tr>
				<td><%=user.getLoginname()%></td>
				<%
					if (ADMINISTRATOR) {
				%>
				<td>(Administrator)</td>
				<%
					} else if (PHYSICIAN) {
				%>
				<td>(Arzt)</td>
				<%
					} else if (PATIENT) {
				%>
				<td>(Patient)</td>
				<%
					}
				%>
				<td>
					<a href="<%=request.getContextPath().toString()%>/Dispatcher?identity=showuserdatapage">
						<img src="images/information.gif" alt="Benutzerinformationen anzeigen"
							title="Benutzerinformationen anzeigen">
					</a>
				</td>
				<td>
					<a href="<%=request.getContextPath().toString()%>/Dispatcher?identity=logout"
					title="Abmelden" onClick="if (!confirm('Durch best&auml;tigen werden Sie vom System abgemeldet.')) return false;">
					Abmelden
					</a>
				</td>
			</tr>
		</table>
	<%
		}
	%>
	</div>
</div>


<!-- /headersection -->

