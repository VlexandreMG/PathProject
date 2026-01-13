package ui.node;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Function;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Composant minimaliste qui affiche une liste de chaînes.
 *
 * Cette classe étend VBox et contient une javafx.scene.control.ListView<String>
 * interne. Elle fournit des constructeurs pratiques pour initialiser les
 * éléments depuis un tableau ou une List et une méthode setItems pour mettre à
 * jour la liste.
 */
public class ListView extends VBox {

    private final javafx.scene.control.ListView<String> fxList;
    private Button addButton;
    // support minimal pour traiter des objets : si objectItems != null alors la liste
    // d'affichage est construite depuis objectItems via labeler
    private List<?> objectItems = null;
    private Function<Object, String> labeler = null;

    // stockage simple et global d'une ou plusieurs sélections (objets) utilisable partout
    private static List<Object> savedObjects = new ArrayList<>();
    private static SelectionPanel selectionPanel = null;
    private Button saveButton;
    private Label savedStatus;

    /**
     * Lie le SelectionPanel pour mettre à jour l'affichage automatiquement
     */
    public static void setSelectionPanel(SelectionPanel panel) {
        selectionPanel = panel;
    }

    /**
     * Crée une ListView vide.
     */
    public ListView() {
        this.fxList = new javafx.scene.control.ListView<>();
        initialize();
    }

    /**
     * Crée une ListView contenant les éléments du tableau fourni.
     */
    public ListView(String[] items) {
        this(Arrays.asList(items));
    }

    /**
     * Crée une ListView contenant les éléments de la liste fournie.
     */
    public ListView(List<String> items) {
        this.fxList = new javafx.scene.control.ListView<>(FXCollections.observableArrayList(items));
        initialize();
    }

    /**
     * Constructeur minimaliste pour afficher des objets.
     * - items : liste d'objets à afficher
     * - labeler : fonction qui retourne la chaîne affichée pour chaque objet
     */
    public ListView(List<?> items, Function<Object, String> labeler) {
        // construire la liste des labels sans utiliser de streams (simple boucle)
        java.util.List<String> labels = new java.util.ArrayList<>();
        for (Object o : items) {
            labels.add(labeler.apply(o));
        }
        this.fxList = new javafx.scene.control.ListView<>(FXCollections.observableArrayList(labels));
        this.objectItems = items;
        this.labeler = (Function<Object, String>) labeler;
        initialize();
    }

    private void initialize() {
        setSpacing(4);
        setPadding(new Insets(6));
        fxList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        fxList.setPrefHeight(120);

        // Petit titre au-dessus de la liste (optionnel)
        Label title = new Label("Liste voitures :");

        // Bouton simple en bas (ouvre un popup) + bouton Save pour sauvegarder la sélection
        addButton = new Button("Ajouter");
        addButton.setOnAction(evt -> opendialog());

        saveButton = new Button("Save");
        saveButton.setDisable(true);

        savedStatus = new Label("");
        if (!savedObjects.isEmpty()) {
            savedStatus.setText("Saved: " + savedObjects.size() + " item(s)");
        }

        saveButton.setOnAction(evt -> {
            List<Integer> indices = fxList.getSelectionModel().getSelectedIndices();
            savedObjects.clear();
            if (!indices.isEmpty()) {
                for (int idx : indices) {
                    if (objectItems != null && idx < objectItems.size()) {
                        savedObjects.add(objectItems.get(idx));
                    } else {
                        // cas simple : on a juste des strings affichées
                        String sel = fxList.getItems().get(idx);
                        if (sel != null) {
                            savedObjects.add(sel);
                        }
                    }
                }
                savedStatus.setText("Saved: " + savedObjects.size() + " item(s)");
                
                // Mettre à jour le SelectionPanel si lié
                if (selectionPanel != null) {
                    selectionPanel.updateSelections();
                }
            }
        });

        // activer/désactiver le bouton Save selon la sélection
        fxList.getSelectionModel().getSelectedIndices().addListener((javafx.collections.ListChangeListener.Change<? extends Integer> c) -> {
            saveButton.setDisable(fxList.getSelectionModel().getSelectedIndices().isEmpty());
        });

        HBox controls = new HBox(8, addButton, saveButton, savedStatus);
        HBox.setHgrow(savedStatus, Priority.ALWAYS);

        getChildren().addAll(title, fxList, controls);
    }

    /**
     * Ouvre une petite fenêtre modale adaptée au type de modèle.
     */
    public void opendialog() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Ajouter");

        VBox fieldsBox = new VBox(8);
        fieldsBox.setPadding(new Insets(10));

