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

<script src="${pageContext.request.contextPath}/js/nav.js"></script>
<script src="${pageContext.request.contextPath}/js/editProfile.js"></script>

<link
	rel="stylesheet"
	href="${pageContext.request.contextPath}/main.css">
</head>

<body>
	<%@ include file="./fragments/nav.jspf"%>

	<main>

	<h1>Dein Profil</h1>
	<div
		id="button-bar"
		style="display: flex; flex-flow: row-wrap; margin-bottom: 1em;">
		<button
			id="profileDataButton"
			type="button"
			style="flex: 1; border-top-right-radius: 0; border-bottom-right-radius: 0;"
			class="button">Deine Profildaten</button>
		<button
			id="ownRecipesButton"
			type="button"
			style="flex: 1; border-radius: 0"
			class="button-secondary">Eigene Rezepte</button>
		<button
			id="savedRecipesButton"
			type="button"
			style="flex: 1; border-top-left-radius: 0; border-bottom-left-radius: 0;"
			class="button-secondary">Gemerkte Rezepte</button>
	</div>

	<c:if test="${not empty message}">
		<h2>${message}</h2>
	</c:if>

	<div id="contentDiv">
		<form
			action="/rezeptbuch/EditProfileServlet"
			method="post">
			<input
				type="hidden"
				name="id"
				class="id"
				value="${user.id}">
			<p>
				<label for="vorname">Vorname</label>
				<input
					type="text"
					name="vorname"
					id="vorname"
					placeholder="Ihr Vorname"
					disabled
					value="${user.firstName}">
			</p>
			<p>
				<label for="nachname">Nachname</label>
				<input
					type="text"
					name="nachname"
					id="nachname"
					placeholder="Ihr Nachname"
					disabled
					value="${user.lastName}">
			</p>
			<p>
				<label for="mail">E-Mail</label>
				<input
					type="text"
					name="mail"
					id="mail"
					placeholder="Ihre E-Mail"
					disabled
					value="${user.mail}">
			</p>
			<p>
				<label
					class="labelfortextarea"
					for="beschreibung">Profilbeschreibung</label>
				<textarea
					name="beschreibung"
					id="beschreibung"
					class="textarea-transitional"
					placeholder="Ihre Profilbeschreibung"
					readonly>${user.description}</textarea>
			</p>

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