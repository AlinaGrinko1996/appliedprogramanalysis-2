package ua.nure.swirchkov.Task1;

public class Task7 {
	public static void main(String[] args) {

		Digit7 d = new Digit7();
		int[] arr = d.fillArray();

		for (int i = 0; i < 100; i++) {
			System.out.println(arr[i]);
		}
	}
}

class Digit7 {
	int[] fillArray() {
		int[] arr = new int[100];
		arr[0] = 2;
		int elem = 3;
		int pos = 1;

		while (pos < 100) {
			for (int j = 0; j < pos; j++) {
				if (elem % arr[j] == 0) {
					break;
				}

				if (Math.sqrt(elem) < arr[j]) {
					arr[pos++] = elem;
					break;
				}
			}
			elem++;
		}

		return arr;
	}
}