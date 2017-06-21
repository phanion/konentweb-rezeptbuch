<%@ taglib
	prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page
	errorPage="errorpage.jsp"
	language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<form
	action="/rezeptbuch/EditProfileServlet"
	method="post">
	<input
		type="hidden"
		name="id"
		class="id"
		value="${user.id}">
	<p>
		<label for="vorname">Vorname</label>
		<input
			type="text"
			name="vorname"
			id="vorname"
			placeholder="Ihr Vorname"
			disabled
			value="${user.firstName}">
	</p>
	<p>
		<label for="nachname">Nachname</label>
		<input
			type="text"
			name="nachname"
			id="nachname"
			placeholder="Ihr Nachname"
			disabled
			value="${user.lastName}">
	</p>
	<p>
		<label for="mail">E-Mail</label>
		<input
			type="text"
			name="mail"
			id="mail"
			placeholder="Ihre E-Mail"
			disabled
			value="${user.mail}">
	</p>
	<p>
		<label
			class="labelfortextarea"
			for="beschreibung">Profilbeschreibung</label>
		<textarea
			name="beschreibung"
			id="beschreibung"
			class="textarea-transitional"
			placeholder="Ihre Profilbeschreibung"
			readonly>${user.description}</textarea>
	</p>

	<button
		type="button"
		class="button"
		id="aendernButton"
		name="aendernButton">Ändern</button>
	<div
		id="editButtons"
		class="hidden-block">

		<button
			name="save"
			class="button"
			type="submit">Speichern</button>
		<button
			type="button"
			class="button-secondary"
			id="refreshButton"
			name="refresh"
			value="Verwerfen">Verwerfen</button>
	</div>
</form>