package com.example.soundsephere.controller;


import com.example.soundsephere.dao.PlaylistSongsDAO;
import com.example.soundsephere.dao.PlaylistsDAO;
import com.example.soundsephere.dao.SongsDAO;
import com.example.soundsephere.enumModel.EnumStatus;
import com.example.soundsephere.enumModel.EnumTypePlaylist;
import com.example.soundsephere.model.PlaylistSongs;
import com.example.soundsephere.model.Playlists;
import com.example.soundsephere.model.Songs;
import com.example.soundsephere.model.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
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
        if (action == null){
            action = request.getPathInfo();
        }
        switch (action) {
            case "getbyuser":
                getPlaylistByUser(request, response);
                break;
            case "goToAddPlaylist":
                goToPlayListMain(request, response);
                break;
            case "/getListAlbumJson":
                getListAlbumJson(request, response);
                break;
            case "/getListPlaylistJson" :
                getListPlaylistJson(request, response);
                break;
            default:
                RequestDispatcher dispatcher = request.getRequestDispatcher("user/playlist_main.jsp");
                dispatcher.forward(request, response);
                break;
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();

        if ("/addNewAlbum".equals(action)) {
            addNewAlbum(request, response);
        } else if ("/addNewPlaylist".equals(action)) {
            addNewPlaylist(request, response);
        } else if("/addSongToPlaylist".equals(action)){
            addSongToPlaylist(request, response);
        } else if ("/deletePlaylist".equals(action)) {
            deletePlaylist(request, response);
        } else if ("/getPlaylistDetail".equals(action)) {
            getPlaylistDetail(request, response);
        } else {
            doGet(request, response);
        }
    }
    public void goToPlayListMain(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/playlist_main.jsp");
        dispatcher.forward(request, response);
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
        playlist.setUser_id(curUser.getUsername());

        boolean result = playlistsDAO.insert(playlist);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{ \"result\": " + result + " }");
    }
    public void addNewPlaylist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String data = buffer.toString();

        JSONObject json = new JSONObject(data);
        String playlistName = json.getString("playlistName");
        System.out.println(playlistName);
        Users curUser = (Users) request.getSession().getAttribute("user");

        Playlists playlist = new Playlists();
        playlist.setName(playlistName);
        playlist.setType(EnumTypePlaylist.PLAYLIST);
        playlist.setStatus(EnumStatus.AVAILABLE);
        playlist.setUser_id(curUser.getUsername());

        boolean result = playlistsDAO.insert(playlist);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{ \"result\": " + result + " }");
    }

    public void addSongToPlaylist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String data = buffer.toString();

        JSONObject json = new JSONObject(data);
        int songId = json.getInt("songId");
        int playlistId = json.getInt("playlistId");
        System.out.println(songId + " " + playlistId);

        PlaylistSongs playlistSongs = new PlaylistSongs();
        playlistSongs.setSong_id(songId);
        playlistSongs.setPlaylist_id(playlistId);

        PlaylistSongsDAO playlistSongsDAO = new PlaylistSongsDAO();
        boolean result = playlistSongsDAO.insert(playlistSongs);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{ \"result\": " + result + " }");
    }

    public void getListAlbumJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Users curUser = (Users) session.getAttribute("user");
        List<Playlists> lstPlaylist = playlistsDAO.selectAllPlaylistByUserId(curUser.getUsername());
        lstPlaylist.removeIf(playlist -> playlist.getType().equals(EnumTypePlaylist.PLAYLIST));
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPlaylist = objectMapper.writeValueAsString(lstPlaylist);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(jsonPlaylist);
    }

    public void getListPlaylistJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Users curUser = (Users) session.getAttribute("user");
        List<Playlists> lstPlaylist = playlistsDAO.selectAllPlaylistByUserId(curUser.getUsername());
        lstPlaylist.removeIf(playlist -> playlist.getType().equals(EnumTypePlaylist.ALBUM));
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPlaylist = objectMapper.writeValueAsString(lstPlaylist);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(jsonPlaylist);
    }

    public void deletePlaylist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String data = buffer.toString();

        JSONObject json = new JSONObject(data);
        int playlistId = json.getInt("playlistId");

        int numberOfSongs = playlistsDAO.getNumberofsongs(playlistId);
        boolean result = true;
        if(numberOfSongs != 0){
            PlaylistSongsDAO playlistSongsDAO = new PlaylistSongsDAO();
            result = playlistSongsDAO.delete(playlistId);
        }
        if (result)
            result = playlistsDAO.delete(playlistId);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{ \"result\": " + result + " }");
    }
    public void getPlaylistDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String data = buffer.toString();

        JSONObject json = new JSONObject(data);
        int playlistId = json.getInt("playlistId");

        Playlists playlist = playlistsDAO.selectById(playlistId);
        List<Songs> lstSong = songsDAO.selectBySql(SongsDAO.SELECT_SONGS_IN_PLAYLIST_QUERY, playlistId);
        JSONObject responseJson = new JSONObject();
        responseJson.put("playlist", convertPlaylistToJson(playlist));
        responseJson.put("songs", convertSongsToJson(lstSong));

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(responseJson.toString());
        out.flush();
    }

    private JSONObject convertPlaylistToJson(Playlists playlist) {
        JSONObject json = new JSONObject();
        json.put("id", playlist.getId());
        json.put("name", playlist.getName());
        json.put("number_of_songs", playlist.getNumber_of_songs());
        return json;
    }

    private JSONArray convertSongsToJson(List<Songs> songs) {
        JSONArray jsonArray = new JSONArray();
        for (Songs song : songs) {
            JSONObject json = new JSONObject();
            json.put("id", song.getId());
            json.put("title", song.getTitle());
            json.put("artist", song.getId_artist());
            json.put("image", song.getImage());
            json.put("description", song.getDescription());
            jsonArray.put(json);
        }
        return jsonArray;
    }
}