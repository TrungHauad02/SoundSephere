package com.example.soundsephere.controller;

import com.example.soundsephere.dao.RateDAO;
import com.example.soundsephere.model.Rating;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

public class RateController extends HttpServlet {
    RateDAO rateDAO = null;

    public RateController()
    {
        rateDAO = new RateDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        System.out.println(action);

        try{
            switch(action){
                case "getRateData":
                    GetPointData(request, response);
                    break;
            }
        }catch (Exception ex)
        {
            throw new ServletException(ex);
        }
    }


    protected void GetPointData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            HttpSession session = request.getSession();
            String username = (String) session.getAttribute("currentUserLogin");
            int id_song = Integer.parseInt(request.getParameter("id_song"));
            Rating rateCurrent = GetData("user2", id_song);
            if(rateCurrent != null)
            {
                int point = (int) rateCurrent.getRating();
                PrintWriter out = response.getWriter();

                out.println("<div class=\"rating\">");
                // Điều chỉnh radio button dựa trên điểm đã nhận được
                for (int i = 5; i >= 1; i--) {
                    out.println("<input type=\"radio\" id=\"star" + i + "\" name=\"rating\" value=\"" + i + "\"");
                    // Nếu điểm hiện tại bằng i, đánh dấu input này là checked
                    if (point == i) {
                        out.println(" checked");
                    }
                    out.println("/><label for=\"star" + i + "\"><i class=\"fas fa-star\"></i></label>");
                }
                out.println("</div>");
            }else {

            }
        } catch (Exception e){
            PrintWriter out = response.getWriter();
            out.println("<h3 style='color: red;'>Lấy thông tin thất bại</h3>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        System.out.println(action);

        try{
            switch(action){
                case "submitRateData":
                    SubmitRating(request,response);
                    break;
            }
        }catch (Exception ex)
        {
            throw new ServletException(ex);
        }
    }

        private void SubmitRating(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            try {
                HttpSession session = request.getSession();
                String username = (String) session.getAttribute("currentUserLogin");
                int id_song = Integer.parseInt(request.getParameter("id_song"));
                int point = Integer.parseInt(request.getParameter("point"));
                boolean check = rateDAO.insertVoteRate("user2", id_song, point);
                if (check) {
                    PrintWriter out = response.getWriter();
                    out.println("<div class=\"alert alert-success\" role=\"alert\">Rating submitted successfully.</div>");
                }
            }catch(Exception e)
            {
                PrintWriter out = response.getWriter();
                out.println("<h3 style='color: red;'>Đánh giá bài hát không thành công</h3>");
            }
        }


    //Service
    protected Rating GetData(String username, int id_song)
    {
        return rateDAO.getVoteRate(username, id_song);
    }
}