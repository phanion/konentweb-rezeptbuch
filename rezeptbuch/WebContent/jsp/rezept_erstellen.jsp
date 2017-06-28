<!-- 
	Autor: Florien, Lorenz
	Refactoring: Michael
-->

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
<title>Kochrezepte - Erstellen</title>
<c:choose>
	<c:when test="${empty user}">
		<c:redirect url="login.jsp" />
	</c:when>
</c:choose>
<link
	rel="stylesheet"
	href="${pageContext.request.contextPath}/main.css">
<script src="${pageContext.request.contextPath}/js/nav.js"></script>
<script src="${pageContext.request.contextPath}/js/createRecipe.js"></script>
<base href="${pageContext.request.requestURI}">
</head>
<body>
	<%@ include file="fragments/nav.jspf"%>
	<main>
	<h1>Rezept erstellen</h1>
	<form
		action="/rezeptbuch/NeuesRezeptServlet"
		method="post"
		enctype="multipart/form-data">

		<label for="name">Name</label>
		<input
			type="text"
			name="name"
			id="name"
			size="30"
			maxlength="40"
			required>
		<h3>Zutaten</h3>
		<div id="zutaten"></div>
		<button
			type="button"
			class="button"
			id="addIngredientButton">Zutat hinzufügen</button>
		<label
			class="labelfortextarea"
			for="description">Beschreibung</label>
		<textarea
			name="description"
			id="description"
			placeholder="Hier die Beschreibung eingeben..."
			class="textarea-transitional"
			required
			maxlength="2500"></textarea>

		<label for="durationPreparation">Vorbereitungsdauer</label>
		<input
			name="durationPreparation"
			id="durationPreparation"
			placeholder="Hier die Vorbereitungsdauer in Minuten angeben"
			type="number"
			min="0"
			max="500"
			value="0">
		<label for="durationCooking">Kochzeit</label>
		<input
			name="durationCooking"
			id="durationCooking"
			placeholder="Hier die Kochzeit in Minuten angeben"
			type="number"
			min="0"
			max="500"
			value="0">
		<label for="difficulty">Schwierigkeit</label>
		<input
			name="difficulty"
			id="difficulty"
			placeholder="Hier die Schwierigkeit angeben"
			type="number"
			max="5"
			min="0"
			value="0">
		<label for="servings">Portionen</label>
		<input
			name="servings"
			id="servings"
			placeholder="Hier die Schwierigkeit angeben"
			type="number"
			max="99"
			min="0"
			value="0">
		<div>
			<label
				for="image"
				class="button-secondary">Bild auswählen ...</label>
			<input
				type="file"
				name="image"
				id="image"
				accept="image/*">
			<span id="imagefilename"></span>
		</div>
		<button
			name="absenden"
			class="button"
			type="submit">Absenden</button>
	</form>
	</main>
</body>
</html>