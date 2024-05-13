package com.example.soundsephere.controller;


import com.example.soundsephere.dao.SongsDAO;
import com.example.soundsephere.model.Songs;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/Search")
public class SearchController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private SongsDAO songsDAO;

    public void init() {
        songsDAO = new SongsDAO();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "goToSearch":
                goToSearch(request, response);
                break;
            case "searchSong":
                searchSong(request, response);
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
    public void goToSearch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/dang/search_main.jsp");
        dispatcher.forward(request, response);
    }

    public void searchSong(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("searchInput");
        List<Songs> listSongs = songsDAO.searchSong(search);
        System.out.println(listSongs);
        request.setAttribute("listSongs", listSongs);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/dang/search_result.jsp");
        dispatcher.forward(request, response);
    }

}