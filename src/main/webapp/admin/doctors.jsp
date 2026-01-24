<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Manage Doctors</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/shared/body.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/admin/doctors.css">
</head>
    <jsp:include page="/components/header.jsp"/>
<body>
    <main class="main-container">

        <h1 class="page-title">Medical Staff Directory</h1>

        <c:if test="${not empty error}">
            <div id="temp-message">${error}</div>
            <a class="register position" href="${pageContext.request.contextPath}/admin/registerDoctor">Register a doctor</a></div>
        </c:if>
        <div class="card-container">
            <c:forEach items="${doctorList}" var="doctor">

                <div class="doctor-card">
                    <div class="doctor-avatar">
                        Dr.
                    </div>

                    <div class="doctor-name">Dr. ${doctor.name} ${doctor.lastName}</div>
                    <div class="doctor-gender">${doctor.gender}</div>

                    <div class="major-badge">
                            ${doctor.major}
                    </div>

                    <div class="card-actions">
                        <form action="${pageContext.request.contextPath}/admin/deleteDoctor" method="post">
                            <input name="doctorId" type="hidden" value="${doctor.id}">
                            <button type="submit" class="delete-btn">Remove Doctor</button>
                        </form>
                    </div>
                </div>
            </c:forEach>
        </div>
    </main>
</body>
    <jsp:include page="/components/footer.jsp"/>
</html>