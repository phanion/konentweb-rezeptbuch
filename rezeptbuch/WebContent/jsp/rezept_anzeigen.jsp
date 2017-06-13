<!-- Autor Flo, Lorenz -->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page errorPage="errorpage.jsp" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:useBean id="rezept" class="bean.RezeptBean" scope="session">
</jsp:useBean>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="main.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="${pageContext.request.requestURI}" />
<script>
	"use strict";
	document.addEventListener("DOMContentLoaded", init);
	function init() {
		document.getElementById("commentButton").addEventListener("click", addComment);
		document.getElementById("aboButton").addEventListener("click", aboHandling);
	}

	function addComment() {
		var id = document.getElementById("id").value;
		var comment = document.getElementById("newComment").value;

		var xmlhttp = new XMLHttpRequest();
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				var commentResponse = JSON.parse(xmlhttp.responseText);

				var commentsTable = document.getElementById("commentsTable");
				var nextRowPosition = commentsTable.rows.length;

				var newTableRow = commentsTable.insertRow(nextRowPosition);
				var newCommentAuthor = newTableRow.insertCell(0);
				var newCommentComment = newTableRow.insertCell(1);
				var newCommentDelete = newTableRow.insertCell(2);

				newCommentComment.innerHTML = commentResponse.comment;
				newCommentAuthor.innerHTML = commentResponse.author;
				newCommentDelete.innerHTML = "<a href=\"/rezeptbuch/DeleteCommentServlet?id="
						+ commentResponse.id
						+ "&recipe="
						+ commentResponse.recipe + "\">Löschen</a>";

			}
		}

		xmlhttp.open("POST", "../AddCommentServlet", true);
		xmlhttp.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		xmlhttp.send("recipe=" + id + "&comment=" + comment);
	}
	
	function aboHandling(){
		var recipe = document.getElementById("id").value;
		var action = document.getElementById("aboAction").value;
		var xmlhttp = new XMLHttpRequest();
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				if(action === "addAbo"){
					document.getElementById("aboAction").value = "deleteAbo";
					document.getElementById("aboButton").innerHTML = "Deabonieren";
				}
				else{
					document.getElementById("aboAction").value = "addAbo";
					document.getElementById("aboButton").innerHTML = "Abonieren";
				}
				}
			}
			xmlhttp.open("POST", "../AboHandlingServlet", true);
			xmlhttp.setRequestHeader("Content-Type",
					"application/x-www-form-urlencoded");
			xmlhttp.send("recipe=" + recipe + "&action=" + action);
		
		
	}
</script>

<title>Rezeptbuch - ${rezept.name}</title>

<script src="../js/editRecipe.js"></script>

