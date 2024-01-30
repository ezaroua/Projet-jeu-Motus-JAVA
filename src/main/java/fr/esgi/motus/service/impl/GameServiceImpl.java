package fr.esgi.motus.service.impl;

import fr.esgi.motus.business.Attemp;
import fr.esgi.motus.business.Game;
import fr.esgi.motus.business.Score;
import fr.esgi.motus.business.Word;
import fr.esgi.motus.service.IGameService;

public class GameServiceImpl implements IGameService {

    private Game game;

    public GameServiceImpl() {
        this.game = new Game();
    }

    @Override
    public void startGame(Word word) {
        this.game.setWord(word);
        this.game.setScore(new Score()); // reset score
        this.game.getAttempts().clear(); // clear old attempts
    }

    @Override
    public boolean makeGuess(Word guessedWord) {
        Attemp attempt = new Attemp(guessedWord);
        this.game.getAttempts().add(attempt);

        if (guessedWord.getWord().equals(game.getWord().getWord())) {
            game.getScore().increaseScore();
            return true;
            //Todo change max attemps
        } else if (game.getAttempts().size() >= 10) {
            //Todo what happen whe the game end ?
        }

        return false;
    }

    @Override
    public Game getGame() {
        return this.game;
    }
}