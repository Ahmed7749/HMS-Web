<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/shared/body.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/admin/adminHome.css">
</head>
<body>

<jsp:include page="/components/header.jsp" />

<main class="admin-container">

    <section class="stats-row">
        <div class="stat-card">
            <div class="stat-number">ACTIVE</div>
            <div class="stat-label">System Status</div>
        </div>
    </section>

    <section class="dashboard-grid">

        <div class="card card-blue">
            <h2>Manage Doctors</h2>
            <p>View the list of all doctors, check their specialties, or remove them from the system.</p>
            <a href="${pageContext.request.contextPath}/admin/doctors" class="btn">View List</a>
        </div>

        <div class="card card-green">
            <h2>Register Doctor</h2>
            <p>Onboard a new specialist to the hospital team. Create credentials and assign departments.</p>
            <a href="${pageContext.request.contextPath}/admin/registerDoctor" class="btn">Add New</a>
        </div>

        <div class="card card-red">
            <h2>Manage Patients</h2>
            <p>Oversee registered patients. Search for records and manage user accounts.</p>
            <a href="${pageContext.request.contextPath}/admin/patients" class="btn">View Patients</a>
        </div>

    </section>

</main>

<jsp:include page="/components/footer.jsp" />

</body>
</html>