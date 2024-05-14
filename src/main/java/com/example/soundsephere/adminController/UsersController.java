package com.example.soundsephere.adminController;

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
import java.util.List;

@WebServlet("/Users")
public class UsersController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    UsersDAO usersDAO;

    public void init(ServletConfig config) throws ServletException {
        usersDAO = new UsersDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        switch (action) {
            case "/ad_listUsers":
                showListUsers(request, response);
                break;
            case "/ad_ArtistApproval":
                showListApproval(request, response);
            default:
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

    private void showListApproval(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Users> usersList = usersDAO.selectAllApproval();
        request.setAttribute("usersList", usersList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/admin_approvals.jsp");
        dispatcher.forward(request, response);

    }

    private void showListUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Users> usersList = usersDAO.selectAll();
        request.setAttribute("usersList", usersList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/admin_users.jsp");
        dispatcher.forward(request, response);
    }

}

