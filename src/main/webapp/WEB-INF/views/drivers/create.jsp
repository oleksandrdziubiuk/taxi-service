<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create driver</title>
</head>
<body>
<h1>Create driver page</h1>
<form method="post" action="${pageContext.request.contextPath} /drivers/create">
    Please enter drivers name: <input type="text" name="name" required><br>
    Please enter drivers license number: <input type="number" name="licenceNumber" required><br>
    <button type="submit">create</button>
</form>
<form action="${pageContext.request.contextPath}/">
    <button>home page</button>
</form>
</body>
</html>
