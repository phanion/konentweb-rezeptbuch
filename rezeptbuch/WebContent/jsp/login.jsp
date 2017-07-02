<%-- Autor: Lorenz, Florian, Michael --%>

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
<link
	rel="stylesheet"
	type="text/css"
	href="${pageContext.request.contextPath}/main.css">
<meta charset="utf-8">
<base href="${pageContext.request.requestURI}" />
<title>Rezeptbuch - Login</title>
</head>

<body>
	<%@ include file="fragments/nav.jspf"%>
	<main>
	<h1>Login</h1>
	<c:if test="${not empty message}">
		<h2>${message}</h2>
	</c:if>
	<form
		action="/rezeptbuch/login"
		method="post">

		<label>E-Mail</label>
		<input
			name="mail"
			type="email"
			size="30"
			maxlength="30"
			required>

		<label>Passwort</label>
		<input
			name="password"
			type="password"
			size="30"
			maxlength="30"
			required>

		<div>
			<button
				class="button"
				role="button"
				type="submit">Login</button>
			<button
				class="button-secondary"
				onclick="window.location.href='/rezeptbuch/jsp/registration.jsp'"
				type="button">Noch keinen Account?</button>
		</div>
	</form>
	</main>
</body>
</html>