package ua.nure.swirchkov.Task4;

public class MarkRecord {
	
	private String studentName;
	private String testName;
	private Integer mark;
	
	public MarkRecord(String studentName, String testName, Integer mark) {
		this.studentName = studentName;
		this.mark = mark;
		this.testName = testName;
	}
	
	public String getStudentName() {
		return this.studentName;
	}
	
	public Integer getMark() {
		return this.mark;
	}
	
	public String getTestName() {
		return this.testName;
	}
	
	public void setMark(Integer value) {
		this.mark = value;
	}
}
