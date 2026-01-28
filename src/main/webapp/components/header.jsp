<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/shared/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/shared/body.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <header>
        <nav class="header">
            <div class="left-side">
                <a href="${pageContext.request.contextPath}/index.jsp">
                    <img class="logo" src="${pageContext.request.contextPath}/images/hospitalLogo.png">
                </a>
            </div>
            <div class="middle-side">
                <a href="${pageContext.request.contextPath}/about.jsp">About us</a>
                <c:choose>
                    <c:when test="${sessionScope.user.role eq 'PATIENT'}">
                        <a href="${pageContext.request.contextPath}/patient/book">Book now !</a>
                        <a href="${pageContext.request.contextPath}/patient/appointments">Your appointments</a>
                        <a href="${pageContext.request.contextPath}/patient/recipes.jsp">Your doctor recipes</a>
                        <a href="${pageContext.request.contextPath}/patient/home.jsp">Home page</a>
                    </c:when>
                    <c:when test="${sessionScope.user.role eq 'ADMIN'}">
                        <a href="${pageContext.request.contextPath}/admin/registerDoctor">Register doctor</a>
                        <a href="${pageContext.request.contextPath}/admin/doctors">View doctors</a>
                        <a href="${pageContext.request.contextPath}/admin/patients">View patients</a>
                        <a href="${pageContext.request.contextPath}/admin/home.jsp">Home page</a>
                    </c:when>
                    <c:when test="${sessionScope.user.role eq 'DOCTOR'}">
                        <a href="${pageContext.request.contextPath}/doctor/home">Your schedule</a>
                    </c:when>
                    <c:otherwise></c:otherwise>
                </c:choose>
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
