package db;

import java.sql.*;

public class ConnectionOr {

    private static String DB_URL = "jdbc:oracle:thin:@localhost:1521:EE";
    private static String USER = "lalana";
    private static String PASS = "lalana";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Oracle JDBC Driver not found.");
        }
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public static void main(String args[]) {
        try {
            Connection conn = ConnectionOr.getConnection();
            System.out.println("Connection established: " + conn);
        } catch (SQLException e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        }

    }
}