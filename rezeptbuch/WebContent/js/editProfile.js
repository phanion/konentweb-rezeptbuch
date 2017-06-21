/**
 * @author: Flo + Michael
 */
'use strict';

document.addEventListener('DOMContentLoaded', init);

function init() {
	// Standardfunktion am Anfang: Profildaten anzeigen
	var contentDiv = document.getElementById('contentDiv');
	getProfileData();
	

	// document.getElementById('aendernButton').addEventListener('click', edit);
	// document.getElementById('refreshButton').addEventListener('click',
	// refreshPage);

	// Listener für die 'Tab'-Buttons

	var profileDataButton = document.getElementById('profileDataButton');
	profileDataButton.addEventListener('click', getProfileData);

	var ownRecipesButton = document.getElementById('ownRecipesButton');
	ownRecipesButton.addEventListener('click', getOwn);

	var savedRecipesButton = document.getElementById('savedRecipesButton');
	savedRecipesButton.addEventListener('click', getSaved);

}

// In Arbeit!
var getSaved = function() {


	var xhr = new XMLHttpRequest();
	xhr.open('GET', '../savedrecipes', true);
	// xhr.setRequestHeader('Content-Type',
	// 'application/x-www-form-urlencoded');

	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			// Serverseitig gerendertes Html
			contentDiv.innerHTML = xhr.responseText;
			
			manageColors(savedRecipesButton);
		}
	};

	xhr.send();
}

var getOwn = function() {
	
	
	var xhr = new XMLHttpRequest();
	xhr.open('GET', '../ownrecipes', true);
	// xhr.setRequestHeader('Content-Type',
	// 'application/x-www-form-urlencoded');

	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			// Serverseitig gerendertes Html
			contentDiv.innerHTML = xhr.responseText;
			
			manageColors(ownRecipesButton);
		}
	};

	xhr.send();
}

var getProfileData = function() {
	

	var xhr = new XMLHttpRequest();
	xhr.open('GET', '../profiledata', true);
	// xhr.setRequestHeader('Content-Type',
	// 'application/x-www-form-urlencoded');

	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			// Serverseitig gerendertes Html
			contentDiv.innerHTML = xhr.responseText;

			document.getElementById('aendernButton').addEventListener('click',
					edit);
			document.getElementById('refreshButton').addEventListener('click',
					refreshPage);

			manageColors(profileDataButton);
		}
	};

	xhr.send();

}

function edit() {
	document.getElementById('editButtons').classList.remove('hidden-block');
	document.getElementById('aendernButton').classList.add('hidden-block');

	// Formular-Elemente aktivieren
	var inputs = document.getElementsByTagName('input');
	for (var i = 0; i < inputs.length; i++) {
		if (inputs[i].type.toLowerCase() == 'text') {
			inputs[i].disabled = false;
		}
	}

	// Beschreibung editierbar setzen
	document.getElementsByTagName('textarea')[0].disabled = false;
}

function refreshPage() {
	location.reload(true);
}

function manageColors(callingButton) {
	var buttonArray = [ profileDataButton, ownRecipesButton, savedRecipesButton ];
	
	for (let b of buttonArray) {
		if (b == callingButton) {
			b.classList.remove('button-secondary');
			b.classList.add('button');
		} else {
			b.classList.remove('button');
			b.classList.add('button-secondary');
		}
	}
}