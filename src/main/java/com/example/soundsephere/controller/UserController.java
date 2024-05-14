package com.example.soundsephere.controller;


import com.example.soundsephere.dao.PlaylistsDAO;
import com.example.soundsephere.dao.SongsDAO;
import com.example.soundsephere.dao.UsersDAO;
import com.example.soundsephere.model.Playlists;
import com.example.soundsephere.model.Songs;
import com.example.soundsephere.model.Users;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import org.json.JSONObject;
@WebServlet("/User")
public class UserController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    UsersDAO usersDAO;
    SongsDAO songsDAO;
    PlaylistsDAO playlistsDAO;
    public void init(ServletConfig config) throws ServletException {
        usersDAO = new UsersDAO();
        songsDAO = new SongsDAO();
        playlistsDAO = new PlaylistsDAO();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        switch (action) {
            case "/login":
                login(request, response);
                break;
            case "/artist_login":
                artistLogin(request, response);
                break;
            case "/getListSongJson":
                getListSongJson(request, response);
                break;
            case "/getListAlbumJson":
                getListAlbumJson(request, response);
                break;
            case "/addNewAlbum":
                addNewAlbum(request, response);
                break;
            default:
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/User/artist_login");
        dispatcher.forward(request, response);
    }

    private void artistLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Users curUser = usersDAO.selectById(6);
        HttpSession session = request.getSession();
        session.setAttribute("currentUserLogin", curUser);
        int listensCount = usersDAO.listenCount(6);
        int songCount = songsDAO.songsCount(6);
        int albumCount = playlistsDAO.playlistCount(6);
        request.setAttribute("listensCount", listensCount);
        request.setAttribute("songCount", songCount);
        request.setAttribute("albumCount", albumCount);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/user/artist_main.jsp");
        dispatcher.forward(request, response);
    }

    private void getListSongJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Songs> lstSong = songsDAO.selectBySql(SongsDAO.SELECT_SONGS_BY_ID_ARTIST_QUERY, 6);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonSongs = objectMapper.writeValueAsString(lstSong);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(jsonSongs);
    }

    private void getListAlbumJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Playlists> lstPlaylist = playlistsDAO.selectBySql(PlaylistsDAO.SELECT_ALBUM_BY_ID_ARTIST_QUERY, 6);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPlaylist = objectMapper.writeValueAsString(lstPlaylist);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(jsonPlaylist);
    }

        private void addNewAlbum(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }
}
