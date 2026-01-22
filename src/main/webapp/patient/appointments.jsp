<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Appointments</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/patient/appointments.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/shared/body.css">
</head>
<body>
    <jsp:include page="/components/header.jsp" />
    <main class="container">
        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>
        <div class="card-container">
            <c:forEach items="${appointmentList}" var="appointment">
                <div class="appointmentCard">
                    <p class="appointment-date">Appointment date: ${appointment.date}</p>
                    <p class="appointment-time">Appointment time: ${appointment.time}</p>
                    <p class="appointment-doctor">Appointment doctor: ${appointment.doctorName} ${appointment.doctorLastName}</p>
                    <p class="doctor-major">Doctor major: ${appointment.doctorMajor.toLowerCase()}</p>
                    <button class="delete-btn">Cancel Appointment ):</button>
                </div>
            </c:forEach>
        </div>
    </main>
</body>
</html>
