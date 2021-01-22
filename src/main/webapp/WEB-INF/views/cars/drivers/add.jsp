<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add driver to car</title>
</head>
<body>
<h1>Add driver to car page</h1>
<form method="post" action="${pageContext.request.contextPath}/cars/drivers/add">
    Please enter driver id: <input type="number" name="driverId" required><br>
    Please enter car id: <input type="number" name="carId" required><br>
    <button type="submit">add</button>
</form>
<form action="${pageContext.request.contextPath}/">
    <button>home page</button>
</form>
</body>
</html>
