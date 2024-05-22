package com.example.soundsephere.dao;

import com.example.soundsephere.model.SongDetail;
import com.example.soundsephere.utils.HandleExeption;
import com.example.soundsephere.utils.JDBCUtil;

import java.sql.*;

public class SongDetailDAO {
    private static final String SELECT_DETAILSONG_BY_ID = "select * from song_detail where song_id=? ;";

    public SongDetail getSongDetailByIDSong(int song_id)
    {
        System.out.println(SELECT_DETAILSONG_BY_ID);
        SongDetail songDetail = null;

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_DETAILSONG_BY_ID))
        {
            preparedStatement.setInt(1, song_id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id_song = rs.getInt("song_id");
                String written_by = rs.getString("written_by");
                String produced_by = rs.getString("produced_by");
                Date date_release = rs.getDate("date_release");

                songDetail = new SongDetail(id_song, written_by, produced_by, date_release);
            }

        }catch (SQLException exception)
        {
            HandleExeption.printSQLException(exception);
        }
        return songDetail;
    }
}
