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
			<label for="name">Name:</label> <input type="text" name="name"
				id="name" size="30" maxlength="40">
		</p>
		<p>
			<label for="zutatenText">Zutaten:</label>
			<textarea name="zutatenText" id="zutatenText"
				placeholder="Hier die Zutaten mit Menge und Einheit eingeben..."
				rows="10" required maxlength="2500"></textarea>
		</p>
		<p>
			<label for="description">Beschreibung:</label>
			<textarea name="description" id="description"
				placeholder="Hier die Beschreibung eingeben..." cols="50" rows="7"
				required maxlength="2500"></textarea>
		</p>
		<p>
			<label for="durationPreparation">Vorbereitungsdauer:</label>
			<input name="durationPreparation" id="durationPreparation"
				placeholder="Hier die Vorbereitungsdauer in Minuten angeben" type="number"
				min="1" max="500" value="null"/>
		</p>
		<p>
			<label for="durationCooking">Kochzeit:</label>
			<input name="durationCooking" id="durationCooking"
				placeholder="Hier die Kochzeit in Minuten angeben" type="number"
				min="1" max="500" value="null"/>
		</p>
		<p>
			<label for="difficulty">Schwierigkeit:</label>
			<input name="difficulty" id="difficulty"
				placeholder="Hier die Schwierigkeit angeben" type="number"
				max="5" min="1" value="null"/>
		</p>
		<p>
			<label for="servings">Portionen:</label>
			<input name="servings" id="servings"
				placeholder="Hier die Schwierigkeit angeben" type="number"
				max="99" min="1" value="null"/>
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