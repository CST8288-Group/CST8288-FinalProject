<!DOCTYPE html>
<html>
<head>
    <title>Email Verification</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <h2>Email Verification</h2>
    <c:if test="${param.verified != null}">
        <p style="color: green;">Your email has been verified successfully. You can now log in.</p>
        <a href="login.jsp">Login</a>
    </c:if>
    <c:if test="${param.error != null}">
        <p style="color: red;">Invalid verification token.</p>
        <a href="register.jsp">Register again</a>
    </c:if>
</body>
</html>
