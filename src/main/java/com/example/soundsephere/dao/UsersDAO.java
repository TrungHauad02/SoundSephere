package com.example.soundsephere.dao;

import com.example.soundsephere.enumModel.EnumRole;
import com.example.soundsephere.enumModel.EnumSex;
import com.example.soundsephere.model.Users;
import com.example.soundsephere.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UsersDAO extends SoundSysDAO<Users, Integer> {
    private static final String SELECT_USER_BY_ID_QUERY = "";

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
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    user = new Users();
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(EnumRole.valueOf(rs.getString("role").toUpperCase()));
                    user.setDescription(rs.getString("description"));
                    user.setBirthday(
                            rs.getDate("birthday") != null ? new java.util.Date(rs.getDate("birthday").getTime())
                                    : null);
                    user.setSex(EnumSex.valueOf(rs.getString("sex").toUpperCase()));
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
}
