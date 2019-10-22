import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part7 {

	private static final String FILE_NAME = "MyGroup.csv";

	private static final String OUTPUT = "part7.txt";
	
	private static final String ENCODING = "Cp1251";

	private static final int EOF = 65535;

	private static class Record {
		private String theme;
		private String startDate;
		private String startTime;
		private String endDate;
		private String endTime;
		private Boolean isEveryDayEvent;
		private Boolean notifyEnabled;
		private String notifyDate;
		private String notifyTime;
		private String atThisTime;
		private String importance;
		private String description;
		private String note;

		public Record(String[] values) {
			if (values.length != 13) {
				throw new IllegalArgumentException();
			}
			theme = values[0];
			startDate = values[1];
			startTime = values[2];
			endDate = values[3];
			endTime = values[4];
			isEveryDayEvent = values[5].equals("\"������\"");
			notifyEnabled = values[6].equals("\"������\"");
			notifyDate = values[7];
			notifyTime = values[8];
			atThisTime = values[9];
			importance = values[10];
			description = values[11];
			note = values[12];
		}

		public String toStringFull() {
			String s = String.format(
					"[ %s = \"%s\", %s = \"%s\", %s = \"%s\", %s = \"%s\", %n %s = \"%s\""
							+ "%s = \"%s\", %s = \"%s\", %s = \"%s\", %n %s = \"%s\", %s = \"%s\", %s = \"%s\""
							+ "%s = \"%s\", %s = \"%s\" %n ]",
					"Theme", theme, "Start Date", startDate, "Start Time", startTime, "End Date", endDate, "End Time",
					endTime, "Every Day Event", isEveryDayEvent, "Notify enabled", notifyEnabled, "Notify Date",
					notifyDate, "Notify Time", notifyTime, "Events at this time", atThisTime, "Importance", importance,
					"Description", description, "Note", note);
			return s;
		}
	
		public String toString() {
			
			startDate = getFirstValueByPattern(startDate, Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4}"));
			startTime = getFirstValueByPattern(startTime, Pattern.compile("\\d{2}:\\d{2}:\\d{2}"));
			description = description.replace('"', ' ').trim();
			return formatDate(startDate) + " ==> " + formatTime(startTime) + " " + description +"\n";
		}
		
		private String getFirstValueByPattern(String exp, Pattern pattern) {
			Matcher matcher = pattern.matcher(exp);
			
			if (!matcher.find()) {
				return "";
			}
			else {
				return matcher.group();
			}
		}
		
		private String formatDate(String date) {
			
			String[] splittedDate = date.split("\\.");
			
			if (splittedDate.length != 3) {
				throw new IllegalArgumentException();
			}
			
			return splittedDate[2] + "-" + splittedDate[1] + "-" + splittedDate[0];
		}
		
		private String formatTime(String time) {
			String[] splittedDate = time.split(":");
			
			if (splittedDate.length != 3) {
				throw new IllegalArgumentException();
			}
			
			return splittedDate[0] + ":" + splittedDate[1];
		}
		
	}

	private static String readFileContent(Reader reader) throws IOException {

		StringBuilder sb = new StringBuilder();
		char c = (char) reader.read();

		while (c != EOF) {
			sb.append(c);
			c = (char) reader.read();
		}

		return sb.toString();
	}

	private static List<Record> parseFile(Reader reader) throws IOException {
		List<Record> records = new ArrayList<>();

		String fileContent = readFileContent(reader);

		Pattern recordPattern = Pattern.compile("\\\"[^\\\"]*\\\"");

		Matcher matcher = recordPattern.matcher(fileContent);
		Boolean skipFirst = false;
		while (matcher.find()) {
			String[] recordValues = new String[13];
			recordValues[0] = matcher.group();

			for (int i = 0; i < 12; i++) {
				if (!matcher.find()) {
					System.err.println("error while parsing file");
					return records;
				}
				recordValues[i + 1] = matcher.group();
			}
			if (!skipFirst) {
				skipFirst = true;
				continue;
			} 
			Record record = new Record(recordValues);
			records.add(record);
		}

		return records;
	}

	private static List<Record> groupByStartDate(List<Record> records) {
		int count = 1;
		for (int i=0; i < records.size(); i++) {
			count = 1;
			for (int j=i + 1; j < records.size(); j++) {
				if (records.get(j).startDate.equals(records.get(i).startDate)) {
					Record jPosRecord = records.get(j);
					
					if (i + count >= records.size()) {
						continue;
					}
					
					records.set(j, records.get(i + count));
					records.set(i + count, jPosRecord);
					count++;
				}
			} 
		}
		
		return records;
	}
	
	private static class StartDateComparator implements Comparator<Record>, Serializable {

		private static final long serialVersionUID = -7203405521043967494L;

		@Override
		public int compare(Record o1, Record o2) {
			return o1.toString().compareTo(o2.toString());
		}
		
	}
	
	private static void writeToFile(Writer writer, List<Record> records) throws IOException {
		String prevDate = records.get(0).startDate;
		for(Record record : records) {
			
			if (!record.startDate.equals(prevDate)) {
				writer.write("~~~~~~~~~~~~~~~~~~");
				System.out.print("~~~~~~~~~~~~~~~~~~\n");
			}
			
			writer.write(record.toString());
			System.out.print(record.toString());
			prevDate = record.startDate;
		}
	}
	
	public static void main(String[] args) throws IOException {
		InputStreamReader sr = new InputStreamReader(new FileInputStream(FILE_NAME), ENCODING);

		List<Record> records = parseFile(sr);
		records = groupByStartDate(records);
		
		Collections.sort(records, new StartDateComparator());
		
		System.out.println("full data");
		System.out.println(records.get(0).toStringFull());
		
		writeToFile(new OutputStreamWriter(new FileOutputStream(OUTPUT), ENCODING), records);
	}

}




