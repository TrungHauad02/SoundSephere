package com.example.soundsephere.dao;

import com.example.soundsephere.model.SongDetail;
import com.example.soundsephere.utils.HandleExeption;
import com.example.soundsephere.utils.JDBCUtil;

import java.sql.*;

public class SongDetailDAO {
    private static final String SELECT_DETAILSONG_BY_ID = "select * from song_detail where song_id=? ;";

    private final static String INSERT_SONG_DETAIL_QUERY =
            "INSERT INTO song_detail (song_id, written_by, produced_by, date_release) VALUES (?,?,?,?)";
    private final static String DELETE_SONG_DETAIL_BY_ID = "DELETE FROM [song_detail] WHERE song_id = ?;";

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

    public boolean insert(SongDetail songDetail){
        Connection conn = JDBCUtil.getConnection();
        boolean result = true;
        if (conn != null) {
            try (PreparedStatement ps = conn.prepareStatement(INSERT_SONG_DETAIL_QUERY)) {
                ps.setInt(1, songDetail.getSong_id());
                ps.setString(2, songDetail.getWritten_by());
                ps.setString(3, songDetail.getProduced_by());
                ps.setDate(4, (Date) songDetail.getDate_release());

                int rowsAffected = ps.executeUpdate();
                result = rowsAffected > 0;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return result;
    }

    public boolean delete(Integer id) {
        Connection conn = JDBCUtil.getConnection();
        boolean result = true;
        if (conn != null) {
            try (PreparedStatement ps = conn.prepareStatement(DELETE_SONG_DETAIL_BY_ID)) {
                ps.setInt(1, id);

                int rowsAffected = ps.executeUpdate();
                result = rowsAffected > 0;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return result;
    }
}
