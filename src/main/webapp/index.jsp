<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sound Sphere</title>
    <jsp:include page="link_css.jsp"/>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <%
        Users user = null;
        if (session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
        } else if (session.getAttribute("user") != null) {
            user = (Users) session.getAttribute("user");
            session.setAttribute("user", user);
        }
    %>

    <jsp:include page="link_js.jsp"/>
</body>
</html>