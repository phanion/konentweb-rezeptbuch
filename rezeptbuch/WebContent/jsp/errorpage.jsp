<%@ page isErrorPage="true" import="java.io.*" contentType="text/plain"%>

Es ist ein Fehler aufgetreten! Die Fehlermeldung lautet:
<%=exception.getMessage()%>

StackTrace:
<%
	StringWriter stringWriter = new StringWriter();
	PrintWriter printWriter = new PrintWriter(stringWriter);
	exception.printStackTrace(printWriter);
	out.println(stringWriter);
	printWriter.close();
	stringWriter.close();
%>