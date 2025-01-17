/*
 * Name       : SHEIKH UMAR
 */

import java.util.*;

public class Customs {

    private boolean isTest = false;
    private int tallest = 0;
    private int total = 0;
    private Scanner sc = new Scanner(System.in);
    private Stack <Person> stack = new Stack <Person>();

    // Find tallest person from stack after removals
    // Precon: Stack has gone through editsStack method 
    // Postcon: Return tallest person's height
    private int findsTallest(Stack<Person> stack) {
        for (int i = stack.size() - 1; i >= 0; i--) {
            if (stack.get(i).getsStatus()) {
                if (isTest) {
                    System.out.println("tallest:" + tallest);
                }

                return stack.get(i).getsHeight();
            }
        }

        // If stack is empty
        return 0;
    }

    // Check number of people leaving that can see officer.
    // Precon: Stack is not empty
    // Postcon: Return number of leavers that can see officer
    private int editsStack(Stack<Person> stack, int numLeavers) {
        int remove = 0;
        for (int i = 0; i < numLeavers; i++) {
            if (isTest) {
                System.out.println("Person height: " +
                        (stack.peek()).getsHeight() + ", status: " +
                        (stack.peek()).getsStatus());
            }

            // Retrieve but don't remove last person from list
            if ((stack.peek()).getsStatus()) {
                remove++;
            }

            // Remove person from list
            stack.pop();
        }

        return remove;
    }

    // Displays total number of people that can see officer.
    // Precon: All join or leave operations have been executed correctly.
    // Postcon: Nil
    private void displaysTotalNumber(int total) {
        System.out.println(total);
    }


    // Join operation
    // Precon: Query type is join
    // Postcon: Display total number of people that can see the officer
    private void joinOperation() {
        int numLeavers = sc.nextInt();
        int remove;
        if (isTest) {
            System.out.println("numLeavers = " + numLeavers);
            System.out.println("Before removing, stack size = " + stack.size());
        }

        // Check how many leavers can see officer, remove this
        // from total
        remove = editsStack(stack, numLeavers);
        total -= remove;

        // Get tallest person from edited stack
        tallest = findsTallest(stack);
    }


    private void leaveOperation() {
        // Always read in height and create Person object
        int height = sc.nextInt();
        Person person = new Person(height);
                
        // For every entry into stack, check if person is taller
        // than tallest. If yes, number of officers increases by
        // 1, person's status status is true, and 
        // tallest is height of laisTest entry.
        if (person.getsHeight() > tallest) {
            tallest = person.getsHeight();
            person.updatesStatus();
            total++;

            if (isTest) {
                System.out.println("Person: height = " + person.getsHeight() + " , status = " + person.getsStatus());
            }
        }

        stack.push(person);
    }

    private void run() {
        int Q = sc.nextInt();
        
        for (int i = 0; i < Q; i++) {
            String action = sc.next();

            if (action.equals("leave")) {
                joinOperation();
            } else {
                leaveOperation();
            }

            displaysTotalNumber(total);
        }

        sc.close();
    }

    public static void main(String[] args) {
        Customs newCustoms = new Customs();
        newCustoms.run();
    }
}

class Person {
    private boolean status;
    private int height;
 
    public Person() {
        this.height = 0;
        this.status = false;
    }

    public Person(int height) {
        this.height = height;
        this.status = false;
    }

    public int getsHeight() {
        return this.height;
    }

    public boolean getsStatus() {
        return this.status;
    }

    public void updatesStatus() {
        this.status = true;
    }
}
