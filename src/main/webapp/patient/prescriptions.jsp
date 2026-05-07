<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>My Prescriptions</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/shared/body.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/patient/prescriptions.css">
</head>
<body>
<jsp:include page="/components/header.jsp" />

<main class="main-container">




    <div class="prescriptions-grid">
        <c:if test="${empty prescriptions}">
            <div class="temp-message">
                <h3>No Active Prescriptions</h3>
                <p>You currently do not have any active medications or instructions from your doctors.</p>
            </div>
        </c:if>
        <c:forEach var="prescription" items="${prescriptions}">
            <c:set var="doctor" value="${doctorMap[prescription.id]}" />

            <div class="prescription-card">
                <div class="prescription-header">
                    <h3 class="med-name">${prescription.medicationName}</h3>
                    <span class="med-dosage">Dosage: ${prescription.dosage}</span>
                </div>

                <div class="prescription-body">
                    <div class="instruction-block">
                        <strong>Instructions:</strong>
                        <p>${prescription.instructions}</p>
                    </div>
                </div>

                <div class="prescription-footer">
                    <span class="doctor-name">Prescribed by: Dr. ${doctor.lastName}</span>
                </div>
            </div>
        </c:forEach>
    </div>
</main>

<jsp:include page="/components/footer.jsp"/>
<script src="${pageContext.request.contextPath}/scripts/sharedJS.js"></script>
</body>

</html>