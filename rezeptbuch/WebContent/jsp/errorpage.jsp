<%-- Autor: Michael 
	http://www.jsptutorial.org/content/errorHandling --%>

<%@ page errorPage="true" import="java.io.*"  language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
	
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Fehlerseite</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/errorpage.css">
</head>
<body>
<h1>Es ist ein Fehler aufgetreten!</h1>
<strong>Message:</strong> ${pageContext.exception.message}

<br>
<strong>Statuscode:</strong> ${pageContext.errorData.statusCode}
<strong>Request-URI:</strong> ${pageContext.errorData.requestURI}
<strong>Servletname:</strong> ${pageContext.errorData.servletName}
<strong>Exception:</strong> ${pageContext.errorData.throwable}

<br>
<strong>Stacktrace:</strong> ${pageContext.exception.stackTrace}

</body>
</html>