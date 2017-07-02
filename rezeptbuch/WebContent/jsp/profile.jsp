<!-- Refactoring: Lorenz, Flo, Michael -->

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
<c:choose>
	<c:when test="${empty user}">
		<c:redirect url="login.jsp" />
	</c:when>
</c:choose>
<title>Profil</title>

<base href="${pageContext.request.requestURI}" />


<link
	rel="stylesheet"
	href="${pageContext.request.contextPath}/main.css">
</head>

<script src="${pageContext.request.contextPath}/js/nav.js"></script>
<script src="${pageContext.request.contextPath}/js/textarea.js"></script>
<script src="${pageContext.request.contextPath}/js/editProfile.js"></script>


<body>
	<%@ include file="./fragments/nav.jspf"%>

	<main>

	<h1>Dein Profil</h1>
	<div
		id="button-bar">
		<button
			id="profileDataButton"
			type="button"
			class="button">Deine Profildaten</button>
		<button
			id="ownRecipesButton"
			type="button"
			class="button-secondary">Eigene Rezepte</button>
		<button
			id="savedRecipesButton"
			type="button"
			class="button-secondary">Gemerkte Rezepte</button>
	</div>


	<div id="contentDiv">
		<c:if test="${not empty message}">
			<h2>${message}</h2>
		</c:if>
		<form
			action="/rezeptbuch/EditProfileServlet"
			method="post">
			<input
				type="hidden"
				name="id"
				class="id"
				value="${user.id}">
			<label for="vorname">Vorname</label>
			<input
				type="text"
				name="vorname"
				id="vorname"
				placeholder="Ihr Vorname"
				disabled
				value="${user.firstName}">


			<label for="nachname">Nachname</label>
			<input
				type="text"
				name="nachname"
				id="nachname"
				placeholder="Ihr Nachname"
				disabled
				value="${user.lastName}">


			<label for="mail">E-Mail</label>
			<input
				type="text"
				name="mail"
				id="mail"
				placeholder="Ihre E-Mail"
				disabled
				value="${user.mail}">


			<label
				class="labelfortextarea"
				for="beschreibung">Profilbeschreibung</label>
			<textarea
				name="beschreibung"
				id="beschreibung"
				class="textarea-transitional"
				placeholder="Ihre Profilbeschreibung"
				disabled>${user.description}</textarea>
			<button
				type="button"
				class="button"
				id="aendernButton"
				name="aendernButton">Ändern</button>
			<div
				id="editButtons"
				class="hidden-block">

				<button
					name="save"
					class="button"
					type="submit">Speichern</button>
				<button
					type="button"
					class="button-secondary"
					id="refreshButton"
					name="refresh"
					value="Verwerfen">Verwerfen</button>
			</div>
		</form>
	</div>
	</main>
</body>
</html>