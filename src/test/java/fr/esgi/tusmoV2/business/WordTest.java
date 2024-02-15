package fr.esgi.tusmoV2.business;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import fr.esgi.motus.business.Word;

class WordTest {

	
	@Test
    public void testWordInitialization() {
        String testWordStr = "test";
        Word word = new Word(testWordStr);
        assertNotNull(word, "L'instance de Word ne devrait pas être null");
        assertEquals(0, word.getId(),"L'ID devrait être incrémenté"); 
        assertEquals("Le mot devrait correspondre à celui passé au constructeur", testWordStr, word.getWord());
    }

    @Test
    public void testSetWord() {
        String initialWordStr = "initial";
        String newWordStr = "new";
        Word word = new Word(initialWordStr);
        word.setWord(newWordStr);
        assertEquals("Le mot devrait être mis à jour avec la nouvelle valeur", newWordStr, word.getWord());
    }

}
