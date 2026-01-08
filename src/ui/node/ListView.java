package ui.node;

import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;

/**
 * Composant minimaliste qui affiche une liste de chaînes.
 *
 * Cette classe étend VBox et contient une javafx.scene.control.ListView<String> interne.
 * Elle fournit des constructeurs pratiques pour initialiser les éléments depuis un tableau
 * ou une List et une méthode setItems pour mettre à jour la liste.
 */
public class ListView extends VBox {

	private final javafx.scene.control.ListView<String> fxList;

	/** Crée une ListView vide. */
	public ListView() {
		this.fxList = new javafx.scene.control.ListView<>();
		initialize();
	}

	/** Crée une ListView contenant les éléments du tableau fourni. */
	public ListView(String[] items) {
		this(Arrays.asList(items));
	}

	/** Crée une ListView contenant les éléments de la liste fournie. */
	public ListView(List<String> items) {
		this.fxList = new javafx.scene.control.ListView<>(FXCollections.observableList(items));
		initialize();
	}

	private void initialize() {
		setSpacing(4);
		setPadding(new Insets(6));
		fxList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		fxList.setPrefHeight(120);

		// Petit titre au-dessus de la liste (optionnel)
		Label title = new Label("Liste");
		getChildren().addAll(title, fxList);
	}

	/** Remplace les éléments par ceux du tableau. */
	public void setItems(String[] items) {
		setItems(Arrays.asList(items));
	}

	/** Remplace les éléments par ceux de la liste. */
	public void setItems(List<String> items) {
		fxList.setItems(FXCollections.observableList(items));
	}

	/** Retourne le contrôle JavaFX interne si besoin d'accès direct. */
	public javafx.scene.control.ListView<String> getControl() {
		return fxList;
	}

}