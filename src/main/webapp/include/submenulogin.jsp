<%-- 
This page represents the header (include).
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<table class="horizontalmenuetable">
	<tr>
		<td id="horizontalmenuetable_td_portalloginpage">
			<a id="horizontalmenuetable_a_portalloginpage" href="<%=request.getContextPath().toString()%>/Dispatcher?identity=loginpage">
				Anmeldung
			</a>
		</td>
		<td id="horizontalmenuetable_td_doccheckloginpage">
			<a id="horizontalmenuetable_a_doccheckloginpage" href="<%=request.getContextPath().toString()%>/Dispatcher?identity=doccheckloginpage">
				DocCheck-Anmeldung
			</a>
		</td>
		<td style="background-image:none; border-bottom:2px solid #990000; width:380px"></td>
	</tr>
</table>
