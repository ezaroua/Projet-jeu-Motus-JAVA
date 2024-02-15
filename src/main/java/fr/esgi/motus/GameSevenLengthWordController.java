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
import javafx.util.Duration;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import fr.esgi.motus.business.Word;
import fr.esgi.motus.service.impl.WordRepoServiceImpl;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import fr.esgi.motus.service.impl.GameServiceImpl;



public class GameSevenLengthWordController {

	private GameServiceImpl gameService;
    private WordRepoServiceImpl wordRepoService;

    private GridPane grille;
    private GridPane caseGrille;
    private int currentRow = 0;
    private int currentColumn = 0;

    private List<String> lettres = new ArrayList<>(); // Pour suivre les lettres entrées
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
    @FXML
    private Label labelMsg;

    public void initialize() {
        gameService = new GameServiceImpl();
        wordService = WordRepoServiceImpl.getInstance(); // Ensure the word service is initialized
        wordService.importWords(); // Import words from the repository
        currentWord = wordService.getRandomWordByLength(7); // Fetch a random 7 letter word
        
        if (currentWord == null) {
            showAlert("Erreur", "Impossible de démarrer le jeu car aucun mot n'a été trouvé.");
            return; // Exit if no word is found
        }

        gameService.startGame(currentWord); // Start the game with the fetched word

        gridPane.getChildren().clear(); // Clear the grid before initializing

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                Label label = new Label();
                label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // To fill the cell
                label.setAlignment(Pos.CENTER); // Center the text in the label
                label.setFont(new Font("Arial", 24)); // Set a font, if necessary
                label.setStyle("-fx-border-color: white; -fx-border-width: 1; -fx-background-color: transparent;"); // Draw cell borders

                if (row == 0 && col == 0) {
                    label.setText(String.valueOf(currentWord.getWord().charAt(0)).toUpperCase());
                    label.setStyle("-fx-background-color: #147c8b; -fx-text-fill: white; -fx-font-weight: bold; " +
                                   "-fx-border-color: white; -fx-border-width: 1;");
                }

                gridPane.add(label, col, row); // Add the label to the grid at the specified position
            }
        }
     // Pour forcer la saisie à commencer à partir de la deuxième case
        colonne = 1;
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
            label.setStyle("-fx-background-color: #147c8b; -fx-text-fill: white; -fx-font-weight: bold; " +
                           "-fx-border-color: white; -fx-border-width: 0.5;");
            colonne++;
        }
        
        if (colonne == 7) { // Vérifier si nous avons atteint la fin de la ligne après avoir ajouté une lettre.
            isRowCompleted = true; // Empêcher toute saisie supplémentaire si 7 lettres sont saisies.
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
        // Vérifier si l'utilisateur essaie de supprimer la première lettre de n'importe quelle ligne
        if (colonne <= 1) {
            // Si nous sommes dans la première colonne (après la lettre initiale verrouillée), ne rien faire pour empêcher la suppression
            return;
        }

        // Si nous sommes au-delà de la première colonne, permettre la suppression
        colonne--; // Décrémenter la colonne pour revenir à la cellule précédente
        Label label = getLabelByRowColumn(ligne, colonne);
        if (label != null) {
            label.setText(""); // Effacer le texte de la cellule
            label.setStyle("-fx-background-color: transparent; -fx-text-fill: black; -fx-font-weight: normal; " +
                           "-fx-border-color: white; -fx-border-width: 0.5;");
        }

        // Réactiver la saisie si nécessaire
        if (colonne < 7) {
            isRowCompleted = false; // Assurer que l'utilisateur peut continuer à saisir des lettres si la ligne n'est pas complète
        }
    }

    
    
 

    @FXML
    public void onValidButtonClick(ActionEvent event) {
        if (!isRowCompleted) {
            //showAlert("Erreur", "Veuillez entrer 7 lettres avant de valider.");
            Platform.runLater(() -> {
        	    labelMsg.setText("Veuillez entrer 7 lettres avant de valider");
        	    labelMsg.setStyle("-fx-text-fill: #FFFFFF;"); // Optionnel: changer la couleur du texte pour le succès
        	});
            return;
        }

        try {
            StringBuilder wordBuilder = new StringBuilder();
            for (int col = 0; col < 7; col++) {
                Label label = getLabelByRowColumn(ligne, col);
                if (label != null && label.getText() != null && !label.getText().isEmpty()) {
                    wordBuilder.append(label.getText());
                } else {
                	Platform.runLater(() -> {
                	    labelMsg.setText("Veuillez compléter toutes les lettres de la ligne avant de valider.");
                	    labelMsg.setStyle("-fx-text-fill: #FFFFFF;"); // Optionnel: changer la couleur du texte pour le succès
                	});
                    //showAlert("Erreur", "Veuillez compléter toutes les lettres de la ligne avant de valider.");
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
            //showAlert("Mot saisi", "Vous avez saisi : " + guessedWordStr);
            Word guessedWord = new Word(guessedWordStr);
            
            if (wordService.isWordInList(guessedWord)) {
                if (gameService.makeGuess(guessedWord)) {
                	/*Platform.runLater(() -> {
                	    labelMsg.setText("Félicitation, le mot est correct");
                	    labelMsg.setStyle("-fx-text-fill: green;"); // Optionnel: changer la couleur du texte pour le succès
                	});*/
                    App.setRoot("rejouer");
                } else {
                    if (ligne < 5) {
                        ligne++;
                        colonne = 0;
                        isRowCompleted = false;
                        highlightCommonLetters(guessedWordStr);
                        revealFirstCharacterOfWord();
                    } else {
                        //showAlert("Fin du jeu", "Nombre maximum de tentatives atteint.");
                        //restartGame(); // Optionnel: redémarrer le jeu automatiquement ou demander à l'utilisateur
                        App.setRoot("rejouerMotIncorrect");
                    }
                }
            } else {
                //showAlert("Erreur", "Le mot n'existe pas dans la liste. Essayez encore sur la même ligne.");
                
            	clearCurrentRow();
            	Platform.runLater(() -> {
                    clearCurrentRow();
                    labelMsg.setText("Le mot n'existe pas dans la liste !");
                    labelMsg.setStyle("-fx-text-fill: #FFFFFF;"); // Message d'erreur en rouge

                    // Créer un PauseTransition pour effacer le message après 10 secondes
                    PauseTransition pause = new PauseTransition(Duration.seconds(2));
                    pause.setOnFinished(e -> {
                        labelMsg.setText(""); // Effacer le message
                        labelMsg.setStyle(""); // Réinitialiser le style si nécessaire
                    });
                    pause.play(); // Démarrer la PauseTransition
                });
            }

            isRowCompleted = false;
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur inattendue", "Une erreur est survenue: " + e.getMessage());
        }
    }
    
    
    private void highlightCommonLetters(String guessedWordStr) {
        String targetWordStr = currentWord.getWord().toUpperCase(); // Convertir le mot cible en majuscules pour la comparaison
        // Convertir le mot saisi en majuscules pour la comparaison
        guessedWordStr = guessedWordStr.toUpperCase();

        for (int i = 0; i < guessedWordStr.length(); i++) {
            Label label = getLabelByRowColumn(ligne-1, i);
            if (label != null) {
                char letter = guessedWordStr.charAt(i);

                if (targetWordStr.charAt(i) == letter) {
                    // Si la lettre est à la bonne position, la colorer en rouge (déjà géré)
                	label.setStyle("-fx-background-color: RED; -fx-text-fill: black; -fx-font-weight: bold; " +
                            "-fx-border-color: white; -fx-border-width: 0.5;");
                } else if (targetWordStr.contains(String.valueOf(letter)) && !label.getTextFill().equals(Color.RED)) {
                    // Si la lettre est présente dans le mot mais pas à la bonne position, la colorer en jaune
                    label.setStyle("-fx-background-color: #FFD700; -fx-text-fill: black; -fx-font-weight: bold; " +
                                   "-fx-border-color: white; -fx-border-width: 0.5;");
                }
            }
        }
    }


    private void clearCurrentRowExceptFirstAndHighlighted() {
        for (int col = 1; col < 7; col++) { // Commencer à partir de la deuxième colonne
            Label label = getLabelByRowColumn(ligne, col);
            if (label != null && !label.getTextFill().equals(Color.RED)) {
                label.setText(""); // Effacer le texte si la lettre n'est pas en rouge
                // Réinitialiser le style si nécessaire, sauf pour les lettres en rouge
                label.setStyle("-fx-background-color: transparent; -fx-text-fill: black; -fx-font-weight: normal; " +
                               "-fx-border-color: white; -fx-border-width: 0.5;");
            }
        }
    }
    

    

    private void clearCurrentRow() {
        for (int col = 0; col < 7; col++) {
            Label label = getLabelByRowColumn(ligne, col);
            if (label != null) {
                label.setText("");
            }
        }
        revealFirstCharacterOfWord();
    }

    private void revealFirstCharacterOfWord() {
        Label label = getLabelByRowColumn(ligne, 0);
        if (label != null) {
        	label.setText(String.valueOf(currentWord.getWord().charAt(0)).toUpperCase());
            label.setStyle("-fx-background-color: #147c8b; -fx-text-fill: white; -fx-font-weight: bold; " +
                           "-fx-border-color: white; -fx-border-width: 1;");
        }
        colonne = 1;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    

    
    @FXML
    private void ToMenuPrincipal(ActionEvent event) {
        try {
            App.setRoot("primary");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

