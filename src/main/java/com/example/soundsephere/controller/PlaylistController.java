package com.example.soundsephere.controller;



import com.example.soundsephere.dao.PlaylistsDAO;
import com.example.soundsephere.dao.PlaylistSongsDAO;
import com.example.soundsephere.dao.SongsDAO;
import com.example.soundsephere.model.Playlists;
import com.example.soundsephere.model.PlaylistSongs;

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

            default:
                System.out.println("default");
                RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
                dispatcher.forward(request, response);
                break;
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

    public void getPlaylistByUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int user_id = Integer.parseInt(request.getParameter("user_id"));
        List<Playlists> playlists = playlistsDAO.selectAllPlaylistByUserId(user_id);

        request.getSession().setAttribute("playlists", playlists);

    }
}