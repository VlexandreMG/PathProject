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
        System.out.println("========================================");
        System.out.println("   CHARGEMENT DES DONNÉES DEPUIS LA BD");
        System.out.println("========================================\n");

        // Récupération des données depuis les bases de données
        List<Car> cars = new ArrayList<>();
        List<Point> points = new ArrayList<>();
        List<Path> paths = new ArrayList<>();
        List<Hole> holes = new ArrayList<>();
        List<Type> types = new ArrayList<>();
        
        try {
            // Récupération depuis PostgreSQL
            cars = Car.getAll();
            types = Type.getAll();
            
            // Récupération depuis Oracle
            points = Point.getAll();
            holes = Hole.getAll();
            paths = Path.getAll();
            
        } catch (Exception e) {
            System.err.println("❌ Erreur lors du chargement des données: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\n========================================");
        System.out.println("   AFFICHAGE DES DONNÉES RÉCUPÉRÉES");
        System.out.println("========================================\n");

        // Affichage des Cars
        System.out.println("--- VOITURES (PostgreSQL) ---");
        for (Car car : cars) {
            System.out.println("  ID: " + car.getId() + " | Nom: " + car.getNom() + 
                             " | Vitesse Max: " + car.getVitesseMax() + " km/h" +
                             " | Type ID: " + car.getIdType());
        }

        // Affichage des Types
        System.out.println("\n--- TYPES (PostgreSQL) ---");
        for (Type type : types) {
            System.out.println("  ID: " + type.getId() + " | Nom: " + type.getNom());
        }

        // Affichage des Points
        System.out.println("\n--- POINTS (Oracle) ---");
        for (Point point : points) {
            System.out.println("  ID: " + point.getId() + " | Nom: " + point.getNom() + 
                             " | Coordonnées: (" + point.getX() + ", " + point.getY() + ")");
        }

        // Affichage des Holes
        System.out.println("\n--- HOLES (Oracle) ---");
        for (Hole hole : holes) {
            System.out.println("  ID: " + hole.getId() + " | Path ID: " + hole.getIdPath() + 
                             " | Pourcentage: " + hole.getPercent() + "%" +
                             " | KmAge: " + hole.getKmAge() + " | FinKmAge: " + hole.getFinkmAge());
        }

        // Affichage des Paths
        System.out.println("\n--- PATHS (Oracle) ---");
        for (Path path : paths) {
            System.out.println("  ID: " + path.getId() + " | Nom: " + path.getNom() + 
                             " | Distance: " + path.getDistance() + " km" +
                             " | De: " + path.getPointDep().getNom() + " -> À: " + path.getPointArr().getNom());
        }

        System.out.println("\n========================================\n");

        // Création de quelques objets supplémentaires pour l'UI (si les listes sont vides)
        if (cars.isEmpty()) {
            Car car1 = new Car("Car1", 180.0, 1, 4.5, 1.8);
            Car car2 = new Car("Car2", 150.0, 2, 4.2, 1.75);
            Car car3 = new Car("Car3", 200.0, 3, 5.0, 2.0);
            cars.add(car1);
            cars.add(car2);
            cars.add(car3);
        }

        if (points.isEmpty()) {
            Point point1 = new Point(10.5, 20.3, "PointA");
            Point point2 = new Point(15.2, 25.8, "PointB");
            Point point3 = new Point(8.7, 12.1, "PointC");
            Point point4 = new Point(5.0, 15.0, "PointD");
            points.add(point1);
            points.add(point2);
            points.add(point3);
            points.add(point4);
        }

        if (paths.isEmpty() && points.size() >= 3) {
            Path path1 = new Path(points.get(0), points.get(1), 12.5, 2.4, "PathA");
            Path path2 = new Path(points.get(1), points.get(2), 7.8, 1.8, "PathB");
            Path path3 = new Path(points.get(0), points.get(2), 20.0, 3.0, "PathC");
            paths.add(path1);
            paths.add(path2);
            paths.add(path3);
        }

        if (holes.isEmpty()) {
            Hole hole1 = new Hole(1, 15.5, 2.3, 2.8);
            Hole hole2 = new Hole(2, 22.0, 1.5, 2.0);
            Hole hole3 = new Hole(1, 8.5, 3.2, 3.5);
            holes.add(hole1);
            holes.add(hole2);
            holes.add(hole3);
        }

        // Création des routes
        List<Route> routes = new ArrayList<>();
        if (points.size() >= 3 && paths.size() >= 2) {
            Route route1 = new Route(25.5, 30.0, points.get(0), points.get(1), Arrays.asList(paths.get(0)));
            Route route2 = new Route(18.3, 22.5, points.get(1), points.get(2), Arrays.asList(paths.get(1)));
            if (paths.size() >= 3) {
                Route route3 = new Route(32.1, 40.0, points.get(0), points.get(2), Arrays.asList(paths.get(0), paths.get(2)));
                routes.add(route3);
            }
            routes.add(route1);
            routes.add(route2);
        }

        if (types.isEmpty()) {
            Type type1 = new Type(1, "TypeA");
            Type type2 = new Type(2, "TypeB");
            Type type3 = new Type(3, "TypeC");
            types.add(type1);
            types.add(type2);
            types.add(type3);
        }

        // Création de l'interface
        RootLayout rootLayout = new RootLayout();

        // Passer les listes au Dashboard
        Dashboard dashboard = new Dashboard(cars, paths, holes, points, routes, types);
        rootLayout.getBorderPane().setLeft(dashboard);

        //Creation de la scene
        Scene scene = new Scene(rootLayout.getBorderPane(), 1000, 600);

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
