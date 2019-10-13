package com.my.MyTask.services;

import java.util.List;

import com.my.MyTask.entities.Language;
import com.my.MyTask.entities.Word;

public interface IWordFinderService {

	Word findById(long id);
	
	Word findByName(String name);
	
	Word findByName(Language language, String name);
	
	//List<Word> findByPartialName(Language language, String partialName);
	
	List<Word> findByMeaning(long meaningId);
	
	List<Word> findAll();

	long findMaxMeaningId();

	List<Word> findTranslations(Language languageFrom, Language languageTo, String wordName);
}
