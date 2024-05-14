package com.example.soundsephere.dao;

import com.example.soundsephere.MyUtils;
import com.example.soundsephere.enumModel.EnumStatus;
import com.example.soundsephere.model.Genre;
import com.example.soundsephere.model.Songs;
import com.example.soundsephere.model.Users;
import com.example.soundsephere.utils.HandleExeption;
import com.example.soundsephere.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class SongsDAO extends SoundSysDAO<Songs, Integer> {
    private static final String INSERT_SONG_QUERY =
            "INSERT INTO [songs] ([title], [id_artist], [genre_id], [description], [time_play], [song_data], [image], [lyric], [rating], [status]) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_SONGS_QUERY = "SELECT s.*, a.name AS artist_name, g.name AS genre_name\n" +
            "FROM songs s\n" +
            "JOIN users a ON s.id_artist = a.id\n" +
            "JOIN genre g ON s.genre_id = g.id\n" +
            "WHERE s.status IN ('available', 'unavailable');";
    private static final String BLOCK_SONG_BY_ID = "UPDATE songs SET status = 'unavailable' WHERE id = ?";
    private static final String DELETE_SONG_BY_ID ="DELETE FROM songs WHERE id = ?" ;

    public boolean insert(Songs entity) {
        Connection conn = JDBCUtil.getConnection();
        //Connection conn = MyUtils.getConnection();
        boolean result = true;
        if (conn != null) {
            try (PreparedStatement ps = conn.prepareStatement(INSERT_SONG_QUERY)) {
                ps.setString(1, entity.getTitle());
                ps.setString(2, entity.getId_artist());
                ps.setString(3, entity.getGenre_id());
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
        Connection connection = MyUtils.getConnection();
        List<Songs> songsList = new ArrayList<>();
        //Connection connection = JDBCUtil.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(SELECT_ALL_SONGS_QUERY);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String title = rs.getString("title");
                String id_artist = rs.getString("id_artist");
                String genre_id = rs.getString("genre_id");
                String description = rs.getString("description");
                int time_play = rs.getInt("time_play");
                String song_data = rs.getString("song_data");
                String image = rs.getString("image");
                String lyric = rs.getString("lyric");
                float rating = rs.getFloat("rating");
                EnumStatus status = EnumStatus.valueOf(rs.getString("status").toUpperCase());
                Songs song = new Songs(id, title, id_artist, genre_id, description, time_play, song_data, image, lyric, rating, status);

                Users user = new Users();
                user.setName(rs.getString("artist_name"));
                song.setUsers(user);

                Genre genre = new Genre();
                genre.setName(rs.getString("genre_name"));
                song.setGenre(genre);

                songsList.add(song);
            }

        } catch (SQLException e) {
            HandleExeption.printSQLException(e);//
        }
        return songsList;
    }

    public boolean deleteSongById(String songId) throws SQLException {
        try (
                Connection connection = MyUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_SONG_BY_ID)) {
            statement.setString(1, songId);

            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        }
    }


    public boolean blockSongById(String songId) throws SQLException {

        try (
                Connection connection = MyUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(BLOCK_SONG_BY_ID)) {
            statement.setString(1, songId);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    protected List<Songs> selectBySql(String sql, Object... args) {
        return null;
    }
}
