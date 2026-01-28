<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <title>Book Appointment</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/shared/body.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/patient/book.css">
</head>
    <jsp:include page="/components/header.jsp" />
<body>


    <main class="form-holder">
        <c:if test="${not empty error}">
            <div id="temp-message">${error}</div>
            <c:remove var="error" scope="session"/>
        </c:if>
        <c:if test="${not empty sessionScope.AddedMessage}">
            <div id="temp-message">${sessionScope.AddedMessage}</div>
            <c:remove var="AddedMessage" scope="session"/>
        </c:if>
        <form action="${pageContext.request.contextPath}/patient/book" method="post" onsubmit="const btn = this.querySelector('button[type=submit]'); btn.disabled = true; btn.innerText='Booking...';">
            <h2>Book Your Visit</h2>

            <label for="doctor">Choose a Doctor:</label>
            <select name="doctorId" id="doctor" required>
                <option value="" disabled selected>-- Select a Specialist --</option>

                <c:forEach items="${doctorList}" var="doc">
                    <option value="${doc.id}">
                        Dr. ${doc.name} ${doc.lastName} (${doc.major})
                    </option>
                </c:forEach>
            </select>

            <label for="date">Date:</label>
            <input name="appointmentDate" type="date" required>

            <label for="time">Time:</label>
            <input name="appointmentTime" type="time" required>

            <input type="hidden" name="patientId" value="${sessionScope.user.id}">

            <button type="submit">Confirm Booking</button>
        </form>
    </main>
    <script src="${pageContext.request.contextPath}/scripts/sharedJS.js"></script>
</body>
    <jsp:include page="/components/footer.jsp"/>
</html>