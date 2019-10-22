import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part3 {

	private static final String FILE_NAME = "part3.txt";
	private static final String ENCODING = "Cp1251";

	private static String[] getValuesByPattern(Pattern pat) throws IOException {
		try (InputStreamReader fr = new InputStreamReader(new FileInputStream(FILE_NAME), ENCODING)) {
			char[] values = new char[30];
			int count = 0;
			ArrayList<String> list = new ArrayList<>(10);

			while ((count = fr.read(values)) > 0) {
				String s = new String(values, 0, count);
				Matcher matcher = pat.matcher(s);

				while (matcher.find()) {
					String seq = matcher.group();
					list.add(seq);
				}
			}

			fr.close();

			return list.toArray(new String[list.size()]);
		}
	}

	private static String[] getValuesForType(String type) throws IOException {

		switch (type) {

		case "char":
			return getValuesByPattern(Pattern.compile("[a-zA-Z�-��-�]{1}"));

		case "int":
			return getValuesByPattern(Pattern.compile("(0|[1-9][0-9]*)"));

		case "double":
			return getValuesByPattern(Pattern.compile("0|([1-9][0-9]*\\.[0-9]+)"));

		case "String":
			return getValuesByPattern(Pattern.compile("[a-zA-Z�-��-�]{2}[a-zA-Z�-��-�]*"));
		}
		return new String[0];
	}

	public static void main(String[] args) throws IOException {

		Scanner sc = new Scanner(System.in, ENCODING);

		System.out.print("Enter command - ");

		String command = sc.next();

		while (!command.equals("stop")) {
			System.out.print("Values in file - [ ");

			String[] values = getValuesForType(command);

			for (int i = 0; i < values.length; i++) {
				System.out.print("\'" + values[i] + "\'");

				if (i < values.length - 1) {
					System.out.print(", ");
				}
			}

			System.out.println(" ]");

			System.out.print("Enter command - ");
			command = sc.next();
		}

		sc.close();
	}

}