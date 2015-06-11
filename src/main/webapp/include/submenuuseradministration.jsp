<%-- 
This page represents the submenu for user administration (include)
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<table class="horizontalmenuetable">
	<tr>
		<td id="horizontalmenuetable_td_showuserdatapage">
			<a id="horizontalmenuetable_a_showuserdatapage"
				href="<%=request.getContextPath().toString()%>/Dispatcher?identity=showuserdatapage">
					Meine Benutzerdaten
			</a>
		</td>
		<td id="horizontalmenuetable_td_useradministrationpage">
			<a id="horizontalmenuetable_a_useradministrationpage" href="<%=request.getContextPath().toString()%>/Dispatcher?identity=useradministrationpage">
				Benutzer
			</a>
		</td>
		<td style="background-image:none; border-bottom:2px solid #990000; width:190px"></td>
		<td style="background-image:none; border-bottom:2px solid #990000; width:190px"></td>
	</tr>
</table>
