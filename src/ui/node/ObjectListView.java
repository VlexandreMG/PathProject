package ui.node;

import java.util.List;
import java.util.stream.Collectors;
import java.util.function.Function;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import mvc.model.SelectedStore;

/**
 * Liste générique d'objets affichée par label (fourni par labelProvider).
 * Fournit un bouton "Save" qui enregistre l'objet sélectionné dans
 * {@link mvc.model.SelectedStore} pour réutilisation globale.
 */
public class ObjectListView<T> extends VBox {

    private final javafx.scene.control.ListView<String> fxList;
    private final List<T> items;
    private final Function<T, String> labelProvider;
    private final Button saveButton;
    private final Label statusLabel;

    public ObjectListView(List<T> items, Function<T, String> labelProvider, String title) {
        this.items = items;
        this.labelProvider = labelProvider;
        this.fxList = new javafx.scene.control.ListView<>();
        this.saveButton = new Button("Save");
        this.statusLabel = new Label("");
        initialize(title);
    }

    private void initialize(String title) {
        setSpacing(6);
        setPadding(new Insets(8));

        Label t = new Label(title == null ? "List" : title);

        // Construire la vue texte à partir des objets
        fxList.setItems(FXCollections.observableArrayList(
                items.stream().map(labelProvider).collect(Collectors.toList())
        ));

        saveButton.setDisable(true);
        saveButton.setOnAction(e -> {
            int idx = fxList.getSelectionModel().getSelectedIndex();
            if (idx >= 0 && idx < items.size()) {
                T obj = items.get(idx);
                SelectedStore.setSelected(obj);
                statusLabel.setText("Saved: " + labelProvider.apply(obj));
            }
        });

        // Activer le bouton seulement si un élément est sélectionné
        fxList.getSelectionModel().selectedIndexProperty().addListener((obs, oldV, newV) -> {
            saveButton.setDisable(newV == null || newV.intValue() < 0);
        });

        HBox controls = new HBox(8, saveButton, statusLabel);
        HBox.setHgrow(statusLabel, Priority.ALWAYS);

        getChildren().addAll(t, fxList, controls);
    }

    public void setItems(List<T> newItems) {
        fxList.setItems(FXCollections.observableArrayList(
                newItems.stream().map(labelProvider).collect(Collectors.toList())
        ));
    }

    public T getSelectedObject() {
        int idx = fxList.getSelectionModel().getSelectedIndex();
        if (idx >= 0 && idx < items.size()) return items.get(idx);
        return null;
    }
}
