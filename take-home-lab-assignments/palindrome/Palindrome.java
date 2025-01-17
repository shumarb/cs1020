/*
 * author		: [Sheikh Umar]
 */

import java.util.*;

public class Palindrome {
	/* use this method to check whether the string is palindrome word or not
	 * 		PRE-Condition  : User has entered first and second words
	 * 		POST-Condition : Nil
	 */
	public static boolean isPalindrome(String word) {
		int startIndex = 0;
		int lastIndex = word.length() - 1;

		while (startIndex < lastIndex) {
			if (word.charAt(lastIndex) != word.charAt(startIndex)) {
				return false;
			}

			lastIndex -= 1;
			startIndex += 1;
		}

		return true;
	}
	
	public static void main(String[] args) {
		// declare the necessary variables
		boolean isPalindrome;
		String combinedWord;
		String firstWord;
		String secondWord;
		
		// declare a Scanner object to read input
		Scanner sc = new Scanner(System.in);

		// read input and process them accordingly
		firstWord = sc.nextLine();
		secondWord = sc.nextLine();
		sc.close();
		combinedWord = firstWord + secondWord;

		// simulate the problem
		isPalindrome = isPalindrome(combinedWord);

		// output the result
		if (isPalindrome)
		{
			System.out.println("YES");
		} else {
			System.out.println("NO");
		}

	}
}