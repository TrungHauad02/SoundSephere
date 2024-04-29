<%--
  Created by IntelliJ IDEA.
  User: Asus ROG
  Date: 20/04/2024
  Time: 1:06 PM
  To change this template use File | Settings | File Templates.
--%>
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

    </style>
</head>
<body>
    <jsp:include page="../header.jsp"/>

    <div class="container">
        <div class="row" style="padding-bottom: 20px;padding-top: 10px;">
            <div class="col-md-12">
                <div class="card info-artist">
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-3 text-center">
                                <img src="img/artist.jpg" alt="avatar" class="img-fluid rounded-circle" style="width: 150px; height: 150px;">
                            </div>
                            <div class="col-md-4">
                                <ul class="list-group">
                                    <li class="list-group-item">Artist details</li>
                                    <li class="list-group-item">Name</li>
                                    <li class="list-group-item">Description</li>
                                </ul>
                            </div>
                            <div class="col-md-1"></div>
                            <div class="col-md-4">
                                <ul class="list-group">
                                    <li class="list-group-item">Số lượng lượt nghe</li>
                                    <li class="list-group-item">Số lượng bài hát</li>
                                    <li class="list-group-item">Số lượng album</li>
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
                    <div class="card artist-albums">
                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-10">
                                    <h5 class="card-title">Albums</h5>
                                </div>
                                <div class="col-md-2">
                                    <a href="#" class="btn btn-primary mb-2" id="openPopup">+</a>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <ul class="list-group" style="list-style-type: none; max-height: 300px; overflow-y: auto;">
                                        <li class="pb-2">
                                            <div class="card">
                                                <div class="card-body">
                                                    <div class="row align-items-center">
                                                        <div class="col-auto">
                                                            <div class="badge badge-primary rounded-circle" style="background-color: #3E862C; height: 50px; width: 50px;"> </div>
                                                        </div>
                                                        <div class="col">
                                                            <h6 class="card-title">Album 1</h6>
                                                            <p class="card-text">Description</p>
                                                        </div>
                                                        <div class="col-auto">
                                                            <button class="btn btn-primary">Play</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                        <li class="pb-2">
                                            <div class="card">
                                                <div class="card-body">
                                                    <div class="row align-items-center">
                                                        <div class="col-auto">
                                                            <div class="badge badge-primary rounded-circle" style="background-color: #3E862C; height: 50px; width: 50px;"> </div>
                                                        </div>
                                                        <div class="col">
                                                            <h6 class="card-title">Album 1</h6>
                                                            <p class="card-text">Description</p>
                                                        </div>
                                                        <div class="col-auto">
                                                            <button class="btn btn-primary">Play</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                        <li class="pb-2">
                                            <div class="card">
                                                <div class="card-body">
                                                    <div class="row align-items-center">
                                                        <div class="col-auto">
                                                            <div class="badge badge-primary rounded-circle" style="background-color: #3E862C; height: 50px; width: 50px;"> </div>
                                                        </div>
                                                        <div class="col">
                                                            <h6 class="card-title">Album 1</h6>
                                                            <p class="card-text">Description</p>
                                                        </div>
                                                        <div class="col-auto">
                                                            <button class="btn btn-primary">Play</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="card artist-singer">
                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-10">
                                    <h5 class="card-title">Singles</h5>
                                </div>
                                <div class="col-md-2">
                                    <a href="#" class="btn btn-primary mb-2" id="openSongPopup">+</a>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <ul class="list-group" style="list-style-type: none; max-height: 300px; overflow-y: auto;">
                                        <li class="pb-2">
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
                                                                <button class="btn btn-primary dropdown-toggle dropdownMenuButton" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                                    ...
                                                                </button>
                                                                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                                                    <a class="dropdown-item playSong" href="#">Play</a>
                                                                    <a class="dropdown-item addToPlaylist" href="#">Add to Playlist</a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                        <li class="pb-2">
                                            <div class="card">
                                                <div class="card-body">
                                                    <div class="row align-items-center">
                                                        <div class="col-auto">
                                                            <div class="badge badge-primary rounded-circle" style="background-color: #3E862C; height: 50px; width: 50px;"> </div>
                                                        </div>
                                                        <div class="col">
                                                            <h6 class="card-title">Song 1</h6>
                                                            <p class="card-text">Description</p>
                                                        </div>
                                                        <div class="col-auto">
                                                            <div class="dropdown">
                                                                <button class="btn btn-primary dropdown-toggle dropdownMenuButton" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                                    ...
                                                                </button>
                                                                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                                                    <a class="dropdown-item playSong" href="#">Play</a>
                                                                    <a class="dropdown-item addToPlaylist" href="#">Add to Playlist</a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                        <li class="pb-2">
                                            <div class="card">
                                                <div class="card-body">
                                                    <div class="row align-items-center">
                                                        <div class="col-auto">
                                                            <div class="badge badge-primary rounded-circle" style="background-color: #3E862C; height: 50px; width: 50px;"> </div>
                                                        </div>
                                                        <div class="col">
                                                            <h6 class="card-title">Song 1</h6>
                                                            <p class="card-text">Description</p>
                                                        </div>
                                                        <div class="col-auto">
                                                            <div class="dropdown">
                                                                <button class="btn btn-primary dropdown-toggle dropdownMenuButton" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                                    ...
                                                                </button>
                                                                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                                                    <a class="dropdown-item playSong" href="#">Play</a>
                                                                    <a class="dropdown-item addToPlaylist" href="#">Add to Playlist</a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
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
    <!-- Popup cho danh sách playlist -->
    <div id="playlistList" class="popup" style="display: none;">
        <div class="popup-content" style="min-width: 500px; max-height:90vh; overflow-y: auto; " >
            <span class="close" onclick="closePopup()">&times;</span>
            <h5 class="mb-4">Add Song to Playlist</h5>
            <ul id="playlistItems" class="list-group">
                <!-- Các playlist sẽ được thêm vào đây bằng JavaScript -->
            </ul>
            <button type="button" class="btn btn-secondary mt-2" onclick="closePopup()">Cancel</button>
        </div>
    </div>

    <!-- Popup -->
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
                <button type="button" class="btn btn-success" onclick="createAlbum()">Create</button>
                <button type="button" class="btn btn-secondary" onclick="closePopup()">Cancel</button>
            </form>
        </div>
    </div>

    <div class="popup" id="songPopup" style="max-height: 100vh; ">
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
                    <label for="image">Image:</label>
                    <input type="file" class="form-control-file" id="image" name="imageInput" accept="image/*">
                </div>
                <div id="imagePreviewContainer" class="form-group" style="display: none;">
                    <label>Preview:</label>
                    <img id="imagePreview" src="#" alt="Preview" style="max-width: 100%; max-height: 200px;">
                </div>
                <div class="form-group">
                    <label for="mp3File">MP3 File:</label>
                    <input type="file" class="form-control-file" id="mp3File" name="mp3File">
                </div>
                <button type="button" class="btn btn-success" onclick="createSong()">Create</button>
                <button type="button" class="btn btn-secondary" onclick="closeSongPopup()">Cancel</button>
            </form>
        </div>
    </div>
    <!-- Javascript -->
    <script>

       // Add new Album
        const openPopupButton = document.getElementById('openPopup');
        const albumPopup = document.getElementById('albumPopup');

        openPopupButton.addEventListener('click', function() {
            albumPopup.style.display = 'flex';
        });
        function closePopup() {
            albumPopup.style.display = 'none';
        }

        function createAlbum() {
            const albumName = document.getElementById('albumName').value;
            const description = document.getElementById('description').value;

            // Gọi JSP hoặc các hàm xử lý tiếp theo ở đây (ví dụ: gửi dữ liệu điều khiển tới server)
            console.log('Album Name:', albumName);
            console.log('Description:', description);
            closePopup();
        }

       // Add new Song -->
        const openSongPopupButton = document.getElementById('openSongPopup');
        const songPopup = document.getElementById('songPopup');

        openSongPopupButton.addEventListener('click', function() {
            songPopup.style.display = 'flex';
        });

        function closeSongPopup() {
            songPopup.style.display = 'none';
        }

        function createSong() {
            // Lấy giá trị từ các trường input trong form
            const songName = document.getElementById('songName').value;
            const writtenBy = document.getElementById('writtenBy').value;
            const producedBy = document.getElementById('producedBy').value;
            const imageURL = document.getElementById('image').value;
            const mp3File = document.getElementById('mp3File').files[0]; // File MP3

            // Gọi JSP hoặc các hàm xử lý tiếp theo ở đây (ví dụ: gửi dữ liệu điều khiển tới server)
            console.log('Song Name:', songName);
            console.log('Written By:', writtenBy);
            console.log('Produced By:', producedBy);
            console.log('Image URL:', imageURL);
            console.log('MP3 File:', mp3File);

            closeSongPopup();
        }
        const imageInput = document.getElementById('imageInput');
        const imagePreviewContainer = document.getElementById('imagePreviewContainer');
        const imagePreview = document.getElementById('imagePreview');

        imageInput.addEventListener('change', function() {
            const file = this.files[0]; // Lấy file hình ảnh đã chọn

            if (file) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    imagePreview.src = e.target.result;
                    imagePreviewContainer.style.display = 'block';
                };
                reader.readAsDataURL(file);
            }
        });

       // Add new Song to playlist -->
        // Lấy danh sách playlist từ máy chủ hoặc dữ liệu mẫu (giả sử là mảng các playlist)
        const playlists = [
            { name: "Playlist 1" },
            { name: "Playlist 2" },
            { name: "Playlist 3" }
        ];

        // Lấy phần tử dropdown menu và div danh sách playlist từ HTML
        const dropdownMenu = document.querySelector('.dropdown-menu');
        const playlistListContainer = document.getElementById('playlistList');
        const playlistItemsContainer = document.getElementById('playlistItems');
        dropdownMenu.addEventListener('click', function(event) {
            event.preventDefault();

            const target = event.target;

            if (target.classList.contains('addToPlaylist')) {
                openPopup();
            } else if (target.classList.contains('playSong')) {
                console.log('Play the song');
            }
        });
        const playlistListPopup = document.getElementById('playlistList');

        // Hàm mở popup
        function openPopup() {
            playlistListContainer.style.display = 'block';

            // Xóa các playlist cũ trong danh sách (nếu có)
            playlistItemsContainer.innerHTML = '';

            // Tạo các mục danh sách playlist và thêm vào danh sách
            playlists.forEach(function(playlist) {
                // Tạo một mục danh sách mới
                const playlistItem = document.createElement('li');
                playlistItem.className = 'list-group-item d-flex justify-content-between align-items-center';

                // Tên playlist
                const playlistName = document.createTextNode(playlist.name);
                playlistItem.appendChild(playlistName);

                // Nút "Add" (+) trong mục danh sách
                const addButton = document.createElement('button');
                addButton.className = 'btn btn-primary btn-sm';
                addButton.textContent = '+';
                addButton.addEventListener('click', function() {
                    console.log('Add song to playlist:', playlist.name);
                });

                playlistItem.appendChild(addButton);
                playlistItem.style.marginBottom = '5px';
                playlistItemsContainer.appendChild(playlistItem);
            });
            playlistListPopup.style.display = 'flex';
        }

        // Hàm đóng popup
        function closePopup() {
            playlistListPopup.style.display = 'none';
        }
    </script>
    <jsp:include page="../link_js.jsp"/>
</body>
</html>
