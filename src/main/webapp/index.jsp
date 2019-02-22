<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Find Jobs</title>
</head>
<body>
<h2>Find Jobs!</h2>

 <form action="find" method="get">
  Job Title:<br>
  <input type="text" name="jobTitle" value="<c:out value='${job.title}' />" />
  <br>
  City:<br>
  <input type="text" name="cityName" value="<c:out value='${job.city}' />" />
  <br><br>
  <input type="submit" value="Submit">
</form>

<p>If you click the "Submit" button, the data will be processed.</p>

</body>
</html>
