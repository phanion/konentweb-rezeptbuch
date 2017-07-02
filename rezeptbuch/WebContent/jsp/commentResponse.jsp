<%-- Autor: Lorenz --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="errorpage.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
{
		"id":"${comment.id}",
		"comment":"${comment.comment}",
		"author":"${comment.author.firstName} ${comment.author.lastName}",
		"recipe":"${comment.recipe.id}"
}
