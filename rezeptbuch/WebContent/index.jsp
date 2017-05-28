<!-- Autor: Lorenz -->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="main.css">
<meta charset="utf-8">
<title>Kochrezepte - Homepage</title>
</head>
<body>
	<%@ includefile="/jsp/fragments/nav.jspf" %>
	<main>
	<h1>Herzlich Willkommen.</h1>
	<p>Hier können Sie Kochbücher speichern, teilen und lesen!</p>

	<c:if test="${not empty message}">
		<h2>${message}</h2>
	</c:if> </main>

</body>
</html>