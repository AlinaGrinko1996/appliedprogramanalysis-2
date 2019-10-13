package ua.nure.swirchkov.Task3.part4;

public class Part4 {

	private static final String FILE_NAME = "part4.txt";

	private static final String ENCODING = "Cp1251";

	public static void main(String[] args) {
		Parser parser = new Parser(FILE_NAME, ENCODING);		
		for (String str : parser) {
			System.out.println(str);
		}
	}

}