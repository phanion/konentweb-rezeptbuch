<!-- Autor: Lorenz -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

	<c:forEach items="${recipeList}" var="recipe">
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
			<dd>Ersteller</dd>
			<dt>${recipe.getCreator().getFirstName()} ${recipe.getCreator().getLastName()}</dt>
				<dd>Bewertung</dd>
				<dt>
					<c:choose>
						<c:when test="${recipe.ratingCount == 0}">-</c:when>
						<c:otherwise>${recipe.getRatingInteger()}</c:otherwise>
					</c:choose>
				</dt>
				<dd>Erstellt am:</dd><dt>${recipe.timestampToDate(recipe.getCreated()) }</dt>
				



			</dl>
		</div>
	</c:forEach>