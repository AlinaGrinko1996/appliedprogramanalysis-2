package ua.nure.swirchkov.Task1;

import java.util.HashMap;
import java.util.Map;

public class Task5 {
	public static void main(String[] args) {
		Digit5 d = new Digit5();
		System.out.println(d.countNumbers());
	}
}

class Digit5 {
	int getTriadNumber(int n) {
		int count = 0;
		for (int i = 1; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (i + j > n) {
					break;
				} else {
					int dif = n - (i + j);
					if (dif < 10) {
						count++;
						break;
					}
				}
			}
		}
		return count;
	}

	int digitssum(int a) {
		int sum = 0;
		int aLocal = a;
		while (aLocal > 0) {
			sum += aLocal % 10;
			aLocal /= 10;
		}

		return sum;
	}

	private Map<Integer, Integer> map = new HashMap<Integer, Integer>();

	int countNumbers() {
		int count = 0;
		for (int a = 0; a <= 999; a++) {
			int digitSum = this.digitssum(a);

			map.put(digitSum, map.containsKey(digitSum) ? ((Integer) map.get(digitSum)) + 1 : 1);
		}

		for (Integer value : map.values()) {
			count += value * value;
		}

		return count;
	}
}