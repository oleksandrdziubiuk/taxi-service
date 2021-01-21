<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create manufacturer</title>
</head>
<body>
<h1>Create manufacturer page</h1>
<form method="post" action="${pageContext.request.contextPath} /manufacturers/create">
    Please enter manufacturers name: <input type="text" name="name" required><br>
    Please enter manufacturers country: <input type="text" name="country" required><br>
    <button type="submit">create</button>
</form>
<form action="${pageContext.request.contextPath}/">
    <button>home page</button>
</form>
</body>
</html>
