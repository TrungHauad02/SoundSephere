<%@ page import="com.example.soundsephere.model.Users" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        if (session.getAttribute("user") == null) {
            response.sendRedirect(url + "/User?action=showAccount");
        }
    %>
    <jsp:include page="../../header.jsp"/>
    <div class="container p-5">
        <div class="row " style="display: flex;justify-content: center;align-items: center;height: 100%">
            <div class="col">
                <div class="container main-profile-edit">
                    <form action="<%=url%>/User?action=updateAccount" method="post">

                        <div class="row main-profile-edit-component">
                            <div class="main-profile-edit-component-header">
                                <h3 class="label_title">Edit profile</h3>
                            </div>
                            <input name="idUser" value="<%=user.getId()%>" hidden="hidden">
                            <div class="col main-profile-edit-infor">
                                <div class="item">
                                    <label for="nameUser">Name</label>
                                    <input class="input-item" name="nameUser" id="nameUser" type="text" value="<%=user.getName()%>">
                                </div>
                                <div class="item">
                                    <label for="emailUser">Email</label>
                                    <input class="input-item" id="emailUser" type="text" name="emailUser" value="<%=user.getEmail()%>">

                                </div>
                                <div class="item" >
                                    <label for="sex">Gender</label>
                                    <div class="sex input-item" id="sex">
                                        <%
                                            String userSex = String.valueOf(user.getSex());
                                        %>
                                        <input  name="sexUser" value="MALE" id="sex-male" type="radio" <%= "MALE".equals(userSex) ? "checked" : "" %> >
                                        <label for="sex-male">Male</label>

                                        <input  name="sexUser" value="FEMALE" id="sex-female" type="radio" <%= "FEMALE".equals(userSex) ? "checked" : "" %> >
                                        <label for="sex-female">Female</label>
                                    </div>


                                </div>
                                <div class="item d-flex " style="align-items: center;">
                                    <label for="descriptionUser">Description</label>
                                    <%
                                        if (user.getDescription() == null) {
                                            user.setDescription("");
                                        }
                                    %>
                                    <textarea class="input-item" name="descriptionUser" id="descriptionUser"
                                    ><%=user.getDescription()%></textarea>
                                </div>

                                <div class="item">
                                    <label for="passwordUser" style="color: red;">Password</label>
                                    <input class="input-item" id="passwordUser" type="password" name="passwordUser" value="<%=user.getPassword()%>">

                                </div>

                            </div>
                            <div class="row main-profile-edit-button">
                                <div class="col-6">
                                    <a href="<%=url%>/User?action=showAccount" class="btn item">Cancel</a>
                                </div>
                                <div class="col-6">
                                    <button  type="submit" class="btn item">Save</button>
                                </div>

                            </div>
                        </div>

                    </form>


                </div>
            </div>
        </div>
        <jsp:include page="../../link_js.jsp"/>

        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
</body>
</html>
