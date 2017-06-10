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
<script type="text/javascript" src="../js/search.js" async></script>
<link rel="stylesheet" type="text/css" href="../main.css">
</head>

<body>
	<%@ includefile="./fragments/nav.jspf" %>
	<main>
	<h1>Suche</h1>
<!-- 	<form action="../newsearch" method="POST"> -->
<!-- 		<input type="search" name="searchstring" class="search" placeholder="Suche"> -->
<!-- 		<button type="submit" id="submitsearch" class="button">M</button> -->
<!-- 	</form> -->
	<input type="search" name="searchstring" placeholder="Suche">
	<button type="submit" id="submitsearch" class="button" onclick="doSearch()">Suchen</button>
	<div id="treffer">${requestScope.searchstring }</div>
	</main>
</body>
</html>