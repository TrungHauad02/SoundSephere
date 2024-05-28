<%@ page import="com.example.soundsephere.model.Songs" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.soundsephere.model.Playlists" %>
<%@ page import="com.example.soundsephere.model.Users" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

    <jsp:include page="../../link_css.jsp"/>
    <title>Document</title>
</head>
<body style="height: 80vh">
    <jsp:include page="../../header.jsp"/>
    <%
        Users user = null;
        if (session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
        } else if (session.getAttribute("user") != null) {
            user = (Users) session.getAttribute("user");
            session.setAttribute("user", user);
        }
    %>

<%--    lấy dữ liệu từ session--%>
    <%
        List<Playlists> list = (List<Playlists>) session.getAttribute("playlists");
        List<Songs> songs = (List<Songs>) session.getAttribute("recentPlayed");
    %>


    <div class="container-fluid" style="height: 100%; padding-top: 30px">
        <div class="row h-100">
            <!-- playlist_display -->
            <div class="col-6" style="display: flex;" >
                <div class="container-fluid playlist_display"> 
                        <div class="row title">
                            <div class="col-6 title_item" >
                                <h2 style="color: white">Playlist</h2>
                            </div>
                            <div class="col-6 title_item"  style="justify-content:flex-end ">
                                <button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#exampleModal">
                                    <a href="<%=request.getContextPath()%>/Playlist?action=goToAddPlaylist">
                                        <img class="image_card" src="<%=request.getContextPath()%>/assets/img/icons/add.png" alt="">
                                    </a>
                                </button>
                            </div>
                        </div>
                                <%
                                    if (list == null || list.isEmpty())
                                    {
                                %>
                                <div class="emptyNof">
                                    <h2 >You don't have any playlist !</h2>
                                </div>
                                <%
                                    }
                                    else
                                    {
                                        int count = 0;
                                        for (Playlists playlist : list) {
                                            count++;
                                            if (count == 4) {
                                                break;
                                            }
                                %>
                                    <div class="row list">
                                            <div class="card card-hover" style="border-radius: 40px; background-color: black;height: 100px;">
                                                <div class="card-body card_song_body">

                                                    <div class="card_song_title">
                                                         <h5 class="card-title"><%=playlist.getName()%></h5>
                                                         <p class="card-text"><%=playlist.getNumber_of_songs()%></p>
                                                    </div>

                                                    <a href="#" class="btn btn-primary card-button">
                                                        <img class="image_card" src="https://cdn-icons-png.flaticon.com/512/4028/4028535.png" alt="">
                                                    </a>
                                                </div>
                                            </div>
                                    </div>
                                <%
                                    }
                                    }
                                %>

                </div>
            </div>
            <!-- song_display -->
            <div class="col-6" style="display: flex; " >
                <div class="container-fluid song_display">
                    <div class="row title">
                        <div class="col-8 title_item" >
                            <h2 style="color: white">Recent Played</h2>
                        </div>

                    </div>

                    <%
                        if (songs == null || songs.isEmpty())
                        {
                    %>
                        <div class="emptyNof">
                            <h2 >You don't have any song !</h2>
                        </div>
                    <%
                        }
                    else
                        {
                            int count = 0;
                            for (Songs song : songs) {
                                count++;
                                if (count == 4) {
                                    break;
                                }
                    %>
                        <div class="row list">
                            <div class="card card-hover" style="border-radius: 40px; background-color: black;height: 100px;">
                                <div class="card-body card_song_body">
                                    <img
                                         id="song-image-<%= count %>"
                                         class="card-img-left image_card"
                                         alt="..."
                                    >
                                    <div class="card_song_title">
                                        <h5 class="card-title"><%=song.getTitle()%></h5>
                                        <p class="card-text"><%=song.getArtistName()%></p>
                                    </div>


<%--                                    ///////////////////////////// chuển hướng sang trang play nhạc--%>
                                    <%
                                        System.out.println(song.getId());
                                    %>
                                    <a href="<%=url%>/SongPlay?action=getSong&idSong=<%= song.getId() %>" class="btn btn-primary card-button">
                                        <img class="image_card" src="https://cdn-icons-png.flaticon.com/512/4028/4028535.png" alt="">
                                    </a>


                                </div>
                            </div>
                        </div>
                        <script type="module">
                            import { getImage } from '<%=url%>/assets/js/getImage.js';
                            document.addEventListener('DOMContentLoaded', () => {
                                getImage("<%= song.getImage() %>").then(
                                    (url) => {
                                        console.log(url);
                                        document.getElementById("song-image-<%= count %>").src = url;
                                    }
                                ).catch((error) => {
                                    console.error("Error fetching image:", error);
                                });
                            });
                        </script>
                    <%
                            }
                        }
                    %>

                    </div>
                    
                </div>
                
            </div>    
        </div> 
            


    </div>


    <jsp:include page="../../link_js.jsp"/>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
</body>
</html>