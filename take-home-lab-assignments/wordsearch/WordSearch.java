/*
 * Name       : SHEIKH UMAR
 */

// Program computes the number of times a word appears in a puzzle.

import java.util.*;

public class WordSearch{
    private boolean isTest = false;
    private int count = 0;
    private String word, wordRev = "";
    private Scanner sc = new Scanner(System.in);

    // Display number of times word is found in grid.
    // Precon: Word has been search in all possible directions in grid.
    // Postcon: Nil.
    private void displaysResult() {
        System.out.println(count);
    }

    // Check if word formed has word and/or wordRev.
    // Precon: Word formed's length is >= word's length
    // Poston: Count increases after word formed from grid is checked
    // with word and/or wordRev.
    private void check(String form) {
        int start = 0;

        while ( (form.length()-start) >= word.length() ) {
            String form2 = "";
            int end = start+word.length();

            for (int i = start; i < end; i++) {
                form2 += form.charAt(i);
            }

            if (isTest) {
                System.out.println("form2 = " + form2);
            }

            if (form2.contains(word) || form2.contains(wordRev)) {
                count += 1;
            }

            start += 1;
        }
    }

    // Check for appearance of word & worfRev in north-east diagonals.
    // Precon: Word & wordRev have been checked in rows, cols, and north-west diagonals of grid.
    // Postcon: Count has increased.
    private void checksDiagonal2(char[][] grid) {
        int i = 1;
        int j = 0;
        int k = 1;

        while (i < grid.length) {
            String form = "";
            while (i >= 0 && j < grid.length) {
                form += grid[i--][j++];
            }

            if (isTest) {
                System.out.println("form = " + form);
            }

            if (form.length() >= word.length()) {
                check(form);
            }
            k += 1;
            i = k;
            j = 0;
        }

        i = grid.length - 1;
        j = 1;
        k = 1;

        while (j < (grid.length - 1)) {
            String form = "";
            while (i >= 0 && j < grid.length) {
                form += grid[i--][j++];
            }
            if (isTest) {
                System.out.println("form = " + form);
            }
            if (form.length() >= word.length()) {
                check(form);
            }

            k += 1;
            j = k;
            i = grid.length - 1;
        }
    }

    // Check for appearance of word & wordRev in north-west diagonals.
    // Precon: Word & wordRev have been checked in all rows & cols of grid.
    // Postcon: Count has increased.
    private void checksDiagonal1(char[][] grid) {
        int i = grid.length - 1;
        int j = 1;
        int k = 1;

        while (j < grid.length) {
            String form = "";
            while (i >= 0 && j >= 0) {
                form += grid[i][j];
                i -= 1;
                j -= 1;
            }
            if (isTest) {
                System.out.println("form = " + form);
            }
            if (form.length() >= word.length()) {
                check(form);
            }
            i = grid.length - 1;
            k += 1;
            j = k;
        }

        i = grid.length - 2;
        k = grid.length - 2;
        j = grid.length - 1;
        if (isTest) {
            System.out.println("i = " + i + ", k = " + k + ", j = " + j);
        }

        while (i > 0) {
            String form = "";
            while (i >= 0 && j >= 0) {
                form += grid[i--][j--];
            }

            if (isTest) {
                System.out.println("form = " + form);
            }

            if (form.length() >= word.length()) {
                check(form);
            }

            k -= 1;
            i = k;
            j = grid.length - 1;
        }
    }

    // Check if word and/or wordRevis in rows
    // Precon: Grid contains all characters derived from string that user entered.
    // Postcon: Count has increased
    private void checksRows(char[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            String form = "";
            for (int j = 0; j < grid.length; j++) {
                form += grid[i][j];
            }
            if (isTest) {
                System.out.println("form = " + form);
            }
            if (form.length() >= word.length()) {
                check(form);
            }
        }
    }

    // Check if word and/or wordRevis in cols.
    // Precon: Grid contains all characters derived from string that user entered.
    // Postcon: Count has increased
    private void checksCols(char[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            String form = "";
            for (int j = 0; j < grid.length; j++) {
                form += grid[j][i];
            }
            if (isTest) {
                System.out.println("form = " + form);
            }
            if (form.length() >= word.length()) {
                check(form);
            }
        }
    }

    // Display grid.
    // Precon: isTest == true.
    // Postcon: Nil.
    private void printGrid(char[][] grid) {
        System.out.println();
        System.out.println("Grid: ");

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println();
    }

    // Read in word to be searched.
    // Precon: User has not entered word.
    // Postcon: Forms grid.
    private void readsWord() {
        word = sc.next();

        for (int i = word.length() - 1; i >= 0; i--) {
            wordRev += word.charAt(i);
        }

        if (isTest) {
            System.out.println("Word = " + word + ", wordRev = " + wordRev);
        }
    }

    // Forms grid
    // Precon: User entered word
    // Postcon read in characters for grid
    private char[][] formsGrid() {
        int N = sc.nextInt();
        if (isTest) {
            System.out.println("N = " + N);
        }
        return new char[N][N];        
    }

    // Reads in characters as strings.
    // Precon: Grid cells are null.
    // Postcon: Grid cells contain a character (entered as a string) entered from user.
    private void readsGrid(char[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            String readWord = sc.next();
            if (isTest) {
                System.out.println("readWord = " + readWord);
            }
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = readWord.charAt(j);
            }
        }
        if (isTest) {
            printGrid(grid);
        }
    }

    private void run() {
        readsWord();
        char[][] grid = formsGrid();
        readsGrid(grid);
        checksRows(grid);
        checksCols(grid);
        checksDiagonal1(grid);
        checksDiagonal2(grid);
        displaysResult();
    }

    public static void main(String[] args) {
        WordSearch obj = new WordSearch();
        obj.run();
    }
}
