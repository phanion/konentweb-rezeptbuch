/**
 * @author michael
 * Refactoring: Lorenz
 */

	document.addEventListener('DOMContentLoaded', function() {

	    var ratingForm = document.getElementById('rating-form');
	    var ratingFormRadios = document.forms['rating-form'].elements['rating'];
	    var ratingResultBox = document.getElementById('current-rating');
	    var ratingCalculated = document.getElementById('rating-label');


	    // Listener für Radiobuttons (Sternchen-Bewertung)
	    for (let radio of ratingFormRadios) {
	        radio.addEventListener('change', function() {
	        	// Triggert den Listener des Formulars, submit() funktioniert dafür nicht.
	        	ratingForm.dispatchEvent(new Event('submit'));
	        })
	    }

	    // Listener für Form-Abschicken
	    ratingForm.addEventListener('submit', function(e) {
	        // Kein normales Submit Starten --> page refresh
	        e.preventDefault();

	        var formData = new FormData(ratingForm);
	        
	        // Stattdessen AJAX request
	        var xhr = new XMLHttpRequest();
	        xhr.onreadystatechange = function() {
	            if (xhr.readyState == 4 && xhr.status == 200) {
	            	var recipeRating = xhr.responseText;
	            	if(recipeRating == 0) {
	            		ratingResultBox.innerHTML = 'Das Rezept wurde noch nicht bewertet';
	            	}
	            	else {
	            		ratingCalculated.innerHTML = recipeRating;
	            		
	            	}
	            }	            
	        }
	        
	        xhr.open(ratingForm.method, ratingForm.action, true);
	        xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	        xhr.send(urlencodeFormData(formData));
	    })
	})
	
	/* CUIXIPING: https://stackoverflow.com/questions/7542586/new-formdata-application-x-www-form-urlencoded */
	var  urlencodeFormData = function (fd) {
	    var s = '';

	    function encode(s) {
	        return encodeURIComponent(s).replace(/%20/g, '+');
	    }

	    for (var entry of fd.entries()) {
	        if (typeof entry[1] == 'string') {
	            s += (s ? '&' : '') + encode(entry[0]) + '=' + encode(entry[1]);
	        }
	    }

	    return s;
	}
	
