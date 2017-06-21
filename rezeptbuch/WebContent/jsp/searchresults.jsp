<!--
  - Author(s): Michael
  - Refactoring: Lorenz
  - Date: 2017-05-06
  - Copyright Notice:
  - @(#)
  - Description: JSP-Page building the Html for results after search.
  -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="errorpage.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- <!DOCTYPE html> -->
<c:if test="${fn:length(requestScope.treffer) eq 0}">
	<div class="sorry search-entry">Leider keine Treffer zu diesem
		Begriff &#9785</div>
</c:if>

<c:forEach var="tmap" items="${requestScope.treffer}">
	<div class="search-entry clearfix">
		<!-- Bei Klick zur Rezept-seite -->
		<h2>
			<a href="/rezeptbuch/LoadRecipeServlet?id=${tmap['recipeID']}" class="recipe-title">${tmap['recipeName']}</a>
		</h2>

		<c:if
			test="${not(tmap['filename'] eq null or fn:length(tmap['filename']) eq 0)}">
			<a href="/rezeptbuch/LoadRecipeServlet?id=${tmap['recipeID']}"><img alt="Ein Bild von ${tmap['recipename']}" class="search-img"
				src="../LoadImage?id=${tmap['recipeID']}&table=recipes"
				width="200" height="180"></a>
			<!-- 			<div style="clear: both;"></div> -->
		</c:if>

		<dl>
			<dt>Autor</dt>
			<dd>${tmap['authorFullName']}</dd>
			<dt>Zubereitungsdauer</dt>
			<dd>${tmap['prepDuration']} Minuten</dd>
			<dt>Kochdauer</dt>
			<dd>${tmap['cookDuration']} Minuten</dd>
			<dt>Schwierigkeitsgrad</dt>
			<dd>${tmap['difficulty']}/5</dd>
			<dt>Portionen</dt>
			<dd>${tmap['servings']}</dd>
		</dl>
	</div>

</c:forEach>
