package ua.nure.swirchkov.Task3.part1;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Part1 {

	private static final String FILE_NAME = "part1.txt";

	private static final String ENCODING = "Cp1251";

	public static void main(String[] args) throws IOException {
		
		InputStreamReader sr = new InputStreamReader(new FileInputStream(FILE_NAME), ENCODING);

		char[] values = new char[30];
		int i = 0;
		
		StringBuilder sb = new StringBuilder();
		
		while ((i = sr.read(values)) > 0) {
			sb.append(new String(values, 0, i));
		}
		sr.close();
		
		String[] splitted = sb.toString().split("[ \\t\\n]");
		sb = new StringBuilder();
		
		for(i =0; i < splitted.length; i++) {
			if (splitted[i].length() > 2) {
				splitted[i] = splitted[i].toUpperCase();
			}
			sb.append(splitted[i]);
			if (splitted[i].charAt(splitted[i].length() - 1) != '\n') {
				sb.append(" ");
			}
		}
		
		System.out.println(sb.toString());
	}

}