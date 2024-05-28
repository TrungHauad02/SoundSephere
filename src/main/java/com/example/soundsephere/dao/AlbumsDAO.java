package com.example.soundsephere.dao;

import com.example.soundsephere.enumModel.EnumStatus;
import com.example.soundsephere.enumModel.EnumTypePlaylist;
import com.example.soundsephere.model.Albums;
import com.example.soundsephere.model.Users;
import com.example.soundsephere.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlbumsDAO extends SoundSysDAO<Albums, Integer> {
    private static final String SELECT_ALL_ALBUMS_QUERY = "SELECT p.*, u.name as artist_name " +
            "FROM playlists p " +
            "JOIN users u ON p.user_id = u.username " +
            "WHERE p.type = 'album'";
    private static final String DELETE_ALBUM_BY_ID =  " UPDATE playlists SET status = 'deleted' WHERE id = ?" ;
    @Override
    public List<Albums> selectAll() {
        Connection connection = JDBCUtil.getConnection();
        List<Albums> albums = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(SELECT_ALL_ALBUMS_QUERY);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String user_id = rs.getString("user_id");
                EnumTypePlaylist type = EnumTypePlaylist.valueOf(rs.getString("type").toUpperCase());
                EnumStatus status = EnumStatus.valueOf(rs.getString("status").toUpperCase());

                Users artist = new Users();
                artist.setName(rs.getString("artist_name"));

                Albums album = new Albums();
                album.setId(id);
                album.setName(name);
                album.setUser_id(user_id);
                album.setType(type);
                album.setStatus(status);

                album.setUsers(artist);
                albums.add(album);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return albums;
    }
    public boolean deleteAlbumById(String albumId) throws SQLException {
        try (
                Connection connection = JDBCUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_ALBUM_BY_ID)) {
            statement.setString(1, albumId);

            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        }

    }

    @Override
    public boolean insert( Albums entity) {
        return false;
    }

    @Override
    public boolean update(Albums entity) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public Albums selectById(Integer id) {
        return null;
    }




        @Override
        protected List<Albums> selectBySql (String sql, Object...args){
            return null;
        }
    }
