/**
 * @author: Flo
 */
'use strict';

document.addEventListener('DOMContentLoaded', init);

function init() {
	document.getElementById('aendernButton').addEventListener('click', edit);
	document.getElementById('refreshButton').addEventListener('click', refreshPage);
}

function edit() {
	document.getElementById('editButtons').classList.remove('hidden-block');
	document.getElementById('aendernButton').classList.add('hidden-block');
	
	// Formular-Elemente aktivieren
	var inputs = document.getElementsByTagName("input");
	for (var i = 0; i < inputs.length; i++) {
		if (inputs[i].type.toLowerCase() == "text") {
			inputs[i].disabled = false;
		}
	}

	// Beschreibung editierbar setzen
	document.getElementsByTagName("textarea")[0].disabled = false;
}

function refreshPage() {
	location.reload(true);
}