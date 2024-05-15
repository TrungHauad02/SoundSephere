<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + request.getContextPath();
%>
<html>
<head>
    <jsp:include page="link_css.jsp"/>
</head>
<body>
    <header class="header-section clearfix">
        <a href="<%=url%>/User?action=goToHome" class="site-logo">
            <img src="./assets/img/logo.png" alt="">
        </a>
        <div class="header-right">
            <div class="user-panel">
                <a href="<%=url%>/User?action=logout" class="logout">Logout</a>
            </div>
        </div>
        <ul class="main-menu">
            <li><a href="<%=url%>/User?action=goToHome">Home</a></li>
            <li><a href="<%= request.getContextPath() %>/User/artist_login" class="nav-link">Artist</a></li>
            <li><a href="<%=url%>/Search?action=goToSearch">Search</a></li>
            <li><a href="#">Playlist</a></li>
            <li><a href="<%=url%>/User?action=showAccount">Profile</a></li>
        </ul>
    </header>
    <jsp:include page="link_js.jsp"/>
</body>
</html>
