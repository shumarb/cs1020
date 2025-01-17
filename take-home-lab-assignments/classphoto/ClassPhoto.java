/**
 * Name : Sheikh Umar
 */

import java.util.*;

public class ClassPhoto {
    private boolean isTest = false;
    private int numPersons = 0;
    private Person head = null;
    private Scanner sc = new Scanner(System.in);

    // Reads an integer value
    // Precon: Value of integer variable to be assigned to is 0
    // Postcon: Nil
    private int readsInteger() {
        int value = sc.nextInt();
        if (isTest) {
            System.out.println("*** readsInteger | * value entered: " + value);
        }
        return value;
    }

    // Reads a String value
    // Precon: Value of string variable to be assigned to is null
    // Postcon: Nil
    private String readsString() {
        String value = sc.next();
        if (isTest) {
            System.out.println("*** readsString | string entered: " + value);
        }
        return value;
    }

    // Closes scanner
    // Precon: All queries have been processed
    // Postcon: End of program
    private void closesScanner() {
        sc.close();
    }

    // Executes arrive query
    // Precon: queryType is arrive
    // Postcon: Executes next queryType
    private void arriveQuery() {
        String name = readsString();
        int height = readsInteger();
        if (isTest) {
            System.out.println("*** arrive: | (" + name + ", " + height + ")");
        }

        int numPosition = 0;
        Person incomingPerson = new Person(name, height, null);

        // 1. No one in the row, hence incomingPerson is to be first person in linkedlist
        if (head == null) {
            head = new Person("HEAD", -1, incomingPerson);
            numPersons++;
            numPosition++;
        } else {
            Person currentPerson = head;
            while (currentPerson != null) {
                Person nextPerson = currentPerson.getsNextPerson();
                // 2. Get current person's next person
                // 2a. If incoming person's height >= next person's height after current person and before next person
                // hence incoming person is to be positioned after current person and before next person
                // meaning the arrangement is: currentPerson -> incomingPerson -> nextPerson
                // hence set incoming person's next person to be next person (current person's next)
                // and set current person's next person to be incoming person
                if (currentPerson.hasNextPerson() && incomingPerson.getsHeight() >= nextPerson.getsHeight()) {
                    incomingPerson.setsNextPerson(nextPerson);
                    currentPerson.setsNextPerson(incomingPerson);
                    numPersons++;
                    numPosition++;
                    break;
                } else if (!currentPerson.hasNextPerson()) {
                    // 2b. At this stage, incoming person is the shortest among people in the row,
                    // hence currentPerson has no nextPerson, hence add incomingPerson to tail
                    currentPerson.setsNextPerson(incomingPerson);
                    numPersons++;
                    numPosition++;
                    break;
                } else {
                    // 2c. incoming person to be added somewhere in the row between head and tail
                    // so increase numPosition before checking next person
                    numPosition++;
                    currentPerson = currentPerson.getsNextPerson();
                }
            }
        }

        if (isTest) {
            displaysLinkedList();
        }
        System.out.println(incomingPerson.getsName() + " added at position " + numPosition);
    }
    
    // Executes count query
    // Precon: queryType is count
    // Postcon: Executes next queryType
    private void countQuery() {
        int lowHeight = readsInteger();
        int highHeight = readsInteger();
        if (isTest) {
            System.out.println("*** count | lowHeight: " + lowHeight + ", highHeight: " + highHeight);
        }

        int count = 0;
        Person currentPerson = head;
        
        // Ensure there is >= 1 person in row before proceeding
        if (numPersons > 0) {
            while (currentPerson != null) {
                if ((currentPerson.getsHeight() >= lowHeight) && (currentPerson.getsHeight() <= highHeight)) {
                    count++;
                }
                currentPerson = currentPerson.getsNextPerson();
            }
        }
        
        if (isTest) {
            displaysLinkedList();
        }

        System.out.println(count);
    }

    // Executes leave query
    // Precon: queryType is leave
    // Postcon: Executes next queryType
    private void leaveQuery() {
        String nameToRemove = readsString();
        if (isTest) {
            System.out.println("*** leave | nameToRemove: " + nameToRemove);
        }

        boolean isExist = false;
        int numPosition = 0;
        Person currentPerson = head;

        // Ensure there is >= 1 person in row before proceeding
        if (numPersons > 0) {
            while (currentPerson != null) {
            Person nextPerson = currentPerson.getsNextPerson();

            // 1. Check if removal of last person
            if (nextPerson.getsNextPerson() == null) {
                // 1a. Last person to be removed,
                // so set 2nd-last person to be last person by setting it's nextPerson to be null
                // and reduce number of nodes by 1
                if (nextPerson.getsName().equals(nameToRemove)) {
                    currentPerson.setsNextPerson(null);
                    isExist = true;
                    numPersons--;
                    numPosition++;
                    break;
                } else {
                    // 1b. Last person is not person to be removed, so no need to check
                    break;
                }
            } else {
                // 2. Removal of person between first person and 2nd-last person inclusive
                if (nextPerson.getsName().equals(nameToRemove)) {
                    // 2a. Person next to currentPerson is to be removed
                    // set currentPerson's to be next to person after person to be removed
                    // and decrease number of nodes by 1
                    currentPerson.setsNextPerson(nextPerson.getsNextPerson());
                    nextPerson.setsNextPerson(null);
                    isExist = true;
                    numPersons--;
                    numPosition++;
                    break;
                } else {
                    // 2b. Person next to currentPerson is not to be removed
                    // so check person next to currentPerson
                    numPosition++;
                    currentPerson = currentPerson.getsNextPerson();
                    }
                }
            }
        }

        // 3. If row has no persons, so set head to be null
        if (numPersons == 0) {
            head = null;
        }

        if (isTest) {
            displaysLinkedList();
        }

        if (isExist) {
            System.out.println(nameToRemove + " has left position " + numPosition);
        } else {
            System.out.println("No student with name " + nameToRemove);
        }
    }

