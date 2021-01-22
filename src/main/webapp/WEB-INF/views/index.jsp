<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Hello world!</h1>

<form action="${pageContext.request.contextPath}/manufacturers/create">
<button>create manufacturer</button>
</form>

<form action="${pageContext.request.contextPath}/cars/create">
    <button>create car</button>
</form>

<form action="${pageContext.request.contextPath}/drivers/create">
    <button>create driver</button>
</form>

<form action="${pageContext.request.contextPath}/cars/drivers/add">
    <button>add driver to car</button>
</form>

<form action="${pageContext.request.contextPath}/drivers">
    <button>get all drivers</button>
</form>

</body>
</html>
