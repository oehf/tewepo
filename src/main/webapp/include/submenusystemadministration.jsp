<%-- 
This page represents the submenu for system administration (include)
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<table class="horizontalmenuetable">
	<tr>
		<td id="horizontalmenuetable_td_showlogfilepage">
			<a id="horizontalmenuetable_a_showlogfilepage" href="<%=request.getContextPath().toString()%>/Dispatcher?identity=showlogfilespage">
				Log-Dateien anzeigen
			</a>
		</td>
		<td id="horizontalmenuetable_td_showlistofcontactpersonspage">
			<a id="horizontalmenuetable_a_showlistofcontactpersonspage" href="<%=request.getContextPath().toString()%>/Dispatcher?identity=showlistofcontactpersonspage">
				Partnerliste  anzeigen
			</a>
		</td>
		<td id="horizontalmenuetable_td_systemconfigurationpage">
			<a id="horizontalmenuetable_a_systemconfigurationpage" href="<%=request.getContextPath().toString()%>/Dispatcher?identity=showsystemconf">
				Systemkonfiguration
			</a>
		</td>
		<td style="background-image:none; border-bottom:2px solid #990000; width:180px"></td>
	</tr>
</table>
