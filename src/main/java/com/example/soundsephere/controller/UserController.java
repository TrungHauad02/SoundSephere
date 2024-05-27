package com.example.soundsephere.controller;

import com.example.soundsephere.dao.PlaylistsDAO;
import com.example.soundsephere.dao.SongsDAO;
import com.example.soundsephere.dao.UsersDAO;
import com.example.soundsephere.enumModel.EnumRole;
import com.example.soundsephere.enumModel.EnumSex;
import com.example.soundsephere.enumModel.EnumStatus;
import com.example.soundsephere.enumModel.EnumUserStatus;
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

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet("/User")
public class UserController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    UsersDAO usersDAO;
    SongsDAO songsDAO;
    PlaylistsDAO playlistsDAO;
    public void init() {
        usersDAO = new UsersDAO();
        songsDAO = new SongsDAO();
        playlistsDAO = new PlaylistsDAO();
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        switch (action) {
            case "login":
                login(request, response);
                break;
            case "logout":
                request.getSession().removeAttribute("user");
                RequestDispatcher dispatcherLogout = request.getRequestDispatcher("login.jsp");
                dispatcherLogout.forward(request, response);
                break;
            case "goToHome":
                RequestDispatcher dispatcherHome = request.getRequestDispatcher("user/dang/home_main.jsp");
                dispatcherHome.forward(request, response);
                break;
            case "goToArtist":
                RequestDispatcher dispatcherArtist = request.getRequestDispatcher("user/artist_main.jsp");
                dispatcherArtist.forward(request, response);
                break;
            case "register":
                register(request, response);
                break;
            case "showAccount":
                showAccount(request, response);
                break;
            case "editAccount":
                editAccount(request, response);
                break;
            case "updateAccount":
                updateAccount(request, response);
            case "/artist_login":
                artistLogin(request, response);
                break;
            case "/getListSongJson":
                getListSongJson(request, response);
                break;
            case "/getListAlbumJson":
                getListAlbumJson(request, response);
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

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (usersDAO.checkLogin(username, password)) {
            System.out.println("login success");

            Users user = usersDAO.getUserByUsername(username);
            request.getSession().setAttribute("user", user);

            //lấy danh sách playlist của user
            List<Playlists> playlists = playlistsDAO.selectAllPlaylistByUserId(user.getUsername());
            request.getSession().setAttribute("playlists", playlists);
            // lấy danh sách bài hát của user vừa nghe
            List<Songs> recentlyPlayed = songsDAO.selectAllSongById(user.getUsername());
            request.getSession().setAttribute("recentPlayed", recentlyPlayed);

            if ((user.getRole() == EnumRole.ARTIST) && ((user.getRole() == EnumRole.ARTIST) && (user.getStatus().equals(EnumUserStatus.NORMAL)))) {
                System.out.println("Artist");
                RequestDispatcher dispatcher = request.getRequestDispatcher("user/artist_main.jsp");
                dispatcher.forward(request, response);
            } else if((user.getRole() == EnumRole.LISTENER) || ((user.getRole() == EnumRole.ARTIST) && (user.getStatus().equals(EnumUserStatus.PENDING)))) {
                System.out.println("user");
                RequestDispatcher dispatcher = request.getRequestDispatcher("user/dang/home_main.jsp");
                dispatcher.forward(request, response);
            } else if (user.getRole() == EnumRole.MANAGER) {
                System.out.println("admin");
                RequestDispatcher dispatcher = request.getRequestDispatcher("admin/admin_songs.jsp");
                dispatcher.forward(request, response);
            }


        } else {
            request.setAttribute("errorInvalid", "Username or Password is incorrect");
            RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Users user = new Users();
        user.setUsername(request.getParameter("usernameUser"));
        user.setName(request.getParameter("nameUser"));
        user.setEmail(request.getParameter("emailUser"));
        user.setPassword(request.getParameter("passwordUser"));
        String role = Objects.equals(request.getParameter("checkArtist"), "checked") ? "ARTIST" : "LISTENER";
        user.setRole(EnumRole.valueOf(role));

        try {

//            if (user.getPassword().trim().length() <8) {
//                request.setAttribute("errorConfirm", "Password must be at least 8 characters long");
//                RequestDispatcher dispatcher = request.getRequestDispatcher("register.jsp");
//                dispatcher.forward(request, response);
//            }

            if (!usersDAO.checkConfirmPassword(user.getPassword(), request.getParameter("confirmPasswordUser"))) {
                request.setAttribute("errorConfirm", "Password and Confirm Password are not the same");
                RequestDispatcher dispatcher = request.getRequestDispatcher("register.jsp");
                dispatcher.forward(request, response);
            }

            usersDAO.insert(user);
            request.setAttribute("alertMsg", "Register successfully!");
            RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e ) {
            e.printStackTrace();
            request.setAttribute("errorExist", "Username or Email already exists");
            RequestDispatcher dispatcher = request.getRequestDispatcher("register.jsp");
            dispatcher.forward(request, response);
        }

    }

    public void showAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/dang/user_showAccount.jsp");
        dispatcher.forward(request, response);
    }

    public void editAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/dang/user_showAccount_edit.jsp");
        dispatcher.forward(request, response);
    }

    public void updateAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Users user = new Users();
        user.setName(request.getParameter("nameUser"));
        user.setEmail(request.getParameter("emailUser"));
        if (request.getParameter("descriptionUser") == null){
            user.setDescription("");
        } else
        {
            user.setDescription(request.getParameter("descriptionUser").trim());
        }

        user.setSex(EnumSex.valueOf(request.getParameter("sexUser")));
        user.setPassword(request.getParameter("passwordUser"));

        System.out.println(user);

        try {
            usersDAO.update(user);
            request.setAttribute("editProfileMessage", "Update successfully!");

            request.getSession().setAttribute("user", user);

            RequestDispatcher dispatcher = request.getRequestDispatcher("user/dang/user_showAccount.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("editProfileMessage", "Can't update user information");
            RequestDispatcher dispatcher = request.getRequestDispatcher("user/dang/user_showAccount.jsp");
            dispatcher.forward(request, response);
        }
    }
    public  void artistLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Users curUser = usersDAO.selectById(6);
        HttpSession session = request.getSession();
        session.setAttribute("currentUserLogin", curUser);
        int listensCount = usersDAO.listenCount(6);
        int songCount = songsDAO.songsCount(6);
        int albumCount = playlistsDAO.playlistCount(6);
        request.setAttribute("listensCount", listensCount);
        request.setAttribute("songCount", songCount);
        request.setAttribute("albumCount", albumCount);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/user/artist_main.jsp");
        dispatcher.forward(request, response);
    }

    public void getListSongJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Songs> lstSong = songsDAO.selectBySql(SongsDAO.SELECT_SONGS_BY_ID_ARTIST_QUERY, 6);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonSongs = objectMapper.writeValueAsString(lstSong);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(jsonSongs);
    }

    public void getListAlbumJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Playlists> lstPlaylist = playlistsDAO.selectBySql(PlaylistsDAO.SELECT_ALBUM_BY_ID_ARTIST_QUERY, 6);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPlaylist = objectMapper.writeValueAsString(lstPlaylist);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(jsonPlaylist);
    }

}
