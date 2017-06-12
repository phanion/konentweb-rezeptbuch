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
		document.getElementById("button").addEventListener("click", addComment);
	}
	
	function addComment() {
		var id = document.getElementById("recipeId").value;
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
				newCommentDelete.innerHTML = "<a href=\"/rezeptbuch/DeleteCommentServlet?id=" + commentResponse.id + "&recipe=" + commentResponse.recipe + "\">Löschen</a>";
					
			
		}
		}
		
		xmlhttp.open("POST", "../AddCommentServlet", true);
		xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		xmlhttp.send("recipe=" + id + "&comment=" + comment);
	}

</script>

<title>Rezeptbuch - ${rezept.name}</title>

</head>
<body>
	<%@ include file="fragments/nav.jspf" %>
	<main>
	
	<h1>${rezept.name}</h1>
	
	<c:if test="${not empty user}">
		<c:if test="${user.getID() == rezept.getCreator().getID()}">
			<button class="button" type="button">Rezept bearbeiten</button>
		</c:if>
	</c:if>

	<c:if test="${not empty message}">
		<p>${message}</p><br>
	</c:if> 
	
	<h2>Details zum Rezept</h2>
	
	<c:if test="${not empty rezept.getFilename()}">
		<img src="../LoadImage?id=${rezept.id}&table=recipes" width="250" height="200" alt="Ein Foto vom Rezept ${rezept.name}">
	</c:if> 
	
	<label for="id">ID:</label> <input disabled type="text" name="id" id="id" value="${rezept.id}">

	<label class="labelfortextarea">Zutaten:</label>
	<table>
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
	
	<br><label for="description">Beschreibung:</label> <textarea disabled type="text" name="description" id="description">${rezept.description}</textarea>
	<br><label for="durationPreparation">Vorbereitungszeit:</label> <input  disabled type="text" name="durationPreparation" id="durationPreparation" value="${rezept.durationPreparation}">
	<br><label for="durationCooking">Kochzeit:</label> <input  disabled type="text" name="durationCooking" id="durationCooking" value="${rezept.durationCooking}">
	<br><label for="difficulty">Schwierigkeitsgrad:</label> <input  disabled type="text" name="difficulty" id="difficulty" value="${rezept.difficulty}">
	<br><label for="servings">Portionen:</label> <input  disabled type="text" name="servings" id="servings" value="${rezept.servings}">
	<br><label for="ratingCount">Anzahl Bewertungen:</label> <input  disabled type="text" name="ratingCount" id="ratingCount" value="${rezept.ratingCount}">
	<br><label for="ratingSum">Summe Bewertungen:</label> <input disabled  type="text" name="ratingSum" id="ratingSum" value="${rezept.ratingSum}">
	<br><label for="creator">Ersteller:</label> <input  disabled type="text" name="creator" id="creator" value="${rezept.creator.lastName}, ${rezept.creator.firstName}">
	


	<br><br>
	<h2>Bewertung</h2>
	<c:choose>
		<c:when test="${rezept.ratingCount == 0}">
			<p>Es wurden noch keine Bewertungen abgegeben!</p>
		</c:when>
	
		<c:otherwise>
			<p>Aktuelle Bewertung: ${rezept.ratingSum / rezept.ratingCount}</p>
		</c:otherwise>
	</c:choose>
	
	<c:if test="${not empty user}">
		<form action="/rezeptbuch/RatingServlet" method="post">
			<p>
				<label for="rating">Bewertung abgeben:</label> <input name="rating"
					id="rating" type="number" max="5" min="0" required></input>
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
				<td>${comment.author.firstName} ${comment.author.lastName}</td>
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
				<label class="labelfortextarea" for="comment">Kommentar abgeben:</label>
				<textarea name="newComment" id="newComment"
					placeholder="Kommentar verfassen..." cols="50" rows="7"
					required maxlength="2500"></textarea>
			</p>
			<input type="hidden" name="recipeId" id="recipeId" value="${rezept.id}" />
			<button id="button" class="button" type="button">Kommentieren</button>
		</form>
	</c:if> </main>
</body>
</html>