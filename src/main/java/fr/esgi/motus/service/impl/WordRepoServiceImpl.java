package fr.esgi.motus.service.impl;

import fr.esgi.motus.business.Word;
import fr.esgi.motus.service.IWordRepoService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public final class WordRepoServiceImpl implements IWordRepoService {

    private static final String SIX_LENGHT_WORD_URL = "https://raw.githubusercontent.com/gverdier/motus/master/Console/Dictionnaire6.txt";
    private static final String SEVEN_LENGHT_WORD_URL = "https://raw.githubusercontent.com/gverdier/motus/master/Console/Dictionnaire7.txt";
    private static final String EIGHT_LENGHT_WORD_URL = "https://raw.githubusercontent.com/gverdier/motus/master/Console/Dictionnaire8.txt";

    
    private ArrayList<Word> sixLengthWord = new ArrayList<>();
    private ArrayList<Word> sevenLengthWord = new ArrayList<>();
    private ArrayList<Word> eightLengthWord = new ArrayList<>();

    private static final WordRepoServiceImpl instance = new WordRepoServiceImpl();

    private WordRepoServiceImpl() {
    }

    public static WordRepoServiceImpl getInstance() {
        return instance;
    }


    @Override
    public void importWords() {
        this.importWordsFromURL(SIX_LENGHT_WORD_URL, sixLengthWord);
        this.importWordsFromURL(SEVEN_LENGHT_WORD_URL, sevenLengthWord);
        this.importWordsFromURL(EIGHT_LENGHT_WORD_URL, eightLengthWord);


    }

    @Override
    public Word getRandomWordByLength(int length) {
        switch(length) {
            case 6 :
                return sixLengthWord.get(new Random().nextInt(sixLengthWord.size()));
            case 7 :
                return sevenLengthWord.get(new Random().nextInt(sevenLengthWord.size()));
            case 8 :
                return eightLengthWord.get(new Random().nextInt(sevenLengthWord.size()));
        }
        return null;
    }


    private void importWordsFromURL(String urlStr, ArrayList<Word> wordList) {
        try {
            URL url = new URL(urlStr);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                Word word = new Word(line);
                wordList.add(word);
            }

            reader.close();
        } catch (IOException e) {
            //ToDo implement better error handling !
            e.printStackTrace();
        }
    }
    


    @Override
    public Boolean isWordInList(Word word) {
        int wordLength = word.getWord().length();

        switch (wordLength) {
            case 6:
                for (Word wordList : sixLengthWord) {
                    if (wordList.getWord().equalsIgnoreCase(word.getWord())) {
                        return true;
                    }
                }
                break;
            case 7:
                for (Word wordList : sevenLengthWord) {
                    if (wordList.getWord().equalsIgnoreCase(word.getWord())) {
                        return true;
                    }
                }
                break;
            case 8:
                for (Word wordList : eightLengthWord) {
                    if (wordList.getWord().equalsIgnoreCase(word.getWord())) {
                        return true;
                    }
                }
                break;
            default:
                return false;
        }

        return false;
    }

}
