package com.example.soundsephere.dao;

import com.example.soundsephere.enumModel.EnumStatus;
import com.example.soundsephere.model.Songs;
import com.example.soundsephere.utils.HandleExeption;
import com.example.soundsephere.utils.JDBCUtil;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SongsDAO{
    private static final String INSERT_SONG_QUERY =
            "INSERT INTO [songs] ([title], [id_artist], [genre_id], [description], [time_play], [song_data], [image], [lyric], [rating], [status]) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_SONG_BY_ID = "SELECT * FROM songs where id=?; ";
    private static final String SELECT_LIST_SONG_RANDOM =
            "SELECT * FROM songs " +
                    "WHERE id <> ? "+
                    "ORDER BY RAND() " +
                    "LIMIT ?;";




    public boolean insert(Songs entity) {
        Connection conn = JDBCUtil.getConnection();
        boolean result = true;
        if (conn != null) {
            try (PreparedStatement ps = conn.prepareStatement(INSERT_SONG_QUERY)) {
                ps.setString(1, entity.getTitle());
                ps.setString(2, entity.getId_artist());
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

    public Songs selectById(Integer idSong) {
        System.out.println(SELECT_SONG_BY_ID);
        Songs song = null;
        try (Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SONG_BY_ID))
        {
            preparedStatement.setInt(1, idSong);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String title = rs.getString("title");
                String artist = rs.getString("id_artist");
                int genre_id = rs.getInt("genre_id");
                String description = rs.getString("description");
                int time_play = rs.getInt("time_play");
                String song_data = rs.getString("song_data");
                String image = rs.getString("image");
                String lyric = rs.getString("lyric");
                Float rating = rs.getFloat("rating");
                EnumStatus status = EnumStatus.valueOf(rs.getString("status").toUpperCase());

                song = new Songs(idSong, title, artist, genre_id, description,
                        time_play, song_data, image, lyric, rating, status);
            }

        }catch (SQLException exception)
        {
            HandleExeption.printSQLException(exception);
        }
        return song;
    }

    public List<Songs> selectAll(int number){


        return null;
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
                int genre_id = rs.getInt("genre_id");
                String description = rs.getString("description");
                int time_play  = rs.getInt("time_play");
                String song_data = rs.getString("song_data");
                String image = rs.getString("image");
                String lyric = rs.getString("lyric");
                Float rating = rs.getFloat("rating");
                EnumStatus status = EnumStatus.valueOf(rs.getString("status").toUpperCase());

                listSong.add(new Songs(id_song, title, artist,genre_id, description,
                        time_play, song_data, image,  lyric,  rating, status));
            }

        }catch (SQLException exception)
        {
            HandleExeption.printSQLException(exception);
        }
        return listSong;
    }

    protected List<Songs> selectBySql(String sql, Object... args) {
        return null;
    }
}