/**
 * 
 */

'use strict';

document.addEventListener('DOMContentLoaded', init);

function init() {
	document.getElementById('commentButton').addEventListener('click',
			addComment);
	document.getElementById('aboButton').addEventListener('click', aboHandling);
	
	// FUnktion aus textarea.js
	manageTextareas();
//	resizeTextareas();
//	document.getElementById('description').addEventListener('input', resizeTextareas);
//	document.getElementById('newComment').addEventListener('input', resizeTextareas);

}

// Die sichtbare Ausführung ist wegen dem Versand von Mails leicht verzögert.
function addComment() {
	var id = document.getElementById('id').value;
	// Carriage Return wird durch ein Whitespace ersetzt, um Fehler im JSON Format zu vermeiden
	var comment = document.getElementById('newComment').value.replace(/[\n\r]/g, ' ');
	
	if (comment != "" && comment != null && comment != undefined) {
		var xmlhttp = new XMLHttpRequest();
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				var commentResponse = JSON.parse(xmlhttp.responseText);
				
				// Ein komplett neuer Kommentar wird in Javascript erzeugt
				var newComm = document.createElement("div");
				newComm.setAttribute("class", "commentBox");
				
				var commentator = document.createElement("h4");
				commentator.innerHTML = commentResponse.author;
				
				var commText = document.createElement("div");
				commText.setAttribute("class", "commentContent");
				commText.innerHTML = commentResponse.comment;
				
				var deleteComm = document.createElement("a");
				
				deleteComm.setAttribute('href','/rezeptbuch/DeleteCommentServlet?id='
						+ commentResponse.id
						+ '&recipe='
						+ commentResponse.recipe);
				
				deleteComm.classList.add('button-del');
				deleteComm.classList.add('clearfix');
				deleteComm.innerHTML = '';
				
				var div = document.getElementById('comments');
				
				newComm.appendChild(commentator);
				newComm.appendChild(commText);
				newComm.appendChild(deleteComm);
			
				div.appendChild(newComm);

				document.getElementById('commentForm').reset();

			}
		}

		xmlhttp.open('POST', '../AddCommentServlet', true);
		xmlhttp.setRequestHeader('Content-Type',
				'application/x-www-form-urlencoded');
		xmlhttp.send('recipe=' + id + '&comment=' + comment);

	}
	else{
		window.alert("Bitte geben Sie ihren Kommentar in das Feld ein.");
		
	}
}

function aboHandling() {
	var recipe = document.getElementById('id').value;
	var action = document.getElementById('aboAction').value;
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			if (action === 'addAbo') {
				document.getElementById('aboAction').value = 'deleteAbo';
				document.getElementById('aboButton').innerHTML = 'Nicht länger merken';
			} else {
				document.getElementById('aboAction').value = 'addAbo';
				document.getElementById('aboButton').innerHTML = 'Merken';
			}
		}
	}
	xmlhttp.open('POST', '../AboHandlingServlet', true);
	xmlhttp.setRequestHeader('Content-Type',
			'application/x-www-form-urlencoded');
	xmlhttp.send('recipe=' + recipe + '&action=' + action);

}

//function resizeTextareas(){
//	var textareas = document.getElementsByTagName("textarea");
//	
//	for(var i = 0; i < textareas.length; i++){
//		var rowsCount = textareas[i].value.split(/\n|\r/).length;
//		textareas[i].setAttribute("rows", rowsCount);
//		
//		// https://stackoverflow.com/questions/4814398/how-can-i-check-if-a-scrollbar-is-visible
//		while(textareas[i].scrollHeight > textareas[i].clientHeight){
//			rowsCount = rowsCount + 1;
//			textareas[i].setAttribute("rows", rowsCount);
//		}
//	}
//}