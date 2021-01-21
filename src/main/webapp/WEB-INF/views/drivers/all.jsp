<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All drivers</title>
</head>
<body>
<h1>All drivers page</h1>
<table border=1>
    <tr>
        <th>id</th>
        <th>name</th>
        <th>licenseNumber</th>
        <th>delete</th>
    </tr>
    <c:forEach var="driver" items="${driver}">
        <tr>
            <td>
                <c:out value="${driver.id}"/>
            </td>
            <td>
                <c:out value="${driver.name}"/>
            </td>
            <td>
                <c:out value="${driver.licenceNumber}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath} /drivers/delete?id=${driver.id}">delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
<form action="${pageContext.request.contextPath}/">
    <button>home page</button>
</form>
</body>
</html>
