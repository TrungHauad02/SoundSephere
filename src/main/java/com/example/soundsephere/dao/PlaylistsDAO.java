package com.example.soundsephere.dao;

import com.example.soundsephere.enumModel.EnumStatus;
import com.example.soundsephere.enumModel.EnumTypePlaylist;
import com.example.soundsephere.model.Playlists;
import com.example.soundsephere.model.Users;
import com.example.soundsephere.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PlaylistsDAO extends SoundSysDAO<Playlists, Integer> {
    private static final String INSERT_PLAYLIST_QUERY =
            "INSERT INTO playlists (name, user_id, type, status) " +
                    "VALUES (?, ?, ?, ?)";
    private static final String DELETE_PLAYLIST_QUERY =
            "DELETE FROM playlists WHERE id = ?";
    private static final String SELECT_ALL_PLAYLISTS_QUERY = "SELECT p.*, u.name as user_name " +
            "FROM playlists p " +
            "JOIN users u ON p.user_id = u.username " +
            "WHERE p.type = 'playlist'";
    private static final String DELETE_PLAYLIST_BY_ID =  " UPDATE playlists SET status = 'deleted' WHERE id = ?" ;

    private static final String SELECT_PLAYLIST_BY_ID_QUERY = "SELECT * FROM playlists WHERE id = ?";
    private static final String SELECT_ALL_PLAYLIST_BY_USER_ID_QUERY ="SELECT * FROM playlists WHERE user_id = ?";
    private static final String SELECT_NUMBER_OF_SONGS_IN_PLAYLIST_QUERY = "SELECT COUNT(*) FROM playlist_songs WHERE playlist_id = ?";
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
                ps.setString(2, entity.getUser_id());
                ps.setString(3, entity.getType().name().toLowerCase());
                ps.setString(4, entity.getStatus().name().toLowerCase());

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

        Connection connection = JDBCUtil.getConnection();
        List<Playlists> playlists  = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(SELECT_ALL_PLAYLISTS_QUERY);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String user_id = rs.getString("user_id");
                EnumTypePlaylist type = EnumTypePlaylist.valueOf(rs.getString("type").toUpperCase());
                EnumStatus status = EnumStatus.valueOf(rs.getString("status").toUpperCase());

                Users users = new Users();
                users.setName(rs.getString("user_name"));


                Playlists playlist = new Playlists();
                playlist.setId(id);
                playlist.setName(name);
                playlist.setUser_id(user_id);
                playlist.setType(type);
                playlist.setStatus(status);

                playlist.setUsers(users);
                playlists.add(playlist);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return playlists;

    }

    public  int getNumberofsongs(int playlist_id){
        Connection conn = JDBCUtil.getConnection();
        int numberofsongs = 0;
        if (conn != null) {
            try (PreparedStatement ps = conn.prepareStatement(SELECT_NUMBER_OF_SONGS_IN_PLAYLIST_QUERY)) {
                ps.setInt(1, playlist_id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    numberofsongs = rs.getInt(1);
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
        return numberofsongs;
    }

    public List<Playlists> selectAllPlaylistByUserId(String username) {
        Connection conn = JDBCUtil.getConnection();
        List<Playlists> lstPlaylist = new LinkedList<>();
        if (conn != null) {
            try (PreparedStatement ps = conn.prepareStatement(SELECT_ALL_PLAYLIST_BY_USER_ID_QUERY)) {
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Playlists playlist = new Playlists();
                    playlist.setId(rs.getInt("id"));
                    playlist.setName(rs.getString("name"));
                    playlist.setUser_id(rs.getString("user_id"));
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
        return  lstPlaylist;
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
                    playlist.setUser_id(rs.getString("user_id"));
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
    public boolean deletePlaylistById(String playlistId) throws SQLException {
        try (
                Connection connection = JDBCUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_PLAYLIST_BY_ID)) {
            statement.setString(1, playlistId);

            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        }

    }


    public int playlistCount(String idArtist){
        int count = 0;
        Connection conn = JDBCUtil.getConnection();
        if (conn != null) {
            try (PreparedStatement ps = conn.prepareStatement(ALBUM_COUNT_BY_ID_ARTIST_QUERY)) {
                ps.setString(1, idArtist);
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
