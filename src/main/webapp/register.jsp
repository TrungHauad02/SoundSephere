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
            <div class="col main-register">
                <h2 class="label_title">Register</h2>
                <form action="<%=url%>/User?action=register" method="POST" style="width: 80%">
                    <div class="m-2" >
                        <label for="name" class="form-label">Full name</label>
                        <input type="text" class="form-control" id="name" name="nameUser">
                    </div>
                    <div class="m-2">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" class="form-control" id="email" name="emailUser">
                    </div>
                    <div class="m-2">
                        <label for="username" class="form-label">Username</label>
                        <input type="text" class="form-control" id="username" name="usernameUser">
                    </div>
                    <%
                        if (request.getAttribute("errorExist") != null) {
                    %>
                    <label for="confirmPasswordUser" style="color: red">Username or Email already exists</label>
                    <%
                        }
                    %>
                    <div class="m-2">
                        <label for="password" class ="form-label">Password</label>
                        <input type="password" class="form-control" id="password" name="passwordUser">
                    </div>
                    <div class="m-2">
                        <label for="confirmPasswordUser" class="form-label">Confirm Password</label>
                        <input type="password" class="form-control" id="confirmPasswordUser" name="confirmPasswordUser">
                    </div>

                    <%
                        if (request.getAttribute("errorConfirm") != null) {
                    %>
                        <label for="confirmPasswordUser" style="color: red">Password and Confirm Password are not the same! </label>
                    <%
                        }
                    %>

                    <div class="m-2 form-check">
                        <input type="checkbox" value="checked" class="form-check-input" id="checkArtist" name="checkArtist">
                        <label class="form-check-label" for="checkArtist">Artist</label>
                    </div>
                    <button type="submit" class="btn mt-3" style="color:white;background-color: black;display: flex;width: 50%;margin-left: 25%;justify-content: center">Register</button>
                </form>
                <p style="padding-top: 20px;">
                    You already have an account?, <a href="login.jsp">Login</a>
                </p>

            </div>
        </div>
    </div>



    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
</body>
</html>