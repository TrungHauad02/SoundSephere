package com.example.soundsephere.adminController;

import com.example.soundsephere.dao.PlaylistsDAO;
import com.example.soundsephere.dao.SongsDAO;
import com.example.soundsephere.dao.UsersDAO;
import com.example.soundsephere.model.Playlists;
import com.example.soundsephere.model.Songs;
import com.example.soundsephere.model.Users;
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
@WebServlet("/song")
public class SongController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private SongsDAO songsDAO;

    public void init() {
        songsDAO = new SongsDAO();
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
                case "/ad_listSong":
                    showListSongs(request, response);
                    break;
                case "/ad_blockSong":
                    blockSong(request, response);
                    break;
                case "/ad_deleteSong":
                    deleteSong(request, response);
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

    private void blockSong(HttpServletRequest request, HttpServletResponse response)throws SQLException, ServletException, IOException {
        HttpSession session = request.getSession();
        String songId = request.getParameter("id");
        if (songId == null || songId.isEmpty()) {
            throw new ServletException("Song ID is missing.");
        }

        boolean isBlocked = songsDAO.blockSongById(songId);
        if (isBlocked) {
            request.setAttribute("message", "Song with ID " + songId + " is successfully blocked.");
        } else {
            request.setAttribute("message", "Failed to block the song with ID " + songId + ".");
        }
        List<Songs> songsList = songsDAO.selectAll();
        request.setAttribute("songsList", songsList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/admin_songs.jsp");
        dispatcher.forward(request, response);

    }
    private void deleteSong(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String songId = request.getParameter("id");
        if (songId == null || songId.isEmpty()) {
            request.setAttribute("error", "No song ID provided.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/admin_songs.jsp");
            dispatcher.forward(request, response);
            return;
        }
        boolean isDeleted = songsDAO.deleteSongById(songId);
        if (isDeleted) {
            request.setAttribute("message", "Song with ID " + songId + " has been successfully deleted.");
        } else {
            request.setAttribute("error", "Failed to delete the song with ID " + songId + ".");
        }
        List<Songs> songsList = songsDAO.selectAll();
        request.setAttribute("songsList", songsList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/admin_songs.jsp");
        dispatcher.forward(request, response);
    }


    private void showListSongs(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        HttpSession session = request.getSession();
        List<Songs> songsList = songsDAO.selectAll();
        request.setAttribute("songsList", songsList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/admin_songs.jsp");
        dispatcher.forward(request, response);
    }

}
