package com.example.soundsephere.adminController;

import com.example.soundsephere.dao.PlaylistsDAO;
import com.example.soundsephere.model.Albums;
import com.example.soundsephere.model.Playlists;
import com.example.soundsephere.model.Songs;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/playlist")

public class PlaylistController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PlaylistsDAO playlistsDAO;

    @Override
    public void init() {
        playlistsDAO = new PlaylistsDAO();

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        System.out.println(action);
        try {
        switch (action) {
            case "/ad_listPlaylists":
                showListPlaylist(request, response);
                break;
            case "/ad_deletePlaylist":
                deletePlaylist(request, response);
                break;
            default:
                RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/admin_songs.jsp");
                dispatcher.forward(request, response);
                break;
        }

        }catch (SQLException ex) {
            throw new ServletException(ex);
        }

    }

    private void deletePlaylist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

        HttpSession session = request.getSession();
        String playlistId = request.getParameter("id");

        boolean isDeleted = playlistsDAO.deletePlaylistById(playlistId);
        if (isDeleted) {
            request.setAttribute("message", "Playlist with ID " + playlistId + " has been successfully deleted.");
        } else {
            request.setAttribute("error", "Failed to delete the playlist with ID " + playlistId + ".");
        }
        List<Playlists> playlists = playlistsDAO.selectAll();
        request.setAttribute("playlists", playlists);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/admin_playlists.jsp");
        dispatcher.forward(request, response);

    }

    private void showListPlaylist(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Playlists> playlistsList = playlistsDAO.selectAll();
        request.setAttribute("playlists", playlistsList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/admin_playlists.jsp");
        dispatcher.forward(request, response);

    }


}

