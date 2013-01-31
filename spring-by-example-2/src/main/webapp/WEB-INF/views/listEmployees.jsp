<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
</head>
<body>
<h1>Listing Employees</h1>
<c:forEach items="${employeeList}" var="employee">
	${employee.lastName} ${employee.firstName} |  <a href="edit?id=${employee.id}">edit with using param</a> | <a href="edit/${employee.id}/">edit with using path variable</a> | <a href="delete?id=${employee.id}" onClick="return alert('R U sure?')">delete with using param</a> | <a href="rest/view/${employee.id}.json">view JSON</a> | <a href="rest/view/${employee.id}.xml">view XML</a>  
	<br />
</c:forEach>
<a href="edit"> Add Person</a>
</body>
</html>
