package mvc.model;

public class Path {
    String nom;
    double distance;
    double largeur;

    public double getDistance() {
        return distance;
    }

    public double getLargeur() {
        return largeur;
    }

    public String getNom() {
        return nom;
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

    public Path(double distance,double largeur,String nom) {
        setDistance(distance);
        setLargeur(largeur);
        setNom(nom);
    }
}