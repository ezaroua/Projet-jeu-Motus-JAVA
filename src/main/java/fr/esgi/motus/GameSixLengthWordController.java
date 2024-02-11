package fr.esgi.motus;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;







public class GameSixLengthWordController {



    private GridPane grille;
    private GridPane caseGrille;
    private int currentRow = 0;
    private int currentColumn = 0;

    private List<String> lettres = new ArrayList<>(); // Pour suivre les lettres entrées.
    private int colonne = 0;
    private int ligne = 0;
    private static final Background BACKGROUND_BLEU = new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY));


    @FXML
    private GridPane gridPane;

    public void initialize() {
        gridPane.getChildren().clear(); // Nettoyer la grille avant de l'initialiser

        for (int row = 0; row < 6; row++) { // Supposons que vous avez 6 lignes
            for (int col = 0; col < 6; col++) { // Supposons que vous avez 6 colonnes
                Label label = new Label();
                label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // Pour remplir la cellule
                label.setAlignment(Pos.CENTER); // Centrer le texte dans le label
                label.setFont(new Font("Arial", 24)); // Définir une police, si nécessaire
                label.setStyle("-fx-border-color: white; -fx-border-width: 1; -fx-background-color: transparent;"); // Dessiner les bordures des cellules

                gridPane.add(label, col, row); // Ajouter le label à la grille à la position spécifiée
            }
        }
    }

    
 // Saisie de lettre bouton

    @FXML
    public void onAlphabetButtonClick(ActionEvent actionEvent) {
        Button boutonSource = (Button) actionEvent.getSource();
        String boutonLettre = boutonSource.getText();

        // Retrieve the label at the current row and column
        Label label = getLabelByRowColumn(ligne, colonne);

        // If a label is found, set its text and style
        if (label != null) {
            label.setText(boutonLettre);
            // Here you set the background color to blue, text fill to white, and font weight to bold
            // You also reapply the border color and width to maintain the grid appearance
            label.setStyle("-fx-background-color: blue; -fx-text-fill: white; -fx-font-weight: bold; " +
                           "-fx-border-color: white; -fx-border-width: 0.5;");
        }

        // Increment the column index
        colonne++;

        // If the column index reaches the grid's column count, reset it and increment the row index
        if (colonne >= gridPane.getColumnConstraints().size()) {
            colonne = 0; // Reset column index to 0
            ligne++;     // Move to the next row
        }

        // If the row index reaches the grid's row count, reset it or stop input
        if (ligne >= gridPane.getRowConstraints().size()) {
            ligne = 0; // Reset the row index to 0 or handle as needed
        }
    }


    private Label getLabelByRowColumn(int row, int column) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                return (Label) node;
            }
        }
        return null; // No label found at the specified row and column
    }


    
}