package com.example.soundsephere.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {
    public static Connection getConnection() {
        Connection conn = null;
        try {
            //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/musicweb?";
            String username = "root";
            String password = "";


            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connection Successfully!");
        } catch (ClassNotFoundException e) {
            System.err.println("SQL Server JDBC driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
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
