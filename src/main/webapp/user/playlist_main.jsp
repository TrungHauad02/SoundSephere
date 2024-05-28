<%--
  Created by IntelliJ IDEA.
  User: Asus ROG
  Date: 29/04/2024
  Time: 5:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Playlist</title>
    <jsp:include page="../link_css.jsp"/>
    <style>
        ::-webkit-scrollbar {
            width: 10px; /* Chiều rộng của scrollbar */
        }

        /* Thiết lập màu nền của scrollbar */
        ::-webkit-scrollbar-track {
            background: #f1f1f1;
        }

        /* Thiết lập màu của thanh cuộn (thumb) */
        ::-webkit-scrollbar-thumb {
            background: #009309;
            border-radius: 5px;
        }

        /* Thiết lập hover cho thanh cuộn (thumb) */
        ::-webkit-scrollbar-thumb:hover {
            background: #1e52c8;
        }
        .popup {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5); /* Màu nền đen nhẹ */
            z-index: 999; /* Chỉ số z để popup hiển thị trên cùng */
            justify-content: center;
            align-items: center;
        }

        .popup-content {
            background-color: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
        }
        label{
            color: #000000;
        }
    </style>
</head>
<body>
    <jsp:include page="../header.jsp"/>

    <div class="container mt-5" style="max-height: 100vh; overflow-y: auto; background: #1ED760; border-radius: 30px;">
        <div class="row mt-5">
            <div class="col-sm-11">
                <h3 style="color: #000000">Playlist</h3>
            </div>
            <div class="col-sm-1">
                <button class="btn btn-success" id="btnOpenPopupCreatePlaylist" style="background: #000000; color: #1ED760; font-size:large ;">+</button>
            </div>
        </div>
        <div class="row mt-3" >
            <div class="col-sm-12">
                <ul id="playlistContainer" class="list-group" style="list-style-type: none;">

                </ul>
            </div>
        </div>

    </div>

    <!-- popup create playlist -->
    <div class="popup" id="popupCreatePlaylist">
        <div class="popup-content">
            <h3>Create New Playlist</h3>
            <form id="albumForm">
                <div class="form-group">
                    <label for="playlistName">Playlist Name:</label>
                    <input type="text" class="form-control" id="playlistName" name="playlistName">
                </div>
                <button type="button" class="btn btn-success" id="btnCreatePlaylist">Create</button>
                <button type="button" class="btn btn-secondary" id="btnClosePopupCreatePlaylist">Cancel</button>
            </form>
        </div>
    </div>
    <script src="<%= request.getContextPath() %>/assets/js/playlist_main.js" type="module"></script>
    <jsp:include page="../link_js.jsp"/>
</body>
</html>