        // Déterminer le type de modèle et créer les champs appropriés
        if (objectItems != null && !objectItems.isEmpty()) {
            Object firstItem = objectItems.get(0);
            String modelType = firstItem.getClass().getSimpleName();
            
            // Créer les champs selon le type de modèle
            java.util.List<TextField> fields = new java.util.ArrayList<>();
            
            switch (modelType) {
                case "Car":
                    fieldsBox.getChildren().addAll(
                        createField("Nom :", "Entrez le nom", fields),
                        createField("Vitesse Max :", "Entrez la vitesse max", fields),
                        createField("ID Type :", "Entrez l'ID type", fields),
                        createField("Longueur :", "Entrez la longueur", fields),
                        createField("Largeur :", "Entrez la largeur", fields)
                    );
                    break;
                    
                case "Path":
                    fieldsBox.getChildren().addAll(
                        createField("Nom :", "Entrez le nom", fields),
                        createField("Distance :", "Entrez la distance", fields),
                        createField("Largeur :", "Entrez la largeur", fields),
                        new Label("Note: Points non gérés dans ce dialog simple")
                    );
                    break;
                    
                case "Hole":
                    fieldsBox.getChildren().addAll(
                        createField("ID Path :", "Entrez l'ID du path", fields),
                        createField("Pourcentage :", "Entrez le pourcentage", fields),
                        createField("KmAge :", "Entrez le kmAge", fields)
                    );
                    break;
                    
                case "Point":
                    fieldsBox.getChildren().addAll(
                        createField("Nom :", "Entrez le nom", fields),
                        createField("X :", "Entrez la coordonnée X", fields),
                        createField("Y :", "Entrez la coordonnée Y", fields)
                    );
                    break;
                    
                case "Route":
                    fieldsBox.getChildren().addAll(
                        createField("Distance :", "Entrez la distance", fields),
                        createField("Durée :", "Entrez la durée", fields),
                        new Label("Note: Points et Paths non gérés dans ce dialog simple")
                    );
                    break;
                    
                case "Type":
                    fieldsBox.getChildren().addAll(
                        createField("ID :", "Entrez l'ID", fields),
                        createField("Nom :", "Entrez le nom", fields)
                    );
                    break;
                    
                default:
                    fieldsBox.getChildren().add(new Label("Type inconnu: " + modelType));
            }
            
            Button ok = new Button("OK");
            ok.setDefaultButton(true);
            ok.setOnAction(e -> {
                // Pour l'instant, juste ajouter le premier champ (nom ou id) à la liste d'affichage
                if (!fields.isEmpty() && fields.get(0).getText() != null && !fields.get(0).getText().trim().isEmpty()) {
                    String label = fields.get(0).getText().trim();
                    fxList.getItems().add(label);
                }
                dialog.close();
            });
            
            fieldsBox.getChildren().add(ok);
            
        } else {
            // Mode String simple (pas d'objets)
            TextField nom = new TextField();
            nom.setPromptText("Entrez le texte");
            HBox row = new HBox(8, new Label("Texte :"), nom);
            HBox.setHgrow(nom, Priority.ALWAYS);
            
            Button ok = new Button("OK");
            ok.setDefaultButton(true);
            ok.setOnAction(e -> {
                String text = nom.getText() == null ? "" : nom.getText().trim();
                if (!text.isEmpty()) {
                    fxList.getItems().add(text);
                }
                dialog.close();
            });
            
            fieldsBox.getChildren().addAll(row, ok);
        }

        Scene scene = new Scene(fieldsBox);
        dialog.setScene(scene);
        dialog.setWidth(420);
        dialog.setHeight(Math.min(500, 150 + fieldsBox.getChildren().size() * 40));
        dialog.showAndWait();
    }
    
    /**
     * Crée un champ de saisie avec son label
     */
    private HBox createField(String labelText, String prompt, java.util.List<TextField> fields) {
        Label label = new Label(labelText);
        label.setMinWidth(120);
        TextField field = new TextField();
        field.setPromptText(prompt);
        fields.add(field);
        HBox row = new HBox(8, label, field);
        HBox.setHgrow(field, Priority.ALWAYS);
        return row;
    }

    /**
     * Remplace les éléments par ceux du tableau.
     */
    public void setItems(String[] items) {
        setItems(Arrays.asList(items));
    }

    /**
     * Remplace les éléments par ceux de la liste.
     */
    public void setItems(List<String> items) {
        fxList.setItems(FXCollections.observableArrayList(items));
    }

    /**
     * Retourne le contrôle JavaFX interne si besoin d'accès direct.
     */
    public javafx.scene.control.ListView<String> getControl() {
        return fxList;
    }

    /** Récupère la liste des valeurs sauvegardées. */
    public static List<Object> getSavedItems() {
        return new ArrayList<>(savedObjects);
    }

    /** Efface la valeur sauvegardée. */
    public static void clearSavedItems() {
        savedObjects.clear();
    }

}
