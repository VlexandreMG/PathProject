package mvc.model;

import db.ConnectionOr;
import java.sql.*;
import java.util.*;

public class Path {
    int id;
    String nom;
    double distance;
    double largeur;
    Point pointDep;
    Point pointArr;
    List<Hole> listHole;

    public List<Hole> getListHole() {
        return listHole;
    }

    public void setListHole(List<Hole> listHole) {
        this.listHole = listHole;
    }

    public Point getPointArr() {
        return pointArr;
    }

    public Point getPointDep() {
        return pointDep;
    }

    public void setPointArr(Point pointArr) {
        this.pointArr = pointArr;
    }

    public void setPointDep(Point pointDep) {
        this.pointDep = pointDep;
    }

    public double getDistance() {
        return distance;
    }

    public double getLargeur() {
        return largeur;
    }

    public String getNom() {
        return nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setLargeur(double largeur) {
        this.largeur = largeur;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Path(Point pointDep,Point pointArr,double distance,double largeur,String nom) {
        setDistance(distance);
        setLargeur(largeur);
        setNom(nom);
        setPointDep(pointDep);
        setPointArr(pointArr);
    }

    // ===== CRUD Methods =====

    /**
     * Insère un nouveau path dans la base de données
     * @return l'ID généré ou -1 en cas d'erreur
     */
    public int insert() throws SQLException {
        String sql;
        if (this.id > 0) {
            // Si l'ID est déjà défini, l'utiliser
            sql = "INSERT INTO Path (id, nom, distance, largeur, point_dep_id, point_arr_id) VALUES (?, ?, ?, ?, ?, ?)";
        } else {
            // Sinon, laisser la base générer l'ID
            sql = "INSERT INTO Path (nom, distance, largeur, point_dep_id, point_arr_id) VALUES (?, ?, ?, ?, ?)";
        }
        
        try (Connection conn = ConnectionOr.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, new String[]{"id"})) {
            
            if (this.id > 0) {
                pstmt.setInt(1, this.id);
                pstmt.setString(2, this.nom);
                pstmt.setDouble(3, this.distance);
                pstmt.setDouble(4, this.largeur);
                pstmt.setInt(5, this.pointDep.getId());
                pstmt.setInt(6, this.pointArr.getId());
            } else {
                pstmt.setString(1, this.nom);
                pstmt.setDouble(2, this.distance);
                pstmt.setDouble(3, this.largeur);
                pstmt.setInt(4, this.pointDep.getId());
                pstmt.setInt(5, this.pointArr.getId());
            }
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                if (this.id <= 0) {
                    ResultSet rs = pstmt.getGeneratedKeys();
                    if (rs.next()) {
                        this.id = rs.getInt(1);
                    }
                }
                System.out.println("Path inséré avec l'ID: " + this.id);
                return this.id;
            }
            return -1;
        }
    }

    /**
     * Récupère un path par son ID
     * @param id l'identifiant du path
     * @return l'objet Path ou null si non trouvé
     */
    public static Path getById(int id) throws SQLException {
        String sql = "SELECT p.*, " +
                     "pd.id as dep_id, pd.x as dep_x, pd.y as dep_y, pd.nom as dep_nom, " +
                     "pa.id as arr_id, pa.x as arr_x, pa.y as arr_y, pa.nom as arr_nom " +
                     "FROM Path p " +
                     "JOIN Point pd ON p.point_dep_id = pd.id " +
                     "JOIN Point pa ON p.point_arr_id = pa.id " +
                     "WHERE p.id = ?";
        
        try (Connection conn = ConnectionOr.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Point pointDep = new Point(
                    rs.getDouble("dep_x"),
                    rs.getDouble("dep_y"),
                    rs.getString("dep_nom")
                );
                pointDep.setId(rs.getInt("dep_id"));
                
                Point pointArr = new Point(
                    rs.getDouble("arr_x"),
                    rs.getDouble("arr_y"),
                    rs.getString("arr_nom")
                );
                pointArr.setId(rs.getInt("arr_id"));
                
                Path path = new Path(
                    pointDep,
                    pointArr,
                    rs.getDouble("distance"),
                    rs.getDouble("largeur"),
                    rs.getString("nom")
                );
                path.setId(rs.getInt("id"));
                System.out.println("Path trouvé: " + path.getNom());
                return path;
            } else {
                System.out.println("Path avec l'ID " + id + " non trouvé");
                return null;
            }
        }
    }

    /**
     * Met à jour le path dans la base de données
     * @return true si succès, false sinon
     */
    public boolean update() throws SQLException {
        String sql = "UPDATE Path SET nom = ?, distance = ?, largeur = ?, point_dep_id = ?, point_arr_id = ? WHERE id = ?";
        
        try (Connection conn = ConnectionOr.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, this.nom);
            pstmt.setDouble(2, this.distance);
            pstmt.setDouble(3, this.largeur);
            pstmt.setInt(4, this.pointDep.getId());
            pstmt.setInt(5, this.pointArr.getId());
            pstmt.setInt(6, this.id);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Path mis à jour: " + this.nom);
                return true;
            }
            return false;
        }
    }

    /**
     * Supprime le path de la base de données
     * @return true si succès, false sinon
     */
    public boolean delete() throws SQLException {
        String sql = "DELETE FROM Path WHERE id = ?";
        
        try (Connection conn = ConnectionOr.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, this.id);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Path supprimé: " + this.nom);
                return true;
            }
            return false;
        }
    }
}