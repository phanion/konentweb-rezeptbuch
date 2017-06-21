/**
 * @author: Flo, Lorenz
 */
'use strict';

document.addEventListener('DOMContentLoaded', init);

function init() {
	document.getElementById('editRecipeButton').addEventListener('click', edit);

	document.getElementById('addIngredientButton').addEventListener('click', add);
	
	document.getElementById('refreshButton').addEventListener('click', refreshPage);
	
	document.getElementById('deleteButton').addEventListener('click', deleteRecipe);
	
}

function edit() {
	var elements = document.getElementsByName("zutatenEinheit");
	var menge = document.getElementsByName("zutatenMenge");
	var einheiten = [ "Stück", "Liter", "Milliliter", "Teelöffel", "Esslöffel",
			"Tasse", "Gramm", "Kilogramm", "Prise", "sonstiges" ];
	var selected = document.getElementsByClassName("selectedValue");

	/*
	 * Die bereits erstellten Felder für die Zutaten des Rezeptes werden
	 * eingeblendet, die Menge mit options beladen und das passende Element
	 * ausgewählt
	 */
	for (var i = 0; i < menge.length; i++) {
		var options = [];
		for ( var y in einheiten) {
			options.push(document.createElement("option"));
			options[y].setAttribute("name", einheiten[y]);
			options[y].setAttribute("value", einheiten[y]);
			options[y].text = einheiten[y];
			elements[i].appendChild(options[y]);
		}

		elements[i].value = selected[i].value;

	}

	// Die benötigten Felder zur Bearbeitung und Speicherung werden eingeblendet
	document.getElementById("ingredientContainer").classList.remove('hidden-block');
	document.getElementById("editButtons").classList.remove('hidden-block');
	document.getElementById("addIngredientButton").classList.remove('hidden-block');

	
	
	// Die bisherigen Zutaten werden ausgeblendet
	document.getElementById("recipeIngredients").classList.add('hidden-block');

	// Kochzeit, Vorbereitungszeit, Schwierigkeit und Portionen editierbart
	// setzen
	var attributes = document.getElementById("editRecipe")
	var inputs = attributes.getElementsByTagName("input");

	for (var i = 0; i < inputs.length; i++) {
		if (inputs[i].type.toLowerCase() == "number") {
			inputs[i].disabled = false;
		}
	}

	// Beschreibung editierbar setzen
	document.getElementsByTagName("textarea")[0].disabled = false;
}

function add() {
	// http://stackoverflow.com/questions/34127450/dynamically-add-forminput-textbox-in-jsp
	var paragraph = document.createElement("P");
	var Menge = document.createElement("input");
	Menge.setAttribute("type", "number");
	Menge.setAttribute("name", "zutatenMenge");
	Menge.setAttribute("class", "zutatenMenge");
	Menge.setAttribute("placeholder", "Menge");
	Menge.setAttribute("required", "");

	var Einheit = document.createElement("select");

	Einheit.setAttribute("name", "zutatenEinheit");
	Einheit.setAttribute("class", "zutatenEinheit");
	Einheit.setAttribute("placeholder", "Einheit");
	Einheit.setAttribute("required", "");
	Einheit.setAttribute("size", "1");

	var einheiten = [ "Stück", "Liter", "Milliliter", "Teelöffel", "Esslöffel",
			"Tasse", "Gramm", "Kilogramm", "Prise", "sonstiges" ];

	var options = [];

	for ( var i in einheiten) {
		options.push(document.createElement("option"));
		options[i].setAttribute("name", einheiten[i]);
		options[i].setAttribute("value", einheiten[i]);
		options[i].text = einheiten[i];
		Einheit.appendChild(options[i]);

	}

	var Zutat = document.createElement("input");

	Zutat.setAttribute("type", "text");
	Zutat.setAttribute("name", "zutatenZutat");
	Zutat.setAttribute("class", "zutatenZutat")
	Zutat.setAttribute("placeholder", "Zutat");
	Zutat.setAttribute("required", "");
	paragraph.appendChild(Menge);
	paragraph.appendChild(Einheit);
	paragraph.appendChild(Zutat);

	var div = document.getElementById("ingredientContainer");
	div.appendChild(paragraph);
}

function refreshPage() {
	location.reload(true);
}


//Ruft das Servlet zum Löschen von Rezepten auf. Es erscheint vor dem finalen Aufruf des Servlets noch ein Bestätigungsfenster.
function deleteRecipe() {
	var id = document.getElementById('id').value;

	confirm = window
			.confirm("Sind Sie sicher, dass Sie dieses Rezept löschen möchten?");

	if (confirm === true) {

		var xmlhttp = new XMLHttpRequest();
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				window.location.replace("/rezeptbuch");
			}
		}
		xmlhttp.open('GET', '../DeleteRecipeServlet?recipe=' + id, true);
		xmlhttp.send();
	}

}