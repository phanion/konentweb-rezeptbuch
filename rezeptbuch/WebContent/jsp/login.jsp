<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page errorPage="errorpage.jsp" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="../main.css">
<meta charset="utf-8">
<title>Kochrezepte - Login</title>
</head>
<body>
	<%@ includefile="fragments/nav.jspf" %>
	<main>
	<h1>Login</h1>
	<c:if test="${not empty message}">
		<h2>${message}</h2>
	</c:if>
	<form action="/rezeptbuch/login" method="post">
		<p>
			<label>E-Mail</label><input name="mail" type="email" size="30"
				maxlength="30" required>
		</p>
		<p>
			<label>Passwort</label><input name="password" type="password"
				size="30" maxlength="30" required>
		</p>
		<button class="button" role="button" type="submit">Login</button>
		<a class="button-secondary" role="button" href="registration.jsp">Noch keinen
			Account?</a>
	</form>
	</main>
</body>
</html>