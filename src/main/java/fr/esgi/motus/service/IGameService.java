package fr.esgi.motus.service;

import fr.esgi.motus.business.Game;
import fr.esgi.motus.business.Word;

public interface IGameService {
    void startGame(Word word);

    boolean makeGuess(Word guessedWord);

    Game getGame();

}