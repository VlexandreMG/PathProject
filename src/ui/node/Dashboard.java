package ui.node;

import javafx.scene.layout.VBox;
import javafx.scene.control.ListView;

/**
 * Dashboard très simple : affiche une liste statique de noms de classes modèle.
 */
public class Dashboard extends VBox {

    public Dashboard() {
        ListView<String> list = new ListView<>();
        list.getItems().addAll("Car", "Hole", "Path", "Point", "Route", "Type");
        getChildren().add(list);
    }

}
