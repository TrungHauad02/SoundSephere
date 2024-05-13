<%--
  Created by IntelliJ IDEA.
  User: Asus ROG
  Date: 20/04/2024
  Time: 1:06 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.example.soundsephere.model.Users" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Artist</title>
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

        .dropdown-menu {
            display: none;
            position: absolute;
            right: 0;
            top: 100%;
            min-width: 150px;
            background-color: #ffffff;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
        }

        .dropdown-menu.show {
            display: block;
        }
    </style>
</head>
<body style="background-color: black;">
    <jsp:include page="../header.jsp"/>
    <%
        Users currentUserLogin = (Users) session.getAttribute("currentUserLogin");%>
        <%
            if (currentUserLogin == null) {
        %>
        <%
            response.sendRedirect(request.getContextPath()+"/index.jsp");
        %>
        <%
        }
    %>
    <div class="container">
        <div class="row" style="padding-bottom: 20px;padding-top: 10px;">
            <div class="col-md-12">
                <div class="card artist-info" style="background-color: #1ED760;">
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-3 text-center">
                                <img src="<%= request.getContextPath() %>/assets/img/artist.jpg" alt="avatar" class="img-fluid rounded-circle" style="width: 150px; height: 150px;">
                            </div>
                            <div class="col-md-4">
                                <ul class="list-group">
                                    <li class="list-group-item">Artist details</li>
                                    <li class="list-group-item">${currentUserLogin.name}</li>
                                    <li class="list-group-item">${currentUserLogin.description}</li>
                                </ul>
                            </div>
                            <div class="col-md-1"></div>
                            <div class="col-md-4">
                                <ul class="list-group">
                                    <li class="list-group-item">Số lượng lượt nghe: ${requestScope.listensCount}</li>
                                    <li class="list-group-item">Số lượng bài hát: ${requestScope.songCount}</li>
                                    <li class="list-group-item">Số lượng album: ${requestScope.albumCount}</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="row">
                <div class="col-md-6">
                    <div class="card artist-albums" style="background-color: #1ED760;">
                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-10">
                                    <h5 class="card-title">Albums</h5>
                                </div>
                                <div class="col-md-2">
                                    <a href="#" class="btn btn-primary mb-2" id="openCreateAlbumPopup">+</a>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <ul class="list-group" id="albumContainer" style="list-style-type: none; max-height: 300px; overflow-y: auto;">

                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="card artist-singer"  style="background-color: #1ED760;">
                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-10">
                                    <h5 class="card-title">Singles</h5>
                                </div>
                                <div class="col-md-2">
                                    <a href="#" class="btn btn-primary mb-2" id="btnOpenSongPopup">+</a>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                        <ul id="songListContainer" class="list-group" style="list-style-type: none; max-height: 300px; overflow-y: auto;">
                                            <!-- Các thẻ <li> sẽ được thêm vào đây bởi JavaScript -->
                                        </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Thêm div để hiển thị danh sách playlist -->
    <!-- Popup Add Song To Playlist -->
    <div id="addSongToPlaylistPopUp" class="popup" style="display: none;">
        <div class="popup-content" style="min-width: 500px; max-height:90vh; overflow-y: auto; " >
            <span class="close" id="btnClosePlaylistPopupTop">&times;</span>
            <h5 class="mb-4">Add Song to Playlist</h5>
            <ul id="playlistItems" class="list-group">

            </ul>
            <button type="button" class="btn btn-secondary mt-2" id="btnClosePlaylistPopup">Cancel</button>
        </div>
    </div>

    <!-- Popup Create Album -->
    <div class="popup" id="albumPopup">
        <div class="popup-content">
            <h3>Create Album</h3>
            <form id="albumForm">
                <div class="form-group">
                    <label for="albumName">Album Name:</label>
                    <input type="text" class="form-control" id="albumName" name="albumName">
                </div>
                <div class="form-group">
                    <label for="description">Description:</label>
                    <textarea class="form-control" id="description" name="description"></textarea>
                </div>
                <button id="btnCreateAlbum" type="button" class="btn btn-success" >Create</button>
                <button id="btnCloseCreateAlbumPopup" type="button" class="btn btn-secondary">Cancel</button>
            </form>
        </div>
    </div>

    <!-- Popup Add Song -->
    <div class="popup" id="addSongPopup" style="max-height: 100vh; ">
        <div class="popup-content" style="max-height:90vh; overflow-y: auto;" >
            <h3>Create Song</h3>
            <form id="songForm">
                <div class="form-group">
                    <label for="songName">Song Name:</label>
                    <input type="text" class="form-control" id="songName" name="songName">
                </div>
                <div class="form-group">
                    <label for="writtenBy">Written By:</label>
                    <input type="text" class="form-control" id="writtenBy" name="writtenBy">
                </div>
                <div class="form-group">
                    <label for="producedBy">Produced By:</label>
                    <input type="text" class="form-control" id="producedBy" name="producedBy">
                </div>
                <div class="form-group">
                    <label for="imageInput">Image:</label>
                    <input type="file" class="form-control-file" id="imageInput" name="imageInput" accept="image/*">
                </div>
                <div id="imagePreviewContainer" class="form-group" style="display: none;">
                    <label>Preview:</label>
                    <img id="imagePreview" src="#" alt="Preview" style="max-width: 100%; max-height: 200px;">
                </div>
                <div class="form-group">
                    <label for="mp3File">MP3 File:</label>
                    <input type="file" class="form-control-file" id="mp3File" name="mp3File">
                </div>
                <button id="btnAddSong" type="button" class="btn btn-success">Create</button>
                <button id="btnCloseSongPopup" type="button" class="btn btn-secondary">Cancel</button>
            </form>
        </div>
    </div>
    <!-- Javascript -->
    <script src="<%= request.getContextPath() %>/assets/js/artist_main.js" type="module"></script>
    <jsp:include page="../link_js.jsp"/>
</body>
</html>
