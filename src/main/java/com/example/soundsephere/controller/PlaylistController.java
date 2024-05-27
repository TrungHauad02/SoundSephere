package com.example.soundsephere.controller;


import com.example.soundsephere.dao.PlaylistSongsDAO;
import com.example.soundsephere.dao.PlaylistsDAO;
import com.example.soundsephere.dao.SongsDAO;
import com.example.soundsephere.model.Playlists;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/Playlist")
public class PlaylistController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    PlaylistsDAO playlistsDAO;
    PlaylistSongsDAO playlistSongsDAO;
    SongsDAO songsDAO;

    public void init() {
        playlistsDAO = new PlaylistsDAO();
        playlistSongsDAO = new PlaylistSongsDAO();
        songsDAO = new SongsDAO();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        System.out.println(action);

        switch (action) {
            case "getbyuser":
                getPlaylistByUser(request, response);
                break;
            case "goToAddPlaylist":
                goToPlayListMain(request, response);
                break;
            default:
                RequestDispatcher dispatcher = request.getRequestDispatcher("user/playlist_main.jsp");
                dispatcher.forward(request, response);
                break;
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
    public void goToPlayListMain(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/playlist_main.jsp");
        dispatcher.forward(request, response);
    }

    public void getPlaylistByUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user_id = String.valueOf(Integer.parseInt(request.getParameter("user_id")));
        List<Playlists> playlists = playlistsDAO.selectAllPlaylistByUserId(user_id);

        request.getSession().setAttribute("playlists", playlists);

    }
}