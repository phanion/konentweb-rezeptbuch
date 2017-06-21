/**
 * Originale Funktion: Lorenz, Listener: Michael
 */

	// Wenn das DOM geladen ist, soll folgendes ausgeführt werden:
	document.addEventListener('DOMContentLoaded', function() {
		// Der Zutaten-Hinzufüge-Button soll auf Klicks hören
		document.getElementById('addIngredientButton').addEventListener('click', add);
		resizeTextareas();
		document.getElementById('description').addEventListener('input', resizeTextareas);
	});
		
	

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
	
	function resizeTextareas(){
		var textareas = document.getElementsByTagName("textarea");
		
		for(var i = 0; i < textareas.length; i++){
			var rowsCount = textareas[i].value.split(/\n|\r/).length;
			textareas[i].setAttribute("rows", rowsCount);
			
			//https://stackoverflow.com/questions/4814398/how-can-i-check-if-a-scrollbar-is-visible
			while(textareas[i].scrollHeight > textareas[i].clientHeight){
				rowsCount = rowsCount + 1;
				textareas[i].setAttribute("rows", rowsCount);
			}
		}
	}