<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Doctor Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/shared/body.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/doctor/home.css">
</head>
<body>

<jsp:include page="/components/header.jsp"/>

<main class="main-container">

    <div class="dashboard-header">
        <h1 class="welcome-title">
            Welcome, Dr. ${doctor.lastName} <small style="font-size: 15px; color: #ccc;">(ID: ${doctor.id})</small>
        </h1>
        <p class="date-subtitle">Your Upcoming Schedule</p>
    </div>

    <c:if test="${not empty sessionScope.success}">
        <div id="success-msg">${sessionScope.success}</div>
        <c:remove var="success" scope="session"/>
    </c:if>

    <div class="agenda-container">

        <c:if test="${empty schedule}">
            <div class="empty-state">
                <h2>No appointments scheduled</h2>
                <p>Enjoy your free time, Doctor.</p>
            </div>
        </c:if>

        <c:forEach items="${schedule}" var="appt">

            <c:set var="patient" value="${patientMap[appt.patient_id]}" />

            <div class="appointment-card">

                <div class="time-column">
                    <span class="time-display">${appt.appointmentTime}</span>
                    <span class="date-display">${appt.appointmentDate}</span>
                </div>

                <div class="details-column">
                    <div class="patient-info">
                            <span class="patient-name">
                                ${patient.name} ${patient.lastName}
                            </span>
                        <div class="patient-meta">
                            <span class="meta-badge">${patient.gender}</span>
                            <span class="meta-badge">DOB: ${patient.birthDate}</span>
                        </div>
                    </div>

                    <div class="action-column">
                        <form action="${pageContext.request.contextPath}/doctor/cancel" method="post">
                            <input type="hidden" name="appointmentId" value="${appt.id}">
                            <button type="submit" class="cancel-btn">Cancel Visit</button>
                        </form>
                    </div>
                </div>
            </div>

        </c:forEach>
    </div>

</main>

<jsp:include page="/components/footer.jsp"/>

</body>
</html>