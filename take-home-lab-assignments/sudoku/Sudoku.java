/*
 * Name		: SHEIKH UMAR
 */

import java.util.*;

public class Sudoku {
    private Scanner sc = new Scanner(System.in);
    private boolean isTest = false;
    private int N;
    private int size;

    // Read in all entries of matrix
    // Precon: Matrix is filled with 0's only, and user has not entered
    // values of matrix
    // Postcon: Matrix contains all entries from user
    private void read(int[][]mat, int size) {
        if (isTest) {
            System.out.println("Enter entries: ");
        }

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                mat[x][y] = sc.nextInt();
            }
        }

        // Display matrix
        if (isTest) {
            System.out.println("matrix after reading in values ");
            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    System.out.print( mat[x][y] + " " );
                }
                System.out.println();
            }
        }
    }

    // Precon: Matrix has valid row, columns, and submatrices
    // Postcon: NIL
    private void printValid() {
        System.out.println("VALID");
    }

    // Precon: Matrix has an invalid row/column/submatrices
    // Postcon: NIL
    private void printInvalid() {
        System.out.println("INVALID");
    }

    // Check if row entries have no duplicates and are within 1 and size^2 inclusive
    // Precon: Row array is row entries of the matrix
    // Postcon: Return true if row entries have no duplicates and are within 1 and size^2 inclusive
    private boolean check(int[] row, int size) {
        int[] dig = new int[size];
        int elem;
        if (isTest) {
            System.out.print("dig array: ");
            for (int f = 0; f < size; f++) {
                System.out.print(dig[f] + " ");
            }
            System.out.println();
        }
        // Obtain entry in row, increase dig element representing this
        // element to 1, check if this element is >1. If yes, return false
        for (int e = 0; e < size; e++) {
            elem = row[e];
            dig[elem - 1] += 1;

            // Valid only if no duplicates and entry is <=size
            if (dig[elem - 1] > 1 || elem <= 0 || elem > size) {
                return false;
            }
        }

        return true;
    }

    // Form a 1D array consisting of row entries from mat.
    // Send rows to check method to check if it is valid.
    // Precon: Matrix has all entries from user
    // Postcon: Row entries are valid
    private boolean passRow(int[][]mat, int size) {
        boolean isProceed = true;

        for (int a = 0; a < size && isProceed; a++) {
            int c = 0;
            int[] row = new int[size];
            for (int b = 0; b < size && isProceed; b++) {
                row[c] = mat[a][b];
                c += 1;
            }

            if (isTest) {
                System.out.print("row: ");
                for (int d = 0; d < size; d++) {
                    System.out.print(row[d] + " ");
                }
                System.out.println();
            }

            // Send row to check method
            isProceed = check(row,size);
        }

        return isProceed;
    }

    // Form a 1D array consisting of column entries from mat.
    // Send columns to check method to check if it is valid.
    // Precon: Row entries are valid
    // Postcon: Column entries are valid
    private boolean passCol(int[][]mat, int size) {
        boolean isProceed = true;

        for (int a = 0; a<size && isProceed; a++) {
            int c = 0;
            int[] row = new int[size];
            for ( int b = 0; b < size && isProceed; b++) {
                row[c] = mat[b][a];
                c += 1;
            }

            if (isTest) {
                System.out.print("row: ");
                for (int d = 0; d < size; d++) {
                    System.out.print(row[d] + " ");
                }
                System.out.println();
            }

            // Send row to check method, which returns true or false
            isProceed = check(row,size);
        }

        return isProceed;
    }

    // Matrix checks submatrices and returns true or false.
    // False when one of the submatrices is invalid, hence matrix is invalid
    // Precon: Matrix has valid row and column entries
    // Postcon: Matrix has valid submatrices
    private boolean passSubMat(int[][] mat,int size) {
        // First 2 loops are based to jump from submatrix in a row and
        // col to the next row and col by N
        for (int a = 0; a < size; a += N) {
            int start = a;
            int endStart = start + N;
            for (int b = 0; b < size; b += N) {
                int end = b;
                int endEnd = end + N;

                if (!formSub(mat, start, endStart, end, endEnd)) {
                    return false;
                }
            }
        }

        return true; // Submatrices are valid
    }

    // Form submatrices, check if it is valid, return true if valid
    // Precon: starting and ending index received
    // Postcon: nil
    private boolean formSub(int[][]mat, int start, int startEnd, int end, int endEnd) {
        int[][] sub = new int[N][N];
        int e = 0;
        int f = 0;

        // Form submatrix, check submatrix
        for (int i = start; i < startEnd; i++) {
            f = 0;
            for (int j = end; j < endEnd; j++) {
                sub[e][f] = mat[i][j];
                f += 1; // Go to next column;
            }

            e += 1;
        }
        
        // Display submatrix
        if (isTest) {
            dispSub(sub);
        }
        
        if (!checkSub(sub)) {
            return false;
        }

        return true; // valid submatrix
    }

    // Check if entries of submatrix has no duplicates
    // and is within 1 to N^2.
    // Precon: Argument is submatrix
    // Postcon: nil
    private boolean checkSub(int[][] sub) {
        int[] dig = new int[size]; // Digits representing 1 to N^2
        int elem;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                elem = sub[i][j];

                // Check if elem is >N^2. If yes, return false
                if (elem > (N * N) || elem <= 0) {
                    return false;
                }

                // elem between 1 to N
                dig[elem - 1] += 1;

                // if value of element in dig is >1, there is a
                // duplicate, so return false
                if (dig[elem - 1] > 1) {
                    return false;
                }
            }
        }

        return true; // Valid submatrix
    }

    // Display submatrix found
    // Precon: Submatrix is formed and of size N
    // Postcon: nil
    private void dispSub(int[][] sub) {
        System.out.println("Submat: ");
        for (int h = 0; h < N; h++) {
            for (int p = 0; p < N; p++) {
                System.out.print(sub[h][p] + " ");
            }
            System.out.println();
        }
    }

    // forms matrix
    // Precon: User has not entered N
    // Postcon: Read in each value of the matrix
    private int[][] formsMat() {
        N = sc.nextInt();
        size = N * N;
        if (isTest) {
            System.out.println("***************");
            System.out.print("N = " + N);
            System.out.println(", row = " + size + ", col = " + size);
            System.out.println("***************");
        }
        return new int[size][size];
    } 

    // Decision proceed based on validity checks
    // Precon: Matrix contains values that user has entered
    // Postcon: Displays result
    private void decisionProcess(boolean isProceed, int[][] mat) {
        if (!isProceed) {
            printInvalid();
        } else {
            // Check column entries if they are valid.
            isProceed = passCol(mat, size);
            if (!isProceed) {
                printInvalid();
            } else {
                isProceed = passSubMat(mat, size);
                if (!isProceed) {
                    printInvalid();
                } else {
                    printValid();
                }
            }
        }
    }

    private void run() {
        int[][] mat = formsMat();
        read(mat, size);
        boolean isProceed = passRow(mat, size);
        decisionProcess(isProceed, mat);
    }

    public static void main(String[] args) {
        Sudoku mysudoku = new Sudoku();
        mysudoku.run();
    }
}
