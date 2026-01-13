package mvc.model;

import db.ConnectionOr;
import java.sql.*;

public class Point {
    int id;
    double x;
    double y;
    String nom;

    public String getNom() {
        return nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Point(double x, double y, String nom) {
        this.x = x;
        this.y = y;
        this.nom = nom;
    }

    // ===== CRUD Methods =====

    /**
     * Insère un nouveau point dans la base de données
     * @return l'ID généré ou -1 en cas d'erreur
     */
    public int insert() throws SQLException {
        String sql = "INSERT INTO Point (x, y, nom) VALUES (?, ?, ?)";
        
        try (Connection conn = ConnectionOr.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, new String[]{"id"})) {
            
            pstmt.setDouble(1, this.x);
            pstmt.setDouble(2, this.y);
            pstmt.setString(3, this.nom);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    this.id = rs.getInt(1);
                    System.out.println("Point inséré avec l'ID: " + this.id);
                    return this.id;
                }
            }
            return -1;
        }
    }

    /**
     * Récupère un point par son ID
     * @param id l'identifiant du point
     * @return l'objet Point ou null si non trouvé
     */
    public static Point getById(int id) throws SQLException {
        String sql = "SELECT * FROM Point WHERE id = ?";
        
        try (Connection conn = ConnectionOr.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Point point = new Point(
                    rs.getDouble("x"),
                    rs.getDouble("y"),
                    rs.getString("nom")
                );
                point.setId(rs.getInt("id"));
                System.out.println("Point trouvé: " + point.getNom());
                return point;
            } else {
                System.out.println("Point avec l'ID " + id + " non trouvé");
                return null;
            }
        }
    }

    /**
     * Met à jour le point dans la base de données
     * @return true si succès, false sinon
     */
    public boolean update() throws SQLException {
        String sql = "UPDATE Point SET x = ?, y = ?, nom = ? WHERE id = ?";
        
        try (Connection conn = ConnectionOr.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDouble(1, this.x);
            pstmt.setDouble(2, this.y);
            pstmt.setString(3, this.nom);
            pstmt.setInt(4, this.id);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Point mis à jour: " + this.nom);
                return true;
            }
            return false;
        }
    }

    /**
     * Supprime le point de la base de données
     * @return true si succès, false sinon
     */
    public boolean delete() throws SQLException {
        String sql = "DELETE FROM Point WHERE id = ?";
        
        try (Connection conn = ConnectionOr.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, this.id);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Point supprimé: " + this.nom);
                return true;
            }
            return false;
        }
    }
}