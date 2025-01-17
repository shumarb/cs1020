/**
 * Name         : Sheikh Umar
 */

import java.util.*;

public class Storage {
    private ArrayList <Box> boxList = new ArrayList <> ();
    private boolean isTest = false;
    private HashMap <String, Item> handHeldMap = new HashMap <> ();
    private HashMap <String, Item> itemsMap = new HashMap <>(); 
    private int[] values = new int[4];
    private Scanner sc = new Scanner(System.in);

    // Precon: User has not entered these values
    // Postcon: Each of these values is >= 1
    private void readsValues() {
        for (int i = 0; i < values.length; i++) {
            values[i] = sc.nextInt();
        }

        if (isTest) {
            System.out.print("*** Values: ");
            for (int i = 0; i < values.length - 1; i++) {
                System.out.print(values[i] + " ");
            }
            System.out.println(values[values.length - 1]);
        }
    }

    // Location query
    // Precon: QueryType is location
    // Postcon: Nil
    private void locationQuery() {
        String itemName = sc.next();
        if (isTest) {
            System.out.println("*** location | itemName = " + itemName);
        }

        // 1. If item not bought, print item does not exist
        if (!itemsMap.containsKey(itemName)) {
            System.out.println("item " + itemName + " does not exist");
        } else {
            // 2. If item is hand-held, print item is being held
            Item itemToCheck = itemsMap.get(itemName);
            if (itemToCheck.getsItemLocation() == 0) {
                System.out.println("item " + itemName + " is being held");
            } else {
                // 3. Item is in a box, so print item's box number
                System.out.println("item " + itemName + " is in box " + itemToCheck.getsItemLocation());
            }
        }
    }

    // Valuable query
    // Precon: QueryType is valuable
    // Postcon: Nil
    private void valuableQuery() {
        if (isTest) {
            System.out.println("*** valuable");
        }

        ArrayList <Item> itemsList = new ArrayList <> ();
        itemsMap.forEach((itemsName, item) -> itemsList.add(item));
        if (isTest) {
            System.out.print("** itemsList: ");
            for (int i = 0; i < itemsList.size() - 1; i++) {
                Item currentItem = itemsList.get(i);
                System.out.print(currentItem.getsItemName() + " -> " + currentItem.getsItemValue() + ", ");
            }
            System.out.println(itemsList.get(itemsList.size() - 1).getsItemName() + " -> " + itemsList.get(itemsList.size() - 1).getsItemValue());
        }

        Item mostValuableItem = itemsList.get(0);
        for (int i = 1; i < itemsList.size(); i++) {
            Item currentItem = itemsList.get(i);
            if (isTest) {
                System.out.println("** currentItem: " + currentItem.getsItemName() + " -> " + currentItem.getsItemValue()
                                    + " || mostValueItem: " + mostValuableItem.getsItemName() + " -> " + mostValuableItem.getsItemValue());
            }

            if ((currentItem.getsItemValue() > mostValuableItem.getsItemValue())
                || (currentItem.getsItemValue() == mostValuableItem.getsItemValue() && currentItem.getsItemName().compareTo(mostValuableItem.getsItemName()) < 0)) {
                    mostValuableItem = currentItem;
            }
            if (isTest) {
                System.out.println("** after iteration, most valuable: " + currentItem.getsItemName() + " -> " + currentItem.getsItemValue());
            }
        }

        String mostValueItemString = mostValuableItem.getsItemName();
        System.out.println(mostValueItemString);
    }

    // Creates item
    // Precon: User entered purchase, followed by item's name and value
    // Postcon: Store item in box/hand-held
    private Item createsItem(String itemName, int itemValue, int itemLocation) {
        Item newItem = new Item(itemName, itemValue, itemLocation);
        itemsMap.put(itemName, newItem);
        return newItem;
    }

    // Purchase query
    // Precon: QueryType is purchase
    // Postcon: Nil
    private void purchaseQuery() {
        String itemName = sc.next();
        int itemValue = sc.nextInt();
        if (isTest) {
            System.out.println("*** purchase | itemName = " + itemName + ", itemValue = " + itemValue);
        }

        // Check if item can be hand-held. If can, add item to hand-held map and print hand-held message
        // else, store item in lowest-numbered box that has vacancy, and print message of item deposited in box
        if (handHeldMap.size() < values[1]) {
            Item newItem = createsItem(itemName, itemValue, 0);
            handHeldMap.put(itemName, newItem);
            System.out.println("item " + itemName + " is now being held");
        } else {
            // BoxList has N number of boxes.
            // Check for lowest-numbered box that has vacancy, store item in this box, and display item being stored in which box number
            for (int i = 0; i < boxList.size(); i++) {
                Box currentBox = boxList.get(i);

                if (isTest) {
                    System.out.println("** Now at box #" + currentBox.getsBoxNumber());
                }

                if (currentBox.hasVacancy()) {
                    Item newItem = createsItem(itemName, itemValue, currentBox.getsBoxNumber());
                    currentBox.addsItem(newItem);
                    System.out.println("item " + itemName + " has been deposited to box " + currentBox.getsBoxNumber());
                    break;
                }
            }
        }
    }

