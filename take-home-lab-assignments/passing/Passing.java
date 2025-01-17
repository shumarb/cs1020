
/**
*	Name		: Sheikh Umar
*/

import java.util.*;

public class Passing {
    private boolean isTest = false;
    private int numPlayers = 0;
    private int[] values = new int[3]; // elements in values array correspond to N, M, K
    private Player head = null;
    private Player lastPlayer = null;
    private Player startPlayer = null;
    private Scanner sc = new Scanner(System.in);

    // Closes scanner
    // Precon: All M number of turns have been processed
    // Postcon: End of program
    private void closesScanner() {
        sc.close();
    }

    // Reads values of N, M, and K
    // Precon: N, M, and K (corresponding to index of the values array) are initially each 0
    // Postcon: N, M, and K are each > 0
    private void readsValues() {
        if (isTest) {
            System.out.println("*** readsValues");
        }
        for (int i = 0; i < values.length; i++) {
            values[i] = sc.nextInt();
        }
        if (isTest) {
            System.out.println("* N: " + values[0] 
                                + ", M: " + values[1] 
                                + ", K: " + values[values.length - 1]
            );
        }
    }

    // Forms N players in a circle
    // Precon: N > 0
    // Postcon: Processes M queries
    private void formsPlayers() {
        if (isTest) {
            System.out.println("*** formsPlayers");
        }

        for (int i = 0; i < values[0]; i++) {
            String name = sc.next();
            Player newPlayer = new Player(name, values[values.length - 1]);
            if (numPlayers == 0) {
                head = new Player("HEAD", -1);
                head.setsNextPlayer(newPlayer);
                lastPlayer = newPlayer;
                numPlayers++;
            } else {
                // Iterate from first player to last player
                // and set last player's next player to be the new player
                // so doing the if statement so program will guarantee not throw NullPointerException
                if (lastPlayer != null) {
                    lastPlayer.setsNextPlayer(newPlayer);
                    lastPlayer = newPlayer;
                    numPlayers++;
                }  
            }

            // Set first player to be the start player for first round of passing
            startPlayer = head.getsNexPlayer();

            // Set last player's next player to be first player
            // hence a circular linked list formed
            // Logically, there will be a lastPlayer, 
            // so doing the if statement so program will guarantee not throw NullPointerException
            if (lastPlayer != null) {
                lastPlayer.setsNextPlayer(head.getsNexPlayer());
            }
        }

        if (isTest) {
            displaysPlayersLinkedList();
        }
    }

    // Displays players in order
    // Precon: N players constructed && isTest == true
    // Postcon: processes M queries
    private void displaysPlayersLinkedList() {
        System.out.println("*** displays linkedList");
        Player currentPlayer = head.getsNexPlayer();

        System.out.print("* ");
        while (currentPlayer != null) {
            // These 2 if statements to prevent infinite loop as this is a circular list
            if (currentPlayer == lastPlayer) {
                System.out.println(currentPlayer.getsPlayerInformation() 
                                    + " -> " + currentPlayer.getsNexPlayer().getsPlayerInformation());
                break;
            }
            System.out.print(currentPlayer.getsPlayerInformation() + " -> ");
            currentPlayer = currentPlayer.getsNexPlayer();
        }
    }

    // Process M queries
    // Precon: Circular linkedlist of players have been formed
    // Postcon: Closes scanner
    private void processesQueries() {
        if (isTest) {
            System.out.println("*** processes queries");
        }
        for (int i = 0; i < values[1]; i++) {
            int numPasses = sc.nextInt();
            if (isTest) {
                System.out.println("* query #" + i + " -> numPasses: " + numPasses);
            }
            identifiesPlayerToReport(numPasses);
        }
    }

    // Identifies and displays player to report to Herbert based on number of passes
    // Precon: Players' position and presence have not been updated
    // Postcon: Processes next query
    private void identifiesPlayerToReport(int numPasses) {
        if (isTest) {
            System.out.println("*** identifies and displays player to report");
        }

        int i = 0;
        Player currentPlayer = startPlayer;
        Player endPlayer = startPlayer;

        // 1. For every pass made up to numPass, set endPlayer to be currentPlayer's enxt player
        // Before proceeding, ensure currentPlayer is eligible to play
        if (startPlayer.isEligiblePlayer()) {
            while (true) {
                if (isTest) {
                    System.out.println("* i: " + i 
                                        + " | currentPlayer: " + currentPlayer.getsPlayerInformation() 
                                        + " | nextPlayer: " + currentPlayer.getsNexPlayer().getsPlayerInformation());
                }
                if (i++ == numPasses) {
                    endPlayer = currentPlayer;
                    break;
                }
                currentPlayer = currentPlayer.getsNexPlayer();
            }
        }

        // 2. endPlayer is now the player that receives the ball after numPasses
        // Display this player's name
        System.out.println(endPlayer.getsName());

        // 3. Updates player's numReceived and circular linked list if required
        startPlayer = updatesPlayersAndList(endPlayer);
    }

