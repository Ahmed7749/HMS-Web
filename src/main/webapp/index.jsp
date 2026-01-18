<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/shared/body.css">
</head>
<body>
<jsp:include page="/components/header.jsp" />
<h1><%= "Hello World!" %>
</h1>
<br/>
<a href="login.jsp">Login</a>
<a href="signup.jsp">sign up</a>
<a href="patient/home.jsp">p-home</a>
</body>
</html>