/**
 * @author: Flo, Lorenz
 */
'use strict';

document.addEventListener('DOMContentLoaded', init);

function init() {
	if (document.getElementById('editRecipeButton') != null) {
		document.getElementById('editRecipeButton').addEventListener('click', edit);
	}
	
	if (document.getElementById('addIngredientButton') != null) {
	document.getElementById('addIngredientButton').addEventListener('click', add);
	}
	
	if (document.getElementById('refreshButton') != null) {
	document.getElementById('refreshButton').addEventListener('click', refreshPage);
	
	document.getElementById('deleteButton').addEventListener('click', deleteRecipe);
	}
	
	if (document.getElementsByName("deleteIng") != null) {
		var elements = document.getElementsByName("deleteIng");
	
		for (var i = 0; i < elements.length; i++) {
			elements[i].addEventListener('click', del)		
			}
	}
}

function edit() {
	var elements = document.getElementsByName("zutatenEinheit");
	var menge = document.getElementsByName("zutatenMenge");
	var einheiten = [ "Stück", "Liter", "Milliliter", "Teelöffel", "Esslöffel",
			"Tasse", "Gramm", "Kilogramm", "Centiliter", "Prise", "sonstiges" ];
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
	
	document.getElementById("recipeName").disabled = false;
	
	// Die benötigten Felder zur Bearbeitung und Speicherung werden eingeblendet
	document.getElementById("ingredientContainer").classList.remove('hidden-block');
	document.getElementById("editButtons").classList.remove('hidden-block');
	document.getElementById("addIngredientButton").classList.remove('hidden-block');

	
	
	// Die bisherigen Zutaten werden ausgeblendet
	document.getElementById("recipeIngredients").classList.add('hidden-block');

	// Kochzeit, Vorbereitungszeit, Schwierigkeit und Portionen editierbart
	// setzen
	var attributes = document.getElementById("editRecipe");
	var inputs = attributes.getElementsByTagName("input");

	for (var i = 0; i < inputs.length; i++) {
		if (inputs[i].type.toLowerCase() == "number") {
			inputs[i].disabled = false;
		}
	}

	document.getElementById("recipeName").removeAttribute("disabled");
	
	// Beschreibung editierbar setzen
	document.getElementsByTagName("textarea")[0].removeAttribute("disabled");
}

function add() {
	// http://stackoverflow.com/questions/34127450/dynamically-add-forminput-textbox-in-jsp
	var divNewLine = document.createElement("div");
	
	// Div erhält die selbe Klasse wie die bereits erstellten Zutaten-Reihen, um später auf alle zugreifen zu können
	divNewLine.setAttribute("class", "newLine clearfix");
	
	var Menge = document.createElement("input");
	Menge.setAttribute("type", "number");
	Menge.setAttribute("name", "zutatenMenge");
	Menge.setAttribute("class", "zutatenMenge");
	Menge.setAttribute("placeholder", "Menge");
	Menge.setAttribute("required", "");
	Menge.setAttribute("min", "1");

	var Einheit = document.createElement("select");

	Einheit.setAttribute("name", "zutatenEinheit");
	Einheit.setAttribute("class", "zutatenEinheit");
	Einheit.setAttribute("placeholder", "Einheit");
	Einheit.setAttribute("required", "");
	Einheit.setAttribute("size", "1");

	var einheiten = [ "Stück", "Liter", "Milliliter", "Teelöffel", "Esslöffel",
			"Tasse", "Gramm", "Kilogramm", "Centiliter", "Prise", "sonstiges" ];

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
	Zutat.setAttribute("class", "zutatenZutat");
	Zutat.setAttribute("placeholder", "Zutat");
	Zutat.setAttribute("required", "");
	
	var Button = document.createElement("button");
	
	Button.setAttribute("type", "button");
	Button.setAttribute("name", "deleteIng");
	Button.setAttribute("class", "deleteIng button-del");
	Button.innerHTML = "";
	
	Button.addEventListener('click', del);
	
	divNewLine.appendChild(Menge);
	divNewLine.appendChild(Einheit);
	divNewLine.appendChild(Zutat);
	divNewLine.appendChild(Button);
	
	var div = document.getElementById("ingredientContainer");
	div.appendChild(divNewLine);
}

function refreshPage() {
	location.reload(true);
}

function del() {
	this.parentNode.outerHTML = "";
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