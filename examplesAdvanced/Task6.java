package ua.nure.swirchkov.Task1;

public class Task6 {
	public static void main(String[] args) {

		Fibbonaci fib = new Fibbonaci();
		int[] arr = fib.fillArray();

		for (int i = 0; i < arr.length; i++) {
			System.out.println(i + " - " + arr[i]);
		}
	}
}

class Fibbonaci {

	int[] fillArray() {
		int[] a = new int[20];

		a[0] = 1;
		a[1] = 1;

		for (int i = 2; i < 20; i++) {
			a[i] = a[i - 1] + a[i - 2];
		}

		return a;
	}
}