<!-- Autor: Lorenz -->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Kochrezepte - Homepage</title>
<script src="${pageContext.request.contextPath}/js/nav.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/main.css">
</head>
<body>
	<%@ include file="/jsp/fragments/nav.jspf" %>
	<main>
	<h1>Herzlich Willkommen.</h1>
	<p>Hier können Sie Kochbücher speichern, teilen und lesen!</p>

	<c:if test="${not empty message}">
		<h2>${message}</h2>
	</c:if> </main>

</body>
</html>