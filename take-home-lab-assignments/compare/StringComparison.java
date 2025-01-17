/**
 * StringComparison.java
 * Program reads in 2 strings, compares them lexicographically,
 * and prints out a number that reflects their lexicographic relationship.
 * author	: [SHEIKH UMAR]
 */

import java.util.*;

public class StringComparison {

	public static void main(String[] args) {

		String word1, word2;
		int lex; // Number that reflects the lexicographic relationship between both strings

		// declare a Scanner object to read input
		Scanner sc = new Scanner(System.in);// declare the necessary variables

		// read input and process them accordingly
		word1 = sc.nextLine();
		word2 = sc.nextLine();
		sc.close();

		// Convert all characters in both strings to lowercase for equal comparison.
		word1 = word1.toLowerCase();
		word2 = word2.toLowerCase();
		
		// Do lexicographic comparison
		// Negative value is produced if the first string is lexicographically smaller than the second string. Hence, lex = 1.
		// Positive value is produced if the first string is lexicographically larger than the second string. Hence, lex = 2
		// 0 is produced when both strings are the identical. Hence, lex = 0
		if (word1.compareTo(word2) < 0) {
			lex = 1;
		} else if (word1.compareTo(word2) > 0) {
			lex = 2;
		} else {
			lex = 0;
		}

		// Display result
		System.out.println(lex);
	}
}
