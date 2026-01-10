package main;

import mvc.model.Car;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import ui.layout.RootLayout;
import ui.node.ListView;
import ui.node.Dashboard;
import java.util.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Création de l'interface
        RootLayout rootLayout = new RootLayout();

        //Different Node
        // Création des voitures et préparation de la liste affichée
        Car car1 = new Car("Car1",180.0, 1, 4.5, 1.8);
        Car car2 = new Car("Car2",150.0, 2, 4.2, 1.75);
        Car car3 = new Car("Car3",200.0, 3, 5.0, 2.0);
        List<String> cars = new ArrayList<>();
        cars.add(car1.getNom());
        cars.add(car2.getNom());
        cars.add(car3.getNom());

        ListView listView = new ListView(cars);
        Dashboard dashboard = new Dashboard();
        rootLayout.getBorderPane().setCenter(listView);
        rootLayout.getBorderPane().setLeft(dashboard);

        //Creation de la scene
        Scene scene = new Scene(rootLayout.getBorderPane(), 800, 600);

        primaryStage.setTitle("PathProject");
        primaryStage.setScene(scene);
        primaryStage.show();

        // System.out.println("Cars created:");
        // printCar(car1);
        // printCar(car2);
        // printCar(car3);
    }

    // private static void printCar(Car c) {
    //     System.out.println("Car { vitesseMax=" + c.getVitesseMax()
    //             + ", idType=" + c.getIdType()
    //             + ", longueur=" + c.getLongueur()
    //             + ", largeur=" + c.getLargeur() + " }");
    // }
    public static void main(String[] args) {
        launch(args);
    }
}
