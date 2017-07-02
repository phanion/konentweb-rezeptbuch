/**
 * @author Michael
 */

	'use strict';
	
	

	var eventManagement = function () {
		var buttonSearchBar = document.getElementById("search-b");
		buttonSearchBar.addEventListener("click", toggleSearch);
		
		var buttonProfileIcon = document.getElementById("profile-b");
		buttonProfileIcon.addEventListener("mouseover", toggleProfile);
		buttonProfileIcon.addEventListener("mouseleave", waitProfile);
	}
	
	
	
	var toggleSearch = function() {

		var searchbox = document.getElementById('search-container');
		if (searchbox.classList.contains('hide')) {
			searchbox.classList.remove('hide');
		} else {
			searchbox.classList.add('hide');
		}
	}
	var toggleProfile = function() {

		var searchbox = document.getElementById('profile-container');
		if (searchbox.classList.contains('hide')) {
			searchbox.classList.remove('hide');

		} else {
			searchbox.classList.add('hide');

		}
	}
	var waitProfile = function() {
		setTimeout(toggleProfile, 1000);
	}
	
	document.addEventListener("DOMContentLoaded", eventManagement);