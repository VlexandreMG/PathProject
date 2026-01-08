package mvc.model;

public class Car {
    double vitesseMax;
    int idType;
    double longueur;
    double largeur;
    String nom;

    public String getNom() {
        return nom;
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
}