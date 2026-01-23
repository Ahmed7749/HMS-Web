<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Recipes</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/patient/recipes.css">
</head>
<body>
    <jsp:include page="/components/header.jsp" />
    <main class="main-container">
        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>
        <div class="recipe-container">
            Coming soon (:
        </div>
    </main>
</body>
    <jsp:include page="/components/footer.jsp"/>
</html>