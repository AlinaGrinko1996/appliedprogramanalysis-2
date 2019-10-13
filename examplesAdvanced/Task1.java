package ua.nure.swirchkov.Task1;

public class Task1 {

	public static void main(String[] args) {
		int a = 0, b = 0;

		a = Integer.parseInt(args[0]);
		b = Integer.parseInt(args[1]);

		Evklid alg = new Evklid();
		System.out.println(alg.nod(a, b));
	}
}

class Evklid {

	int nod(int a, int b) {
		if (b == a) {
			return a;
		} else if (b > a) {
			return nod(a, b - a);
		} else {
			return nod(b, a - b);
		}
	}
}