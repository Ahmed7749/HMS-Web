<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Register Doctor</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/shared/body.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/admin/registerDoctor.css">
</head>
<body>
<jsp:include page="/components/header.jsp" />

<div class="register-card">
    <form action="${pageContext.request.contextPath}/admin/registerDoctor" method="post">
        <h1>Register Doctor</h1>

        <c:if test="${not empty sessionScope.error}">
            <div id="temp-message" style="color: red; text-align: center; margin-bottom: 10px;">
                    ${sessionScope.error}
            </div>
            <c:remove var="error" scope="session"/>
        </c:if>
        <c:if test="${not empty sessionScope.success}">
            <div id="temp-message" style="color: green; text-align: center; margin-bottom: 10px;">
                    ${sessionScope.success}
            </div>
            <c:remove var="success" scope="session"/>
        </c:if>

        <div class="account-info">
            <p>Account Info:</p>
            <input name="username" type="text" placeholder="Username" required>
            <input name="password" type="password" placeholder="Password" required>
        </div>

        <div class="personal-info">
            <p>Doctor Details:</p>
            <input name="name" type="text" placeholder="First Name" required>
            <input name="lastName" type="text" placeholder="Last Name" required>

            <select name="major" required>
                <option value="" disabled selected>Select Specialty</option>
                <option value="SURGEON">Surgeon</option>
                <option value="GENERAL">General</option>
            </select>

            <div class="gender-info">
                <input type="radio" id="male" name="gender" value="MALE" required>
                <label for="male" class="gender-label">Male</label>

                <input type="radio" id="female" name="gender" value="FEMALE">
                <label for="female" class="gender-label">Female</label>
            </div>
        </div>

        <input type="submit" class="submit-button" value="Register">

        <div><small><a href="${pageContext.request.contextPath}/admin/home.jsp">Cancel</a></small></div>
    </form>
</div>

<script src="${pageContext.request.contextPath}/scripts/sharedJS.js"></script>
<jsp:include page="/components/footer.jsp"/>
</body>
</html>