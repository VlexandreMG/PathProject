package model;

public class Hole {
    int idPath;
    double percent;
    double kmAge;

    public int getIdPath() {
        return idPath;
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
}