<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page errorPage="errorpage.jsp" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Kochrezepte - Registrierung</title>
</head>
<body>
	<%@ includefile="fragments/nav.jspf" %>
	<main>
	<h1>Registration</h1>
	<c:if test="${not empty message}">
		<h2>${message}</h2>
	</c:if>
	
	<form action="/rezeptbuch/registration" method="post">
		<p>
			<label>E-Mail</label><input name="mail" type="email" size="30"
				maxlength="30" required>
		</p>
		<p>
			<label>Name</label><input name="lastName" type="text" size="30"
				maxlength="30" required>
		</p>
		<p>
			<label>Vorname</label><input name="firstName" type="text" size="30"
				maxlength="30" required>
		</p>
		<p>
			<label>Passwort</label><input name="password" type="password"
				size="30" maxlength="30" required>
		</p>
		<p>
			<label>Passwort wiederholen</label><input name="password_retype"
				type="password" size="30" maxlength="30" required>
		</p>
		<button type="submit">Registrieren</button>
	</form>

	<p>
		<a href="login.jsp">Bereits ein Account?</a>
	</p>
	</main>
</body>
</html>