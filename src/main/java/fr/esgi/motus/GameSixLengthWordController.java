package fr.esgi.motus;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import java.util.ArrayList;
import java.util.List;
import fr.esgi.motus.business.Word;
import fr.esgi.motus.service.impl.WordRepoServiceImpl;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import fr.esgi.motus.service.impl.GameServiceImpl;








public class GameSixLengthWordController {

	private GameServiceImpl gameService;
    private WordRepoServiceImpl wordRepoService;

    private GridPane grille;
    private GridPane caseGrille;
    private int currentRow = 0;
    private int currentColumn = 0;

    private List<String> lettres = new ArrayList<>(); // Pour suivre les lettres entrées.
    private int colonne = 0;
    private int ligne = 0;
    private static final Background BACKGROUND_BLEU = new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY));

    private WordRepoServiceImpl wordService = WordRepoServiceImpl.getInstance();
    private Word currentWord;
    private StringBuilder currentAttempt = new StringBuilder();

    @FXML
    private GridPane gridPane;
    private boolean isRowCompleted = false; // Nouvel attribut pour suivre si la ligne est complétée
    private boolean readyForValidation = false;

    public void initialize() {
        gameService = new GameServiceImpl(); // Ensure the game service is initialized
        wordService = WordRepoServiceImpl.getInstance(); // Ensure the word service is initialized
        wordService.importWords(); // Import words from the repository
        currentWord = wordService.getRandomWordByLength(6); // Fetch a random 6-letter word
        
        if (currentWord == null) {
            showAlert("Erreur", "Impossible de démarrer le jeu car aucun mot n'a été trouvé.");
            return; // Exit if no word is found
        }

        gameService.startGame(currentWord); // Start the game with the fetched word

        gridPane.getChildren().clear(); // Clear the grid before initializing

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                Label label = new Label();
                label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // To fill the cell
                label.setAlignment(Pos.CENTER); // Center the text in the label
                label.setFont(new Font("Arial", 24)); // Set a font, if necessary
                label.setStyle("-fx-border-color: white; -fx-border-width: 1; -fx-background-color: transparent;"); // Draw cell borders

                if (row == 0 && col == 0) {
                    label.setText(String.valueOf(currentWord.getWord().charAt(0))); // Set the first letter of the word
                    label.setStyle("-fx-background-color: blue; -fx-text-fill: white; -fx-font-weight: bold; " +
                                   "-fx-border-color: white; -fx-border-width: 1;");
                }

                gridPane.add(label, col, row); // Add the label to the grid at the specified position
            }
        }
    }

  
    
 // Saisie de lettre bouton
    @FXML
    public void onAlphabetButtonClick(ActionEvent actionEvent) {
        if (isRowCompleted) { // Ne permettre aucune saisie si la ligne est complète.
            return;
        }
        
        Button boutonSource = (Button) actionEvent.getSource();
        String boutonLettre = boutonSource.getText();
        Label label = getLabelByRowColumn(ligne, colonne);
        
        if (label != null) {
            label.setText(boutonLettre);
            label.setStyle("-fx-background-color: blue; -fx-text-fill: white; -fx-font-weight: bold; " +
                           "-fx-border-color: white; -fx-border-width: 0.5;");
            colonne++;
        }
        
        if (colonne == 6) { // Vérifier si nous avons atteint la fin de la ligne après avoir ajouté une lettre.
            isRowCompleted = true; // Empêcher toute saisie supplémentaire si 6 lettres sont saisies.
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

    @FXML
    public void onSuprButtonClick(ActionEvent actionEvent) {
        if (colonne == 0 && ligne > 0) {
            ligne--;
            colonne = 5; // Placer le curseur à la fin de la ligne précédente
        } else if (colonne > 0) {
            colonne--;
        }
        
        Label label = getLabelByRowColumn(ligne, colonne);
        if (label != null) {
            label.setText("");
            label.setStyle("-fx-background-color: transparent; -fx-text-fill: black; -fx-font-weight: normal; " +
                           "-fx-border-color: white; -fx-border-width: 0.5;");
        }
        
        if (colonne < 6) {
            isRowCompleted = false; // Réactiver la saisie de lettres si le nombre de lettres est inférieur à 6.
        }
    }

    
    


    @FXML
    public void onValidButtonClick(ActionEvent event) {
        if (!isRowCompleted) {
            showAlert("Erreur", "Veuillez entrer 6 lettres avant de valider.");
            return;
        }

        try {
            StringBuilder wordBuilder = new StringBuilder();
            for (int col = 0; col < 6; col++) {
                Label label = getLabelByRowColumn(ligne, col);
                if (label != null && label.getText() != null && !label.getText().isEmpty()) {
                    wordBuilder.append(label.getText());
                } else {
                    showAlert("Erreur", "Veuillez compléter toutes les lettres de la ligne avant de valider.");
                    return;
                }
            }
            
            System.out.println("currentWord is: " + currentWord);
            if (currentWord != null) {
                System.out.println("Word to guess is: " + currentWord.getWord());
            } else {
                System.out.println("currentWord is null!");
            }

            String guessedWordStr = wordBuilder.toString();
            // Afficher le mot saisi par l'utilisateur avant la vérification
            showAlert("Mot saisi", "Vous avez saisi : " + guessedWordStr);
            Word guessedWord = new Word(guessedWordStr);
            
            if (wordService.isWordInList(guessedWord)) {
                if (gameService.makeGuess(guessedWord)) {
                    showAlert("Félicitations!", "Vous avez réussi!");
                    restartGame(); // Appel de la méthode pour redémarrer le jeu
                } else {
                    if (ligne < 5) {
                        ligne++;
                        colonne = 0;
                        isRowCompleted = false;
                        revealFirstCharacterOfWord();
                    } else {
                        showAlert("Fin du jeu", "Nombre maximum de tentatives atteint.");
                        restartGame(); // Optionnel: redémarrer le jeu automatiquement ou demander à l'utilisateur
                    }
                }
            } else {
                showAlert("Erreur", "Le mot n'existe pas dans la liste. Essayez encore sur la même ligne.");
                clearCurrentRow();
            }

            isRowCompleted = false;
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur inattendue", "Une erreur est survenue: " + e.getMessage());
        }
    }
    
    
/*
    private void restartGame() {
        gridPane.getChildren().clear(); // Nettoyer la grille
        currentRow = 0;
        currentColumn = 0;
        ligne = 0;
        colonne = 0;
        isRowCompleted = false; // Réinitialiser l'état de complétude de la ligne
        initialize(); // Réinitialiser le jeu
    }*/

    
    private void restartGame() {
        try {
            // Charger la nouvelle vue à partir de rejouer.fxml
        	Parent root = FXMLLoader.load(getClass().getResource("/fr/esgi/motus/rejouer.fxml"));

            
            // Obtenir la scène actuelle à partir d'un composant (ici, gridPane)
            Scene scene = gridPane.getScene(); // Assurez-vous que gridPane est accessible ici
            
            // Si gridPane n'est pas accessible directement (par exemple, si vous êtes dans un contexte statique ou si gridPane est privé), 
            // vous devrez trouver une autre façon d'obtenir la Stage, comme passer la Stage courante à cette méthode ou la stocker dans une variable accessible.
            
            // Obtenir la stage à partir de la scène
            Stage stage = (Stage) scene.getWindow();
            
            // Définir la nouvelle scène sur la stage
            stage.setScene(new Scene(root));
            
            // Afficher la nouvelle vue
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur lors du chargement", "Impossible de charger la vue rejouer.fxml");
        }
    }

    private void clearCurrentRow() {
        for (int col = 0; col < 6; col++) {
            Label label = getLabelByRowColumn(ligne, col);
            if (label != null) {
                label.setText("");
            }
        }
        colonne = 0;
    }

    private void revealFirstCharacterOfWord() {
        Label label = getLabelByRowColumn(ligne, 0);
        if (label != null) {
            label.setText(String.valueOf(currentWord.getWord().charAt(0)));
            label.setStyle("-fx-background-color: blue; -fx-text-fill: white; -fx-font-weight: bold; " +
                           "-fx-border-color: white; -fx-border-width: 1;");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}