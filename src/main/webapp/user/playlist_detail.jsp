<%--
  Created by IntelliJ IDEA.
  User: Asus ROG
  Date: 29/04/2024
  Time: 7:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Playlist_detail</title>
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
    </style>
</head>
<body>
    <jsp:include page="../header.jsp"/>

    <div class="container mt-5" style="max-height: 100vh; overflow-y: auto; background-color: #1ED760; padding-bottom: 20px; border-radius: 20px;">
        <div class="row mt-3">
            <div class="col-sm-4">
                <div class="badge badge-primary rounded-circle" style="background-color: #ffffff; height: 100px; width: 100px;"> </div>
            </div>
            <div class="col-sm-8">
                <div class="row">
                    <div class="col-sm-12">
                        <h3 id="playlistName" style="color: #ffffff;">Playlist Name</h3>
                    </div>
                </div>
                <div class="row mt-3">
                    <div class="col-sm-12">
                        <h5 id="numberOfSong" style="color: #ffffff;">Number of song: 0</h5>
                    </div>
                </div>
            </div>
        </div>
        <div class="row mt-3">
            <div class="col-sm-12">
                <ul class="list-group" id="songsContainer" style="list-style-type: none; max-height: 100vh; overflow-y: auto;">

                </ul>
            </div>
        </div>

    </div>

    <div id="AddSongToPlaylist" class="popup" style="display: none;">
        <div class="popup-content" style="min-width: 500px; max-height:90vh; overflow-y: auto; " >
            <span class="close" >&times;</span>
            <h5 class="mb-4">Add Song to Playlist</h5>
            <ul id="playlistItems" class="list-group">

            </ul>
            <button type="button" class="btn btn-secondary mt-2">Cancel</button>
        </div>
    </div>

    <script src="<%= request.getContextPath() %>/assets/js/playlistDetail.js" type="module"></script>
    <jsp:include page="../link_js.jsp"/>
</body>
</html>
