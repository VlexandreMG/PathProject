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
    private Button saveButton;
    private Label savedStatus;

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

    /** Récupère la liste des valeurs sauvegardées. */
    public static List<Object> getSavedItems() {
        return new ArrayList<>(savedObjects);
    }

    /** Efface la valeur sauvegardée. */
    public static void clearSavedItems() {
        savedObjects.clear();
    }

}
