package com.example.soundsephere.dao;

import com.example.soundsephere.model.Songs;
import com.example.soundsephere.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SongsDAO extends SoundSysDAO<Songs, Integer> {
    private static final String INSERT_SONG_QUERY =
            "INSERT INTO [songs] ([title], [id_artist], [genre_id], [description], [time_play], [song_data], [image], [lyric], [rating], [status]) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public boolean insert(Songs entity) {
        Connection conn = JDBCUtil.getConnection();
        boolean result = true;
        if (conn != null) {
            try (PreparedStatement ps = conn.prepareStatement(INSERT_SONG_QUERY)) {
                ps.setString(1, entity.getTitle());
                ps.setInt(2, entity.getId_artist());
                ps.setInt(3, entity.getGenre_id());
                ps.setString(4, entity.getDescription());
                ps.setInt(5, entity.getTime_play());
                ps.setString(6, entity.getSong_data());
                ps.setString(7, entity.getImage());
                ps.setString(8, entity.getLyric());
                ps.setFloat(9, entity.getRating());
                ps.setString(10, entity.getStatus().name().toLowerCase());

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

    public boolean update(Songs entity) {
        return false;
    }

    public boolean delete(Integer id) {
        return false;
    }

    public Songs selectById(Integer id) {
        return null;
    }

    public List<Songs> selectAll() {
        return null;
    }

    protected List<Songs> selectBySql(String sql, Object... args) {
        return null;
    }
}
