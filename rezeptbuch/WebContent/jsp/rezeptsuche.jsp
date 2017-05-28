<!--  Autor: Flo -->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="../main.css">
<meta charset="UTF-8">
<title>Rezeptsuche</title>
<script>
	"use strict";
	document.addEventListener("DOMContentLoaded", init);
	function init() {
		document.getElementById("button").addEventListener("click",
				changeContent);

	}

	function changeContent() {

		var searchURL = "../SucheRezeptServlet";
		var rezept = document.getElementById("rezept").value;
		if (rezept != null && rezept.length > 0)
			searchURL += "?rezept=" + encodeURIComponent(rezept);

		var xmlhttp = new XMLHttpRequest();
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				var rezeptList = JSON.parse(xmlhttp.responseText);
				if (rezeptList == null || rezeptList.length == 0) {
					document.getElementById("hitlist").innerHTML = "Keine Treffer";
					return;
				}

				var ausgabe = "<table><tr><th></th><th>Id</th><th>Rezept</th><th>Vorbereitungsdauer</th><th>Kochdauer</th><th>Schwierigkeit</th></tr>";
				for (var i = 0; i < rezeptList.length; i++) {
					ausgabe += "<tr><td rowspan=\"2\">";
					ausgabe += "<img src=\"../LoadImage?id=" + rezeptList[i].id
							+ "&table=recipes\" width=\"200\" heigt=\"180\">";
					ausgabe += "</td><td>";
					ausgabe += rezeptList[i].id;
					ausgabe += "</td><td>";
					ausgabe += rezeptList[i].name;
					ausgabe += "</td><td>";
					ausgabe += rezeptList[i].durPrep;
					ausgabe += "</td><td>";
					ausgabe += rezeptList[i].durCook;
					ausgabe += "</td><td>";
					ausgabe += rezeptList[i].difficulty;
					ausgabe += "</td></tr>";
					ausgabe += "<tr><td colspan=\"5\">";
					ausgabe += rezeptList[i].description;
					ausgabe += "</td></tr>";
				}
				ausgabe += "</table>";
				document.getElementById("hitlist").innerHTML = ausgabe;
			}
		};

		xmlhttp.open("GET", searchURL, true);
		xmlhttp.send();
	}
</script>
</head>
<body>
	<%@ includefile="/jsp/fragments/nav.jspf" %>
	<main>
	<form id="myForm">
		<fieldset>
			<legend><h1>Rezeptsuche</h1></legend>
			<div>
				<label for="rezept">Rezept:</label> <input type="text" name="rezept"
					id="rezept" placeholder="Ihr Rezept">
			</div>
			<div>
				<button type="button" class="button" id="button" name="button">Suchen</button>
				<button name="reset" class="button-secondary" type="reset">Zurücksetzen</button>
			</div>
		</fieldset>
	</form>
	<br>
	<h3>Trefferliste</h3>
	<div id="hitlist">Keine Treffer</div>
	</main>
</body>
</html>