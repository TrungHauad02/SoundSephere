package com.example.soundsephere.controller;


import com.example.soundsephere.dao.UsersDAO;
import com.example.soundsephere.model.Users;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/User")
public class UserController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    UsersDAO usersDAO;

    public void init(ServletConfig config) throws ServletException {
        usersDAO = new UsersDAO();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        switch (action) {
            case "/login":
                login(request, response);
                break;
            case "/artist_login":
                artistLogin(request, response);
                break;
            default:
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/User/artist_login");
        dispatcher.forward(request, response);
    }

    private void artistLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Users curUser = usersDAO.selectById(6);
        HttpSession session = request.getSession();
        session.setAttribute("curUser", curUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/user/artist_main.jsp");
        dispatcher.forward(request, response);
    }
}
