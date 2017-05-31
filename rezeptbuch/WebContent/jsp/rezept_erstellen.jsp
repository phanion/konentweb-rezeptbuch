<!-- Autor: Florien, Lorenz -->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page errorPage="errorpage.jsp" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<link rel="stylesheet" type="text/css" href="../main.css">
<meta charset="UTF-8">
<c:choose>
	<c:when test="${empty user}">
		<c:redirect url="login.jsp" />
	</c:when>
</c:choose>
<script type="text/javascript">
	function add() {
		//http://stackoverflow.com/questions/34127450/dynamically-add-forminput-textbox-in-jsp
		var paragraph = document.createElement("P")
		var Menge = document.createElement("input");
		Menge.setAttribute("type", "number");
		Menge.setAttribute("name", "zutatenMenge");
		Menge.setAttribute("id", "zutatenMenge");
		Menge.setAttribute("placeholder", "Menge");
		Menge.setAttribute("required", "");

		var Einheit = document.createElement("select");

		Einheit.setAttribute("name", "zutatenEinheit");
		Einheit.setAttribute("id", "zutatenEinheit");
		Einheit.setAttribute("placeholder", "Einheit");
		Einheit.setAttribute("required", "");
		Einheit.setAttribute("size", "1");

		var einheiten = [ "Stück", "Liter", "Milliliter", "Teelöffel",
			"Esslöffel", "Tasse", "Gramm", "Kilogramm", "Prise",
			"sonstiges" ];
		
		
	var options = [];

	for
	(var i in einheiten)
	{
		options.push(document.createElement("option"));
		options[i].setAttribute("name",einheiten[i]);
		options[i].setAttribute("value",einheiten[i]);
		options[i].text = einheiten[i];
		Einheit.appendChild(options[i]);
		
	}
	

		var Zutat = document.createElement("input");

		Zutat.setAttribute("type", "text");
		Zutat.setAttribute("name", "zutatenZutat");
		Zutat.setAttribute("id", "zutatenZutat")
		Zutat.setAttribute("placeholder", "Zutat");
		Zutat.setAttribute("required", "");
		paragraph.appendChild(Menge);
		paragraph.appendChild(Einheit);
		paragraph.appendChild(Zutat);

		var div = document.getElementById("zutaten");
		div.appendChild(paragraph);
	}
</script>
<title>Kochrezepte - Erstellen</title>
</head>


<body>
	<%@ includefile="fragments/nav.jspf" %>
	<main>

	<h1>Rezept erstellen</h1>
	<form action="/rezeptbuch/NeuesRezeptServlet" method="post"
		enctype="multipart/form-data">
		<p>
			<label for="name">Name:</label> <input type="text" name="name"
				id="name" size="30" maxlength="40">
		</p>
		<p>
		<h3>Zutaten</h3>


		<div id="zutaten"></div>
		<p>
			<input type="button" class="button" id="addrow" name="addrow"
				value="Zutat hinzufügen" onclick="add();" />
		</p>
		<p>
			<label class="labelfortextarea" for="description">Beschreibung:</label>
			<textarea name="description" id="description"
				placeholder="Hier die Beschreibung eingeben..." cols="50" rows="7"
				required maxlength="2500"></textarea>
		</p>
		<p>
			<label for="durationPreparation">Vorbereitungsdauer:</label> <input
				name="durationPreparation" id="durationPreparation"
				placeholder="Hier die Vorbereitungsdauer in Minuten angeben"
				type="number" min="0" max="500" value="0" />
		</p>
		<p>
			<label for="durationCooking">Kochzeit:</label> <input
				name="durationCooking" id="durationCooking"
				placeholder="Hier die Kochzeit in Minuten angeben" type="number"
				min="0" max="500" value="0" />
		</p>
		<p>
			<label for="difficulty">Schwierigkeit:</label> <input
				name="difficulty" id="difficulty"
				placeholder="Hier die Schwierigkeit angeben" type="number" max="5"
				min="0" value="0" />
		</p>
		<p>
			<label for="servings">Portionen:</label> <input name="servings"
				id="servings" placeholder="Hier die Schwierigkeit angeben"
				type="number" max="99" min="0" value="0" />
		</p>
		<p>
			<label for="image">Bild auswählen:</label> <input type="file"
				name="image" id="image" accept="image/*">
		</p>
		<p>
			<!--  Input Type Submit soll laut Skript nicht verwendet werden (02_HTML -> Seite 54) -->
			<button name="absenden" class="button" type="submit">Absenden</button>
		</p>
	</form>

	</main>
</body>
</html>