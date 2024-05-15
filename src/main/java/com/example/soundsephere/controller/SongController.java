package com.example.soundsephere.controller;

import com.example.soundsephere.dao.SongDetailDAO;
import com.example.soundsephere.dao.SongsDAO;
import com.example.soundsephere.enumModel.EnumStatus;
import com.example.soundsephere.model.SongDetail;
import com.example.soundsephere.model.Songs;
import com.example.soundsephere.model.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.util.Base64;

@WebServlet("/Song/*")
public class SongController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    SongsDAO songsDAO;

    public void init() {
        songsDAO = new SongsDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle GET requests if necessary
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();

        if ("/addNewSong".equals(action)) {
            addNewSong(request, response);
        } else {
            doGet(request, response);
        }
    }

    private void addNewSong(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        JSONObject jsonObject = new JSONObject(sb.toString());

        String songName = jsonObject.getString("songName");
        String description = jsonObject.getString("description");
        String writtenBy = jsonObject.getString("writtenBy");
        String producedBy = jsonObject.getString("producedBy");
        String imageFileName = jsonObject.optString("imageFileName", null);
        String mp3FileName = jsonObject.optString("mp3FileName", null);
        String lyricFileName = jsonObject.optString("lyricFileName", null);
        int timePlay = jsonObject.getInt("timePlay");

        Users curUser = (Users) request.getSession().getAttribute("user");

        Songs song = new Songs();
        song.setTitle(songName);
        song.setDescription(description);
        song.setImage("image/"+imageFileName);
        song.setSong_data("songdata/"+mp3FileName);
        song.setLyric("lyric/"+lyricFileName);
        song.setStatus(EnumStatus.AVAILABLE);
        song.setTime_play(timePlay);
        song.setRating(0);
        song.setId_artist(curUser.getUsername());
        if(!songsDAO.insert(song))
        {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{ \"result\": " + false + " }");
        }
        SongDetailDAO songDetailDAO = new SongDetailDAO();
        SongDetail songDetail = new SongDetail(songsDAO.getLastestID(), writtenBy,producedBy, new Date(new java.util.Date().getTime()));
        boolean result = songDetailDAO.insert(songDetail);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{ \"result\": " + result + " }");
    }
}