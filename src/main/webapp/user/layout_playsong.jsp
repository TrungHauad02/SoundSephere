<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.soundsephere.model.Users"%>
<%@ page import="com.example.soundsephere.model.Songs" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <jsp:include page="../link_css.jsp"/>
    <title>Nghe nhạc cùng bẹn</title>
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
            crossorigin="anonymous"
    />
    <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
            crossorigin="anonymous"
    ></script>
    <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css"
            integrity="sha512-HK5fgLBL+xu6dm/Ii3z4xhlSUyZgTT9tuc/hSrtw6uzJOvgRr2a9jyxxT1ely+B+xFAmJKVSTbpM/CuL7qxO8w=="
            crossorigin="anonymous"
    />
    <%
        String urlFile = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath();
    %>
    <link rel="stylesheet" href="<%=urlFile%>/assets/css/css_layout_playsong.css" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>

<%
    Users currentUser = (Users) session.getAttribute("currentUserLogin");
%>
<div class="outer-container">
    <jsp:include page="../header.jsp"/>

    <div class="container text-center inner-container" id="menuPlay">
        <div class="row align-items-center">
            <!--Bảng trái -->
            <div class="col window-modal">
                <!--Title-->
                <h1>Info of Song</h1>
                <!--Cd-->
                <div class="cd">
                    <div class="cd-thumb"></div>
                </div>
                <!--Chi tiết-->
                <header>
                    <h4>Now playing:</h4>
                    <h2>Song name</h2>
                    <h3>Author</h3>
                </header>
            </div>

            <!--Bảng phải -->
            <!--Bảng lyrics-->
            <div class="col window-modal" id="windowPlaylist">
<%--                <h1>Playlist</h1>--%>
<%--                <div class="playlist">--%>
<%--                    <c:forEach items="${listSongCurrentPlay}" var="song">--%>
<%--                        <div class="song">--%>
<%--                            <div--%>
<%--                                    class="thumb"--%>
<%--                                    style="--%>
<%--                    background-image: url('https://i.ytimg.com/vi/jTLhQf5KJSc/maxresdefault.jpg');--%>
<%--                  "--%>
<%--                            ></div>--%>
<%--                            <div class="body">--%>
<%--                                <h3 class="title">Music name</h3>--%>
<%--                                <p class="author">Singer</p>--%>
<%--                            </div>--%>
<%--                            <div class="option">--%>
<%--                                <i class="fas fa-ellipsis-h"></i>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                    </c:forEach>--%>
<%--                </div>--%>
                <div class="playlist">
                    <%
                        List<Songs> listSongCurrentPlay = (List<Songs>) request.getAttribute("listSongCurrentPlay");
                        if(listSongCurrentPlay != null) {
                            for(Songs song : listSongCurrentPlay) {
                    %>
                    <div class="song">
                        <div class="thumb" style="background-image: url('https://i.ytimg.com/vi/jTLhQf5KJSc/maxresdefault.jpg');"> <%= song.getImage() %> </div>
                        <div class="body">
                            <h3 class="songID"> <%= song.getId() %></h3>
                            <h3 class="title"><%= song.getTitle() %></h3>
                            <h4 class="author"><%= song.getArtistName() %></h4>
                            <h6 class="sourceSong"> <%= song.getSong_data()%> </h6>
                            <h6 class="sourceLyric"> <%= song.getLyric()%></h6>
                        </div>
                        <div class="option">
                            <i class="fas fa-ellipsis-h"></i>
                        </div>
                    </div>
                    <%
                            }
                        }
                    %>
                </div>

            </div>
            <!--Bảng lyrics-->
            <div class="col window-modal window-modal-hidden" id="windowLyric">
                Lyrics
            </div>
        </div>
    </div>
</div>

