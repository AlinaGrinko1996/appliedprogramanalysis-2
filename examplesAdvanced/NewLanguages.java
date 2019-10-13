/**
 * 
 */
package com.my.MyTask.pages;

import javax.ejb.EJB;

import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.my.MyTask.entities.Language;
import com.my.MyTask.services.ILanguageFinderService;
import com.my.MyTask.services.LanguageFinderService;

public class NewLanguages {

	// The activation context

	private String languageFromName;

	private String languageToName;

	// Screen fields

	@Property
	private Language languageFrom;

	@Property
	private Language languageTo;
	// Work fields

	@Inject
	private Session session;

	@InjectPage
	private WordSearch wordSearchContextPage;
	// Generally useful bits and pieces

	@InjectComponent("newlanguagesform")
	private Form form;

	@EJB
	private ILanguageFinderService languageFinderService = new LanguageFinderService();

	void onPrepareForRender() throws Exception {
		languageFrom = new Language();
		languageTo = new Language();
	}

	void onPrepareForSubmit() throws Exception {
		languageFrom = languageFinderService.findLanguageByName(languageFromName);
		languageTo = languageFinderService.findLanguageByName(languageToName);
	}

	void onValidateFromNewLanguagesForm() {
		languageToName = languageTo == null ? null : languageTo.getName();
		languageFromName = languageFrom == null ? null : languageFrom.getName();
	}

	@CommitAfter
	public Object onSuccess() {
		Language language = null;
		if ((language = languageFinderService.findLanguageByName(languageFromName)) == null) {
			session.persist(languageFrom);
		} else {
			languageFrom = language;
			session.refresh(languageFrom);
		}
		if ((language = languageFinderService.findLanguageByName(languageToName)) == null) {
			session.persist(languageTo);
		} else {
			languageTo = language;
			session.refresh(languageTo);
		}

		wordSearchContextPage.set(languageFrom, languageTo);
		
		return wordSearchContextPage;
	}
}