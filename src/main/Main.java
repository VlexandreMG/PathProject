package main;

import model.Car;

public class Main {
    public static void main(String[] args) {
        Car car1 = new Car(180.0, 1, 4.5, 1.8);
        Car car2 = new Car(150.0, 2, 4.2, 1.75);
        Car car3 = new Car(200.0, 3, 5.0, 2.0);

        System.out.println("Cars created:");
        printCar(car1);
        printCar(car2);
        printCar(car3);
    }

    private static void printCar(Car c) {
        System.out.println("Car { vitesseMax=" + c.getVitesseMax()
                + ", idType=" + c.getIdType()
                + ", longueur=" + c.getLongueur()
                + ", largeur=" + c.getLargeur() + " }");
    }
}