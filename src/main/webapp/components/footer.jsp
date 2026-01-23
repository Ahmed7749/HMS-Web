<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.time.Year" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/shared/footer.css">

<footer class="site-footer">
  <div class="footer-content">

    <div class="footer-section">
      <div class="footer-brand">Pure Hearts Hospital</div>
      <p class="footer-text">
        Dedicated to providing world-class healthcare with compassion and excellence. Your health is our priority.
      </p>
    </div>

    <div class="footer-section">
      <h4 class="footer-heading">Quick Links</h4>
      <div class="footer-links">
        <a href="${pageContext.request.contextPath}/index.jsp" class="footer-link">Home</a>
        <a href="${pageContext.request.contextPath}/login" class="footer-link">Login</a>
        <a href="${pageContext.request.contextPath}/signup" class="footer-link">Signup</a>
      </div>
    </div>

    <div class="footer-section">
      <h4 class="footer-heading">Contact Us</h4>
      <p class="footer-text">Riyadh, Saudi Arabia</p>
      <p class="footer-text">ahmadkmm2@gmail.com</p>
      <p class="footer-text">+966 53 503 3940</p>
    </div>

  </div>

  <div class="footer-bottom">
    &copy; <%= Year.now() %> Pure Hearts Hospital. All rights reserved.
  </div>
</footer>