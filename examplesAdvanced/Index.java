package com.my.MyTask.pages;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import com.my.MyTask.encoders.LanguageEncoder;
import com.my.MyTask.entities.Language;
import com.my.MyTask.models.LanguageSelectModel;
import com.my.MyTask.services.ILanguageFinderService;
import com.my.MyTask.services.LanguageFinderService;


public class Index
{
    static private final int MAX_RESULTS = 30;

    private Long languageFromId;
    
    private Long languageToId;

    @Property
    private SelectModel languagesFromModel;

    @Property
    private SelectModel languagesToModel;
    
    @Property
    private Language languageFrom;
    
    @Property
    private Language languageTo;
    
    @InjectPage
    private WordSearch wordSearchContextPage;
    
    @InjectPage
    private NewLanguages newLanguagesContextPage;  
    
    @InjectComponent
    private Zone languageModelZone;

    @Inject
    private Request request;

    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;

    @Inject
    private JavaScriptSupport javaScriptSupport;
    
    @EJB
    private ILanguageFinderService languageFinderService=new LanguageFinderService();

    
    Long onPassivate() {
        return languageFrom == null ? null : languageFrom.getId();
    }

    void onActivate(EventContext context) {
        if (context.getCount() > 0) {
            languageFromId = null;
        }
    }

    
    void onPrepareForRender() {
        //Preparing data to be shown in drag-and-drop field
        List<Language> languages = new ArrayList<Language>();
        languages=languageFinderService.findLanguages(MAX_RESULTS);
        
        if (languageFromId != null) {
            languageFrom = findLanguageInList(languageFromId, languages);
            
        } else{
        	 languagesFromModel=new  LanguageSelectModel(new ArrayList<Language>());
        }
        if((languages!=null)&&(languages.size()>0))
        	  languagesFromModel = new LanguageSelectModel(languages);
        else{
        	languagesFromModel=new  LanguageSelectModel(new ArrayList<Language>());
        }
        languagesToModel=new  LanguageSelectModel(new ArrayList<Language>());
    }

    void onValidateForm() {
        languageFromId = languageFrom == null ? null : languageFrom.getId();
        languageToId = languageTo == null ? null : languageTo.getId();
    }

    private Language findLanguageInList(Long languageId, List<Language> languages) {
        for (Language language : languages) {
            if (language.getId()==languageId) {
                return language;
            }
        }
        return null;
    }

    //For drag-and-drop fields
    public LanguageEncoder getLanguageEncoder() {
        return new LanguageEncoder(languageFinderService);
    }

    Object onSuccess() {
    	//GoTo word search page
    	wordSearchContextPage.set(languageFrom, languageTo);
        return wordSearchContextPage;
    }

    Object onGoToNewLanguages() {
    	return newLanguagesContextPage;
    }
    
    public Object getLayoutComponent() { 
    	   return "Index"; 
    	} 

    
    void onValueChangedFromLanguageFrom(Language languageFrom) {
    	
        List<Language> languages = new ArrayList<Language>();
        languages=languageFinderService.findLanguages(MAX_RESULTS);
        
        if (languageToId != null) {
            languageTo = findLanguageInList(languageToId, languages);
        }
        
        //Not to show in second field data? that was selected at first
        for(int i=0;i<languages.size();i++){
        	if(languages.get(i).getId()==languageFrom.getId()){
        		languages.remove(i);
        	}
        }
        languagesToModel = new LanguageSelectModel(languages);

        if (request.isXHR()) {
            ajaxResponseRenderer.addRender(languageModelZone);
        }
    }
}
