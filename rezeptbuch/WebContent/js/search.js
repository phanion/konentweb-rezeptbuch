/**
 * @author: Michael
 */

// document.addEventListener("Load", init);
var init = function() {
	doSearch();

	document.getElementById("submitsearch").addEventListener("click",
			doSearch);
}

var keyEnterSearch = function(event) {
	if (event.which == 13 || event.keyCode == 13) {
		doSearch();
		return false;
	}
	return true;
}

var doSearch = function() {
	var searchval;
	var searchstring_hidden = document.getElementById("searchstring-hidden").value;
	var searchstring_input = document.getElementById("searchstring-input").value;

	if (searchstring_hidden !== null && searchstring_hidden !== "") {
		searchval = searchstring_hidden;
	} else {
		searchval = searchstring_input;
	}
	// } else if (searchstring_input !== null && searchstring_input !== "") {
	// searchval = searchstring_input;
	// } else {
	// return;
	// }

	var xhr = new XMLHttpRequest();
	xhr.open("POST", "../newsearch", true);
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

	xhr.send("searchstring=" + searchval);

	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			// Serverseitig gerendertes Html
			document.getElementById("treffer").innerHTML = xhr.responseText;
			document.getElementById("searchstring-hidden").value = "";
		}
	};

}

document.addEventListener("DOMContentLoaded", init);