    // Updates players' status and circular linked list if required
    // and returns start player for next round
    // Precon: Ball passed around numPasses times and correct player received the ball
    // startPlayer is player that had the ball at the start of each round of numPasses
    // Postcon: Player to start next round is correct
    private Player updatesPlayersAndList(Player endPlayer) {
        // 1. Increase endPlayers' numReceived by 1
        endPlayer.increasesNumReceived();

        // 2. 2 Situations: no elimination, and elimination
        if (!endPlayer.isToBeEliminated()) {
            // 2a. No elimination, so do swap of start player and end player
            int startPlayerNumReceived = startPlayer.getsNumReceived();
            String startPlayerName = startPlayer.getsName();
            startPlayer.swapsPlayer(endPlayer.getsName(), endPlayer.getsNumReceived());
            endPlayer.swapsPlayer(startPlayerName, startPlayerNumReceived);
        } else {
            // 2. Set start player for next round to be player after end player
            startPlayer = endPlayer.getsNexPlayer();

            // 3. Elimination of endPlayer
            // 3 situations: eliminate first player, last player, and player between first and last players
            
            // 3a. Eliminate first player
            if (endPlayer == head.getsNexPlayer()) {
                // 3a1. Set first player to be the second player
                head.setsNextPlayer(endPlayer.getsNexPlayer());
                
                // 3a2. Set last player's next player to be the second player
                lastPlayer.setsNextPlayer(endPlayer.getsNexPlayer());

                // 3a3. Set endPlayer's next player to be null to indicate it's elimination
                endPlayer.setsNextPlayer(null);
            } else {
                Player currentPlayer = head.getsNexPlayer();
                if (endPlayer == lastPlayer) {
                    // 3b. Eliminate last player
                    // iterate through list to get second-last player
                    // set this player as the last player
                    // set this player's next player to be first person in circular linked list
                    // and set endPlayer's next player to be null to indicate it's elimination
                    while (true) {
                        if (currentPlayer.getsNexPlayer() == endPlayer) {
                            lastPlayer = currentPlayer;
                            lastPlayer.setsNextPlayer(head.getsNexPlayer());
                            endPlayer.setsNextPlayer(null);
                            break;
                        }
                        currentPlayer = currentPlayer.getsNexPlayer();
                    }
                } else {
                    // 3c. Eliminate player between first and last player
                    // Get to player before endPlayer
                    // set this players' next player to be endPlayer's next player
                    // and set endPlayer's next player to be null to indicate it's elimination
                    while (true) {
                        if (currentPlayer.getsNexPlayer() == endPlayer) {
                            currentPlayer.setsNextPlayer(endPlayer.getsNexPlayer());
                            endPlayer.setsNextPlayer(null);
                            break;
                        }
                        currentPlayer = currentPlayer.getsNexPlayer();
                    }
                }
            }
        }

        if (isTest) {
            System.out.println("* startPlayer for next round: " + startPlayer.getsPlayerInformation());
            System.out.println("* after update");
            displaysPlayersLinkedList();
        }

        return startPlayer;
    } 

    private void run() {
        readsValues();
        formsPlayers();
        processesQueries();
        closesScanner();
    }
	public static void main(String[] args) {
        Passing passingObj = new Passing();
        passingObj.run();
    }
}

class Player {
    private int numLimit;
    private int numReceived;
    private Player nextPlayer;
    private String name;
    
    public Player (String name, int numLimit) {
        this.name = name;
        this.nextPlayer = null;
        this.numLimit = numLimit;
        this.numReceived = 0;
    }

    public boolean checksHasNextPlayer() {
        return this.nextPlayer == null;
    }

    public boolean isEligiblePlayer() {
        return this.numReceived < this.numLimit;
    }

    public boolean isToBeEliminated() {
        return this.numLimit == this.numReceived;
    }

    public int getsNumReceived() {
        return this.numReceived;
    }

    public Player getsNexPlayer() {
        return this.nextPlayer;
    }

    public String getsName() {
        return this.name;
    }

    public String getsPlayerInformation() {
        return ("(" + this.name + ", " + this.numReceived + ")");
    }

    public void increasesNumReceived() {
        this.numReceived++;
    }

    public void setsNextPlayer(Player nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public void swapsPlayer(String name, int numReceived) {
        this.name = name;
        this.numReceived = numReceived;
    }
}
