package ua.nure.swirchkov.Task3.part5;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Part5 {

	private static final String BASE_NAME = "resources";
	private static final String ENCODING = "Cp1251";

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in, ENCODING);
		String command = "";

		while (true) {
			
			System.out.print("Enter key and locale name - ");
			
			command = sc.nextLine();
			
			if (command.equals("stop")) {
				break;
			}
			
			String[] keys = command.split("\\s");
			
			Locale locale = new Locale(keys[1]);
			
			ResourceBundle resource = ResourceBundle.getBundle(BASE_NAME, locale);
			
			System.out.println(resource.getString(keys[0]));
		}
		
		sc.close();
	}

	class Part4 {

		private static final String FILE_NAME = "part4.txt";

		private static final String ENCODING = "Cp1251";

		public static void main(String[] args) {
			Parser parser = new Parser(FILE_NAME, ENCODING);
			for (String str : parser) {
				System.out.println(str);
			}
		}
	}
}