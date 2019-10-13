package ua.nure.swirchkov.Task1;

public class Task10 {
	public static void main(String[] args) {
		Triangle tr = new Triangle();

		String[][] arr = tr.generatePasqualTriangle();

		for (String[] line : arr) {
			for (String s : line) {

				System.out.print(s != null ? s + "\t" : "\t");
			}
			System.out.println("");
		}
	}
}

class Triangle {

	String[][] generatePasqualTriangle() {
		String[][] arr = new String[10][22];

		int[] prev = new int[1];

		prev[0] = 1;

		int startWr = 12;
		for (int i = 0; i < 10; i++) {
			String[] line = new String[22];

			int start = startWr--;
			for (int j = 0; j < prev.length; j++, start += 2) {
				line[start] = Integer.toString(prev[j]);
			}

			arr[i] = line;

			int[] cur = new int[prev.length + 1];

			cur[0] = 1;
			cur[cur.length - 1] = 1;

			for (int k = 0; k < prev.length - 1; k++) {
				cur[k + 1] = prev[k] + prev[k + 1];
			}

			prev = cur;
		}

		return arr;
	}
}