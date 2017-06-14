<!-- Autor: Lorenz -->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page errorPage="errorpage.jsp" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="main.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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
			<dl>
				<dd>Bewertung</dd>
				<dt>
					<c:choose>
						<c:when test="${recipe.ratingCount == 0}">-</c:when>
						<c:otherwise>${recipe.ratingSum / recipe.ratingCount}</c:otherwise>
					</c:choose>
				</dt>



			</dl>
		</div>
	</c:forEach> </main>
</body>
</html>