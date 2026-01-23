<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Pure Hearts Hospital</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/shared/body.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/index.css">
</head>
<body>

<jsp:include page="/components/header.jsp" />

<header class="hero-banner">
    <div class="hero-content">
        <h1 class="hero-title">Healthcare Made <br> Simple & Secure.</h1>
        <p class="hero-subtitle">Pure Hearts Hospital connects you with top-tier specialists instantly. Book appointments, manage records, and prioritize your health.</p>

        <div class="hero-actions">
            <a href="${pageContext.request.contextPath}/login" class="btn-primary">Book Now</a>
            <a href="${pageContext.request.contextPath}/signup" class="btn-outline">New Patient</a>
        </div>
    </div>

    <div class="hero-visual">
        <span class="visual-text"><img class="doctor-img" src="${pageContext.request.contextPath}/images/doctorimg.png"> </span>
    </div>
</header>

<section class="features-container">
    <div class="info-card">
        <h3 class="card-title">Expert Doctors</h3>
        <p class="card-description">We've got the best doctors with high education</p>
    </div>

    <div class="info-card">
        <h3 class="card-title">Instant Booking</h3>
        <p class="card-description">Skip the phone queues. View real-time availability and book your slot in seconds.</p>
    </div>

    <div class="info-card">
        <h3 class="card-title">Secure Data</h3>
        <p class="card-description">Your medical history is encrypted and accessible only to you and your assigned doctors.</p>
    </div>
</section>
    <jsp:include page="/components/footer.jsp"/>
</body>
</html>