<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 
	uri="http://java.sun.com/jsp/jstl/core"%>
[
<c:forEach var="rezept" items="${rezepte}" varStatus="status">
	{
		"id":${rezept.id},
		"name":"${rezept.name}",
		"durPrep":${rezept.durationPreparation},
		"durCook":${rezept.durationCooking},
		"difficulty":${rezept.difficulty}
	}<c:if test="${not status.last}">,</c:if>
</c:forEach>
]