package mvc.model;

import db.ConnectionOr;
import java.sql.*;

public class Hole {
    int id;
    int idPath;
    double percent;
    double kmAge;

    public int getIdPath() {
        return idPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPercent() {
        return percent;
    }

    public double getKmAge() {
        return kmAge;
    }

    public void setIdPath(int idPath) {
        this.idPath = idPath;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public void setKmAge(double kmAge) {
        this.kmAge = kmAge;
    }

    public Hole(int idPath, double percent, double kmAge) {
        setIdPath(idPath);
        setPercent(percent);
        setKmAge(kmAge);
    }

    // ===== CRUD Methods =====

    /**
     * Insère un nouveau hole dans la base de données
     * @return l'ID généré ou -1 en cas d'erreur
     */
    public int insert() throws SQLException {
        String sql = "INSERT INTO Hole (id_path, percent, km_age) VALUES (?, ?, ?)";
        
        try (Connection conn = ConnectionOr.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, new String[]{"id"})) {
            
            pstmt.setInt(1, this.idPath);
            pstmt.setDouble(2, this.percent);
            pstmt.setDouble(3, this.kmAge);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    this.id = rs.getInt(1);
                    System.out.println("Hole inséré avec l'ID: " + this.id);
                    return this.id;
                }
            }
            return -1;
        }
    }

    /**
     * Récupère un hole par son ID
     * @param id l'identifiant du hole
     * @return l'objet Hole ou null si non trouvé
     */
    public static Hole getById(int id) throws SQLException {
        String sql = "SELECT * FROM Hole WHERE id = ?";
        
        try (Connection conn = ConnectionOr.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Hole hole = new Hole(
                    rs.getInt("id_path"),
                    rs.getDouble("percent"),
                    rs.getDouble("km_age")
                );
                hole.setId(rs.getInt("id"));
                System.out.println("Hole trouvé (Path ID: " + hole.getIdPath() + ")");
                return hole;
            } else {
                System.out.println("Hole avec l'ID " + id + " non trouvé");
                return null;
            }
        }
    }

    /**
     * Met à jour le hole dans la base de données
     * @return true si succès, false sinon
     */
    public boolean update() throws SQLException {
        String sql = "UPDATE Hole SET id_path = ?, percent = ?, km_age = ? WHERE id = ?";
        
        try (Connection conn = ConnectionOr.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, this.idPath);
            pstmt.setDouble(2, this.percent);
            pstmt.setDouble(3, this.kmAge);
            pstmt.setInt(4, this.id);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Hole mis à jour (ID: " + this.id + ")");
                return true;
            }
            return false;
        }
    }

    /**
     * Supprime le hole de la base de données
     * @return true si succès, false sinon
     */
    public boolean delete() throws SQLException {
        String sql = "DELETE FROM Hole WHERE id = ?";
        
        try (Connection conn = ConnectionOr.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, this.id);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Hole supprimé (ID: " + this.id + ")");
                return true;
            }
            return false;
        }
    }
}