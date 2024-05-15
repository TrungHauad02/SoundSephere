package com.example.soundsephere.controller;


import com.example.soundsephere.dao.PlaylistSongsDAO;
import com.example.soundsephere.dao.PlaylistsDAO;
import com.example.soundsephere.dao.SongsDAO;
import com.example.soundsephere.enumModel.EnumStatus;
import com.example.soundsephere.enumModel.EnumTypePlaylist;
import com.example.soundsephere.model.Playlists;
import com.example.soundsephere.model.Users;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.BufferedReader;
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

        if(action == null)
            action = request.getPathInfo();

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
        String action = request.getPathInfo();

        if ("/addNewAlbum".equals(action)) {
            addNewAlbum(request, response);
        } else {
            doGet(request, response);
        }
    }

    public void getPlaylistByUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user_id = request.getParameter("user_id");
        List<Playlists> playlists = playlistsDAO.selectAllPlaylistByUserId(user_id);

        request.getSession().setAttribute("playlists", playlists);

    }

    public void addNewAlbum(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String data = buffer.toString();

        JSONObject json = new JSONObject(data);
        String albumName = json.getString("albumName");
        System.out.println(albumName);
        Users curUser = (Users) request.getSession().getAttribute("user");

        Playlists playlist = new Playlists();
        playlist.setName(albumName);
        playlist.setType(EnumTypePlaylist.ALBUM);
        playlist.setStatus(EnumStatus.AVAILABLE);
        playlist.setUser_id(curUser.getId());

        boolean result = playlistsDAO.insert(playlist);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{ \"result\": " + result + " }");
    }
}