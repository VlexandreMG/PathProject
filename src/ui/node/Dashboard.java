package ui.node;

import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import mvc.model.*;

/**
 * Dashboard simple : liste de noms à gauche, aperçu du composant correspondant à droite.
 * Affiche un ListView pour chaque type de modèle.
 */
public class Dashboard extends VBox {

    private final List<?> cars;
    private final List<?> paths;
    private final List<?> holes;
    private final List<?> points;
    private final List<?> routes;
    private final List<?> types;

    public Dashboard(List<?> cars, List<?> paths, List<?> holes, List<?> points, List<?> routes, List<?> types) {
        this.cars = cars;
        this.paths = paths;
        this.holes = holes;
        this.points = points;
        this.routes = routes;
        this.types = types;

        setSpacing(6);
        setPadding(new Insets(8));

        // Liste des noms
        javafx.scene.control.ListView<String> names = new javafx.scene.control.ListView<>();
        names.getItems().addAll("Car", "Hole", "Path", "Point", "Route", "Type");
        names.setPrefWidth(140);

        // Zone d'aperçu où on affiche l'instance correspondante
        VBox previewArea = new VBox();
        previewArea.setPadding(new Insets(6));
        previewArea.setPrefWidth(320);

        HBox container = new HBox(8, names, previewArea);
        HBox.setHgrow(previewArea, Priority.ALWAYS);

        getChildren().addAll(new Label("Modeles"), container);

        // Au clic sur un nom, afficher le composant correspondant
        names.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            previewArea.getChildren().clear();
            if (newV == null) return;
            switch (newV) {
                case "Car":
                    previewArea.getChildren().add(new ui.node.ListView(this.cars, o -> ((Car)o).getNom()));
                    break;
                case "Path":
                    previewArea.getChildren().add(new ui.node.ListView(this.paths, o -> ((Path)o).getNom()));
                    break;
                case "Hole":
                    previewArea.getChildren().add(new ui.node.ListView(this.holes, o -> "Hole id:" + ((Hole)o).getIdPath()));
                    break;
                case "Point":
                    previewArea.getChildren().add(new ui.node.ListView(this.points, o -> ((Point)o).getNom()));
                    break;
                case "Route":
                    previewArea.getChildren().add(new ui.node.ListView(this.routes, o -> "Route " + ((Route)o).getDistance() + "km"));
                    break;
                case "Type":
                    previewArea.getChildren().add(new ui.node.ListView(this.types, o -> ((Type)o).getNom()));
                    break;
                default:
                    previewArea.getChildren().add(new Label("Pas d'aperçu pour: " + newV));
                    break;
            }
        });
    }

}
