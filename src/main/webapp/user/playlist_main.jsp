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
    </style>
</head>
<body>
    <jsp:include page="../header.jsp"/>

    <div class="container mt-5" style="max-height: 100vh; overflow-y: auto;">
        <div class="row">
            <div class="col-sm-11">
                <h3>Playlist</h3>
            </div>
            <div class="col-sm-1">
                <button class="btn btn-success" id="btnAddPlaylist">+</button>
            </div>
        </div>
        <div class="row mt-3">
            <div class="col-sm-12">
                <div class="card">
                    <div class="card-body">
                        <div class="row align-items-center">
                            <div class="col-auto">
                                <div class="badge badge-primary rounded-circle" style="background-color: #3E862C; height: 50px; width: 50px;"> </div>
                            </div>
                            <div class="col">
                                <h6 class="card-title">Playlist 1</h6>
                                <p class="card-text">Number of song: 12</p>
                            </div>
                            <div class="col-auto">
                                <div class="dropdown">
                                    <button class="btn btn-primary dropdown-toggle dropdownMenuButton" type="button" id="" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        ...
                                    </button>
                                    <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                        <a class="dropdown-item playlist" href="#">Delete playlist</a>
                                        <a class="dropdown-item addToPlaylist" href="#">Add to playlist</a>
                                    </div>
                                </div>
                            </div>
                            <div class="col-auto">
                                <button class="btn btn-primary">Play</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="popup" id="popupAddPlaylist">
            <div class="popup-content">
                <h3>Create Playlist</h3>
                <form id="albumForm">
                    <div class="form-group">
                        <label for="playlistName">Playlist Name:</label>
                        <input type="text" class="form-control" id="playlistName" name="playlistName">
                    </div>
                    <button type="button" class="btn btn-success" onclick="createPlaylist()">Create</button>
                    <button type="button" class="btn btn-secondary" onclick="closePopup()">Cancel</button>
                </form>
            </div>
        </div>
    </div>

    <jsp:include page="../link_js.jsp"/>

    <script>
        $(document).ready(function() {
            $('.dropdown-toggle').click(function() {
                $(this).next('.dropdown-menu').toggleClass('show');
            });

            $(document).click(function(e) {
                if (!$(e.target).closest('.dropdown').length) {
                    $('.dropdown-menu').removeClass('show');
                }
            });
        });
    </script>
    <script>
        function showPopup() {
            document.getElementById('popupAddPlaylist').style.display = 'flex';
        }

        function closePopup() {
            document.getElementById('popupAddPlaylist').style.display = 'none';
        }

        document.getElementById('btnAddPlaylist').addEventListener('click', function() {
            showPopup();
        });

        function createPlaylist() {
            var playlistName = document.getElementById('playlistName').value;
            alert('Playlist "' + playlistName + '" đã được tạo!');
            closePopup();
        }
    </script>
</body>
</html>
