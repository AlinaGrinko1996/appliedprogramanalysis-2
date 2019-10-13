package ua.nure.swirchkov.Task1;

import java.math.BigInteger;
import java.util.Random;

public class Task3 {
	public static void main(String[] args) {

		Integer arg = Integer.parseInt(args[0]);

		System.out.println("Digit to check - " + Digit.checkPrime(new BigInteger(arg.toString()), 10));
	}
}

class Digit {

	private static BigInteger getRandomFermatBase(BigInteger n) {

		Random rand = new Random();
		// Rejection method: ask for a random integer but reject it if it isn't
		// in the acceptable set.

		while (true) {
			final BigInteger a = new BigInteger(n.bitLength(), rand);
			// must have 1 <= a < n
			if (BigInteger.ONE.compareTo(a) <= 0 && a.compareTo(n) < 0) {
				return a;
			}
		}
	}

	// a after mod must be equal 1 if the number is prime.
	public static boolean checkPrime(BigInteger n, int maxIterations) {
		if (n.equals(BigInteger.ONE)) {
			return false;
		}

		for (int i = 0; i < maxIterations; i++) {
			BigInteger a = getRandomFermatBase(n);
			a = a.modPow(n.subtract(BigInteger.ONE), n);

			if (!a.equals(BigInteger.ONE)) {
				return false;
			}
		}

		return true;
	}

}