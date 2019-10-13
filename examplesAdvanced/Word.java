package com.my.MyTask.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.xml.bind.ValidationException;

import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Translate;
import org.apache.tapestry5.ioc.annotations.Inject;

@Entity
@SuppressWarnings("serial")
@Table(name = "Word")
public class Word implements Serializable {

	@Id
	@NonVisual
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private long id;

	@Column(nullable = false)
	private String name;

	@NonVisual
	@Column(nullable = false)
	private long meaningId;
	
	@NonVisual
	@ManyToOne
	@JoinColumn(name = "language_id", columnDefinition = "varchar(25555)")
	private Language language;

	@Inject 
	public Word() {
	}
	
	public Word(String name, long meaningId) {
		this.name=name;
		this.meaningId=meaningId;
	}

	public long getId() {
		return this.id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public long getMeaningId() {
		return meaningId;
	}

	public void setMeaningId(long meaningId) {
		this.meaningId = meaningId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
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

        if (meaningId == 0) {
            throw new ValidationException("Meaning is required.");
        }
    }
}
