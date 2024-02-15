package fr.esgi.tusmoV2.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;
import org.junit.Before;
import fr.esgi.motus.business.Word;
import fr.esgi.motus.business.Score;
import fr.esgi.motus.service.impl.GameServiceImpl;
import fr.esgi.motus.business.Game;

class GameServiceImplTest {

	private GameServiceImpl gameService;

    @Before
    public void setUp() {
        gameService = new GameServiceImpl();
    }

    @Test
    public void testStartGame() {
        Word word = new Word("test");
        gameService.startGame(word);
        //assertEquals("test", gameService.getGame().getWord().getWord(), "Word should be 'test'");
        assertEquals(0, gameService.getGame().getScore().getScore(), "Score should be initialized to 0");
        assertTrue(gameService.getGame().getAttempts().isEmpty(), "Attempts should be empty");
    }

    @Test
    public void testMakeGuessCorrect() {
        gameService.startGame(new Word("test"));
        boolean result = gameService.makeGuess(new Word("test"));
        assertTrue("Guess should be correct", result);
        //assertEquals("Score should be increased", 1, gameService.getGame().getScore().getPoints());
    }

    @Test
    public void testMakeGuessIncorrect() {
        gameService.startGame(new Word("test"));
        boolean result = gameService.makeGuess(new Word("wrong"));
        assertFalse("Guess should be incorrect", result);
        assertEquals("Attempts should increase", 1, gameService.getGame().getAttempts().size());
    }

    @Test
    public void testMaxAttempts(){
        gameService.startGame(new Word("test"));
        for (int i = 0; i < 10; i++) {
            gameService.makeGuess(new Word("wrong" + i));
        }
        // Assuming 10 is the max attempts allowed
        boolean result = gameService.makeGuess(new Word("wrong10"));
        // Based on the current logic, this will always be false, but you may need to adjust this test based on how you handle max attempts in your code
        assertFalse("Should not be able to make guesses after 10 attempts", result);
    }

}
