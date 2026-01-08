package ui.layout;

import javafx.scene.layout.*;

public class RootLayout {
    private BorderPane borderPane;

    public RootLayout() {
        borderPane = new BorderPane();
        borderPane.setPrefSize(800, 600);
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }
    
}