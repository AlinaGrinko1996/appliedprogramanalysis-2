package com.my.MyTask.models;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.util.AbstractSelectModel;

import com.my.MyTask.entities.Language;

public class LanguageSelectModel extends AbstractSelectModel {
	private List<Language> languages;

	public LanguageSelectModel(List<Language> languages) {
		this.languages = languages;
	}

	@Override
	public List<OptionGroupModel> getOptionGroups() {
		return null;
	}

	@Override
	public List<OptionModel> getOptions() {
		List<OptionModel> options = new ArrayList<OptionModel>();
		for (Language lang : languages) {
			options.add(new OptionModelImpl(lang.getName(), lang));
		}
		return options;
	}
}