package fr.esgi.tusmoV2.service;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import fr.esgi.motus.business.Word;
import fr.esgi.motus.service.impl.WordRepoServiceImpl;

public class WordRepoServiceImplTest {

	private WordRepoServiceImpl wordRepoService;

    @Before
    public void setUp() {
        wordRepoService = WordRepoServiceImpl.getInstance();
        wordRepoService.importWords();
    }

    @Test
    public void testWordsImported() {
        assertFalse("Six-length words should not be empty", wordRepoService.getSixLengthWord().isEmpty());
        assertFalse("Seven-length words should not be empty", wordRepoService.getSevenLengthWord().isEmpty());
        assertFalse("Eight-length words should not be empty", wordRepoService.getEightLengthWord().isEmpty());
    }

    @Test
    public void testGetRandomWordByLength() {
        Word sixLengthWord = wordRepoService.getRandomWordByLength(6);
        assertNotNull(sixLengthWord);
        assertEquals("Random six-length word should have 6 characters", 6, sixLengthWord.getWord().length());

        Word sevenLengthWord = wordRepoService.getRandomWordByLength(7);
        assertNotNull("Random seven-length word should not be null", sevenLengthWord);
        assertEquals("Random seven-length word should have 7 characters", 7, sevenLengthWord.getWord().length());

        Word eightLengthWord = wordRepoService.getRandomWordByLength(8);
        assertNotNull("Random eight-length word should not be null", eightLengthWord);
        assertEquals("Random eight-length word should have 8 characters", 8, eightLengthWord.getWord().length());
    }

    @Test
    public void testIsWordInList() {
        Word wordInList = new Word("example"); 
        assertTrue("Word should be found in list", wordRepoService.isWordInList(wordInList));

        Word wordNotInList = new Word("notaword"); 
        assertFalse("Word should not be found in list", wordRepoService.isWordInList(wordNotInList));
    }
}
