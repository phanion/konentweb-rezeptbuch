<!-- Refactoring: Lorenz, Flo -->

<%@ taglib 
	prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page 
	errorPage="errorpage.jsp" 
	language="java"
	contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>
	
	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<c:choose>
	<c:when test="${empty user}">
		<c:redirect url="login.jsp" />
	</c:when>
</c:choose>
<base href="${pageContext.request.requestURI}" />

<title>Profil</title>

<script src="${pageContext.request.contextPath}/js/nav.js"></script>
<script src="${pageContext.request.contextPath}/js/editProfile.js"></script>


<link rel="stylesheet" href="${pageContext.request.contextPath}/main.css">
</head>

<body>
	<%@ include file="./fragments/nav.jspf" %>
	
	<main>
	<h1>Dein Profil</h1>
	<c:if test="${not empty message}">
		<h2>${message}</h2>
	</c:if>
	<form action="/rezeptbuch/EditProfileServlet" method="post">
		<input
			type="hidden"
			name="id"
			class="id"
			value="${user.getId()}">
		<p>
			<label 
				for="vorname">Vorname</label> 			
			<input 
				type="text"
				name="vorname" 
				id="vorname" 
				placeholder="Ihr Vorname" 
				disabled
				value="${user.getFirstName()}">
		</p>
		<p>
			<label 
				for="nachname">Nachname</label> 
			<input 
				type="text"
				name="nachname" 
				id="nachname" 
				placeholder="Ihr Nachname" 
				disabled
				value="${user.getLastName()}">
		</p>
		<p>
			<label 
				for="mail">E-Mail</label> 
			<input 
				type="text" 
				name="mail"
				id="mail" 
				placeholder="Ihre E-Mail" 
				disabled
				value="${user.getMail()}">
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
				disabled>
				${'Profilbeschreibung: Lorem ipsum dolor sit amet.'}
			</textarea>
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
				class="button"
				id="refreshButton"
				name="refresh"
				value="Verwerfen">Verwerfen</button>
		</div>
		
	</form>
	</main>
	<footer></footer>
</body>
</html>