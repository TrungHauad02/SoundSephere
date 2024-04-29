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

    <div class="container mt-5" style="max-height: 100vh; overflow-y: auto;">
        <div class="row mt-3">
            <div class="col-sm-4">
                <div class="badge badge-primary rounded-circle" style="background-color: #3E862C; height: 100px; width: 100px;"> </div>
            </div>
            <div class="col-sm-8">
                <div class="row">
                    <div class="col-sm-12">
                        <h3>Playlist Name</h3>
                    </div>
                </div>
                <div class="row mt-3">
                    <div class="col-sm-12">
                        <h5>Number of song: 12</h5>
                    </div>
                </div>
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
                                <h6 class="card-title">Song 1</h6>
                                <p class="card-text">Artist Name</p>
                            </div>
                            <div class="col-auto">
                                <div class="dropdown">
                                    <button class="btn btn-primary dropdown-toggle dropdownMenuButton" type="button" id="" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        ...
                                    </button>
                                    <div class="dropdown-menu" aria-labelledby="dropdownMenuButton" style="right: 0; left: auto;">
                                        <a class="dropdown-item playSong" href="#">Play</a>
                                        <a class="dropdown-item addToPlaylist" href="#">Add to Playlist</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>

    <div id="AddSongToPlaylist" class="popup" style="display: none;">
        <div class="popup-content" style="min-width: 500px; max-height:90vh; overflow-y: auto; " >
            <span class="close" onclick="closePopup()">&times;</span>
            <h5 class="mb-4">Add Song to Playlist</h5>
            <ul id="playlistItems" class="list-group">

            </ul>
            <button type="button" class="btn btn-secondary mt-2" onclick="closePopup()">Cancel</button>
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
            document.getElementById('AddSongToPlaylist').style.display = 'flex';
        }

        function closePopup() {
            document.getElementById('AddSongToPlaylist').style.display = 'none';
        }

        document.querySelector('.addToPlaylist').addEventListener('click', function(event) {
            event.preventDefault();
            showPopup();
        });
    </script>
</body>
</html>
