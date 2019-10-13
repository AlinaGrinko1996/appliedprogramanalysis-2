package ua.nure.swirchkov.Task1;

public class Task2 {
	public static void main(String[] args) {
		DigitTask2 dig = new DigitTask2();

		int arg = Integer.parseInt(args[0]);

		System.out.println("digit sum - " + dig.digitssum(arg));
	}
}

class DigitTask2 {
	int digitssum(int a) {
		int sum = 0;
		int aLocal = a;
		while (aLocal > 0) {
			sum += aLocal % 10;
			aLocal /= 10;
		}

		return sum;
	}
}