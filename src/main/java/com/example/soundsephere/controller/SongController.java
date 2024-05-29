package com.example.soundsephere.controller;

import com.example.soundsephere.dao.PlaylistSongsDAO;
import com.example.soundsephere.dao.SongDetailDAO;
import com.example.soundsephere.dao.SongsDAO;
import com.example.soundsephere.enumModel.EnumStatus;
import com.example.soundsephere.model.PlaylistSongs;
import com.example.soundsephere.model.SongDetail;
import com.example.soundsephere.model.Songs;
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
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/SongPlay")
public class SongController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private SongsDAO songDAO;
    private PlaylistSongsDAO playListSongsDAO;
    private SongDetailDAO songDetailDAO;

    public void init() {
        songDAO = new SongsDAO();
        songDetailDAO = new SongDetailDAO();
        playListSongsDAO = new PlaylistSongsDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if (action == null){
                action = request.getPathInfo();
            }
            if (action != null)
                switch (action) {
                    case "getSong":
                        getListSongHaveCurrentSong(request, response);
                        break;
                    case "getDetailSong":
                        getDetailSong(request, response);
                        break;
                    case "getListSongFromList":
                        getListSongFromList(request, response);
                        break;
                    case "/getSong":
                        getListSongHaveCurrentSong(request, response);
                        break;
                    case "/getDetailSong":
                        getDetailSong(request, response);
                        break;
                    case "/getListSongFromList":
                        getListSongFromList(request, response);
                        break;
                    default:
                        System.out.println("default");
                        break;
            }
            else {
                List<Songs> listSong = ListSong(request, response);
                request.setAttribute("listSong", listSong);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/user/layout_song.jsp");
                dispatcher.forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            PrintWriter out = response.getWriter();
            out.println("<h3 style='color: red;'>Error: " + e.getMessage() + "</h3>");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();

        if ("/addNewSong".equals(action)) {
            addNewSong(request, response);
        } else {
            doGet(request, response);
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if ("/deleteSong".equals(action)) {
            deleteSong(request, response);
        } else {
            doPost(request, response);
        }
    }

    private void deleteSong(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        JSONObject jsonObject = new JSONObject(sb.toString());

        Integer songId = jsonObject.getInt("songId");

        playListSongsDAO.deleteSong(songId);
        boolean success = songDetailDAO.delete(songId);
        if(success) {
            success = songDAO.delete(songId);
        }

        if (success) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private List<Songs> ListSong(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return songDAO.selectAll();
    }

    private void getListSongFromList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Songs> listSongCurrentPlay = new ArrayList<>();

        int idPlaylist = Integer.parseInt(request.getParameter("idPlaylist"));

        String status = getStatusOfPlayList(idPlaylist);
        if(status.equals("available")) { // Sử dụng equals() để so sánh chuỗi

            List<Songs> listSongRandomByIDPlayList = getListSongFromByIDList(idPlaylist);

            listSongCurrentPlay.addAll(listSongRandomByIDPlayList);

            request.setAttribute("listSongCurrentPlay", listSongCurrentPlay);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/user/layout_playsong.jsp");
            dispatcher.forward(request, response);
        } else {
            // Nếu playlist không có sẵn, chuyển hướng sang trang báo lỗi
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<html><head><title>Error</title></head><body>");
            out.println("<h1>List nhạc không có sẵn hoặc bị khóa!</h1>");
            out.println("</body></html>");
        }
    }


    private void getDetailSong(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int idSongCurrent = Integer.parseInt(request.getParameter("id_song"));
            SongDetail songDetail = getSongDetail(idSongCurrent);
            Songs currentSong = getSongByID(idSongCurrent);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            PrintWriter out = response.getWriter();
            out.println("<h3>Title: " + currentSong.getTitle() + "</h3>");
            out.println("<h3>Name Artist: " + currentSong.getArtistName() + "</h3>");
            out.println("<h3>Rating: " + currentSong.getRating() + "</h3>");
            out.println("<h3>Written By: " + songDetail.getWritten_by() + "</h3>");
            out.println("<h3>Produced By: " + songDetail.getProduced_by() + "</h3>");
            out.println("<h3>Date Release: " + dateFormat.format(songDetail.getDate_release()) + "</h3>");
        } catch (Exception e) {
            PrintWriter out = response.getWriter();
            out.println("<h3 style='color: red;'>Lấy thông tin thất bại</h3>");
        }
    }

    private void getListSongHaveCurrentSong(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Songs> listSongCurrentPlay = new ArrayList<>();

        int idSongCurrent = Integer.parseInt(request.getParameter("idSong"));

        Songs songCurrent = getSongByID(idSongCurrent);
        List<Songs> listSongRandom = getListSongsRandomExceptionID(idSongCurrent);

        listSongCurrentPlay.add(songCurrent);
        listSongCurrentPlay.addAll(listSongRandom);

        request.setAttribute("listSongCurrentPlay", listSongCurrentPlay);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/user/layout_playsong.jsp");
        dispatcher.forward(request, response);
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
        song.setImage("myimages/" + imageFileName);
        song.setSong_data("audioSongs/" + mp3FileName);
        song.setLyric("lyricsSongs/" + lyricFileName);
        song.setStatus(EnumStatus.AVAILABLE);
        song.setTime_play(timePlay);
        song.setRating(0);
        song.setId_artist(curUser.getUsername());
        if (!songDAO.insert(song)) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{ \"result\": false }");
            return;
        }

        SongDetail songDetail = new SongDetail(songDAO.getLastestID(), writtenBy, producedBy, new Date(new java.util.Date().getTime()));
        boolean result = songDetailDAO.insert(songDetail);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{ \"result\": " + result + " }");
    }

    // Service methods
    protected SongDetail getSongDetail(int idSong) {
        return songDetailDAO.getSongDetailByIDSong(idSong);
    }

    protected Songs getSongByID(int idSong) {
        return songDAO.selectById(String.valueOf(idSong));
    }

    protected List<Songs> getListSongsRandomExceptionID(int idSongCurrent) {
        return songDAO.selectRandomSong(idSongCurrent, 9);
    }

    protected List<Songs> getListSongFromByIDList(int id) {
        return playListSongsDAO.selectRandomSongByID(id);
    }

    protected String getStatusOfPlayList(int id)
    {
        return playListSongsDAO.getStatusById(id);
    }
}
