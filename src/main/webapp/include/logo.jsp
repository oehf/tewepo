<%-- 
This page represents the logo-include.
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script type="text/javascript">
	function logo_slideshow() {
		if (document.getElementById('logo_1')) {
			document.getElementById('logo_1').id = 'logo_2';
			document.getElementById('logo_2').src = 'images/logos/logo_2.gif';
		} else if (document.getElementById('logo_2')) {
			document.getElementById('logo_2').id = 'logo_3';
			document.getElementById('logo_3').src = 'images/logos/logo_3.gif';
		} else if (document.getElementById('logo_3')) {
			document.getElementById('logo_3').id = 'logo_1';
			document.getElementById('logo_1').src = 'images/logos/logo_1.gif';
		}
	}
	window.setInterval("logo_slideshow()", 4000);
</script>

<div id="logos">

	<img id="logo_1" alt="logos" src="images/logos/logo_1.gif" title="Logos">

</div>
