package com.example.soundsephere.controller;

import com.example.soundsephere.dao.SongDetailDAO;
import com.example.soundsephere.dao.SongsDAO;
import com.example.soundsephere.model.SongDetail;
import com.example.soundsephere.model.Songs;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SongController extends HttpServlet {
    SongsDAO songDAO = null;
    SongDetailDAO songDetailDAO = null;

    public SongController()
    {
        songDAO = new SongsDAO();
        songDetailDAO = new SongDetailDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try{
            switch (action)
            {
                case "getSong":
                    getListSongHaveCurrentSong(request, response);
                    break;
                case "getDetailSong":
                    getDetailSong(request,response);
                    break;
                default:
                    break;
            }
        }
        catch(Exception e)
        {
            System.out.println(action);
            PrintWriter test = response.getWriter();
            test.println(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void getDetailSong(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            int idSongCurrent = Integer.parseInt(request.getParameter("id_song"));

            // Lấy thông tin chi tiết của bài hát từ id
            SongDetail songDetail = getSongDetail(idSongCurrent);
            // Lấy thông tin cơ bản của bài hát từ id
            Songs currentSong = getSongByID(idSongCurrent);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            PrintWriter out = response.getWriter();
            out.println("<h3>Title: " + currentSong.getTitle() + "</h3>");
            out.println("<h3>ID_artist: " + currentSong.getId_artist() + "</h3>");
            out.println("<h3>Rating: " + currentSong.getRating() + "</h3>");
            out.println("<h3>Written By: " + songDetail.getWritten_by() + "</h3>");
            out.println("<h3>Produced By: " + songDetail.getProduced_by() + "</h3>");
            out.println("<h3>Date Release: " + dateFormat.format(songDetail.getDate_release()) + "</h3>");
        }catch (Exception e){
            PrintWriter out = response.getWriter();
            out.println("<h3 style='color: red;'>Lấy thông tin thất bại</h3>");
        }
    }


    void getListSongHaveCurrentSong(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Songs> listSongCurrentPlay = new ArrayList<>();

        int idSongCurrent = Integer.parseInt(request.getParameter("idSong"));

        Songs songCurrent = getSongByID(idSongCurrent);
        List<Songs> listSongRandom = getListSongsRandomExceptionID(idSongCurrent);

        listSongCurrentPlay.add(songCurrent);
        listSongCurrentPlay.addAll(listSongRandom);

        request.setAttribute("listSongCurrentPlay", listSongCurrentPlay);

        RequestDispatcher dispatcher = request.getRequestDispatcher("user/layout_playsong.jsp");
        dispatcher.forward(request, response);
    }

    //Service
    protected SongDetail getSongDetail(int idSong){
        SongDetail songDetail = songDetailDAO.getSongDetailByIDSong(idSong);
        return songDetail;
    }

    protected Songs getSongByID(int idSong) {
        Songs songSelect = songDAO.selectById(idSong);
        return songSelect;
    }

    protected List<Songs> getListSongsRandomExceptionID(int idSongCurrent) {
        List<Songs> listSong = songDAO.selectRandomSong(idSongCurrent,9);
        return listSong;
    }
}