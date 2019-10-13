package ua.nure.swirchkov.Task3.part2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part2 {

	private static final String FILE_NAME = "part2.txt";

	private static final String FILE_NAME2 = "part2_sorted.txt";
	
	private static final String ENCODING = "Cp1251";

	private static final int N = 20;

	private static final int MAX = 50;

	private static void fillTextFile() throws IOException {

		OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(FILE_NAME), ENCODING);

		Random r = new Random();

		for (int i = 0; i < N; i++) {
			int a = r.nextInt(MAX);
			writer.write(a + " ");
			System.out.print(a + " ");
		}

		writer.close();
	}

	private static void sortFile() throws IOException {
		InputStreamReader sr = new InputStreamReader(new FileInputStream(FILE_NAME), ENCODING);

		char[] values = new char[30];
		int count = 0;
		int[] fileValues = new int[N];
		int index = 0;
		
		Pattern pat = Pattern.compile("([0-9])+ ");
		
		while ((count = sr.read(values)) > 0) {
			String s = new String(values, 0, count);
			Matcher matcher = pat.matcher(s);

			while (matcher.find()) {
				String seq = matcher.group();
				Integer a = Integer.parseInt(seq.trim());
				fileValues[index++] = a;
			}
		}

		sr.close();

		Arrays.sort(fileValues);

		OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(FILE_NAME2), ENCODING);

		for (int i = 0; i < fileValues.length; i++) {
			writer.write(fileValues[i] + " ");
			System.out.print(fileValues[i] + " ");
		}

		writer.close();
	}

	public static void main(String[] args) throws IOException {
		System.out.print("Input --> ");
		fillTextFile();
		System.out.println("Output --> ");
		sortFile();
	}
}