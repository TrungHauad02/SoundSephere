package com.example.soundsephere.dao;

import com.example.soundsephere.enumModel.EnumStatus;
import com.example.soundsephere.enumModel.EnumTypePlaylist;
import com.example.soundsephere.model.Playlists;
import com.example.soundsephere.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class PlaylistsDAO extends SoundSysDAO<Playlists, Integer> {
    private static final String INSERT_PLAYLIST_QUERY =
            "INSERT INTO playlists (name, user_id, type, status) " +
                    "VALUES (?, ?, ?, ?)";

    private static final String ALBUM_COUNT_BY_ID_ARTIST_QUERY =
            "SELECT COUNT(*) AS playlist_count\n" +
                    "FROM playlists\n" +
                    "WHERE user_id = ? AND type = 'album';\n";

    public static final String SELECT_ALBUM_BY_ID_ARTIST_QUERY =
            "SELECT id, name, user_id, type, status\n" +
                    "FROM playlists\n" +
                    "WHERE user_id = ? AND type = 'album';\n";

    public boolean insert(Playlists entity) {
        Connection conn = JDBCUtil.getConnection();
        boolean result = true;
        if (conn != null) {
            try (PreparedStatement ps = conn.prepareStatement(INSERT_PLAYLIST_QUERY)) {
                ps.setString(1, entity.getName());
                ps.setInt(2, entity.getId());
                ps.setString(3,entity.getType().name().toLowerCase());
                ps.setString(4,entity.getStatus().name().toLowerCase());

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

    public boolean update(Playlists entity) {
        return false;
    }

    public boolean delete(Integer id) {
        return false;
    }

    public Playlists selectById(Integer id) {
        return null;
    }

    public List<Playlists> selectAll() {
        return null;
    }

    public List<Playlists> selectBySql(String sql, Object... args) {
        List<Playlists> lstPlaylist = new LinkedList<>();
        Connection conn = JDBCUtil.getConnection();
        if(conn != null){
            try(PreparedStatement ps = conn.prepareStatement(sql)){
                for (int i = 0; i < args.length; i++) {
                    ps.setObject(i + 1, args[i]);
                }
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    Playlists playlist = new Playlists();
                    playlist.setId(rs.getInt("id"));
                    playlist.setName(rs.getString("name"));
                    playlist.setUser_id(rs.getInt("user_id"));
                    playlist.setType(EnumTypePlaylist.valueOf(rs.getString("type").toUpperCase()));
                    playlist.setStatus(EnumStatus.valueOf(rs.getString("status").toUpperCase()));
                    lstPlaylist.add(playlist);
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
        return lstPlaylist;
    }

    public int playlistCount(int idArtist){
        int count = 0;
        Connection conn = JDBCUtil.getConnection();
        if (conn != null) {
            try (PreparedStatement ps = conn.prepareStatement(ALBUM_COUNT_BY_ID_ARTIST_QUERY)) {
                ps.setInt(1, idArtist);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    count = rs.getInt("playlist_count");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return count;
    }
}
