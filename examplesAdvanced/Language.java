package com.my.MyTask.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.xml.bind.ValidationException;

import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.annotations.Type;

@Entity
@SuppressWarnings("serial")
@Table(name = "Language")
public class Language implements Serializable {

	@Id
	@NonVisual
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(nullable = false, name = "language_id")
	private long id;
	
	@Column(nullable = false)
	private String name;
	
	@OneToMany(mappedBy = "language", cascade = CascadeType.ALL)
	private List<Word> words;
	
	@Inject 
	public Language(){}
	
	public Language(String name){
		this.name=name;
	}
	
	public Language(String name, List<Word> words){
		this.name=name;
		this.words=words;
	}
	
	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Word> getWords() {
		return words;
	}
	public void setWords(List<Word> words) {
		this.words = words;
	}
	
	public String toString() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj == this) || (obj instanceof Word) && name != null && name.equals(((Word) obj).getName());
	}


	@Override
	public int hashCode() {
		return name == null ? super.hashCode() : name.hashCode();
	}
	
	@PrePersist
    @PreUpdate
    public void validate() throws ValidationException {

        if ((name == null) || (name.trim().length() == 0)) {
            throw new ValidationException("Name is required.");
        }

    }
}

