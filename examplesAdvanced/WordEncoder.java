package com.my.MyTask.encoders;

import org.apache.tapestry5.ValueEncoder;

import com.my.MyTask.entities.Word;
import com.my.MyTask.services.IWordFinderService;

public class WordEncoder  implements ValueEncoder<Word> {

    private IWordFinderService wordFinderService;

    public WordEncoder(IWordFinderService wordFinderService) {
        this.wordFinderService = wordFinderService;
    }

    @Override
    public String toClient(Word value) {
        return String.valueOf(value.getId());
    }

    @Override
    public Word toValue(String id) {
        return wordFinderService.findById(Long.parseLong(id));
    }
}