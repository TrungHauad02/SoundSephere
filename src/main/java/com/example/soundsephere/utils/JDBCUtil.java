package com.example.soundsephere.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://soundsphere.database.windows.net:1433;database=soundsphere;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30";
            String username = "soundsphere@soundsphere";
            String password = "^Y5O6/p`5>q<)uFG(Jg8";


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
