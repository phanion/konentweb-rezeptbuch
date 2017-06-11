/**
 * @author: Michael
 */

document.addEventListener("DOMContentLoaded", init);

var init = function() {
	document.getElementById("submitsearch").addEventListener("click",
			doSearch());
}

var doSearch = function() {
	var xhr = new XMLHttpRequest();
	xhr.open("POST", "../newsearch", true);
	xhr.send();

	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			console.log(xhr.response);
			console.log("-- break --");
			console.log(xhr.responseText);
		}
		// var rezeptList = JSON.parse(xmlhttp.responseText);
		// if (rezeptList == null || rezeptList.length == 0) {
		// document.getElementById("hitlist").innerHTML = "Keine Treffer";
		// return;
		// }
		//
		// var ausgabe =
		// "<table><tr><th></th><th>Id</th><th>Rezept</th><th>Vorbereitungsdauer</th><th>Kochdauer</th><th>Schwierigkeit</th></tr>";
		// for (var i = 0; i < rezeptList.length; i++) {
		// ausgabe += "<tr><td rowspan=\"2\">";
		//
		// // Wenn kein Filename vorhanden, wird auch nicht versucht ein
		// // Bild zu laden.
		// if (rezeptList[i].filename != null) {
		// ausgabe += "<img src=\"../LoadImage?id=" + rezeptList[i].id
		// + "&table=recipes\" width=\"200\" heigt=\"180\">";
		//
		// }
		// ausgabe += "</td><td>";
		// ausgabe += rezeptList[i].id;
		// ausgabe += "</td><td>";
		// ausgabe += "<a href=\"/rezeptbuch/LoadRecipeServlet?id="
		// + rezeptList[i].id + "\">" + rezeptList[i].name
		// + "</a>";
		// ausgabe += "</td><td>";
		// ausgabe += rezeptList[i].durPrep;
		// ausgabe += "</td><td>";
		// ausgabe += rezeptList[i].durCook;
		// ausgabe += "</td><td>";
		// ausgabe += rezeptList[i].difficulty;
		// ausgabe += "</td></tr>";
		// ausgabe += "<tr><td colspan=\"5\">";
		// ausgabe += rezeptList[i].description;
		// ausgabe += "</td></tr>";
		// }
		// ausgabe += "</table>";
		// document.getElementById("hitlist").innerHTML = ausgabe;
		// }
	};

}