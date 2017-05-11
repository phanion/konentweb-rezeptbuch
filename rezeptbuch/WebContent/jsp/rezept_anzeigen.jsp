<!-- Autor Flo -->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page errorPage="errorpage.jsp" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 
 <jsp:useBean id="rezept" 
 	class="bean.RezeptBean"
	scope="session">
</jsp:useBean>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="${pageContext.request.requestURI}" />

<title>Rezept erstellen</title>

</head>
<body>
<%@ includefile="fragments/nav.jspf" %>
<h1>${rezept.name}</h1>
	<c:if test="${not empty message}">
		<h2>${message}</h2>
	</c:if>
<h2>ID: ${rezept.id}</h2>
<h2>Zutaten:</h2>
${rezept.ingredientsToString()}
<h2>Beschreibung:</h2>
${rezept.description}
<h2>Vorbereitsungszeit:</h2>
${rezept.durationPreparation}
<h2>Kochzeit:</h2>
${rezept.durationCooking}
<h2>Schwierigkeitsgrad:</h2>
${rezept.difficulty}
<h2>Portionen:</h2>
${rezept.servings}
</body>
</html>