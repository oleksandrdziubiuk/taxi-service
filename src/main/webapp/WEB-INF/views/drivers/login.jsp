<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login page</title>
</head>
<body>
<h3 style="color: red">${errorMsg}</h3>
<h1>Login page</h1>
<form method="post" action="${pageContext.request.contextPath}/drivers/login">
    Please enter your name: <input type="text" name="login" required><br>
    Please enter your password: <input type="password" name="password" required><br>
    <button type="submit">login</button>
</form>
<form action="${pageContext.request.contextPath}/drivers/create">
    <button>create driver</button>
</form>
</body>
</html>
