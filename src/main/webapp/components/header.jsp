<%@ page contentType="text/html;charset=UTF-8"%>
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
                <a href="${pageContext.request.contextPath}/index">Lorantia hospital</a>
            </div>
            <div class="middle-side">
                <a href="${pageContext.request.contextPath}/about.jsp">About us</a>
                <c:if test="${not empty sessionScope.user}">
                    <a href="${pageContext.request.contextPath}/patient/book">Book now !</a>
                    <a href="${pageContext.request.contextPath}/patient/appointments">Your appointments</a>
                    <a href="${pageContext.request.contextPath}/patient/recipes">Your doctor recipes</a>
                </c:if>
            </div>
            <div class="right-side">
                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        <p class="welcome-message"><span>Welcome ${user.userName}</span>!</p>
                        <a class="logout-click" href="${pageContext.request.contextPath}/logout">Logout</a>
                    </c:when>
                    <c:otherwise>
                        <p>
                            <span class="login-span">
                                <a class="login-click" href="${pageContext.request.contextPath}/login">Log in</a>
                            </span>
                                    /
                            <span class="signup-span">
                                <a class="signup-click" href="${pageContext.request.contextPath}/signup">Sign up</a>
                            </span>
                        </p>
                    </c:otherwise>
                </c:choose>
            </div>
        </nav>
    </header>
</body>
</html>
