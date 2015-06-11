<%-- 
This page represents the header (include).
--%>
<%
	boolean admin = false;
	boolean physician = false;
	boolean patient = false;
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div id="navigation">

<%-- Logos einbinden --%>
<%@ include file="logo.jsp"%>

<ul style="margin-top: 20px">
	<li id="id_li_de_homepage" style="border-top: 1px solid #F9B000">
		<a href="<%=request.getContextPath().toString()%>/Dispatcher?identity=homepage"
		title="Startseite" id="id_a_de_homepage">Startseite</a>
	</li>


	<li id="id_li_de_loginpage">
		<a href="<%=request.getContextPath().toString()%>/Dispatcher?identity=loginpage"
		title="Anmelden" id="id_a_de_loginpage">Anmelden</a>
	</li>


	<li id="id_li_de_registrationpage">
		<a href="<%=request.getContextPath().toString()%>/Dispatcher?identity=registrationpage"
		title="Registrieren" id="id_a_de_registrationpage">Registrieren</a>
	</li>

	<li id="id_li_de_contactpage">
		<a href="<%=request.getContextPath().toString()%>/Dispatcher?identity=contactpage"
		title="Kontakt" id="id_a_de_contactpage">Kontakt</a>
	</li>


	<li id="id_li_de_legalnoticepage">
		<a href="<%=request.getContextPath().toString()%>/Dispatcher?identity=legalnoticepage"
		title="Impressum" id="id_a_de_legalnoticepage">Impressum</a>
	</li>
</ul>
<!--  <img src="images/de.gif" alt="deutsch/german" title="Sprache=deutsch/german"
	style="margin-top: 30px; margin-left: 90px;"></img>-->
	
</div>
