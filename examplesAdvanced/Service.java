/**
 * 
 */
package com.my.MyTask.pages;

import java.util.List;

import javax.ejb.EJB;

import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.util.TextStreamResponse;

import com.my.MyTask.entities.Word;
import com.my.MyTask.services.IWordFinderService;
import com.my.MyTask.services.WordFinderService;

//This is actually not page, it is made to return JSON date from DB
//Called from my-autocomplete.js
public class Service {
	
	@Property
	@Inject
	@Symbol(SymbolConstants.TAPESTRY_VERSION)
	private String tapestryVersion;

	@EJB
	private IWordFinderService wordFinderService = new WordFinderService();

	StreamResponse onActivate() {
		JSONArray json = getResult();
		return new TextStreamResponse("application/json", json.toCompactString());
	}

	private JSONArray getResult() {
		JSONArray resulted = new JSONArray();
		
		List<Word> allWords = wordFinderService.findAll();
		if(allWords!=null&&!allWords.isEmpty()){
			for(Word w:allWords){
				resulted.put(w.getName());
			}
		}
		return resulted;
	}
}
