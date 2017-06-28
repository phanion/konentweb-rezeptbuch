/**
 * @author: Flo + Michael, Lorenz
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
			addEventListenersAbos();
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
// resizeTextareas();
			manageTextareas();
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
// document.getElementById('beschreibung').addEventListener('input',
// resizeTextareas);

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

function deleteAbo(){
	var parent = this.parentElement;
	
	// https://www.w3schools.com/jsref/prop_element_nextelementsibling.asp
	var recipe = this.nextElementSibling.value;
	var action = 'deleteAbo';
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			parent.outerHTML = "";
		}
	}
	xmlhttp.open('POST', '../AboHandlingServlet', true);
	xmlhttp.setRequestHeader('Content-Type',
			'application/x-www-form-urlencoded');
	xmlhttp.send('recipe=' + recipe + '&action=' + action);
	
}

function addEventListenersAbos(){
	var elements = document.getElementsByName('deleteAbo');
	
	for (var i = 0; i < elements.length; i++) {
		elements[i].addEventListener('click', deleteAbo)		
		}
}

/*
 * Angelehnt an:
 * https://stackoverflow.com/questions/2803880/is-there-a-way-to-get-a-textarea-to-stretch-to-fit-its-content-without-using-php
 * Wir haben keine reine CSS Lösung dafür gefunden, daher hier Verwendung von
 * JavaScript für Darstellung
 */
var manageTextareas = function() {
	var textareas = document.getElementsByTagName('textarea');
	
	for(let ta of textareas) {
		ta.addEventListener('change',  sizeTextarea);
		ta.addEventListener('keydown',  sizeTextarea);
		ta.addEventListener('keyup',  sizeTextarea);
		ta.addEventListener('paste',  sizeTextarea);
		ta.addEventListener('cut',  sizeTextarea);
		
		// Einmal anfangs auslösen --> TA wird gesizet.
		var event = new Event('change');
		ta.dispatchEvent(event);
	}
}

var sizeTextarea = function() {
	this.style.height = 1;
	this.style.height = this.scrollHeight+'px';
	
}
	
//
// function resizeTextareas(){
// var textareas = document.getElementsByTagName("textarea");
//	
// for(var i = 0; i < textareas.length; i++){
// var rowsCount = textareas[i].value.split(/\n|\r/).length;
// textareas[i].setAttribute("rows", rowsCount);
//		
// //https://stackoverflow.com/questions/4814398/how-can-i-check-if-a-scrollbar-is-visible
// while(textareas[i].scrollHeight > textareas[i].clientHeight){
// rowsCount = rowsCount + 1;
// textareas[i].setAttribute("rows", rowsCount);
// }
// }
//	
// }
