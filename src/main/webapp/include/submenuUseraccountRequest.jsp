<%-- 
This page represents the submenu for the user data request page
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<table class="horizontalmenuetable">
	<tr>
		<td id="horizontalmenuetable_td_usernamepagerequest">
			<a id="horizontalmenuetable_a_usernamepagerequest" href="<%=request.getContextPath().toString()%>/Dispatcher?identity=usernamepage">
				Benutzername vergessen
			</a>
		</td>
		<td id="horizontalmenuetable_td_keywordpagerequest">
			<a id="horizontalmenuetable_a_keywordpagerequest" href="<%=request.getContextPath().toString()%>/Dispatcher?identity=keywordpage">
				Passwort vergessen
			</a>
		</td>
		<td style="background-image:none; border-bottom:2px solid #990000; width:380px"></td>
	</tr>
</table>
