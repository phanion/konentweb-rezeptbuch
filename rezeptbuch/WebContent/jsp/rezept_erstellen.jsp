<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page errorPage="errorpage.jsp" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<script type="text/javascript">
	function add() {
		//http://stackoverflow.com/questions/34127450/dynamically-add-forminput-textbox-in-jsp
		var paragraph = document.createElement("P")

		var Menge  = document.createElement("input");

		Menge.setAttribute("type", "number");
		Menge.setAttribute("name", "zutatenMenge");
		Menge.setAttribute("placeholder", "Menge");
		Menge.setAttribute("required","");
		
		var Einheit = document.createElement("input");
		
		Einheit.setAttribute("type", "text");
		Einheit.setAttribute("name", "zutatenEinheit");
		Einheit.setAttribute("placeholder", "Einheit");
		Einheit.setAttribute("required","");
		
		var Zutat = document.createElement("input");
		
		Zutat.setAttribute("type", "text");
		Zutat.setAttribute("name", "zutatenZutat");
		Zutat.setAttribute("placeholder", "Zutat");
		Zutat.setAttribute("required","");


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
	<form action="/rezeptbuch/NeuesRezeptServlet" method="post">
		<p>
			<label for="name">Name:</label> <input type="text" name="name"
				id="name" size="30" maxlength="40">
		</p>
		<p>
		<h3>Zutaten</h3>
			
		
		<div id="zutaten">
		
		</div>
		<p><input type="button" id="addrow" name="addrow"
				value="Zutat hinzufügen" onclick="add();"/></p>
		<p>
			<label for="description">Beschreibung:</label>
			<textarea name="description" id="description"
				placeholder="Hier die Beschreibung eingeben..." cols="50" rows="7"
				required maxlength="2500"></textarea>
		</p>
		<p>
			<label for="durationPreparation">Vorbereitungsdauer:</label> <input
				name="durationPreparation" id="durationPreparation"
				placeholder="Hier die Vorbereitungsdauer in Minuten angeben"
				type="number" min="1" max="500" value="null" />
		</p>
		<p>
			<label for="durationCooking">Kochzeit:</label> <input
				name="durationCooking" id="durationCooking"
				placeholder="Hier die Kochzeit in Minuten angeben" type="number"
				min="1" max="500" value="null" />
		</p>
		<p>
			<label for="difficulty">Schwierigkeit:</label> <input
				name="difficulty" id="difficulty"
				placeholder="Hier die Schwierigkeit angeben" type="number" max="5"
				min="1" value="null" />
		</p>
		<p>
			<label for="servings">Portionen:</label> <input name="servings"
				id="servings" placeholder="Hier die Schwierigkeit angeben"
				type="number" max="99" min="1" value="null" />
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