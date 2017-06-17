/**
 * 
 */
	/* https://stackoverflow.com/questions/7542586/new-formdata-application-x-www-form-urlencoded */
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
	
	document.addEventListener('DOMContentLoaded', function() {

	    var ratingForm = document.getElementById('rating-form');
	    var ratingFormRadios = document.forms['rating-form'].elements['rating'];
	    
	    var ratingResultBox = document.getElementById('current-rating');

	    for (let radio of ratingFormRadios) {
	        radio.addEventListener('change', function() {

	        	// Triggers the listener of the form
	        	ratingForm.dispatchEvent(new Event('submit'));
	        })
	    }


	    ratingForm.addEventListener('submit', function(e) {
	        // Don't fire normal submit --> leads to page refresh
	        e.preventDefault();

	        var formData = new FormData(ratingForm);
	        
	        // Instead make AJAX request
	        var xhr = new XMLHttpRequest();
	        xhr.onreadystatechange = function() {
	            if (xhr.readyState == 4 && xhr.status == 200) {
	            	var recipeRating = xhr.responseText;
	            	if(recipeRating == 0) {
	            		ratingResultBox.innerHTML = '<pre>Das Rezept wurde noch nicht bewertet</pre>';
	            	}
	            	else {
	            		ratingResultBox.innerHTML = '<pre>Die aktuelle Rezeptbewertung ist: ' + recipeRating + '</pre>';
	            	}
	            }	            
	        }
	        
	        xhr.open(ratingForm.method, ratingForm.action, true);
	        xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	        xhr.send(urlencodeFormData(formData));
	    })
	})