    // Executes shortest query
    // Precon: queryType is shortest
    // Postcon: Executes next queryType
    private void shortestQuery() {
        int numPosition = readsInteger();
        if (isTest) {
            System.out.println("*** count | position: " + numPosition);
        }

        // 1. Form duplicate of linkedList called reverseLinkedList
        // Note: ensure linkedlist has > 1 person
        if (numPersons > 0) {
            // ReverseHead is a person object with name and height identical to first person in row,
            // with its nextPerson set to null, so set currentPerson as 2nd person in row
            Person reverseHead = new Person(head.getsName(), head.getsHeight(), null);
            Person currentPerson = head.getsNextPerson();
            Person currentReversePerson = reverseHead;

            if (numPersons > 0) {
                while (currentPerson != null) {
                    Person toAddPerson;
                    // 1a. Add last person
                    if (!currentPerson.hasNextPerson()) {
                        toAddPerson = new Person(currentPerson.getsName(), currentPerson.getsHeight(), null);
                    } else {
                        // 1b. Add person that is not last person
                        Person nextPerson = currentPerson.getsNextPerson();
                        toAddPerson = new Person(currentPerson.getsName(), currentPerson.getsHeight(), nextPerson);
                    }
                    currentReversePerson.setsNextPerson(toAddPerson);
                    currentPerson = currentPerson.getsNextPerson();
                    currentReversePerson = currentReversePerson.getsNextPerson();
                }

                if (isTest) {
                    displaysLinkedList();
                    System.out.print("* reverseLinkedList: ");
                    currentReversePerson = reverseHead;
                    while (currentReversePerson != null) {
                        System.out.print("(" + currentReversePerson.getsName() + ", " + currentReversePerson.getsHeight() + ") -> ");
                        currentReversePerson = currentReversePerson.getsNextPerson();
                    }
                    System.out.println("NULL");
                }

                // 2. Reverse reverseLinkedList
                currentReversePerson = reverseHead;
                Person nextReversePerson = null;
                Person previousReversePerson = null;
                while (currentReversePerson != null) {
                    // 2a. Note person after current person for next iteration
                    nextReversePerson = currentReversePerson.getsNextPerson();

                    // 2b. set current person's nextPerson to be person on the left from person on his/her right
                    currentReversePerson.setsNextPerson(previousReversePerson);

                    // 2c. For every iteration, current previous person is the previous iteration's current person
                    previousReversePerson = currentReversePerson;

                    // 2d. Set current person to be next person originally on his/her right for next iteration
                    currentReversePerson = nextReversePerson;
                }
                // 2e. Set head of reverse linked list to be last person on original linked list
                reverseHead = previousReversePerson;

                if (isTest) {
                    System.out.print("* after reverse, reverseLinkedList: ");
                    currentReversePerson = reverseHead;
                    while (currentReversePerson != null) {
                        System.out.print("(" + currentReversePerson.getsName() + ", " + currentReversePerson.getsHeight() + ") -> ");
                        currentReversePerson = currentReversePerson.getsNextPerson();
                    }
                    System.out.println("NULL");
                }

                // 3. Count number of distinct height for check
                // 3a. At initiation, shortest person is checked
                // so set height to check to be this person's height, and number of distinct heights to 1
                currentReversePerson = reverseHead;
                int numHeightToFind = reverseHead.getsHeight();
                int numDistinct = 1;
                while (currentReversePerson != null) {
                    // 3b. If number of distict numbers found matches numPosition, exit loop immediately
                    // this height represents the value of the position-th height
                    // if not, keep finding for distinct heights and increase number of distict heights found by 1
                    // Note: head is part of reverseLinkedList, and is a dummy variable, hence ignore it
                    if (numDistinct == numPosition) {
                        break;
                    } else {
                        if (currentReversePerson.getsHeight() != numHeightToFind && currentReversePerson.isNotHeadDummy()) {
                            numHeightToFind = currentReversePerson.getsHeight();
                            numDistinct++;
                        }
                    }
                    currentReversePerson = currentReversePerson.getsNextPerson();
                }
                if (isTest) {
                    System.out.println("* numPosition: " + numPosition
                                        + " | numDistinct: " + numDistinct 
                                        + " | numHeightToFind: " + numHeightToFind);
                }

                // 4. Identify number of persons with position-th-shortest height
                // this is only possible if numPosition <= numDistinct
                // eg: 3rd-shortest person among 5 distinct heights is possible
                // eg: 10th-shortest person among 10 distinct heights is possible
                // eg: 9th-shortest person among 7 distinct heights is impossible
                if (numPosition <= numDistinct) {
                    int numCount = 0;
                    currentReversePerson = reverseHead;
                    // 4a. Find number of persons with shortest position-th height
                    while (currentReversePerson != null) {
                        if (currentReversePerson.getsHeight() == numHeightToFind) {
                            numCount++;
                        }
                        currentReversePerson = currentReversePerson.getsNextPerson();
                    }
                    if (isTest) {
                        System.out.println("* numHeightToFind: " + numHeightToFind
                                            + " | number of people with " + numPosition 
                                            + "-shortest height: " + numCount);
                    }
                    
                    // 5. Display name of person(s) with height equal to numPosition-shortest height
                    // based on the ordering in the linkedList, not the reverseLinkedList
                    // Note: Ensure no trailing space after displaying names
                    // 5a. Print name of only 1 person
                    if (numCount == 1) {
                        currentPerson = head;
                        while (currentPerson != null) {
                            if (currentPerson.getsHeight() == numHeightToFind) {
                                System.out.println(currentPerson.getsName());
                                break;
                            }
                            currentPerson = currentPerson.getsNextPerson();
                        }
                    } else {
                        // 5b. Print name of > 1 person
                        // Find last Person object with height == position-shortest height
                        // then print all names of persons with height == position-shortest height
                        // and print name of last person's height on a separate line
                        currentPerson = head;
                        Person lastPerson = null;
                        while (currentPerson != null) {
                            if (currentPerson.getsHeight() == numHeightToFind) {
                                lastPerson = currentPerson;
                            }
                            currentPerson = currentPerson.getsNextPerson();
                        }

                        int numDistinctHeightSoFar = 0;
                        currentPerson = head;
                        while (currentPerson != null) {
                            if (currentPerson.getsHeight() == numHeightToFind 
                                && (++numDistinctHeightSoFar < numCount) 
                                && currentPerson.isNotHeadDummy()) {
                                System.out.print(currentPerson.getsName() + " ");
                            }
                            currentPerson = currentPerson.getsNextPerson();
                        }
                        // lastPerson will not be null
                        // because there will be a Person object with a height == numHeightToFind
                        // so doing this if statement so that nullpointer exception is not thrown 
                        if (lastPerson != null) {
                            System.out.println(lastPerson.getsName());
                        }
                    }
                } else {
                    System.out.println("No such student");
                }
            }
        } else {
            System.out.println("No such student");
        }
    }

