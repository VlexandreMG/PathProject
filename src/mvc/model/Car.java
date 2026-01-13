package mvc.model;

import db.ConnectionPg;
import java.sql.*;

public class Car {
    int id;
    double vitesseMax;
    int idType;
    double longueur;
    double largeur;
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

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getIdType() {
        return idType;
    }

    public double getLargeur() {
        return largeur;
    }

    public double getLongueur() {
        return longueur;
    }

    public double getVitesseMax() {
        return vitesseMax;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public void setLargeur(double largeur) {
        this.largeur = largeur;
    }

    public void setLongueur(double longueur) {
        this.longueur = longueur;
    }

    public void setVitesseMax(double vitesseMax) {
        this.vitesseMax = vitesseMax;
    }

    public Car(String nom,double vtsMax, int idType, double longueur, double largeur) {
        setNom(nom);
        setIdType(idType);
        setLargeur(largeur);
        setLongueur(longueur);
        setVitesseMax(vtsMax);
    }

    // ===== CRUD Methods =====

    /**
     * Insère une nouvelle voiture dans la base de données
     * @return l'ID généré ou -1 en cas d'erreur
     */
    public int insert() throws SQLException {
        String sql = "INSERT INTO Car (nom, vitesse_max, id_type, longueur, largeur) VALUES (?, ?, ?, ?, ?) RETURNING id";
        
        try (Connection conn = ConnectionPg.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, this.nom);
            pstmt.setDouble(2, this.vitesseMax);
            pstmt.setInt(3, this.idType);
            pstmt.setDouble(4, this.longueur);
            pstmt.setDouble(5, this.largeur);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                this.id = rs.getInt("id");
                System.out.println("Car insérée avec l'ID: " + this.id);
                return this.id;
            }
            return -1;
        }
    }

    /**
     * Récupère une voiture par son ID
     * @param id l'identifiant de la voiture
     * @return l'objet Car ou null si non trouvé
     */
    public static Car getById(int id) throws SQLException {
        String sql = "SELECT * FROM Car WHERE id = ?";
        
        try (Connection conn = ConnectionPg.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Car car = new Car(
                    rs.getString("nom"),
                    rs.getDouble("vitesse_max"),
                    rs.getInt("id_type"),
                    rs.getDouble("longueur"),
                    rs.getDouble("largeur")
                );
                car.setId(rs.getInt("id"));
                System.out.println("Car trouvée: " + car.getNom());
                return car;
            } else {
                System.out.println("Car avec l'ID " + id + " non trouvée");
                return null;
            }
        }
    }

    /**
     * Met à jour la voiture dans la base de données
     * @return true si succès, false sinon
     */
    public boolean update() throws SQLException {
        String sql = "UPDATE Car SET nom = ?, vitesse_max = ?, id_type = ?, longueur = ?, largeur = ? WHERE id = ?";
        
        try (Connection conn = ConnectionPg.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, this.nom);
            pstmt.setDouble(2, this.vitesseMax);
            pstmt.setInt(3, this.idType);
            pstmt.setDouble(4, this.longueur);
            pstmt.setDouble(5, this.largeur);
            pstmt.setInt(6, this.id);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Car mise à jour: " + this.nom);
                return true;
            }
            return false;
        }
    }

    /**
     * Supprime la voiture de la base de données
     * @return true si succès, false sinon
     */
    public boolean delete() throws SQLException {
        String sql = "DELETE FROM Car WHERE id = ?";
        
        try (Connection conn = ConnectionPg.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, this.id);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Car supprimée: " + this.nom);
                return true;
            }
            return false;
        }
    }
}