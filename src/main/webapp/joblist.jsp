
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
<c:forEach var="jobitem" items="${items}">
    <tr>
        <td><c:out value="${jobitem.title}" /></td>
        <td><c:out value="${jobitem.company}" /></td>
        <td><c:out value="${jobitem.city}" /></td>
        <td><c:out value="${jobitem.url}" /></td>
        <td>

        </td>
    </tr>
</c:forEach>

</body>
</html>
