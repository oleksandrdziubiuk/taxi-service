<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create car</title>
</head>
<body>
<h1>Create car page</h1>
<form method="post" action="${pageContext.request.contextPath}/cars/create">
    Please enter cars model: <input type="text" name="model" required><br>
    Please enter cars manufacturer id: <input type="number" name="manufacturer" required><br>
    <button type="submit">create</button>
</form>
<form action="${pageContext.request.contextPath}/">
    <button>home page</button>
</form>
</body>
</html>
