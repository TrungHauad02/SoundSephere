package com.example.soundsephere.dao;

import com.example.soundsephere.enumModel.EnumRole;
import com.example.soundsephere.enumModel.EnumSex;
import com.example.soundsephere.enumModel.EnumStatus;
import com.example.soundsephere.enumModel.EnumUserStatus;
import com.example.soundsephere.model.Users;
import com.example.soundsephere.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UsersDAO extends SoundSysDAO<Users, Integer> {
    private static final String SELECT_USER_BY_ID_QUERY =
            "SELECT u.id, u.name, u.sex, u.birthday, u.description, u.username, u.email, u.password, u.role, u.status\n" +
                    "FROM [users] u\n" +
                    "WHERE [id] = ?;";
    private static final String LISTEN_COUNT_BY_ID_ARTIST_QUERY =
            "SELECT s.id_artist, SUM(ul.count) AS total_listens_count\n" +
                    "FROM songs s\n" +
                    "INNER JOIN user_listened ul ON s.id = ul.song_id\n" +
                    "WHERE s.id_artist = ?  \n" +
                    "GROUP BY s.id_artist;\n";

    public boolean insert(Users entity) {
        return false;
    }

    public boolean update(Users entity) {
        return false;
    }

    public boolean delete(Integer id) {
        return false;
    }

    public Users selectById(Integer id) {
        Users user = null;
        Connection conn = JDBCUtil.getConnection();
        if (conn != null) {
            try (PreparedStatement ps = conn.prepareStatement(SELECT_USER_BY_ID_QUERY)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    user = new Users();
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(EnumRole.valueOf(rs.getString("role").toUpperCase()));
                    user.setDescription(rs.getString("description"));
                    user.setBirthday(
                            rs.getDate("birthday") != null ? new java.util.Date(rs.getDate("birthday").getTime())
                                    : null);
                    user.setSex(EnumSex.valueOf(rs.getString("sex").toUpperCase()));
                    user.setStatus(EnumUserStatus.valueOf(rs.getString("status").toUpperCase()));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return user;
    }

    public List<Users> selectAll() {
        return null;
    }

    protected List<Users> selectBySql(String sql, Object... args) {
        return null;
    }

    public int listenCount(int idArtist){
        int count = 0;
        Connection conn = JDBCUtil.getConnection();
        if (conn != null) {
            try (PreparedStatement ps = conn.prepareStatement(LISTEN_COUNT_BY_ID_ARTIST_QUERY)) {
                ps.setInt(1, idArtist);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    count = rs.getInt("total_listens_count");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return count;
    }
}
