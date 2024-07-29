<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Email Verification</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h2>Email Verification</h2>
        <c:if test="${param.verified != null}">
            <div class="alert alert-success">
                <p>Your email has been verified successfully. You can now <a href="login.jsp">log in</a>.</p>
            </div>
        </c:if>
        <c:if test="${param.error != null}">
            <div class="alert alert-danger">
                <p>Invalid verification token.</p>
                <a href="register.jsp">Register again</a>
            </div>
        </c:if>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
