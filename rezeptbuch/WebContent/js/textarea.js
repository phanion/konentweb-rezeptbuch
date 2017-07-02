/**
 * @author Michael
 */

'use strict';

document.addEventListener('DOMContentLoaded', init);

function init () {
	manageTextareas();
}

/*
 * Angelehnt an Lösung von NicB:
 * https://stackoverflow.com/questions/2803880/is-there-a-way-to-get-a-textarea-to-stretch-to-fit-its-content-without-using-php
 * 
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
	var dis = (this.disabled == true);
	this.disabled = false;
	
	this.style.height = 1 + 'px';
	this.style.height = (this.scrollHeight)+'px';
	
	this.disabled = dis;
}