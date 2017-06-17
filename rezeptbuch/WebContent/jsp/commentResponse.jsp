<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="errorpage.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
{
	
		"id":"${comment.getId()}",
		"comment":"${comment.getComment()}",
		"author":"${comment.getAuthor().getFirstName()} ${comment.getAuthor().getLastName()}",
		"recipe":"${comment.getRecipe().getId()}"
	
}
