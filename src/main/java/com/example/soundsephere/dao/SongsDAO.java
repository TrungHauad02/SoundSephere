package com.example.soundsephere.dao;

import com.example.soundsephere.MyUtils;
import com.example.soundsephere.enumModel.EnumStatus;
import com.example.soundsephere.model.Genre;
import com.example.soundsephere.enumModel.EnumStatus;
import com.example.soundsephere.model.Songs;
import com.example.soundsephere.utils.HandleExeption;
import com.example.soundsephere.model.Users;
import com.example.soundsephere.utils.HandleExeption;
import com.example.soundsephere.model.Users;
import com.example.soundsephere.utils.JDBCUtil;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;

public class SongsDAO {
    private  static final String SELECT_ALL_SONG = "SELECT * FROM songs";
    private static final String INSERT_SONG_QUERY =
            "INSERT INTO [songs] ([title], [id_artist], [genre_id], [description], [time_play], [song_data], [image], [lyric], [rating], [status]) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_SONGS_QUERY = "SELECT s.*, a.name AS artist_name, g.name AS genre_name\n" +
            "FROM songs s\n" +
            "JOIN users a ON s.id_artist = a.username\n" +
            "JOIN genre g ON s.genre_id = g.name\n" +
            "WHERE s.status IN ('available', 'unavailable', 'deleted');";
    private static final String BLOCK_SONG_BY_ID = "UPDATE songs SET status = 'unavailable' WHERE id = ?";
    private static final String DELETE_SONG_BY_ID ="UPDATE songs SET status = 'deleted' WHERE id = ?" ;
    private static final String SELECT_SONG_BY_ID = "SELECT * FROM songs where id=?; ";
    private static final String SELECT_LIST_SONG_RANDOM =
            "SELECT * FROM songs " +
                    "WHERE id <> ? "+
                    "ORDER BY RAND() " +
                    "LIMIT ?;";
    private static final String SELECT_ALL_SONG_BY_ID_QUERY = "SELECT * FROM user_listened join songs on user_listened.song_id = songs.id  WHERE user_id = ?";
    private static final String SONGS_COUNT_BY_ID_ARTIST_QUERY =
        "SELECT COUNT(*) AS song_count\n" +
                "FROM songs\n" +
                "WHERE id_artist = ?;\n";

    public static final String SELECT_SONGS_BY_ID_ARTIST_QUERY =
            "SELECT id, title,id_artist, description, time_play, song_data, image, lyric, rating, status\n" +
                    "FROM songs\n" +
                    "WHERE id_artist = ?;\n";
    private static final String SELECT_LASTED_ID_QUERY =
            "SELECT TOP 1 id\n" +
                    "FROM songs\n" +
                    "ORDER BY id DESC;";

    public static final String SELECT_SONGS_IN_PLAYLIST_QUERY =
            "SELECT songs.*\n" +
                    "FROM playlist_songs\n" +
                    "JOIN songs ON playlist_songs.song_id = songs.id\n" +
                    "WHERE playlist_id = ?;\n";

