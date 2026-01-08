package model;

public class Car {
    double vitesseMax;
    int idType;
    double longueur;
    double largeur;

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

    public Car(double vtsMax, int idType, double longueur, double largeur) {
        setIdType(idType);
        setLargeur(largeur);
        setLongueur(longueur);
        setVitesseMax(vtsMax);
    }
}