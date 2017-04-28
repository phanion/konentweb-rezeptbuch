<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="${pageContext.request.requestURI}"/>
<title>Profil</title>
</head>
<body>
	<header>
		<h1>Dein Profil</h1>
	</header>
	<main> 
<!-- Alles noch im Aufbau -->
	<form id="userDatenForm">
		<p>
			<label for="vorname">Vorname</label> <input type="text"
				name="vorname" id="vorname" placeholder="Ihr Vorname" value="<%%>">
		</p>
		<p>
			<label for="nachname">Nachname</label> <input type="text"
				name="nachname" id="nachname" placeholder="Ihr Nachname">
		</p>
		<p>
			<label for="mail">E-Mail</label> <input type="text" name="mail"
				id="mail" placeholder="Ihre E-Mail">
		</p>
		<p>
			<label for="beschreibung">Profilbeschreibung</label>
			<textarea name="beschreibung" id="beschreibung"
				placeholder="Ihre Profilbeschreibung"></textarea>
		</p>
	</form>
	<button type="button">Speichern</button>
	</main>
	<footer></footer>
</body>
</html>