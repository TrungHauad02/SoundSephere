package com.example.soundsephere.dao;

import com.example.soundsephere.enumModel.EnumStatus;
import com.example.soundsephere.model.PlaylistSongs;
import com.example.soundsephere.model.Songs;
import com.example.soundsephere.utils.HandleExeption;
import com.example.soundsephere.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PlaylistSongsDAO extends SoundSysDAO<PlaylistSongs, Integer>{
    private static final String INSERT_SONG_TO_PLAYLIST_QUERY = "INSERT INTO playlist_songs (playlist_id, song_id) " +
            "VALUES (?,?);";
    public static final String SELECT_ALL_SONG_IN_PLAYLIST_QUERY =
            "SELECT ps.playlist_id, ps.song_id " +
                    "FROM playlist_songs ps" +
                    "WHERE playlist_id = ?";
    private static final String DELETE_PLAYLIST_SONG_BY_ID_PLAYLIST_QUERY =
            "DELETE FROM playlist_songs WHERE playlist_id = ?";
    private static final String DELETE_PLAYLIST_SONG_BY_ID_SONG_QUERY =
            "DELETE FROM playlist_songs WHERE song_id = ?;";
    private static final String DELETE_PLAYLIST_SONG_BY_PLAYLIST_SONG_QUERY =
            "DELETE FROM playlist_songs WHERE playlist_id = ? AND song_id = ?;";
    public static final String SELECT_SONG_BY_ID_PLAYLIST =
            "SELECT s.*\n" +
                    "FROM (\n" +
                    "    SELECT TOP 5 song_id\n" +
                    "    FROM playlist_songs\n" +
                    "    WHERE playlist_id = ?\n" +
                    "    ORDER BY NEWID()\n" +
                    ") AS ps\n" +
                    "JOIN songs AS s ON ps.song_id = s.id;\n";

    public static final String SELECT_STATUS_ID_PLAYLIST = "Select status from playlists where id = ?;";

    public String getStatusById(int id) {
        Connection conn = JDBCUtil.getConnection();
        String status = null;
        if (conn != null) {
            try (PreparedStatement ps = conn.prepareStatement(SELECT_STATUS_ID_PLAYLIST)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    status = rs.getString("status");
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
        return status;
    }

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
        Connection conn = JDBCUtil.getConnection();
        boolean result = true;
        if (conn != null) {
            try (PreparedStatement ps = conn.prepareStatement(DELETE_PLAYLIST_SONG_BY_ID_PLAYLIST_QUERY)) {
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
    public boolean deleteSongFromPlaylist(PlaylistSongs playlistSongs) {
        Connection conn = JDBCUtil.getConnection();
        boolean result = true;
        if (conn != null) {
            try (PreparedStatement ps = conn.prepareStatement(DELETE_PLAYLIST_SONG_BY_PLAYLIST_SONG_QUERY)) {
                ps.setInt(1, playlistSongs.getPlaylist_id());
                ps.setInt(2, playlistSongs.getSong_id());

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


    public List<Songs> selectRandomSongByID(int id)
    {
        List<Songs> listSong = new ArrayList<>();;

        System.out.println(SELECT_SONG_BY_ID_PLAYLIST);
        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SONG_BY_ID_PLAYLIST))
        {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next())
            {
                int id_song = rs.getInt("id");
                String title = rs.getString("title");
                String artist = rs.getString("id_artist");
                String description = rs.getString("description");
                int time_play  = rs.getInt("time_play");
                String song_data = rs.getString("song_data");
                String image = rs.getString("image");
                String lyric = rs.getString("lyric");
                Float rating = rs.getFloat("rating");
                EnumStatus status = EnumStatus.valueOf(rs.getString("status").toUpperCase());

                listSong.add(new Songs(id_song, title, artist, description,
                        time_play, song_data, image,  lyric,  rating, status));
            }

        }catch (SQLException exception)
        {
            HandleExeption.printSQLException(exception);
        }
        return listSong;
    }

    public boolean deleteSong(int id){
        Connection conn = JDBCUtil.getConnection();
        boolean result = true;
        if (conn != null) {
            try (PreparedStatement ps = conn.prepareStatement(DELETE_PLAYLIST_SONG_BY_ID_SONG_QUERY)) {
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
