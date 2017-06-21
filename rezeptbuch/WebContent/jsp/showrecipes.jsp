<%@ page
	errorPage="errorpage.jsp"
	language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib
	prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <base href="${pageContext.request.requestURI}"> --%>

<c:choose>
	<c:when test="${not empty recipes}">
		<c:forEach
			items="${recipes}"
			var="recipe">
			<div class="search-entry clearfix">
			
				<h2>
					<a
						class="recipe-title"
						href="/rezeptbuch/LoadRecipeServlet?id=${recipe.id}">${recipe.name}</a>
				</h2>

				<c:if test="${not empty recipe.filename}">
					<a href="/rezeptbuch/LoadRecipeServlet?id=${recipe.id}"><img
						src="../LoadImage?id=${recipe.id}&table=recipes"
						width="250"
						height="200"
						class="search-img"
						alt="Ein Foto vom Rezept ${recipe.name}"></a>
				</c:if>
				<dl>
					<dt>Bewertung</dt>
					<dd>
						<c:choose>
							<c:when test="${recipe.ratingCount == 0}">-</c:when>
							<c:otherwise>${recipe.calculateRatingFloat()}</c:otherwise>
						</c:choose>
					</dd>
					<dt>Erstellt am:</dt>
					<dd>${recipe.timestampToDate(recipe.created) }</dd>
					<dt>Letzte Änderung:</dt>
					<dd>${recipe.timestampToDate(recipe.modified) }</dd>
				</dl>
			</div>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<div class="sorry search-entry">${noentrymessage}</div>
	</c:otherwise>
</c:choose>