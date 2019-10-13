package ua.nure.swirchkov.Task4;

public class TestRecord {
	private Integer questions;
	private String testName;
	
	public TestRecord(Integer questions, String name) {
		this.questions = questions;
		testName = name;
	}
	
	public Integer getQuestionNumber() {
		return this.questions;
	}
	
	public String getName() {
		return this.testName;
	} 
}
