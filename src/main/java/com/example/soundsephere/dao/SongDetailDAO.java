package com.example.soundsephere.dao;

import com.example.soundsephere.model.SongDetail;
import com.example.soundsephere.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SongDetailDAO {
    private final static String INSERT_SONG_DETAIL_QUERY =
            "INSERT INTO song_detail (song_id, written_by, produced_by, date_release) VALUES (?,?,?,?)";
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
}
