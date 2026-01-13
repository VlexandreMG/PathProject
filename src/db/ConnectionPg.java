package db;

import java.sql.*;

public class ConnectionPg {

    private static String DB_URL = "jdbc:postgresql://localhost:5432/voiture";
    private static String USER = "postgres";
    private static String PASS = "postgres";

    public static Connection getConnection() throws SQLException {
        try {
            // Pour PostgreSQL, le driver s'enregistre automatiquement depuis JDBC 4.0
            // Mais on peut quand même le charger explicitement
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver not found.");
            e.printStackTrace();
        }
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public static void main(String args[]) {
        try {
            Connection conn = ConnectionPg.getConnection();
            System.out.println("Connection to PostgreSQL established: " + conn);
            
            // Optionnel : tester avec une requête simple
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT version()");
            if (rs.next()) {
                System.out.println("PostgreSQL version: " + rs.getString(1));
            }
            
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Connection to PostgreSQL failed.");
            e.printStackTrace();
        }
    }
}