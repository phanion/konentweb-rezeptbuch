/**
 * Autor: Lorenz
 */
'use strict';

document.addEventListener('DOMContentLoaded', init);

function init() {
	loadNewestRecipes();
}

function loadNewestRecipes() {
		
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			document.getElementById('recipes').innerHTML = xmlhttp.responseText;
					}
	}

	xmlhttp.open('POST', 'IndexServlet', true);
	xmlhttp.setRequestHeader('Content-Type',
			'application/x-www-form-urlencoded');
	xmlhttp.send();
	
	
}