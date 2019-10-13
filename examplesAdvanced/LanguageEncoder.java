package com.my.MyTask.encoders;

import org.apache.tapestry5.ValueEncoder;

import com.my.MyTask.entities.Language;
import com.my.MyTask.services.ILanguageFinderService;

public class LanguageEncoder implements ValueEncoder<Language> {

    private ILanguageFinderService languageFinderService;

    public LanguageEncoder(ILanguageFinderService languageFinderService) {
        this.languageFinderService = languageFinderService;
    }

    @Override
    public String toClient(Language value) {
        return String.valueOf(value.getId());
    }

    @Override
    public Language toValue(String id) {
        return languageFinderService.findLanguage(Long.parseLong(id));
    }
}
