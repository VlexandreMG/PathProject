package mvc.model;

import java.util.*;

public class Path {
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
}