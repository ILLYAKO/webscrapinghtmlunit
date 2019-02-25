<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Find Jobs</title>
</head>
<body>
<h2>Find Jobs!</h2>

 <%--<form action="${pageContext.request.contextPath}/find/new" method="post">--%>
 <form action="./jobsearch/find" method="post">
  Job Title:<br>
  <input type="text" name="jobTitle" placeholder="Enter your job title" />
  <br>
  City:<br>
  <input type="text" name="city" placeholder="Enter the city" />
  <br><br>
  <input type="submit" value="Submit">
</form>

<p>If you click the "Submit" button, the data will be processed.</p>

</body>
</html>
