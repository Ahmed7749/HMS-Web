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
            <div id="temp-message">${error}</div>
            <a id="book-now" href="${pageContext.request.contextPath}/patient/book">Book Now !</a>
        </c:if>
        <div class="card-container">
            <c:forEach items="${appointmentList}" var="appointment">
                <div class="appointmentCard">
                    <p class="appointment-date">Appointment date: ${appointment.date}</p>
                    <p class="appointment-time">Appointment time: ${appointment.time}</p>
                    <p class="appointment-doctor">Appointment doctor: ${appointment.doctorName} ${appointment.doctorLastName}</p>
                    <p class="doctor-major">Doctor major: ${appointment.doctorMajor.toLowerCase()}</p>
                    <form action="${pageContext.request.contextPath}/patient/cancel" method="post">
                        <input type="hidden" name="appointmentId" value="${appointment.appointmentId}">
                        <button type="submit" class="delete-btn">Cancel Appointment ):</button>
                    </form>
                </div>
            </c:forEach>
        </div>
    </main>
    <script src="${pageContext.request.contextPath}/scripts/sharedJS.js"></script>
</body>
</html>
