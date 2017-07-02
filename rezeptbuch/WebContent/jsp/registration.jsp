<%-- Autor: Lorenz + Florian --%>

<%@ taglib
	prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page
	errorPage="errorpage.jsp"
	language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Rezeptbuch - Registrierung</title>
<script src="${pageContext.request.contextPath}/js/nav.js"></script>
<link
	rel="stylesheet"
	href="${pageContext.request.contextPath}/main.css">
</head>
<body>
	<%@ include file="fragments/nav.jspf"%>
	<main>
	<h1>Registration</h1>
	<c:if test="${not empty message}">
		<h2>${message}</h2>
	</c:if>
	<form
		action="/rezeptbuch/registration"
		method="post">
		<label>E-Mail</label>
		<input
			name="mail"
			type="email"
			size="30"
			maxlength="30"
			required>
		<label>Vorname</label>
		<input
			name="firstName"
			type="text"
			size="30"
			maxlength="30"
			required>
		<label>Name</label>
		<input
			name="lastName"
			type="text"
			size="30"
			maxlength="30"
			required>
		<label>Passwort</label>
		<input
			name="password"
			type="password"
			size="30"
			maxlength="30"
			minlength="6"
			required>
		<label>Passwort wiederholen</label>
		<input
			name="password_retype"
			type="password"
			size="30"
			maxlength="30"
			minlength="6"
			required>
		<button
			type="submit"
			class="button">Registrieren</button>
		<button
			type="button"
			class="button-secondary"
			onclick="window.location.href='login.jsp'">Bereits ein
			Account?</button>
	</form>
	</main>
</body>
</html>