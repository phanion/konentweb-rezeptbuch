<!-- Autor Flo -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
 <jsp:useBean id="rezept" 
 	class="bean.RezeptBean"
	scope="session">
</jsp:useBean>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="${pageContext.request.requestURI}" />

<title>Rezept erstellt</title>

</head>
<body>
<h1>Rezept ${rezept.rezept} wurde erstellt!</h1>
<h2>ID: ${rezept.id}</h2>
<h2>Zutaten:</h2>
${rezept.zutaten}
<h2>Beschreibung:</h2>
${rezept.beschreibung}
<h2></h2>
</body>
</html>