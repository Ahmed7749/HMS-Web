<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>About Us - Pure Hearts Hospital</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/shared/body.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/about.css">
</head>
    <jsp:include page="/components/header.jsp" />
<body>



<div class="about-page-wrapper">

    <section class="about-hero-section">
        <h1 class="about-title">Who We Are</h1>
        <p class="about-subtitle">Pure Hearts Hospital was founded on a simple premise: care that comes from the heart. We combine cutting-edge medical technology with compassionate human connection.</p>
    </section>

    <section class="mission-vision-container">
        <div class="text-block">
            <h2 class="block-heading">Our Mission</h2>
            <p>To improve the health and well-being of the communities we serve by providing accessible, high-quality medical care driven by innovation and empathy.</p>
        </div>
        <div class="text-block" style="border-color: #007bff;">
            <h2 class="block-heading">Our Vision</h2>
            <p>To be the region's most trusted healthcare partner, setting the standard for patient safety, clinical excellence, and medical education.</p>
        </div>
    </section>

    <section class="stats-bar">
        <div class="stat-item">
            <span class="stat-number">50+</span>
            <span class="stat-label">Specialist Doctors</span>
        </div>
        <div class="stat-item">
            <span class="stat-number">10k+</span>
            <span class="stat-label">Happy Patients</span>
        </div>
        <div class="stat-item">
            <span class="stat-number">24/7</span>
            <span class="stat-label">Emergency Care</span>
        </div>
    </section>

    <section class="team-section">
        <h2 class="block-heading">Leadership Team</h2>
        <div class="team-grid">
            <div class="team-card">
                <div class="member-role">Chief Medical Officer</div>
                <div class="member-name">Dr. Sarah khalid</div>
                <p>Leading our clinical strategy with over 20 years of experience in cardiology.</p>
            </div>
            <div class="team-card">
                <div class="member-role">Head of Surgery</div>
                <div class="member-name">Dr. Ahmed khalid</div>
                <p>Pioneering minimally invasive surgical techniques for faster patient recovery.</p>
            </div>
            <div class="team-card">
                <div class="member-role">Patient Experience Director</div>
                <div class="member-name">Waleed khalid</div>
                <p>Ensuring every patient feels heard, respected, and comfortable during their stay.</p>
            </div>
        </div>
    </section>

</div>
</body>
    <jsp:include page="/components/footer.jsp" />
</html>