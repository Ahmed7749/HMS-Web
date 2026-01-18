<%--
  Created by IntelliJ IDEA.
  User: ahmad
  Date: 1/18/2026
  Time: 5:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>header</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/shared/header.css">
</head>
<body>
    <header>
        <nav class="header">
            <div class="left-side logo">
                <a href="${pageContext.request.contextPath}/index.jsp">Lorantia hospital</a>
            </div>
            <div class="middle-side">
                <a href="${pageContext.request.contextPath}/about.jsp">About us</a>
            </div>
            <div class="right-side">
                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        <p class="welcome-message">Welcome ${user.userName} !</p>
                    </c:when>
                    <c:otherwise>
                        <p>
                            <span class="login-span">
                                <a href="${pageContext.request.contextPath}/login.jsp">Log in</a>
                            </span>
                                    /
                            <span class="signup-span">
                                <a href="${pageContext.request.contextPath}/signup">Sign up</a>
                            </span>
                        </p>
                    </c:otherwise>
                </c:choose>
            </div>
        </nav>
    </header>
</body>
</html>
