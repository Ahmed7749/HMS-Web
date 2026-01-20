<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Home</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/shared/body.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/patient/home.css">
</head>
<body>
    <jsp:include page="/components/header.jsp" />
    <div class="dashboard-grid">
        <div class="card action-card">
            <h2>Book Appointment</h2>
            <p>Find a doctor and schedule a visit.</p>
            <a href="${pageContext.request.contextPath}/patient/book" class="btn">Book Now</a>
        </div>
        <div class="card info-card">
            <h2>My Appointments</h2>
            <p>View upcoming visits and history.</p>
            <a href="${pageContext.request.contextPath}/patient/appointments.jsp" class="btn">View All</a>
        </div>
        <div class="card info-card">
            <h2>Prescriptions</h2>
            <p>View your medical recipes and meds.</p>
            <a href="${pageContext.request.contextPath}/patient/recipes.jsp" class="btn">View Details</a>
        </div>
    </div>
</body>
</html>
