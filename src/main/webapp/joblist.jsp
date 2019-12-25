
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Job List</title>
    </head>
    <body>
        <h2>Job List:</h2>
        <p>${jobItems.size()} results for "${item.title}" in the city of ${item.city}.</p>

        <c:forEach var="jobItem" items="${jobItems}">
            <c:set var="count" value="${count + 1}" scope="page"/>
            <a href="${jobItem.url}">  ${count } - ${jobItem.title}</a><br>
        </c:forEach>
    </body>
</html>
