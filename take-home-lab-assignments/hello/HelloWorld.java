/**
 *
 * author  : [SHEIKH UMAR]
 * 
 */

import java.util.*;

public class HelloWorld {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int method = sc.nextInt();
        
        if (method == 2) {
            meth2(sc);
        } else if (method == 3) {
            meth3(sc);
        } else{
            meth1(sc);
        }

        sc.close();
    }

    public static void meth1(Scanner sc) {
        int a, b, i, rounds;
        String gate;

        rounds = sc.nextInt();
        for (i = 0; i < rounds; i++) {
            gate = sc.next();
            a = sc.nextInt();
            b = sc.nextInt();
            logic(gate, a, b);
        }
    }

    public static void meth2(Scanner sc) {
        int a, b, stop = 0;
        String gate;

        gate = sc.next();
        while (stop == 0) {
            a = sc.nextInt();
            b = sc.nextInt();
            logic(gate, a, b);
            gate = sc.next();
            if (gate.charAt(0) == '0') {
                stop = 1;
            }
        }
    }

    public static void meth3(Scanner sc) {
        int a, b;
        String gate;

        while (sc.hasNext()) {
            gate = sc.next();
            a = sc.nextInt();
            b = sc.nextInt();
            logic(gate, a, b);
        }
    }

    public static void logic(String gate, int a, int b) {
        if (gate.equals("AND")) {
            if (a == 1 && b == 1) {
                System.out.println(1);
            } else {
                System.out.println(0);
            }
        } else {
            if (a == 0 && b == 0) {
                System.out.println(0);
            } else {
                System.out.println(1);
            }
        }
    }
}