    // Displays linkedlist
    // Precon: isTest = true
    // Postcon: Nil
    private void displaysLinkedList() {
        System.out.println("*** displays list");
        System.out.print("* LinkedList: ");
        Person currentPerson = head;
        while (currentPerson != null) {
            System.out.print("(" + currentPerson.getsName() + ", " + currentPerson.getsHeight() + ") -> ");
            currentPerson = currentPerson.getsNextPerson();
        }
        System.out.println("NULL");
    }

    // Processes query
    // Precon: Q > 0
    // Postcon: Closes scanner
    private void processesQuery(int Q) {
        for (int i = 0; i < Q; i++) {
            String queryType = readsString();
            if (queryType.equals("arrive")) {
                arriveQuery();
            }

            if (queryType.equals("count")) {
                countQuery();
            }

            if (queryType.equals("leave")) {
                leaveQuery();
            }

            if (queryType.equals("shortest")) {
                shortestQuery();
            }
        }
    }
    
    private void run() {
        int Q = readsInteger();
        processesQuery(Q);
        closesScanner();
    }

    public static void main(String[] args) {
        ClassPhoto newClassPhoto = new ClassPhoto();
        newClassPhoto.run();
    }
}

class Person {
    private int height;
    private Person nextPerson;
    private String name;

    public Person(String name, int height, Person nextPerson) {
        this.height = height;
        this.name = name;
        this.nextPerson = nextPerson;
    }

    public boolean hasNextPerson() {
        return this.nextPerson != null;
    }

    public boolean isNotHeadDummy() {
        return ((this.height != -1) && !(this.name.equals("HEAD"))); 
    }

    public int getsHeight() {
        return this.height;
    }

    public Person getsNextPerson() {
        return this.nextPerson;
    }

    public String getsName() {
        return this.name;
    }

    public void setsNextPerson(Person nextPerson) {
        this.nextPerson = nextPerson;
    }
}
