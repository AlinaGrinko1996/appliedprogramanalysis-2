package ua.nure.swirchkov.Task1;

public class Task9 {
	public static void main(String[] args) {
		ArrayCreator creator = new ArrayCreator();
		creator.create1();

		System.out.println(creator.getCounter());
	}
}

class ArrayCreator {

	private static int counter = 1;

	int getCounter() {
		return counter;
	}

	int[][][][][][] create1() {

		int size = 2;
		int sixDimMas[][][][][][] = new int[size][size][size][size][size][size];

		int z = 0;
		for (; z < size * size * size * size * size * size; ++z) {
			int i = z % size;
			int j = z / size % size;
			int k = z / (size * size) % size;
			int l = z / (size * size * size) % size;
			int m = z / (size * size * size * size) % size;
			int n = z / (size * size * size * size * size) % size;
			sixDimMas[n][m][l][k][j][i] = z;
		}
		counter = z;
		return sixDimMas;
	}
}