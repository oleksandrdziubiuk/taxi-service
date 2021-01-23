<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My cars</title>
</head>
<body>
<h1>My cars page</h1>
<table border=1>
    <tr>
        <th>id</th>
        <th>model</th>
        <th>manufacturer</th>
        <th>driver</th>
    </tr>
    <c:forEach var="car" items="${cars}">
        <tr>
            <td>
                <c:out value="${car.id}"/>
            </td>
            <td>
                <c:out value="${car.model}"/>
            </td>
            <td>
                <c:out value="${car.manufacturer}"/>
            </td>
            <td>
                <c:out value="${car.drivers}"/>
            </td>
        </tr>
    </c:forEach>
</table>
<form action="${pageContext.request.contextPath}/">
    <button>home page</button>
</form>
</body>
</html>
