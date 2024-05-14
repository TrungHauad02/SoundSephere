package com.example.soundsephere.dao;

import com.example.soundsephere.enumModel.EnumRole;
import com.example.soundsephere.enumModel.EnumSex;
import com.example.soundsephere.enumModel.EnumUserStatus;
import com.example.soundsephere.model.Users;
import com.example.soundsephere.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UsersDAO {
    private static final String SELECT_USER_BY_ID_QUERY = "select * from users where username = ?";
    private static final String INSERT_USER = "INSERT INTO users (name,username, email, password, role,status) VALUES (? ,?, ?, ?, ?, ?)" ;
    private static final String LISTEN_COUNT_BY_ID_ARTIST_QUERY =
            "SELECT s.id_artist, SUM(ul.count) AS total_listens_count\n" +
                    "FROM songs s\n" +
                    "INNER JOIN user_listened ul ON s.id = ul.song_id\n" +
                    "WHERE s.id_artist = ?  \n" +
                    "GROUP BY s.id_artist;\n";


    public boolean checkConfirmPassword(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }
    public boolean insert(Users user) {
        Connection conn = JDBCUtil.getConnection();

        if (conn != null) {
            try (PreparedStatement ps = conn.prepareStatement(INSERT_USER)) {

                ps.setString(1, user.getName());
                ps.setString(2, user.getUsername());
                ps.setString(3, user.getEmail());
                ps.setString(4, user.getPassword());
                ps.setString(5, user.getRole().toString());
                ps.setString(6, user.getRole() == EnumRole.LISTENER ? "normal" : "pending");

                System.out.println(ps);
                ps.executeUpdate();
                return true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public boolean update(Users user) {
        Connection conn = JDBCUtil.getConnection();
        if (conn != null) {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE users SET name = ?, email = ?, password = ?, description = ?,sex = ? WHERE id = ?")) {
                ps.setString(1, user.getName());
                ps.setString(2, user.getEmail());
                ps.setString(3, user.getPassword());
                ps.setString(4, user.getDescription());
                ps.setString(5, user.getSex().toString());
                ps.setInt(6, user.getId());
                System.out.println(ps);
                ps.executeUpdate();
                return true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public boolean checkLogin(String username, String password) {

        Connection conn = JDBCUtil.getConnection();
        if (conn != null) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?")) {
                ps.setString(1, username);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                return rs.next();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public Users getUserByUsername(String username) {
        Users user = null;
        Connection conn = JDBCUtil.getConnection();
        if (conn != null) {

            try (PreparedStatement ps = conn.prepareStatement(SELECT_USER_BY_ID_QUERY)) {
                ps.setString(1, username);
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
                    user.setSex(EnumSex.valueOf(rs.getString("sex").toUpperCase()));

                    return user;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return user;
    }

    public boolean delete(Integer id) {
        return false;
    }

    public Users selectById(String username) {
        Users user = null;
        Connection conn = JDBCUtil.getConnection();
        if (conn != null) {
            try (PreparedStatement ps = conn.prepareStatement(SELECT_USER_BY_ID_QUERY)) {
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    user = new Users();
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setUsername(rs.getString("username"));
                    user.setRole(EnumRole.valueOf(rs.getString("role").toUpperCase()));
                    user.setDescription(rs.getString("description"));
                    user.setSex(EnumSex.valueOf(rs.getString("sex").toUpperCase()));
                    user.setStatus(EnumUserStatus.valueOf(rs.getString("status").toUpperCase()));
                    return user;

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

