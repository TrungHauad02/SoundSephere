package com.example.soundsephere.adminController;

import com.example.soundsephere.dao.AlbumsDAO;
import com.example.soundsephere.dao.PlaylistsDAO;
import com.example.soundsephere.dao.SongsDAO;
import com.example.soundsephere.dao.UsersDAO;
import com.example.soundsephere.model.Albums;
import com.example.soundsephere.model.Playlists;
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

@WebServlet("/album")
public class AlbumController  extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AlbumsDAO albumsDAO;
    public void init() {
        albumsDAO  = new AlbumsDAO();

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
            case "/ad_listAlbums":
                showListAlbums(request, response);
                break;
            case "/ad_deleteAlbum":
                deleteAlbum(request, response);
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

    private void deleteAlbum(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession();
        String albumId = request.getParameter("id");
        if (albumId == null || albumId.isEmpty()) {
            request.setAttribute("error", "No song ID provided.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/admin_albums.jsp");
            dispatcher.forward(request, response);
            return;
        }

        boolean isDeleted = albumsDAO.deleteAlbumById(albumId);
        if (isDeleted) {
            request.setAttribute("message", "album with ID " + albumId + " has been successfully deleted.");
        } else {
            request.setAttribute("error", "Failed to delete the album with ID " + albumId + ".");
        }
        List<Albums> albums = albumsDAO.selectAll();
        request.setAttribute("albums", albums);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/admin_albums.jsp");
        dispatcher.forward(request, response);

    }

    private void showListAlbums(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Albums> albumsList = albumsDAO.selectAll();
        request.setAttribute("albums", albumsList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/admin_albums.jsp");
        dispatcher.forward(request, response);
    }
}
