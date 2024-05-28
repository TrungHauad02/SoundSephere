package com.example.soundsephere.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {
    private static String jdbcURL = "jdbc:mysql://localhost:3306/musicweb?";
    private static String jdbcUsername = "root";
    private static String jdbcPassword = "123456";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            System.out.println("Connection Success...");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Connection Error...");
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        Connection conn = getConnection();
        closeConnection(conn);
    }
}

