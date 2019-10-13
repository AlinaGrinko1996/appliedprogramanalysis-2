/**
 * 
 */
package com.my.MyTask.pages;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.hibernate.Session;

import com.my.MyTask.entities.Language;
import com.my.MyTask.entities.Word;
import com.my.MyTask.services.IWordFinderService;
import com.my.MyTask.services.WordFinderService;

//JQuery library import
@Import(module = { "my-autocomplete" })
public class WordSearch {

	private Language languageFrom;

	private Language languageTo;

	private String wordFromName;

	private String wordToName;

	@Property
	private Word wordFrom;

	@Property
	private Word wordTo;

	@Property
	private String searchedWord;

	@Property
	private Word foundWord;

	@Property
	private List<Word> foundWords;

	@Component
	private Zone newWordsZone;

	@Component
	private Zone wordSearchZone;

	@Component
	private Zone searchResultZone;

	@InjectComponent("newwordsform")
	private Form newWordsForm;

	@InjectComponent("wordsearchform")
	private Form searchForm;

	@InjectComponent("searchedWord")
	private TextField searchWordField;

	@EJB
	private IWordFinderService wordFinderService = new WordFinderService();

	@Inject
	private Session session;

	//To invoke JQuery autocomplete
	@Inject
	private JavaScriptSupport javaScriptSupport;

	//To refresh few zones simultaneously
	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	public void afterRender() {
		javaScriptSupport.require("my-autocomplete");
	}

	//Link from other page
	public void set(Language languageFrom, Language languageTo) {
		this.languageFrom = languageFrom;
		this.languageTo = languageTo;
	}

	//---------------General Purpose methods-----------------------
	Object[] onPassivate() {
		return new Language[] { languageFrom, languageTo };
	}

	void onActivate(Language languageFrom, Language languageTo) {
		this.languageFrom = languageFrom;
		this.languageTo = languageTo;
	}

	public String getHeader() {
		return "Translation from " + languageFrom + " to " + languageTo;
	}

	//-----------------NewWordsInputForm methods------------------------
	
	void onPrepareForRender() throws Exception {
		wordFrom = new Word();
		wordTo = new Word();
		searchedWord = "";
	}

	void onPrepareForSubmit() throws Exception {
		wordFrom = wordFinderService.findByName(wordFromName);
		wordTo = wordFinderService.findByName(wordToName);
	}

	void onValidateFromNewWordsForm() {

		wordFromName = wordFrom == null ? null : wordFrom.getName();
		wordToName = wordTo == null ? null : wordTo.getName();
		if ((wordToName == null) || (wordFromName == null) || (wordFromName.trim().equals(""))
				|| (wordToName.trim().equals(""))) {
			searchForm.recordError(searchWordField, "The correct word required");
			return;
		}
	}

	@CommitAfter
	public Object onSubmitFromNewWordsForm() {

		if (!newWordsForm.isValid()) {
			newWordsForm.recordError("All fields must be filled in");
			return newWordsZone.getBody();
		}

		Word word = null;
		long meaningId = wordFinderService.findMaxMeaningId();
		meaningId++;
		if ((word = wordFinderService.findByName(wordFromName)) == null) {
			wordFrom.setMeaningId(meaningId);
			wordFrom.setLanguage(languageFrom);
			session.persist(wordFrom);
		} else {
			wordFrom = word;
			meaningId = wordFrom.getMeaningId();
			session.refresh(wordFrom);
		}
		if ((word = wordFinderService.findByName(wordToName)) == null) {
			wordTo.setMeaningId(meaningId);
			wordTo.setLanguage(languageTo);
			session.persist(wordTo);
		} else {
			wordTo = word;
			wordTo.setMeaningId(meaningId);
			session.refresh(wordTo);
		}

		//Refreshing few areas
		ajaxResponseRenderer.addRender(wordSearchZone).addRender(searchResultZone);
		return newWordsZone.getBody();
	}

	void onValidateFromWordSearchForm() {

		if ((searchedWord == null) || (searchedWord.trim().equals(""))) {
			searchForm.recordError(searchWordField, "The correct word required");
		}
	}

	@CommitAfter
	public Object onSuccessFromWordSearchForm() {
		
		List<Word> buf = wordFinderService.findTranslations(languageFrom, languageTo, 
				                                                                searchedWord);
		if ((buf == null) || (buf.size() < 1)) {
			foundWords = new ArrayList<Word>();
			foundWords.add(new Word("Results were not found", -1));
			
		} else {
			foundWords = buf;
		
		}
		return searchResultZone.getBody();
	}

}