<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <jsp:include page="link_css.jsp"/>
</head>
<body>
<div class="container" style="height: 100vh" >
    <div class="row " style="display: flex;justify-content: center;align-items: center;height: 100%">
        <div class="col main-login">
            <h2 class="text-center label_title">Login</h2>
            <form action="<%=url%>/User?action=login" method="post">
                <div class="m-3">
                    <label for="username" class="form-label">Username</label>
                    <input type="text" class="form-control" id="username" name="username">
                </div>
                <div class="m-3">
                    <label for="password" class="form-label">Password</label>
                    <input type="password" class="form-control" id="password" name="password">
                </div>

                <%
                    if (request.getAttribute("errorInvalid") != null)
                    {
                %>
                       <label style="color: red">Invalid username or password</label>
                <%
                    }
                %>
                <button type="submit" class="btn mt-3" style="color:white;background-color: black;display: flex;width: 50%;margin-left: 25%;justify-content: center">Login</button>
            </form>
             <p style="padding-top: 30px;">
                You don't have an account?, <a href="register.jsp" >Register</a>
            </p>


            <%
                if (request.getAttribute("alertMsg") != null) {
            %>
            <script>
                alert("<%= request.getAttribute("alertMsg") %>");
            </script>
            <%
                }
            %>

        </div>
    </div>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
</body>
</html>