<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Issue Prescription</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/shared/body.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/doctor/prescribe.css">
</head>
<body>
<jsp:include page="/components/header.jsp"/>

<main class="main-container">
  <div class="form-card">
    <div class="form-header">
      <h2>New Prescription</h2>
      <p>Patient ID: ${param.patientId}</p>
    </div>

    <form action="${pageContext.request.contextPath}/doctor/prescribe" method="POST">
      <input type="hidden" name="doctor_id" value="${param.doctorId}">
      <input type="hidden" name="patient_id" value="${param.patientId}">

      <div class="form-group">
        <label for="medication_name">Medication Name</label>
        <input type="text" id="medication_name" name="medication_name" required placeholder="e.g. Amoxicillin">
      </div>

      <div class="form-group">
        <label for="dosage">Dosage</label>
        <input type="text" id="dosage" name="dosage" required placeholder="e.g. 500mg">
      </div>

      <div class="form-group">
        <label for="instructions">Instructions</label>
        <textarea id="instructions" name="instructions" rows="4" placeholder="e.g. Take twice daily after meals"></textarea>
      </div>

      <div class="form-actions">
        <button type="submit" class="submit-btn">Issue Prescription</button>
        <a href="${pageContext.request.contextPath}/doctor/home" class="back-link">Cancel</a>
      </div>
    </form>
  </div>
</main>

<jsp:include page="/components/footer.jsp"/>
</body>
</html>