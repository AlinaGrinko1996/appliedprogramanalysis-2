package ua.nure.swirchkov.Task1;

public class Task8 {
	public static void main(String[] args) {

		Task8Helper task = new Task8Helper();
		char[][] desk = task.fillArray();

		for (int i = 0; i < desk.length; i++) {
			System.out.println(desk[i]);
		}
	}
}

class Task8Helper {
	char[][] fillArray() {
		char[][] desk = new char[8][8];

		final char ch1 = '×';
		final char ch2 = 'Á';

		char prev = ch1;
		char cur;
		for (int i = 0; i < 8; i++) {
			if (prev == ch2) {
				prev = ch1;
			} else {
				prev = ch2;
			}
			for (int j = 0; j < 8; j++) {
				if (prev == ch1) {
					cur = ch2;
				} else {
					cur = ch1;
				}
				desk[i][j] = cur;
				prev = cur;
			}
		}

		return desk;
	}

}