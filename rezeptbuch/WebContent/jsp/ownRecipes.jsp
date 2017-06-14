<!-- Autor: Lorenz -->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page errorPage="errorpage.jsp" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="main.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Rezeptbuch - Ihre Rezepte</title>
</head>
<body>
	<%@ include file="fragments/nav.jspf"%>
	<main>
	<h1>Ihre Rezepte</h1>
	<br>
	<c:forEach items="${recipes}" var="recipe">
		<div class="search-entry clearfix">

			<h2>
				<a href="/rezeptbuch/LoadRecipeServlet?id=${recipe.id}">${recipe.name}</a>
			</h2>
			<c:if test="${not empty recipe.getFilename()}">
				<a href="/rezeptbuch/LoadRecipeServlet?id=${recipe.id}"><img
					src="LoadImage?id=${recipe.id}&table=recipes" width="250"
					height="200" alt="Ein Foto vom Rezept ${recipe.name}"></a>
			</c:if>
			<dl>
				<dd>Bewertung</dd>
				<dt>
					<c:choose>
						<c:when test="${recipe.ratingCount == 0}">-</c:when>
						<c:otherwise>${recipe.ratingSum / recipe.ratingCount}</c:otherwise>
					</c:choose>
				</dt>
				<dd>Erstellt am:</dd><dt>${recipe.timestampToDate(recipe.getCreated()) }</dt>
				<dd>Letzte Änderung:</dd><dt>${recipe.timestampToDate(recipe.getModified()) }</dt>



			</dl>
		</div>
	</c:forEach> </main>
</body>
</html>