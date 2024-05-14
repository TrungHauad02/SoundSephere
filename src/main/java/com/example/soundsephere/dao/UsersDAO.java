package com.example.soundsephere.dao;

import com.example.soundsephere.MyUtils;
import com.example.soundsephere.enumModel.EnumRole;
import com.example.soundsephere.enumModel.EnumSex;
import com.example.soundsephere.enumModel.EnumStatus;
import com.example.soundsephere.enumModel.EnumUserStatus;
import com.example.soundsephere.model.Albums;
import com.example.soundsephere.model.Users;
import com.example.soundsephere.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersDAO extends SoundSysDAO<Users, Integer> {
    private static final String SELECT_USER_BY_ID_QUERY = "";
    private static final String SELECT_ALL_USERS_QUERY = "select * from users ;";
    private static final String SELECT_ALL_APPROVALS_QUERY = "select * from users where users.role = 'listener' and users.status = 'pending'; " ;

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
                    user.setId(rs.getString("id"));
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
        Connection connection = MyUtils.getConnection();
        List<Users> usersList = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(SELECT_ALL_USERS_QUERY);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                EnumSex sex = EnumSex.valueOf(rs.getString("sex").toUpperCase());
                String description = rs.getString("description");
                String username = rs.getString("username");
                String email = rs.getString("email");
                String password = rs.getString("password");
                EnumRole role = EnumRole.valueOf(rs.getString("role").toUpperCase());
                EnumUserStatus status = EnumUserStatus.valueOf(rs.getString("status").toUpperCase());

                Users user = new Users();
                user.setId(id);
                user.setName(name);
                user.setSex(sex);
                user.setDescription(description);
                user.setEmail(email);
                user.setPassword(password);
                user.setRole(role);
                user.setStatus(status);
                user.setUsername(username);
                usersList.add(user);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usersList;
    }
    public List<Users> selectAllApproval() {
        Connection connection = MyUtils.getConnection();
        List<Users> usersList = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(SELECT_ALL_APPROVALS_QUERY);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                EnumSex sex = EnumSex.valueOf(rs.getString("sex").toUpperCase());
                String description = rs.getString("description");
                String username = rs.getString("username");
                String email = rs.getString("email");
                String password = rs.getString("password");
                EnumRole role = EnumRole.valueOf(rs.getString("role").toUpperCase());
                EnumUserStatus status = EnumUserStatus.valueOf(rs.getString("status").toUpperCase());

                Users user = new Users();
                user.setId(id);
                user.setName(name);
                user.setSex(sex);
                user.setDescription(description);
                user.setEmail(email);
                user.setPassword(password);
                user.setRole(role);
                user.setStatus(status);
                user.setUsername(username);
                usersList.add(user);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usersList;
    }


    protected List<Users> selectBySql(String sql, Object... args) {
        return null;
    }
}
