package com.example.soundsephere.dao;

import com.example.soundsephere.model.Songs;
import com.example.soundsephere.model.Users;
import com.example.soundsephere.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class SongsDAO extends SoundSysDAO<Songs, Integer> {
    private static final String INSERT_SONG_QUERY =
            "INSERT INTO [songs] ([title], [id_artist], [genre_id], [description], [time_play], [song_data], [image], [lyric], [rating], [status]) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_SONG_BY_ID_QUERY = "SELECT * FROM user_listened join songs on user_listened.song_id = songs.id  WHERE user_id = ?";
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

    public List<Songs> selectAllSongById(int id) {
        Connection conn = JDBCUtil.getConnection();
        List<Songs> songs = null;
        if (conn != null) {
            try (PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SONG_BY_ID_QUERY)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                songs = new LinkedList<>();
                while (rs.next()) {
                    Songs song = new Songs();
                    song.setId(rs.getInt("id"));
                    song.setTitle(rs.getString("title"));

                    //lấy tên nghệ sĩ
                    UsersDAO usersDAO = new UsersDAO();
                    Users artistName = usersDAO.selectById(rs.getInt("id_artist"));

                    song.setArtistName(artistName.getName());
                    song.setGenre_id(rs.getInt("genre_id"));
                    song.setDescription(rs.getString("description"));
                    song.setTime_play(rs.getInt("time_play"));
                    song.setSong_data(rs.getString("song_data"));
                    song.setImage(rs.getString("image"));
                    song.setLyric(rs.getString("lyric"));
                    song.setRating(rs.getFloat("rating"));
                    songs.add(song);
                }
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
        return songs;
    }

    public List<Songs> searchSong(String search) {
        Connection conn = JDBCUtil.getConnection();
        List<Songs> songs = null;
        if (conn != null) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM songs WHERE title LIKE ?")) {
                ps.setString(1, "%" + search + "%");
                ResultSet rs = ps.executeQuery();
                songs = new LinkedList<>();
                while (rs.next()) {
                    Songs song = new Songs();
                    song.setId(rs.getInt("id"));
                    song.setTitle(rs.getString("title"));

                    //lấy tên nghệ sĩ
                    UsersDAO usersDAO = new UsersDAO();
                    Users artistName = usersDAO.selectById(rs.getInt("id_artist"));
                    song.setArtistName(artistName.getName());

                    song.setId_artist(rs.getInt("id_artist"));
                    song.setGenre_id(rs.getInt("genre_id"));
                    song.setDescription(rs.getString("description"));
                    song.setTime_play(rs.getInt("time_play"));
                    song.setSong_data(rs.getString("song_data"));
                    song.setImage(rs.getString("image"));
                    song.setLyric(rs.getString("lyric"));
                    song.setRating(rs.getFloat("rating"));
                    songs.add(song);
                }
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
        return songs;
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
