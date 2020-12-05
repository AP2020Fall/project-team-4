package Controller;

import java.util.LinkedList;
import java.util.Random;

public class IDGenerator {
	private static LinkedList<String> allIDsGenerated = new LinkedList<>();

	public static String generateNext () {
		String result;
		// gets randomly generated numbers and if it hasnt been used before returns it
		do {
			result = String.format(
					"%06d", // adds zeros before the random number if it has less than 6 digits
					new Random(System.nanoTime()).nextInt((int) Math.pow(10, 6)));

		} while (allIDsGenerated.contains(result));

		allIDsGenerated.addLast(result);

		return allIDsGenerated.getLast();
	}

	public static LinkedList<String> getAllIDsGenerated () {
		return allIDsGenerated;
	}

	public static void setAllIDsGenerated (LinkedList<String> allIDsGenerated) {
		IDGenerator.allIDsGenerated = allIDsGenerated;
	}
}
