package mvc.model;

public class Point {
    double x;
    double y;
    String nom;

    public String getNom() {
        return nom;
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
}