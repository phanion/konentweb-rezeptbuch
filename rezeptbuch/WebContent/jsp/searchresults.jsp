<!--
  - Author(s): Michael
  - Date: 2017-05-06
  - Copyright Notice:
  - @(#)
  - Description: JSP-Page building the Html for results after search.
  -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- <!DOCTYPE html> -->
<c:if test="${fn:length(requestScope.treffer) eq 0}">
	<div class="sorry search-entry">Leider keine Treffer zu diesem
		Begriff :(</div>
</c:if>

<c:forEach var="tmap" items="${requestScope.treffer}">
	<div class="search-entry clearfix">

		<h2>${tmap["recipeName"]}</h2><!-- TODO: Bei Klick zur Rezept-seite -->

		<c:if
			test="${not(tmap['filename'] eq null or fn:length(tmap['filename']) eq 0)}">
			<img alt="Ein Bild von ${tmap['recipename']}" class="search-img"
				src="../LoadImage?id=${tmap['recipeID']}&table=recipes" width="200px"
				height="180px">
<!-- 			<div style="clear: both;"></div> -->
		</c:if>
		
		<dl><dd>Autor</dd><dt>${tmap["authorFullName"]}</dt>
			<dd>Zubereitungsdauer</dd><dt>${tmap["prepDuration"]} Minuten</dt>
			<dd>Kochdauer</dd><dt>${tmap["cookDuration"]} Minuten</dt>
			<dd>Schwierigkeitsgrad</dd><dt>${tmap["difficulty"]}/5</dt>
			<dd>Portionen</dd><dt>${tmap["servings"]}</dt></dl>

	</div>

</c:forEach>
