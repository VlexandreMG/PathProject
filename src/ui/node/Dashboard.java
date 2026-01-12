package ui.node;

import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import mvc.model.Car;
import mvc.model.Path;

/**
 * Dashboard simple : liste de noms à gauche, aperçu du composant correspondant à droite.
 * Si l'utilisateur clique sur "Car" on affiche `ui.node.ListView` ;
 * si clique sur "Path" on affiche `ui.node.ListView2`.
 */
public class Dashboard extends VBox {

    private final List<Car> cars;
    private final List<Path> paths;

    /**
     * Dashboard qui reçoit les listes d'objets et affiche le composant correspondant
     * dans la zone d'aperçu lorsqu'on sélectionne un nom.
     */
    public Dashboard(List<Car> cars, List<Path> paths) {
        this.cars = cars;
        this.paths = paths;

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
                    // afficher une vue d'objets qui permet de sauvegarder l'objet sélectionné
                    previewArea.getChildren().add(new ui.node.ListView(this.cars));
                    break;
                case "Path":
                    previewArea.getChildren().add(new ui.node.ListView2(this.paths));
                    break;
                default:
                    previewArea.getChildren().add(new Label("Pas d'aperçu pour: " + newV));
                    break;
            }
        });
    }

}
