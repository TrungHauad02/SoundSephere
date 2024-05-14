<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
    String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + request.getContextPath();
%>
<head>
    <title>Title</title>
</head>
<body>
    <form action = "<%=path%>/Song" method="get">
        <input type = "hidden" name = "action" value="getSong">
        <input type="text" name="idSong" value="12">
        <button> Select Music </button>
    </form>
</body>
</html>
