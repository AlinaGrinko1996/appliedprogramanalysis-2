package ua.nure.swirchkov.Task4;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

public class StreamCounter {

	public static void main(String[] args) {

		Scanner reader = new Scanner(System.in, "utf-8");
		String input = reader.nextLine();
		Arrays.asList(Objects.requireNonNull(input, "input string can't be null").toLowerCase()
				.split("[\\p{Blank}\\p{Punct}]+")).stream()
				.collect(Collectors.groupingBy(s -> s, Collectors.counting())).entrySet().stream().sorted((e0, e1) -> {
					final int res = e1.getValue().compareTo(e0.getValue());
					return res == 0 ? e0.getKey().compareTo(e1.getKey()) : res;
				}).limit(10).forEach((e0) -> {
					System.out.println(e0.getKey());
				});
		reader.close();

	}
}
