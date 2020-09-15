package com.le;

import java.util.Arrays;
import java.util.stream.Stream;

public class MainRun {
	public static void main(String[] args) {
		String[] arrStr = new String[]{"a", "b"};
		int[] arrInt = new int[]{1, 2};

		System.out.println(".....1....");
		Arrays.stream(arrStr).forEach(System.out::println);
		System.out.println(".....2....");
		Stream.of(arrStr).forEach(System.out::println);

		System.out.println(".....3....");
		System.out.println(Arrays.toString(arrInt));

		System.out.println(".....4....");
		System.out.println(arrInt);

		System.out.println(".....5....");
		System.out.println(arrInt.toString());
	}
}
