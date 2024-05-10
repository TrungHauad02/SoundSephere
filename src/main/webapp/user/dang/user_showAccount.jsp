<%@ page import="com.example.soundsephere.model.Users" %>
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
    <jsp:include page="../../link_css.jsp"/>
</head>
<body style="height: 80vh">
    <jsp:include page="../../header.jsp"/>
    <%
        Users user = null;
        if (session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
        } else if (session.getAttribute("user") != null) {
            user = (Users) session.getAttribute("user");
            session.setAttribute("user", user);
        }
    %>
    <%
        String error = (String) request.getAttribute("editProfileMessage");
        if (error != null) {
    %>
        <script>
            alert("<%=error%>");
        </script>
    <%
        }

    %>
    <div class="container p-5" >
        <div class="row " style="display: flex;justify-content: center;align-items: center;height: 100%">
            <div class="col">
                <div class="container main-profile">
                    <div class="row edit-profile">
                        <a href="<%=url%>/User?action=editAccount"  style="text-decoration-line: none;color: black">
                            <img  src="https://simpleicon.com/wp-content/uploads/pencil1.png" alt="">
                            Edit
                        </a>
                    </div>

                    <div class="row item-profile">
                        <img class="img-profile" src="https://cdn-icons-png.flaticon.com/512/3607/3607444.png" alt="">
                    </div>
                    <div class="row item-profile">
                        <h2><%=user.getName()%></h2>
                    </div>
                    <div class="row item-profile " >
                        <div class="col-6 item-profile-p">
                            <p>Playlist: 1</p>
                        </div>
                        <div class="col-6 item-profile-p">
                            <p >Like: 1</p>
                        </div>

                    </div>

                </div>
            </div>
        </div>

        <jsp:include page="../../link_js.jsp"/>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
</body>
</html>



