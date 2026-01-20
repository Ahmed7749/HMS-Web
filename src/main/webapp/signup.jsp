<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>sign up page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/signup.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/shared/body.css">
</head>
<body>
    <jsp:include page="/components/header.jsp" />
    <div class="signup-card">

        <form action="signup" method="post">
            <h1>Sign up</h1>
            <c:if test="${not empty error}">
                <div id="temp-message" style="color: red; text-align: center; margin-bottom: 10px;">
                        ${error}
                </div>
            </c:if>
            <div class="account-info">
                <p>Account info: </p>
                <input name="username" type="text" placeholder="Enter your username" required>
                <input name="password" type="password" placeholder="Enter password" required>
            </div>
            <div class="personal-info">
                <p>Personal Info: </p>
                <input name="name" type="text" placeholder="Enter your first name" required>
                <input name="lastName" type="text" placeholder="Enter your last name" required>
                <input name="middleName" type="text" placeholder="Enter your middle name" required>
                <input name="birthdate" type="date" placeholder="Enter your birth date" required>
                <div class="gender-info">
                    <label for="gender">Male</label>
                    <input name="gender" type="radio" value="MALE">
                    <label for="gender">Female</label>
                    <input name="gender" type="radio" value="FEMALE">
                </div>
            </div>
            <input type="submit" class="submit-button">
            <div><small>Already have an account ? <a href="login.jsp">Log in !</a></small></div>
        </form>
    </div>
    <script src="scripts/sharedJS.js"></script>
</body>
</html>
