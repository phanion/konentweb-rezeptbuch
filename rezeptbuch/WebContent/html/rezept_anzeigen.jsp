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
<title>Insert title here</title>
</head>
<body>
<h1>Rezept</h1>
<h2></h2>
</body>
</html>