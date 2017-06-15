<!--
  - Author(s): Michael
  - Date: 2017-05-06
  - Copyright Notice:
  - @(#)
  - Description: JSP-Page called after input in search field.
  -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<base href="${pageContext.request.requestURI}" />
<title>Rezeptsuche</title>
<script src="${pageContext.request.contextPath}/js/nav.js"></script>
<script src="${pageContext.request.contextPath}/js/search.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/main.css">
</head>

<body>
	<%@ include file="./fragments/nav.jspf"%>
	<main>
	<h1>Suche</h1>
	<input type="hidden" id="searchstring-hidden"
		value="${requestScope.searchstring}"> <input type="search"
		id="searchstring-input" name="searchstring" placeholder="Suche"
		onkeydown="return keyEnterSearch(event);" 
		onfocus="this.value = '';"><button type="button" id="submitsearch"
		class="button-secondary button-input-search">Finden</button>
	<div id="treffer">
		<p class="search-entry">Suchen Sie nach einem Begriff</p>
	</div>
	</main>
</body>
</html>

