<!-- Refactoring: Lorenz -->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page errorPage="errorpage.jsp" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="../main.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="${pageContext.request.requestURI}" />
<title>Profil</title>
</head>

<body>
	<%@ includefile="./fragments/nav.jspf" %>
	<main>
	<h1>Dein Profil</h1>
	<form action="/rezeptbuch/profile" method="post">
		<p>
			<label for="vorname">Vorname</label> <input type="text"
				name="vorname" id="vorname" placeholder="Ihr Vorname" disabled
				value="${user.getFirstName()}">
		</p>
		<p>
			<label for="nachname">Nachname</label> <input type="text"
				name="nachname" id="nachname" placeholder="Ihr Nachname" disabled
				value="${user.getLastName()}">
		</p>
		<p>
			<label for="mail">E-Mail</label> <input type="text" name="mail"
				id="mail" placeholder="Ihre E-Mail" disabled
				value="${user.getMail()}">
		</p>
		<p>
			<label class="labelfortextarea" for="beschreibung">Profilbeschreibung</label>
			<textarea name="beschreibung" id="beschreibung" class="textarea-transitional"
				placeholder="Ihre Profilbeschreibung" disabled>
				${'Profilbeschreibung: Lorem ipsum dolor sit amet.'}
			</textarea>
		</p>
		<a href="../index.jsp"  class="button">Home</a>
		<!-- <button type="submit" id="homeButton" class="button" name="homeButton">Home</button> -->
		<button type="button" class="button-disabled" id="aendernButton" name="aendernButton"
			disabled="disabled">Ändern</button>
	</form>
	</main>
	<footer></footer>
</body>
</html>