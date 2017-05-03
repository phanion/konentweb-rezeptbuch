<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page errorPage="errorpage.jsp" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Kochrezepte - Erstellen</title>
</head>


<body>
	<%@ includefile="fragments/nav.jspf" %>
	<main>

	<h1>Rezept erstellen</h1>
	<form action="/rezeptbuch/NeuesRezeptServlet" method="post">
		<p>
			<label for="rezept">Name:</label> <input type="text" name="rezept"
				id="rezept" size="30" maxlength="40">
		</p>
		<p>
			<label for="zutatenText">Zutaten:</label>
			<textarea name="zutatenText" id="zutatenText"
				placeholder="Hier die Zutaten mit Menge und Einheit eingeben..."
				rows="10" required maxlength="2500"></textarea>
		</p>
		<p>
			<label for="beschreibung">Beschreibung:</label>
			<textarea name="beschreibung" id="beschreibung"
				placeholder="Hier die Beschreibung eingeben..." cols="50" rows="7"
				required maxlength="2500"></textarea>
		</p>
		<p>
			<!--  Input Type Submit soll laut Skript nicht verwendet werden (02_HTML -> Seite 54) -->
			<button name="absenden" type="submit">Absenden</button>
		</p>
	</form>

	<!--   <form action="http://localhost:8080/rezeptbuch/ErstellenServlet" method="post" enctype="multipart/form-data">
		<p>
			Bild auswÃ¤hlen:
			<input type="file" name="bild" accept="image/*">
		</p> 
	</form> --> </main>
</body>
</html>