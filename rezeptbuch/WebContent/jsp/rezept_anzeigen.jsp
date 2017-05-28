<!-- Autor Flo, Lorenz -->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page errorPage="errorpage.jsp" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:useBean id="rezept" class="bean.RezeptBean" scope="session">
</jsp:useBean>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="${pageContext.request.requestURI}" />

<title>Rezeptbuch - ${rezept.name}</title>

</head>
<body>
	<%@ includefile="fragments/nav.jspf" %>
	<h1>${rezept.name}</h1>
	<c:if test="${not empty message}">
		<h2>${message}</h2>
	</c:if>
	<c:if test="${not empty rezept.getFilename()}">
		<img src="../LoadImage?id=${rezept.id}&table=recipes">
	</c:if>
	<h2>ID: ${rezept.id}</h2>
	<h2>Zutaten:</h2>
	<table border="solid">
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
	<h2>Beschreibung:</h2>
	${rezept.description}
	<h2>Vorbereitsungszeit:</h2>
	${rezept.durationPreparation}
	<h2>Kochzeit:</h2>
	${rezept.durationCooking}
	<h2>Schwierigkeitsgrad:</h2>
	${rezept.difficulty}
	<h2>Portionen:</h2>
	${rezept.servings}
	<h2>Anzahl Bewertungen:</h2>
	${rezept.ratingCount}
	<h2>Summe Bewertungen:</h2>
	${rezept.ratingSum}
	<h2>Bewertung</h2>
	<c:choose>
	<c:when test="${rezept.ratingCount == 0}">
	Es wurden noch keine Bewertungeng abgegeben!
	</c:when>
	<c:otherwise>
	${rezept.ratingSum / rezept.ratingCount}
	</c:otherwise>
	</c:choose>
	<c:if test="${not empty user}">
	<form action="/rezeptbuch/RatingServlet" method="post">
		<p>
			<label for="rating">Bewertung abgeben:</label>
			<input name="rating" id="rating" type="number" max="5" min="0"
				required></input>
		</p>
		<input type="hidden" name="recipe" id="recipe" value="${rezept.id}"/>
		<button type="submit">Bewerten</button>
	</form>
	</c:if>
	<h2>Kommentare</h2>
	<table border="solid">
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
			<td><a href="/rezeptbuch/DeleteCommentServlet?id=${comment.getId()}&recipe=${rezept.getId()}">Löschen</a></td>
			</c:if>
			</c:if>
			</tr>
	</c:forEach>
	</table>
	<c:if test="${not empty user}">
	<form action="/rezeptbuch/AddCommentServlet" method="post">
		<p>
			<label for="comment">Kommentar abgeben:</label>
			<textarea name="comment" id="comment" placeholder="Hier Kommentar eingeben..." cols="50" rows="7"
				required maxlength="2500"></textarea>
		</p>
		<input type="hidden" name="recipe" id="recipe" value="${rezept.id}"/>
		<button type="submit">Kommentieren</button>
	</form>
	</c:if>
</body>
</html>