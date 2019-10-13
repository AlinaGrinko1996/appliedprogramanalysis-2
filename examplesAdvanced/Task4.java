package ua.nure.swirchkov.Task1;

public class Task4 {
	static int count(int n) {
		FactorialProvider fact = new FactorialProvider();
		int mult = 1;
		int sum = 0;

		for (int i = 0; i < n; i++) {
			sum += fact.getNext() * mult;
			mult *= -1;
		}

		return sum;
	}

	public static void main(String[] args) {

		Integer arg = Integer.parseInt(args[0]);

		System.out.println("Sum for number - " + count(arg));
	}
}

class FactorialProvider {
	private int n = 0;
	private int fact = 1;

	int getNext() {
		n++;
		fact *= n;
		return fact;
	}
}