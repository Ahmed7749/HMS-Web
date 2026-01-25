<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Manage Patients</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/shared/body.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/admin/patients.css">
</head>
<body>
<jsp:include page="/components/header.jsp"/>

<main class="main-container">

    <h1 class="page-title">Patient Records Directory</h1>

    <c:if test="${not empty error}">
        <div id="temp-message">${error}</div>
    </c:if>

    <div class="patient-list">
        <c:forEach items="${patientList}" var="patient">

            <div class="patient-row">

                <div class="patient-info">
                    <div class="patient-avatar">
                        P
                    </div>

                    <div class="patient-details">
                        <div class="patient-name">
                                ${patient.name} ${patient.lastName}
                        </div>

                        <c:set var="userAccount" value="${userMap[patient.id]}" />

                        <div class="patient-meta">
                            <span class="meta-item">Gender: ${patient.gender}</span>
                            <span class="meta-item">Born: ${patient.birthDate}</span>
                        </div>
                        <div class="patient-user-details">
                            <c:if test="${not empty userAccount}">
                                <span class="username">Username: ${userAccount.userName}</span>
                                <span class="password-text">Password: ${userAccount.password}</span>
                            </c:if>
                            <c:if test="${empty userAccount}">
                                <span style="color: #dc3545;">No Account</span>
                            </c:if>
                        </div>
                    </div>
                </div>

                <div class="action-area">
                    <form action="${pageContext.request.contextPath}/admin/deletePatient" method="post">
                        <input name="patientId" type="hidden" value="${patient.id}">
                        <button type="submit" class="delete-btn">Discharge Patient</button>
                    </form>
                </div>

            </div>

        </c:forEach>
    </div>

</main>

<jsp:include page="/components/footer.jsp"/>

</body>
</html>