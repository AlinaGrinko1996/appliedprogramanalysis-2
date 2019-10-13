package ua.nure.swirchkov.Task4;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Data {

	private List<TestRecord> tests;
	private List<StudentMark> marks;

	public Data(String fileName) throws IOException {
		String content = getFileContent(fileName);
		parseString(content);
	}

	private String getFileContent(String fileName) throws IOException {

		InputStreamReader reader = new InputStreamReader(new FileInputStream(fileName), "Cp1251");

		char[] values = new char[30];
		StringBuilder sb = new StringBuilder(1000);
		int count = 0;

		while ((count = reader.read(values)) != -1) {
			sb.append(values, 0, count);
		}

		reader.close();
		return sb.toString();
	}

	private void parseString(String content) {

		Pattern linePattern = Pattern.compile("[^\n]+\n");

		Matcher lineMatcher = linePattern.matcher(content);

		if (!lineMatcher.find()) {
			System.err.println("invalid char sequence from file");
			return;
		}

		String firstLine = lineMatcher.group();

		if (!lineMatcher.find()) {
			System.err.println("invalid char sequence from file");
			return;
		}

		String secondLine = lineMatcher.group();

		Pattern valuePattern = Pattern.compile("[^;]+;");

		Matcher firstMatcher = valuePattern.matcher(firstLine);
		Matcher secondMatcher = valuePattern.matcher(secondLine);

		ArrayList<TestRecord> tests = new ArrayList<>();

		while (firstMatcher.find()) {
			String firstLineValue = firstMatcher.group();
			firstLineValue = catValue(firstLineValue);

			if (!secondMatcher.find()) {
				System.err.println("invalid char sequence from file");
				return;
			}

			String secondLineValue = secondMatcher.group();
			secondLineValue = catValue(secondLineValue);

			if (firstLineValue.equals("")) {
				continue;
			}
			TestRecord record = new TestRecord(Integer.parseInt(firstLineValue), secondLineValue);
			tests.add(record);
		}

		this.tests = tests;

		ArrayList<StudentMark> studentMarks = new ArrayList<>();
		while (lineMatcher.find()) {
			String line = lineMatcher.group();
			Matcher valueMatcher = valuePattern.matcher(line);

			if (!valueMatcher.find()) {
				System.err.println("invalid char sequence from file");
				return;
			}

			String surname = valueMatcher.group();
			surname = catValue(surname);
			int count = 0;
			ArrayList<MarkRecord> marks = new ArrayList<>();
			while (valueMatcher.find()) {
				String mark = valueMatcher.group();
				String testName = tests.get(count++).getName();
				mark = catValue(mark);
				MarkRecord record;

				if (mark.equals("")) {
					record = new MarkRecord(surname, testName, -1);
				} else {
					record = new MarkRecord(surname, testName, Integer.parseInt(mark));
				}
				marks.add(record);
			}

			studentMarks.add(new StudentMark(surname, marks));
		}

		this.marks = studentMarks;
	}

	private String catValue(String value) {
		return value.substring(0, value.length() - 1).trim();
	}

	public List<TestRecord> getTests() {
		return this.tests;
	}

	public List<StudentMark> getMarks() {
		return this.marks;
	}

	public void saveChanges() throws IOException {
		OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(Constants.INPUT), "Cp1251");

		writer.write("\t\t\t;");

		for (int i = 0; i < tests.size(); i++) {
			TestRecord record = tests.get(i);
			if (record.getQuestionNumber() >= 10) {
				writer.write(" " + record.getQuestionNumber() + "\t\t;");
			} else {
				writer.write("  " + record.getQuestionNumber() + "\t\t;");
			}
		}

		writer.write(String.format("%n"));
		writer.write("\t\t\t;");

		for (int i = 0; i < tests.size(); i++) {
			TestRecord record = tests.get(i);
			writer.write(" " + record.getName() + "\t;");
		}

		writer.write(String.format("%n"));

		for (int i = 0; i < Constants.K; i++) {
			StudentMark markRecord = marks.get(i);
			String surname = markRecord.getSurname();
			List<MarkRecord> records = markRecord.getMarks();
			writer.write(surname + "\t;");

			for (int j = 0; j < records.size(); j++) {
				MarkRecord record = records.get(j);

				if (record.getMark() == -1) {
					writer.write("   " + "\t\t;");
					continue;
				}

				if (record.getMark() >= 10) {
					writer.write(" " + record.getMark() + "\t\t;");
				} else {
					writer.write("  " + record.getMark() + "\t\t;");
				}
			}
			writer.write(String.format("%n"));
		}

		writer.close();
	}
}
