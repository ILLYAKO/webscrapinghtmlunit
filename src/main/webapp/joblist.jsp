
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Job List</title>
</head>
<body>
<h2>Job List</h2>

${item.city}

<p>If you click the "Submit" button, the data will be processed.</p>
<c:forEach var="car" items="${listCar}">
    <tr>
        <td><c:out value="${car.plate}" /></td>
        <td><c:out value="${car.brand}" /></td>
        <td><c:out value="${car.year}" /></td>
        <td><c:out value="${car.color}" /></td>
        <td>
        <a href="${pageContext.request.contextPath}/car/edit?id=<c:out value='${car.id}' />">Edit</a>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <a href="${pageContext.request.contextPath}/car/delete?id=<c:out value='${car.id}' />">Delete</a>
        </td>
    </tr>
</c:forEach>

</body>
</html>
