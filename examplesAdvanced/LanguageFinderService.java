package com.my.MyTask.services;

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

@Stateless
@Local(ILanguageFinderService.class)
@Remote(ILanguageFinderService.class)

public class LanguageFinderService implements ILanguageFinderService {

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
	public Language findLanguage(long id) {

		StringBuilder buf = new StringBuilder();
		buf.append("from Language l");
		buf.append(" where l.id = :id");

		session = createSessionFactory().openSession();
		org.hibernate.Query query = session.createQuery(buf.toString());
		query.setParameter("id", id);

		List<Language> result = query.list();
		if (result != null && !result.isEmpty()) {
			return result.get(0);
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Language findLanguageByName(String name) {

		StringBuilder buf = new StringBuilder();
		buf.append("from Language where name='");
		buf.append(name);
		buf.append("'");

		session = createSessionFactory().openSession();
		org.hibernate.Query query = session.createQuery(buf.toString());

		List<Language> result = query.list();

		if (result != null && !result.isEmpty()) {
			return result.get(0);
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Language> findLanguages(int maxNumber) {

		session = createSessionFactory().openSession();
		org.hibernate.Query query = session.createQuery("from Language");
		List<Language> result = query.list();

		if (result == null || result.isEmpty()) {
			return null;
		}
		return result;
	}

}
