<%-- 
This page represents the header (include).
--%>
<%
	boolean admin = false;
	boolean physician = true;
	boolean patient = false;
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div id="navigation">

<%-- Logos einbinden --%>
<%@ include file="logo.jsp"%>

<ul style="margin-top:20px">
	<li id="id_li_de_homepage" style="border-top:1px solid #F9B000"><a
		href="<%=request.getContextPath().toString()%>/Dispatcher?identity=homepage"
		title="Startseite" id="id_a_de_homepage">Startseite</a></li>


	<li id="id_li_de_dicomemailpage"><a
		href="<%=request.getContextPath().toString()%>/Dispatcher?identity=dicomemailpage"
		title="Dateien versenden" id="id_a_de_dicomemailpage">Dateien versenden</a></li>


	<li id="id_li_de_useradministrationpage"><a
		href="<%=request.getContextPath().toString()%>/Dispatcher?identity=showuserdatapage"
		title="Benutzerverwaltung" id="id_a_de_useradministrationpage">Benutzerverwaltung</a></li>

	<li><a
		href="<%=request.getContextPath().toString()%>/Dispatcher?identity=logout"
		title="Abmelden"
		onClick="
	if (!confirm('Durch best&auml;tigen werden Sie vom System abgemeldet.'))
		return false;">Abmelden</a>
	</li>

	<li id="id_li_de_contactpage"><a
		href="<%=request.getContextPath().toString()%>/Dispatcher?identity=contactpage"
		title="Kontakt" id="id_a_de_contactpage">Kontakt</a></li>


	<li id="id_li_de_legalnoticepage"><a
		href="<%=request.getContextPath().toString()%>/Dispatcher?identity=legalnoticepage"
		title="Impressum" id="id_a_de_legalnoticepage">Impressum</a></li>



</ul>
<!-- <img src="images/de.gif" alt="deutsch" title="Sprache=deutsch/german" style="margin-top:30px; margin-left:90px;"></img> -->

</div>
