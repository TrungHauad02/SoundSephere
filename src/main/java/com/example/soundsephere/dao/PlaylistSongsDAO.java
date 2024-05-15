package com.example.soundsephere.dao;

import com.example.soundsephere.model.PlaylistSongs;
import com.example.soundsephere.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class PlaylistSongsDAO extends SoundSysDAO<PlaylistSongs, Integer>{
    private static final String INSERT_SONG_TO_PLAYLIST_QUERY = "INSERT INTO playlist_songs (playlist_id, song_id) " +
            "VALUES (?,?);";
    public static final String SELECT_ALL_SONG_IN_PLAYLIST_QUERY =
            "SELECT ps.playlist_id, ps.song_id " +
                    "FROM playlist_songs ps" +
                    "WHERE playlist_id = ?";
    public boolean insert(PlaylistSongs entity) {
        Connection conn = JDBCUtil.getConnection();
        boolean result = true;
        if (conn != null) {
            try (PreparedStatement ps = conn.prepareStatement(INSERT_SONG_TO_PLAYLIST_QUERY)) {
                ps.setInt(1, entity.getPlaylist_id());
                ps.setInt(2, entity.getSong_id());

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

    public boolean update(PlaylistSongs entity) {
        return false;
    }

    public boolean delete(Integer id) {
        return false;
    }

    public PlaylistSongs selectById(Integer id) {
        return null;
    }

    public List<PlaylistSongs> selectAll() {
        return null;
    }

    protected List<PlaylistSongs> selectBySql(String sql, Object... args) {
        List<PlaylistSongs> lstPlaylistSongs = new LinkedList<>();
        Connection conn = JDBCUtil.getConnection();
        if(conn != null){
            try(PreparedStatement ps = conn.prepareStatement(sql)){
                for (int i = 0; i < args.length; i++) {
                    ps.setObject(i + 1, args[i]);
                }
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    PlaylistSongs playlistSongs = new PlaylistSongs();
                    playlistSongs.setSong_id(rs.getInt("song_id"));
                    playlistSongs.setPlaylist_id(rs.getInt("playlist_id"));
                    lstPlaylistSongs.add(playlistSongs);
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
        return lstPlaylistSongs;
    }
}