    public boolean insert(Songs entity) {
        Connection conn = JDBCUtil.getConnection();
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

    public List<Songs> selectAllSongById(String username) {
        Connection conn = JDBCUtil.getConnection();
        List<Songs> songs = null;
        if (conn != null) {
            try (PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SONG_BY_ID_QUERY)) {
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();
                songs = new LinkedList<>();
                while (rs.next()) {
                    Songs song = new Songs();
                    song.setId(rs.getInt("id"));
                    song.setTitle(rs.getString("title"));

                    //lấy tên nghệ sĩ
                    UsersDAO usersDAO = new UsersDAO();
                    Users artistName = usersDAO.selectById(rs.getString("id_artist"));

                    song.setArtistName(artistName.getName());
                    song.setGenre_id(rs.getString("genre_id"));
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
                    Users artistName = usersDAO.selectById(rs.getString("id_artist"));
                    song.setArtistName(artistName.getName());

                    song.setId_artist(rs.getString("id_artist"));
                    song.setGenre_id(rs.getString("genre_id"));
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

    public List<Songs> SelectAllSongs(){
        List<Songs> songs = new ArrayList<>();
        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_SONG))
        {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
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

                UsersDAO usersDAO = new UsersDAO();
                Users u = usersDAO.selectById(id_artist);

                Songs song = new Songs(id, title, id_artist, genre_id, description, time_play, song_data, image, lyric, rating, status);
                song.setArtistName(u.getName());

                songs.add(song);
            }
        } catch (SQLException e) {
            HandleExeption.printSQLException(e);
        }
        return songs;
    }

    public boolean update(Songs entity) {
        return false;
    }

    public boolean delete(Integer id) {
        return false;
    }

    public Songs selectById(String idSong) {
        System.out.println(SELECT_SONG_BY_ID);
        Songs song = null;
        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SONG_BY_ID))
        {
            preparedStatement.setString(1, idSong);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String artist = rs.getString("id_artist");
                String description = rs.getString("description");
                int time_play = rs.getInt("time_play");
                String song_data = rs.getString("song_data");
                String image = rs.getString("image");
                String lyric = rs.getString("lyric");
                Float rating = rs.getFloat("rating");
                EnumStatus status = EnumStatus.valueOf(rs.getString("status").toUpperCase());

                song = new Songs(id,title, artist, description,
                        time_play, song_data, image, lyric, rating, status);
            }

        }catch (SQLException exception)
        {
            HandleExeption.printSQLException(exception);
        }
        return song;
    }

    public List<Songs> selectAll() {
        //Connection connection = JDBCUtil.getConnection();
        List<Songs> songsList = new ArrayList<>();
        Connection connection = JDBCUtil.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(SELECT_ALL_SONGS_QUERY);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
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

    public List<Songs> selectRandomSong(int exceptionID, int nums) {
        List<Songs> listSong = new ArrayList<>();;

        System.out.println(SELECT_LIST_SONG_RANDOM);
        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LIST_SONG_RANDOM))
        {
            preparedStatement.setInt(1, exceptionID);
            preparedStatement.setInt(2, nums);
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

    public List<Songs> selectBySql(String sql, Object... args) {
        Connection conn = JDBCUtil.getConnection();
        List<Songs> lstSong = new LinkedList<>();
        if (conn != null) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                for (int i = 0; i < args.length; i++) {
                    ps.setObject(i + 1, args[i]);
                }
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    Songs song = new Songs();
                    song.setId(resultSet.getInt("id"));
                    song.setTitle(resultSet.getString("title"));
                    song.setId_artist(resultSet.getString("id_artist"));
                    song.setGenre_id(resultSet.getString("genre_id"));
                    song.setDescription(resultSet.getString("description"));
                    song.setTime_play(resultSet.getInt("time_play"));
                    song.setSong_data(resultSet.getString("song_data"));
                    song.setImage(resultSet.getString("image"));
                    song.setLyric(resultSet.getString("lyric"));
                    song.setRating(resultSet.getFloat("rating"));
                    song.setStatus(EnumStatus.valueOf(resultSet.getString("status").toUpperCase()));
                    lstSong.add(song);
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
        return lstSong;
    }

    public int songsCount(String idArtist){
        int count = 0;
        Connection conn = JDBCUtil.getConnection();
        if (conn != null) {
            try (PreparedStatement ps = conn.prepareStatement(SONGS_COUNT_BY_ID_ARTIST_QUERY)) {
                ps.setString(1, idArtist);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    count = rs.getInt("song_count");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return count;
    }

    public int getLastestID(){
        int result = 1;
        Connection connection = JDBCUtil.getConnection();
        if (connection != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LASTED_ID_QUERY)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    result = resultSet.getInt("id");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

}