<!--Bảng điều kiển các chức năng của song-->
<div class="container bottom-div player" id="playCtrl">
    <div class="row text-start align-items-end">
        <div class="mt-3 col-2 window-playCtrl">
            <!--Rate -->
            <div
                    type="button"
                    id = "btnRate"
                    class="btn btn-secondary btn-rate"
                    data-bs-toggle="modal"
                    data-bs-target="#staticBackdrop"
            >
                <i class="fa fa-star"></i>
            </div>

            <!-- Modal -->
            <div
                    class="modal fade"
                    id="staticBackdrop"
                    data-bs-backdrop="false"
                    data-bs-keyboard="false"
                    tabindex="1"
                    aria-labelledby="staticBackdropLabel"
                    aria-hidden="false"
            >
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1
                                    class="modal-title fs-5"
                                    id="staticBackdropLabel"
                                    style="color: black"
                            >
                                Rate !
                            </h1>
                            <button
                                    type="button"
                                    class="btn-close"
                                    data-bs-dismiss="modal"
                                    aria-label="Close"
                            ></button>
                        </div>
                            <div class="modal-body" id="ratingLayout">
                                <!-- Hệ thống đánh giá sao -->
                                <div class="rating">
                                    <input
                                            type="radio"
                                            id="star5"
                                            name="rating"
                                            value="5"
                                    /><label for="star5"><i class="fas fa-star"></i></label>
                                    <input
                                            type="radio"
                                            id="star4"
                                            name="rating"
                                            value="4"
                                    /><label for="star4"><i class="fas fa-star"></i></label>
                                    <input
                                            type="radio"
                                            id="star3"
                                            name="rating"
                                            value="3"
                                    /><label for="star3"><i class="fas fa-star"></i></label>
                                    <input
                                            type="radio"
                                            id="star2"
                                            name="rating"
                                            value="2"
                                    /><label for="star2"><i class="fas fa-star"></i></label>
                                    <input
                                            type="radio"
                                            id="star1"
                                            name="rating"
                                            value="1"
                                    /><label for="star1"><i class="fas fa-star"></i></label>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button
                                        type="button"
                                        class="btn btn-secondary"
                                        data-bs-dismiss="modal"
                                >
                                    Close
                                </button>
                                <button id ="buttonVote" type="button" class="btn btn-success" >Submit</button>
                            </div>
                    </div>
                </div>
            </div>

            <!--Chi tiết bài hát-->
            <button
                    type="button"
                    id="btnSongDetail"
                    class="btn btn-outline-success mt-2"
                    data-bs-toggle="modal"
                    data-bs-target="#songDetailModal"
            >
                Chi tiết!
            </button>

            <!-- Modal -->
            <div
                    class="modal fade"
                    id="songDetailModal"
                    tabindex="-1"
                    aria-labelledby="songDetailModalLabel"
                    aria-hidden="false"
                    data-bs-backdrop="false"
                    data-bs-keyboard="false"
            >
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="songDetailModalLabel" style="color: black">Thông tin chi tiết bài hát</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body" id="infoDetail">
                            <!-- Nội dung chi tiết bài hát ở đây -->
                            <h3> Title:  </h3>
                            <h3> ID_artist: </h3>
                            <h3> Rating:  </h3>
                            <h3> Written By</h3>
                            <h3> Produced By</h3>
                            <h3> Date Release</h3>

                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                        </div>
                    </div>
                </div>
            </div>

        </div>

        <!--Pause, next, prev -->
        <div class="col-7 window-playCtrl control">
            <div class="btn btn-prev">
                <i class="fas fa-step-backward"></i>
            </div>
            <div class="btn btn-toggle-play">
                <i class="fas fa-pause icon-pause"></i>
                <i class="fas fa-play icon-play"></i>
            </div>
            <div class="btn btn-next">
                <i class="fas fa-step-forward"></i>
            </div>
        </div>

        <!--SwithMode, Lyrics , Playlist -->
        <div class="col-3 window-playCtrl">
            <div class="form-check form-switch">
                <input
                        class="form-check-input"
                        type="checkbox"
                        role="switch"
                        id="customSwitch"
                />
                <label class="form-check-label" for="customSwitch"
                >Auto-Switch</label
                >
            </div>
            <!--Lyrics, Playlist-->
            <input
                    type="radio"
                    class="btn-check"
                    name="options-outlined"
                    id="lyric-outlined"
                    autocomplete="off"
            />
            <label class="btn btn-outline-secondary" for="lyric-outlined">
                <i class="fas fa-closed-captioning"></i> Lyrics
            </label>

            <input
                    type="radio"
                    class="btn-check"
                    name="options-outlined"
                    id="playlist-outlined"
                    autocomplete="off"
                    checked
            />
            <label class="btn btn-outline-success" for="playlist-outlined">
                <i class="fas fa-list"></i> Playlist
            </label>

        </div>
    </div>

        <!--Progress-->
        <div class="row">
            <div class="col-3 text-end" id = "timeNow">Time Now</div>
            <div class="col-6">
                <input
                        id="progress"
                        class="progress"
                        type="range"
                        value="0"
                        step="1"
                        min="0"
                        max="100"
                />
            </div>
            <div class="col-3 text-start" id="timeDuration">Time Duration</div>
        </div>
    <!-- Aduio src-->
    <audio id="audio" src=""></audio>
</div>

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
    <script src="<%=urlFile%>/assets/js/songModule.js" type="module"></script>
    <script src="<%=urlFile%>/assets/js/js_playsong_handle.js" type="module"></script>
    <script src="<%=urlFile%>/assets/js/js_layout_playsong_label.js" ></script>
    <jsp:include page="../link_js.jsp"/>
    </body>

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

    .list-group-item{
        color: #000000;
    }
    label{
        color: #748a7b;
    }
</style>
</html>

