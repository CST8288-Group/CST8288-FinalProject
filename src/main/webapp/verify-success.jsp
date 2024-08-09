<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Email Verification Success</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        html, body {
            height: 100%;
            margin: 0;
            overflow: hidden; /* Prevents scrolling */
        }

        .background-video {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            object-fit: cover; /* Ensures the video covers the entire screen */
            z-index: -1; /* Places the video behind the content */
        }
        .container {
            background-color: rgba(255, 255, 255, 0.8); /* Optional: Adds a white background with opacity to the form */
            padding: 20px;
            border-radius: 10px;
        }

        .wrapper {
            height: 100%;
            display: flex;
            justify-content: center;
            align-items: center;
        }
    </style>
</head>
<body>
    <video autoplay muted loop class="background-video">
        <source src="2.webm" type="video/webm">
        Your browser does not support the video tag.
    </video>
    <div class="container mt-5">
        <div class="alert alert-success">
            <h1>Email Verification Successful</h1>
            <p>Your email address has been successfully verified. You can now <a href="login.jsp">log in</a> to your account.</p>
            <p>If you encounter any issues, please contact our support team.</p>
        </div>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
