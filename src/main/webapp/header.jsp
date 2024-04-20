<%--
  Created by IntelliJ IDEA.
  User: Asus ROG
  Date: 20/04/2024
  Time: 1:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="link_css.jsp"/>
</head>
<body>
    <header class="header-section clearfix">
        <a href="./index.jsp" class="site-logo">
            <img src="./assets/img/logo.png" alt="">
        </a>
        <div class="header-right">
            <div class="user-panel">
                <a href="" class="logout">Logout</a>
            </div>
        </div>
        <ul class="main-menu">
            <li><a href="index.jsp">Home</a></li>
            <li><a href="#">Artist</a></li>
            <li><a href="#">Search</a></li>
            <li><a href="#">Playlist</a></li>
            <li><a href="#">Profile</a></li>
        </ul>
    </header>
<jsp:include page="link_js.jsp"/>
</body>
</html>