</head>
<body>
	<%@ include file="fragments/nav.jspf"%>
	<main>

	<h1>${rezept.name}</h1>

	<c:if test="${not empty user}">
		<c:if test="${user.getID() == rezept.getCreator().getID()}">
			<button class="button" type="button" onclick="edit()">Rezept
				bearbeiten</button>
		</c:if>
	</c:if> <c:if test="${not empty message}">
		<p>${message}</p>
		<br>
	</c:if>

	<h2>Details zum Rezept</h2>

	<c:if test="${not empty rezept.getFilename()}">
		<img src="../LoadImage?id=${rezept.id}&table=recipes" width="250"
			height="200" alt="Ein Foto vom Rezept ${rezept.name}">
	</c:if>
	
	 <c:if test="${not empty user}">
		<form>
		<c:choose>
		<c:when test="${aboStatus == false }">
			<input type="hidden" id="aboAction" name="aboAction" value="addAbo" />
			<button id="aboButton" name="aboButton" class="button" type="button">Abonieren</button>
		</c:when>
		<c:otherwise>
			<input type="hidden" id="aboAction" name="aboAction" value="deleteAbo" />
			<button id="aboButton" name="aboButton" class="button" type="button">Deabonieren</button>
		</c:otherwise>
		</c:choose>
		</form>
	</c:if>

	<!-- Form für das ändern des Rezeptes -->
	<form action="/rezeptbuch/EditRecipeServlet" method="post">



		<input type="hidden" name="id" id="id" value="${rezept.id}">
		<input type="hidden" name="creatorID" id="creatorID" value="${rezept.creator.getID()}">
		<label class="labelfortextarea">Zutaten:</label>
		<table id="recipeIngredients">
			<tr>
				<th>Menge</th>
				<th>Einheit</th>
				<th>Zutat</th>
			</tr>
			<c:forEach var="ingredient" items="${rezept.ingredients}">
				<tr>
					<td>${ingredient.quantity}</td>
					<td>${ingredient.unit}</td>
					<td>${ingredient.ingredient}</td>
				</tr>
			</c:forEach>
		</table>



		<div id="zutaten" style="display: none">
			<c:forEach var="ingredient" items="${rezept.ingredients}">
				<br>
				<input type="number" name="zutatenMenge" class="zutatenMenge"
					placeholder="Menge" value="${ingredient.quantity}">
				<select name="zutatenEinheit" class="zutatenEinheit"></select>
				<input type="text" name="zutatenZutat" class="zutatenZutat"
					placeholder="Zutat" value="${ingredient.ingredient}">
				<!--  ein versteckter Input, um in Javascript die ausgewählte Einheit zu setzen -->
				<input type="hidden" name="selectedValue" class="selectedValue"
					value="${ingredient.unit}">
			</c:forEach>
		</div>

		<input type="button" class="button" id="addrow" name="addrow"
			value="Zutat hinzufügen" onclick="add();" style="display: none" />

		<!-- Felder des Rezeptes, die später geändert werden sollen -->
		<div id="editRecipe">
			<br> <label for="description">Beschreibung:</label>
			<textarea disabled name="description" id="description"
				class="textarea-transitional" required maxlength="2500">${rezept.description}</textarea>

			<br> <label for="durationPreparation">Vorbereitungszeit:</label>
			<input disabled type="number" name="durationPreparation"
				id="durationPreparation" min="0" max="500"
				value="${rezept.durationPreparation}"> <br> <label
				for="durationCooking">Kochzeit:</label> <input disabled
				type="number" name="durationCooking" id="durationCooking" min="0"
				max="500" value="${rezept.durationCooking}"> <br> <label
				for="difficulty">Schwierigkeitsgrad:</label> <input disabled
				type="number" name="difficulty" id="difficulty" max="5" min="0"
				value="${rezept.difficulty}"> <br> <label
				for="servings">Portionen:</label> <input disabled type="number"
				name="servings" id="servings" max="99" min="0"
				value="${rezept.servings}">

		</div>

		<!-- Buttons zum Speichern und Verwerfen -->
		<div id="editButtons" style="display: none">
			<br>
			<button name="save" class="button" type="submit">Speichern</button>
			<input type="button" class="button" id="refresh" name="refresh"
				onclick="refreshPage();" value="Verwerfen" />
		</div>
	</form>






	<!-- Zur Programmierhilfe werden diese Felder bisher angezeigt --> <br>
	<label for="ratingCount">Anzahl Bewertungen:</label> <input disabled
		type="text" name="ratingCount" id="ratingCount"
		value="${rezept.ratingCount}"> <br>
	<label for="ratingSum">Summe Bewertungen:</label> <input disabled
		type="text" name="ratingSum" id="ratingSum"
		value="${rezept.ratingSum}"> <br>
	<label for="creator">Ersteller:</label> <input disabled type="text"
		name="creator" id="creator"
		value="${rezept.creator.lastName}, ${rezept.creator.firstName}">



	<br>
	<br>
	<h2>Bewertung</h2>
	<c:choose>
		<c:when test="${rezept.ratingCount == 0}">
			<p>Es wurden noch keine Bewertungen abgegeben!</p>
		</c:when>

		<c:otherwise>
			<p>Aktuelle Bewertung: ${rezept.ratingSum / rezept.ratingCount}</p>
		</c:otherwise>
	</c:choose> <c:if test="${not empty user}">
		<form action="/rezeptbuch/RatingServlet" method="post">
			<p>
				<label for="rating">Bewertung abgeben:</label>
				<c:choose>
					<c:when test="${currentRating > 0}">
						<input name="rating" id="rating" type="number" max="5" min="0"
							required value="${currentRating}"></input>
					</c:when>
					<c:otherwise>
						<input name="rating" id="rating" type="number" max="5" min="0"
							required></input>
					</c:otherwise>
				</c:choose>
			</p>
			<input type="hidden" name="recipe" value="${rezept.id}" />
			<button class="button" type="submit">Bewerten</button>
		</form>
	</c:if>
	<br>
	<h2>Kommentare</h2>
	<table id="commentsTable">
		<tr>
			<th>Nutzer</th>
			<th>Kommentar</th>
		</tr>
		<c:forEach var="comment" items="${rezept.comments}">
			<tr>
				<td>${comment.author.firstName}${comment.author.lastName}</td>
				<td>${comment.comment}</td>
				<c:if test="${not empty user}">
					<c:if test="${user.getID() == comment.author.getID()}">
						<td><a
							href="/rezeptbuch/DeleteCommentServlet?id=${comment.getId()}&recipe=${rezept.getId()}">Löschen</a></td>
					</c:if>
				</c:if>
			</tr>
		</c:forEach>
	</table>

	<c:if test="${not empty user}">
		<form>
			<p>
				<label class="labelfortextarea" for="comment">Kommentar
					abgeben:</label>
				<textarea name="newComment" id="newComment"
					placeholder="Kommentar verfassen..." cols="50" rows="7" required
					maxlength="2500"></textarea>
			</p>
			<button id="commentButton" class="button" type="button">Kommentieren</button>
		</form>
	</c:if> </main>
</body>
</html>