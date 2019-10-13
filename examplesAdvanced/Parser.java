package ua.nure.swirchkov.Task3.part4;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Parser implements Iterable<String> {

	private String fileName;
	private String encoding;

	public Parser(String fileName, String encoding) {
		this.fileName = fileName;
		this.encoding = encoding;
	}

	@Override
	public Iterator<String> iterator() {

		StringBuilder total = new StringBuilder();
		char[] values = new char[30];

		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(this.fileName), this.encoding);
			
			int count=0;
			
			while ((count = reader.read(values)) > 0) {
				total.append(new String(values), 0, count);
			}
			reader.close();
		} catch (IOException e) {
			System.err.println(e.getLocalizedMessage());
			return null;
		}
		String content = total.toString();
		
		Pattern pat = Pattern.compile("([A-ZÀ-ß])([^\\.])*\\.");
		Matcher m = pat.matcher(content);
		
		ArrayList<String> list = new ArrayList<>();

		while (m.find()) {
			list.add(m.group());
		}

		return list.iterator();
	}

}
