package com.my.MyTask.services;

import java.util.List;

import com.my.MyTask.entities.Language;

public interface ILanguageFinderService {
	
	Language findLanguage(long id);

	Language findLanguageByName(String name);
	
	List<Language> findLanguages(int maxNumber);
}
