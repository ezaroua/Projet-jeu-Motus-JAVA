package fr.esgi.motus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */

/*
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}*/




import fr.esgi.motus.business.Word;
import fr.esgi.motus.service.impl.GameServiceImpl;
import fr.esgi.motus.service.impl.WordRepoServiceImpl;

import java.util.Scanner;

public class App extends Application {

    private GameServiceImpl gameService;
    private WordRepoServiceImpl wordRepoService;
    private Scanner scanner;
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public App() {
        gameService = new GameServiceImpl();
        wordRepoService = WordRepoServiceImpl.getInstance();
        scanner = new Scanner(System.in);
    }
   


    public void run() {
        System.out.println("Bienvenue dans le jeu Motus !");
        wordRepoService.importWords();

        boolean continuePlaying = true;

        while (continuePlaying) {
            System.out.println("Choisissez la longueur du mot (6, 7 ou 8) :");
            int length = scanner.nextInt();
            Word randomWord = wordRepoService.getRandomWordByLength(length);

            gameService.startGame(randomWord);
            String hiddenWord = randomWord.getWord().substring(0, 1) + "_".repeat(randomWord.getWord().length() - 1);
            System.out.println(randomWord);
            boolean won = false;
            while (!won) {
                System.out.println("Mot à deviner: " + hiddenWord);
                System.out.println("Entrez votre essai :");
                String guess = scanner.next();
                Word guessedWord = new Word(guess);

                if (!wordRepoService.isWordInList(guessedWord)) {
                    System.out.println("Le mot entré n'existe pas dans la liste.");
                    continue;
                }

                won = gameService.makeGuess(guessedWord);
                if (won) {
                    System.out.println("Félicitations, vous avez trouvé le mot !");
                    break;
                }

                hiddenWord = updateHiddenWord(hiddenWord, randomWord.getWord(), guess);
                System.out.println("Indice: " + hiddenWord);
            }

            System.out.println("Voulez-vous rejouer ? (oui/non)");
            String response = scanner.next();
            continuePlaying = response.equalsIgnoreCase("oui");
        }

        System.out.println("Merci d'avoir joué à Motus !");
    }

    private String updateHiddenWord(String hiddenWord, String targetWord, String guess) {
        StringBuilder newHiddenWord = new StringBuilder(hiddenWord);
        for (int i = 1; i < targetWord.length(); i++) { // Start from index 1 as the first letter is already revealed
            if (i < guess.length() && targetWord.charAt(i) == guess.charAt(i)) {
                newHiddenWord.setCharAt(i, guess.charAt(i));
            }
        }
        return newHiddenWord.toString();
    }


    public static void main(String[] args) {
        //launch();
        new App().run();
    }
}
