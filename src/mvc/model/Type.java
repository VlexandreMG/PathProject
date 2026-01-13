package mvc.model;

import db.ConnectionPg;
import java.sql.*;

public class Type {
    int id;
    String nom;

    public int getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setNom(String nom) {
        this.nom = nom; 
    }

    public Type(int id, String nom) {
        setId(id);
        setNom(nom);
    }

    // ===== CRUD Methods =====

    /**
     * Insère un nouveau type dans la base de données
     * @return l'ID généré ou -1 en cas d'erreur
     */
    public int insert() throws SQLException {
        String sql = "INSERT INTO Type (nom) VALUES (?) RETURNING id";
        
        try (Connection conn = ConnectionPg.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, this.nom);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                this.id = rs.getInt("id");
                System.out.println("Type inséré avec l'ID: " + this.id);
                return this.id;
            }
            return -1;
        }
    }

    /**
     * Récupère un type par son ID
     * @param id l'identifiant du type
     * @return l'objet Type ou null si non trouvé
     */
    public static Type getById(int id) throws SQLException {
        String sql = "SELECT * FROM Type WHERE id = ?";
        
        try (Connection conn = ConnectionPg.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Type type = new Type(
                    rs.getInt("id"),
                    rs.getString("nom")
                );
                System.out.println("Type trouvé: " + type.getNom());
                return type;
            } else {
                System.out.println("Type avec l'ID " + id + " non trouvé");
                return null;
            }
        }
    }

    /**
     * Met à jour le type dans la base de données
     * @return true si succès, false sinon
     */
    public boolean update() throws SQLException {
        String sql = "UPDATE Type SET nom = ? WHERE id = ?";
        
        try (Connection conn = ConnectionPg.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, this.nom);
            pstmt.setInt(2, this.id);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Type mis à jour: " + this.nom);
                return true;
            }
            return false;
        }
    }

    /**
     * Supprime le type de la base de données
     * @return true si succès, false sinon
     */
    public boolean delete() throws SQLException {
        String sql = "DELETE FROM Type WHERE id = ?";
        
        try (Connection conn = ConnectionPg.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, this.id);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Type supprimé: " + this.nom);
                return true;
            }
            return false;
        }
    }
}