    // Deposit item in lowest-numbered box that has vacancy
    // Precon: QueryType is deposit
    // Postcon: Nil
    private void depositQuery() {
        String itemName = sc.next();
        if (isTest) {
            System.out.println("*** deposit | itemName = " + itemName);
        }

        // 1. If item not bought, print itemName does not exist
        if (!itemsMap.containsKey(itemName)) {
            System.out.println("item " + itemName + " does not exist");
        } else {
            Item itemToCheck = itemsMap.get(itemName);
            
            // 2. Item exists, so check if it is in a box
            if (itemToCheck.getsItemLocation() > 0) {
                System.out.println("item " + itemName + " is already in storage");
            } else {
                // 3.Item is hand-held and is to be deposited in a box
                // a. Remove item from hand-held and items maps
                itemsMap.remove(itemName);
                handHeldMap.remove(itemName);

                // b. Store item in lowest-numbered box that has vacancy
                for (int i = 0; i < boxList.size(); i++) {
                    Box currentBox = boxList.get(i);
                    if (currentBox.hasVacancy()) {
                        Item itemToAdd = createsItem(itemToCheck.getsItemName(), itemToCheck.getsItemValue(), currentBox.getsBoxNumber());
                        currentBox.addsItem(itemToAdd);

                        // c. In itemsMap info, put item into it with box number that it is stored in
                        itemsMap.put(itemName, itemToAdd);
                        System.out.println("item " + itemName + " has been deposited to box " + currentBox.getsBoxNumber());
                        break;
                    }
                }
            }
        }
    }

    // Withdraw query
    // Precon: QueryType is withdraw
    // Postcon: Nil
    private void withdrawQuery() {
        String itemName = sc.next();
        if (isTest) {
            System.out.println("*** withdraw || itemName = " + itemName);
        }

        // 1. If item not bought, print item does not exist
        if (!itemsMap.containsKey(itemName)) {
            System.out.println("item " + itemName + " does not exist");
        } else {
            // 2. Item exists. if it is hand-held, print item is held
            Item itemToWithdraw = itemsMap.get(itemName);
            if (itemToWithdraw.getsItemLocation() == 0) {
                System.out.println("item " + itemName + " is already being held");
            } else {
                // 3. Item exists and is not hand-held, hence it is in a box.
                // a. If Nathan can't hold onto it, print can't hold on message
                if (handHeldMap.size() == values[1]) {
                    System.out.println("cannot hold any more items");
                } else {
                    // 4. Item exists, is not hand-held, is in a box, and Nathan can hold onto it
                    // a. remove item from box and itemsMap
                    itemsMap.remove(itemName);
                    int itemBoxNumber = itemToWithdraw.getsItemLocation();
                    Box boxContainingItem = boxList.get(itemBoxNumber - 1); // itemBoxNumber is 1-based counting, so - 1
                    boxContainingItem.removesItem(itemToWithdraw);

                    // b. create new item just like itemToWithdraw, with difference being location 0 (hand-held),
                    // and add it to hand-held and items maps
                    Item itemToAdd = createsItem(itemName, itemToWithdraw.getsItemValue(), 0);
                    handHeldMap.put(itemName, itemToAdd);

                    // c. print message that item has been withdrawn
                    System.out.println("item " + itemName + " has been withdrawn");
                }
            }
        }
    }

    // Processes Q queries
    // Precon: Q >= 1
    // Postcon: Close scanner
    private void processesQueries() {
        int Q = values[values.length - 1];

        for (int i = 0; i < Q; i++) {
            String queryType = sc.next();
            if (queryType.equals("purchase")) {
                purchaseQuery();
            }

            if (queryType.equals("deposit")) {
                depositQuery();
            }

            if (queryType.equals("withdraw")) {
                withdrawQuery();
            }

            if (queryType.equals("location")) {
                locationQuery();
            }

            if (queryType.equals("valuable")) {
                valuableQuery();
            }
        }
    }
    
    // Initialises box list with N number of boxes
    // Precon: Number of boxes > 0
    // Postcon: Processses queries from user
    private void initialisesBoxList() {
        for (int i = 0; i < values[0]; i++) {
            Box currentBox = new Box(i + 1, values[2], new ArrayList <> ());
            boxList.add(currentBox);
        }

        if (isTest) {
            System.out.println("** boxList has " + boxList.size() + " boxes.");
        }
    }

    // Closes scanner
    // Precon: All queries have been processed
    // Postcon: Nil
    private void closesScanner() {
        sc.close();
    }

    public void run() {
        readsValues();
        initialisesBoxList();
        processesQueries();
        closesScanner();
    }
    
    public static void main(String[] args) {
        Storage myStorageSystem = new Storage();
        myStorageSystem.run();
    }
}

class Box {
    private ArrayList <Item> boxItemsList;
    private int boxCapacity;
    private int boxCurrentCapacity;
    private int boxNumber;

    public Box(int boxNumber, int boxCapacity, ArrayList <Item> boxItemsList) {
        this.boxCapacity = boxCapacity;
        this.boxCurrentCapacity = 0;
        this.boxItemsList = boxItemsList;
        this.boxNumber = boxNumber;
    }

    public ArrayList <Item> getsBoxItemList() {
        return this.boxItemsList;
    }

    public boolean hasVacancy() {
        return this.boxCurrentCapacity < boxCapacity;
    }

    public int getsBoxCapacity() {
        return this.boxCapacity;
    }

    public int getsBoxCurrentCapacity() {
        return this.boxCurrentCapacity;
    }
    
    public int getsBoxNumber() {
        return this.boxNumber;
    }

    public void addsItem(Item itemToAdd) {
        this.boxCurrentCapacity++;
        this.boxItemsList.add(itemToAdd);
    }

    public void removesItem(Item itemToRemove) {
        this.boxCurrentCapacity--;
        this.boxItemsList.remove(itemToRemove);
    }
}

class Item {
    private int itemLocation; // 0 for handheld, >= 1 means item in box number >= 1
    private int itemValue;
    private String itemName;

    public Item(String itemName, int itemValue, int itemLocation) {
        this.itemLocation = itemLocation;
        this.itemName = itemName;
        this.itemValue = itemValue;
    }

    public int getsItemLocation() {
        return this.itemLocation;
    }

    public int getsItemValue() {
        return this.itemValue;
    }

    public String getsItemName() {
        return this.itemName;
    }
}
