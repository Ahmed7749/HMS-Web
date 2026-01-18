
<%--
  Created by IntelliJ IDEA.
  User: ahmad
  Date: 1/18/2026
  Time: 1:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="styles/shared/body.css">
    <link rel="stylesheet" href="styles/login.css">
    <title>Log in page</title>
</head>
<body>
    <div class="login-card">

        <form action="login" method="post">
            <h1>Log in</h1>
            <c:if test="${not empty error}">
                <div id="temp-message" style="color: red; text-align: center; margin-bottom: 10px;">
                        ${error}
                </div>
            </c:if>
            <label for="name">Username</label>
            <input name="username"type="text" placeholder="Enter your username" required>
            <label for="password">Password</label>
            <input name="password" type="password" placeholder="enter password" required>
            <input type="submit">
        </form>
    </div>
    <script src="scripts/sharedJS.js"></script>
</body>
</html>
