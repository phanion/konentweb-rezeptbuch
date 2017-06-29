<!-- Autor: Lorenz -->

<%@ page
	language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib
	prefix="fn"
	uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib
	prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"%>


<h2>Die neuesten Rezepte</h2>
<c:forEach
	items="${recipeList}"
	var="recipe">
	<div class="search-entry clearfix">
		<h2>
			<a href="/rezeptbuch/LoadRecipeServlet?id=${recipe.id}">${recipe.name}</a>
		</h2>
		<c:if test="${not empty recipe.getFilename()}">
			<a href="/rezeptbuch/LoadRecipeServlet?id=${recipe.id}"><img
				src="LoadImage?id=${recipe.id}&table=recipes"
				width="250"
				height="200"
				class="preview-img"
				alt="Ein Foto vom Rezept ${recipe.name}"></a>
		</c:if>
		<dl>
			<dt>Ersteller</dt>

			<dd>${recipe.getCreator().getFirstName()}
				${recipe.getCreator().getLastName()}</dd>
			<dt>Bewertung</dt>
			<dd>
				<c:choose>
					<c:when test="${recipe.ratingCount == 0}">-</c:when>
					<c:otherwise>${recipe.calculateRatingInt()}</c:otherwise>
				</c:choose>
			</dd>
			<dt>Erstellungsdatum</dt>
			<dd>${recipe.timestampToDate(recipe.created) }</dd>
		</dl>
	</div>
</c:forEach>