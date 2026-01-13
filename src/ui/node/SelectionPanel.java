package ui.node;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import java.util.List;

/**
 * Panneau simple qui affiche les objets sauvegardés
 */
public class SelectionPanel extends VBox {

    private VBox itemsList;
    private Button launchButton;

    public SelectionPanel() {
        setSpacing(8);
        setPadding(new Insets(8));
        setMinWidth(250);

        // Titre
        Label title = new Label("Sélections");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Zone scrollable pour la liste
        itemsList = new VBox(4);
        itemsList.setPadding(new Insets(4));
        
        ScrollPane scrollPane = new ScrollPane(itemsList);
        scrollPane.setFitToWidth(true);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        // Bouton animation
        launchButton = new Button("Lancer l'animation");
        launchButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        launchButton.setMaxWidth(Double.MAX_VALUE);
        launchButton.setOnAction(e -> launchAnimation());

        getChildren().addAll(title, scrollPane, launchButton);
    }

    /**
     * Met à jour l'affichage avec les sélections actuelles
     */
    public void updateSelections() {
        itemsList.getChildren().clear();
        List<Object> saved = ListView.getSavedItems();
        
        launchButton.setDisable(saved.isEmpty());

        if (saved.isEmpty()) {
            Label emptyLabel = new Label("Aucune sélection");
            emptyLabel.setStyle("-fx-text-fill: gray; -fx-font-style: italic;");
            itemsList.getChildren().add(emptyLabel);
        } else {
            for (Object obj : saved) {
                String displayText = getDisplayText(obj);
                
                Label itemLabel = new Label(displayText);
                itemLabel.setWrapText(true);
                itemLabel.setStyle("-fx-padding: 4; -fx-background-color: #f0f0f0; -fx-background-radius: 3;");
                itemLabel.setMaxWidth(Double.MAX_VALUE);
                
                itemsList.getChildren().add(itemLabel);
            }
        }
    }

    /**
     * Retourne le texte d'affichage pour un objet
     */
    private String getDisplayText(Object obj) {
        if (obj == null) return "null";
        
        String className = obj.getClass().getSimpleName();
        
        try {
            java.lang.reflect.Method getNom = obj.getClass().getMethod("getNom");
            Object nom = getNom.invoke(obj);
            return className + ": " + nom;
        } catch (Exception e) {
            return className + ": " + obj.toString();
        }
    }

    /**
     * Lance l'animation
     */
    private void launchAnimation() {
        List<Object> saved = ListView.getSavedItems();
        
        System.out.println("=== Lancement de l'animation ===");
        System.out.println("Nombre d'éléments: " + saved.size());
        
        for (Object obj : saved) {
            System.out.println("- " + obj.getClass().getSimpleName() + ": " + obj);
        }
    }
}
