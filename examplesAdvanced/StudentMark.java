package ua.nure.swirchkov.Task4;

import java.util.List;

public class StudentMark {
	
	private String surname;
	
	private List<MarkRecord> marks;
	
	public StudentMark(String surname, List<MarkRecord> marks) {
		this.surname = surname;
		this.marks = marks;
	}
	
	public String getSurname() {
		return this.surname;
	}
	
	public List<MarkRecord> getMarks() {
		return this.marks;
	}
	
}
