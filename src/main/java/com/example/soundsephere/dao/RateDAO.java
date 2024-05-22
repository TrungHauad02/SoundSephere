package com.example.soundsephere.dao;

import java.sql.Connection;

import com.example.soundsephere.enumModel.EnumStatus;
import com.example.soundsephere.model.Rating;
import com.example.soundsephere.model.Songs;
import com.example.soundsephere.utils.JDBCUtil;
import com.example.soundsephere.utils.HandleExeption;
import jakarta.servlet.RequestDispatcher;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RateDAO {

    private static final String INSERT_RATING = "INSERT INTO RATING"
            + " (user_id, song_id, rating) "
            + "VALUES (?, ?, ?)";
    ;

    private static final String SELECT_RATING_BY_USERNAME_IDSONG = "SELECT * FROM rating where " +
            "user_id= ?  " +
            "and song_id= ?;";

    public boolean insertVoteRate(String user_id, int song_id, int point)
    {
        System.out.println(INSERT_RATING);
        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_RATING)) {
            preparedStatement.setString(1, user_id);
            preparedStatement.setInt(2, song_id);
            preparedStatement.setFloat(3, point);

            preparedStatement.executeUpdate();
            System.out.println("Tài khoản " + user_id + "đã vote " + point + "cho bài hát có id" + song_id);
            return true;
        } catch (SQLException exception) {
            HandleExeption.printSQLException(exception);
        }
        return false;
    }

    public Rating getVoteRate(String user_id, int song_id)
    {
        System.out.println(SELECT_RATING_BY_USERNAME_IDSONG);
        Rating rate = null;

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_RATING_BY_USERNAME_IDSONG))
        {
            preparedStatement.setString(1, user_id);
            preparedStatement.setInt(2, song_id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String username = rs.getString("user_id");
                int id_song = rs.getInt("song_id");
                int point = rs.getInt("rating");

                rate = new Rating(username, id_song, point);
            }

        }catch (SQLException exception)
        {
            HandleExeption.printSQLException(exception);
        }
        return rate;
    }
}
