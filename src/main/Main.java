package main;

import mvc.model.Car;
import mvc.model.Path;
import mvc.model.Hole;
import mvc.model.Point;
import mvc.model.Route;
import mvc.model.Type;
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
        // Création des voitures
        Car car1 = new Car("Car1",180.0, 1, 4.5, 1.8);
        Car car2 = new Car("Car2",150.0, 2, 4.2, 1.75);
        Car car3 = new Car("Car3",200.0, 3, 5.0, 2.0);
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);

        // Création des paths
        Path path1 = new Path(12.5, 2.4, "PathA");
        Path path2 = new Path(7.8, 1.8, "PathB");
        Path path3 = new Path(20.0, 3.0, "PathC");
        List<Path> paths = new ArrayList<>();
        paths.add(path1);
        paths.add(path2);
        paths.add(path3);

        // Création des holes
        Hole hole1 = new Hole(1, 15.5, 2.3);
        Hole hole2 = new Hole(2, 22.0, 1.5);
        Hole hole3 = new Hole(1, 8.5, 3.2);
        List<Hole> holes = new ArrayList<>();
        holes.add(hole1);
        holes.add(hole2);
        holes.add(hole3);

        // Création des points
        Point point1 = new Point(10.5, 20.3, "PointA");
        Point point2 = new Point(15.2, 25.8, "PointB");
        Point point3 = new Point(8.7, 12.1, "PointC");
        List<Point> points = new ArrayList<>();
        points.add(point1);
        points.add(point2);
        points.add(point3);

        // Création des routes
        Route route1 = new Route(25.5, 30.0, point1, point2, Arrays.asList(path1, path2));
        Route route2 = new Route(18.3, 22.5, point2, point3, Arrays.asList(path2, path3));
        Route route3 = new Route(32.1, 40.0, point1, point3, Arrays.asList(path1, path3));
        List<Route> routes = new ArrayList<>();
        routes.add(route1);
        routes.add(route2);
        routes.add(route3);

        // Création des types
        Type type1 = new Type(1, "TypeA");
        Type type2 = new Type(2, "TypeB");
        Type type3 = new Type(3, "TypeC");
        List<Type> types = new ArrayList<>();
        types.add(type1);
        types.add(type2);
        types.add(type3);

        // Passer les listes au Dashboard
        Dashboard dashboard = new Dashboard(cars, paths, holes, points, routes, types);
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
