package ui.node;

import java.util.Arrays;
import java.util.List;

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

    private javafx.scene.control.ListView<String> fxList;
    private Button addButton;
    // valeur sauvegardée globalement (chaîne) — accessible depuis tout le programme via getSavedItem()
    private static String savedItem = null;
    private Button saveButton;
    private Label statusLabel;

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

    private void initialize() {
        setSpacing(4);
        setPadding(new Insets(6));
        fxList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fxList.setPrefHeight(120);

        // Petit titre au-dessus de la liste (optionnel)
        Label title = new Label("Liste voitures :");

        // Bouton simple en bas (ouvre un popup) et bouton Save pour sauvegarder la sélection
        addButton = new Button("Ajouter");
        addButton.setOnAction(evt -> opendialog());

        saveButton = new Button("Save");
        saveButton.setDisable(true);
        statusLabel = new Label("");

        // Sauvegarder l'élément sélectionné (string)
        saveButton.setOnAction(evt -> {
            String sel = fxList.getSelectionModel().getSelectedItem();
            if (sel != null) {
                savedItem = sel;
                statusLabel.setText("Saved: " + sel);
            }
        });

        // Activer le bouton Save seulement quand un élément est sélectionné
        fxList.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            saveButton.setDisable(newV == null);
            if (newV == null) statusLabel.setText("");
        });

        HBox controls = new HBox(8, addButton, saveButton, statusLabel);
        HBox.setHgrow(statusLabel, Priority.ALWAYS);

        getChildren().addAll(title, fxList, controls);
    }

    /**
     * Ouvre une petite fenêtre modale vide (placeholder).
     */
    public void opendialog() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Ajouter");

        // Contenu : chaque label avec son TextField (disposés en HBox)
        Label nameLabel = new Label("Nom :");
        TextField nom = new TextField();
        nom.setPromptText("Entrez le nom de la voiture");
        HBox rowNom = new HBox(8, nameLabel, nom);
        HBox.setHgrow(nom, Priority.ALWAYS);

        Label vitesseMaxLabel = new Label("Vitesse Max :");
        TextField vitesseMax = new TextField();
        vitesseMax.setPromptText("Entrez la vitesse max");
        HBox rowVitesse = new HBox(8, vitesseMaxLabel, vitesseMax);
        HBox.setHgrow(vitesseMax, Priority.ALWAYS);

        Label typeLabel = new Label("Type :");
        TextField type = new TextField();
        type.setPromptText("Entrez le type");
        HBox rowType = new HBox(8, typeLabel, type);
        HBox.setHgrow(type, Priority.ALWAYS);

        Label longueurLabel = new Label("Longueur :");
        TextField longueur = new TextField();
        longueur.setPromptText("Entrez la longueur");
        HBox rowLong = new HBox(8, longueurLabel, longueur);
        HBox.setHgrow(longueur, Priority.ALWAYS);

        Label largeurLabel = new Label("Largeur :");
        TextField largeur = new TextField();
        largeur.setPromptText("Entrez la largeur");
        HBox rowLarge = new HBox(8, largeurLabel, largeur);
        HBox.setHgrow(largeur, Priority.ALWAYS);

        Button ok = new Button("OK");
        ok.setDefaultButton(true);
        // Quand on clique sur OK, récupérer le texte et l'ajouter à la liste
        ok.setOnAction(e -> {
            String nomText = nom.getText() == null ? "" : nom.getText().trim();
            String vitesseMaxText = vitesseMax.getText();
            String typeText = type.getText();
            String longueurText = longueur.getText();
            String largeurText = largeur.getText();
            //Mbola ho alefa any anaty base 
            if (!nomText.isEmpty()) {
                fxList.getItems().add(nomText);
            }
            dialog.close();
        });

        VBox box = new VBox(8, rowNom, rowVitesse, rowType, rowLong, rowLarge, ok);
        box.setPadding(new Insets(10));

        Scene scene = new Scene(box);
        dialog.setScene(scene);
        dialog.setWidth(420);
        dialog.setHeight(320);
        // Demander le focus sur le premier champ
        nom.requestFocus();
        dialog.showAndWait();
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

    /**
     * Récupère la valeur sauvegardée (peut être null si rien de sauvegardé).
     */
    public static String getSavedItem() {
        return savedItem;
    }

    /**
     * Efface la valeur sauvegardée.
     */
    public static void clearSavedItem() {
        savedItem = null;
    }

}
