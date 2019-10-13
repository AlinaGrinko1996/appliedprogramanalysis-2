package com.my.MyTask.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.my.MyTask.entities.Language;
import com.my.MyTask.entities.Word;

@Stateless
@Local(IWordFinderService.class)
@Remote(IWordFinderService.class)

public class WordFinderService implements IWordFinderService {

	@Inject
	private Session session;

	private static SessionFactory sessionFactory;

	public static SessionFactory createSessionFactory() {
		Configuration configuration = new Configuration();
		configuration.configure();
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		return sessionFactory;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Word> findAll() {
		session = createSessionFactory().openSession();
		org.hibernate.Query query = session.createQuery("from Word");
		List<Word> result = query.list();
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Word findById(long id) {

		StringBuilder buf = new StringBuilder();
		buf.append("from Word l");
		buf.append(" where l.id = :id");

		session = createSessionFactory().openSession();
		org.hibernate.Query query = session.createQuery(buf.toString());
		query.setParameter("id", id);

		List<Word> result = query.list();
		if (result == null || result.isEmpty()) {
			result = new ArrayList<Word>();
			return null;
		}
		return result.get(0);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Word findByName(String name) {

		StringBuilder buf = new StringBuilder();
		buf.append("from Word where name='");
		buf.append(name);
		buf.append("'");

		session = createSessionFactory().openSession();
		org.hibernate.Query query = session.createQuery(buf.toString());

		List<Word> result = query.list();

		if (result != null && !result.isEmpty()) {
			return result.get(0);
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Word findByName(Language language, String name) {

		session = createSessionFactory().openSession();

		StringBuilder buf = new StringBuilder();
		buf.append("from Word where name='");
		buf.append(name);
		buf.append("' and language_id = :id");

		org.hibernate.Query query = session.createQuery(buf.toString());
		query.setParameter("id", language.getId());

		List<Word> result = query.list();

		if (result != null && !result.isEmpty()) {
			return result.get(0);
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Word> findByMeaning(long meaningId) {
		session = createSessionFactory().openSession();
		org.hibernate.Query query = session.createQuery("from Word where meaningId='" + meaningId + "'");
		List<Word> result = query.list();
		if (result == null || result.isEmpty()) {
			return null;
		}
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public long findMaxMeaningId() {
		session = createSessionFactory().openSession();
		org.hibernate.Query query = session.createQuery("select max(meaningId) as " + "max from Word");
		List<Long> result = query.list();
		long answer = 0;
		if (result == null || result.isEmpty() || result.get(0) == null) {
			return -1;
		} else {
			System.err.println("Max meaning id --> " + result.get(0));
			answer = result.get(0);
		}
		return answer;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Word> findTranslations(Language languageFrom, Language languageTo, String wordName) {

		// The following means ==>
		// SELECT * FROM `word` WHERE language_id=id and meaningId IN
		// (SELECT meaningId FROM `word` WHERE name='name' AND language_id=id)

		StringBuilder buf = new StringBuilder();
		buf.append("from Word where language_id = ");
		buf.append(languageTo.getId());
		buf.append(" and meaningId in ");
		buf.append("( select word.meaningId from Word as word ");
		buf.append("where word.name='");
		buf.append(wordName);
		buf.append("' and language_id= ");
		buf.append(languageFrom.getId());
		buf.append(" )");

		session = createSessionFactory().openSession();
		org.hibernate.Query query = session.createQuery(buf.toString());

		List<Word> result = query.list();

		if (result != null && !result.isEmpty()) {
			return result;
		}
		return null;

	}
}
