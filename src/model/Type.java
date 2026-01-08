package model;

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
}