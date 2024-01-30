package fr.esgi.motus.service;

import fr.esgi.motus.business.Word;

public interface IWordRepoService {

      void importWords() ;

      Word getRandomWordByLength(int length);

      Boolean isWordInList(Word word);

}
