/**
 * Name         : Sheikh Umar
 */

import java.util.*;

public class ICPC {
    private ArrayList <Problem> problemsList = new ArrayList <> ();
    private ArrayList <Team> teamsList = new ArrayList <> ();
    private boolean isTest = false;
    private HashMap <String, Integer> teamsNameIndexMap = new HashMap <> ();
    private int[] values = new int[3];
    private Scanner sc = new Scanner(System.in);

    // Reads N, P, and Q
    // Precon: N, P and Q are each 0
    // Postcon: N, P and Q are each > 0
    private void readsValues() {
        if (isTest) {
            System.out.println("*** readsValues");
        }

        for (int i = 0; i < values.length; i++) {
            values[i] = sc.nextInt();
        }

        if (isTest) {
            System.out.print("* values: ");
            for (int i = 0; i < values.length; i++) {
                System.out.print(values[i] + " ");
            }
            System.out.println();
        }
    }

    // Closes scanner
    // Precon: All queries have been processed
    // Postcon: Nil
    private void closesScanner() {
        sc.close();
    }

    // Processes each query
    // Precon: Q >= 1
    // Postcon: Close scanner
    private void processesQuery() {
        for (int i = 0; i < values[values.length - 1]; i++) {
            String query = sc.next();
            if (query.equals("FIRST")) {
                firstQuery();
            }

            if (query.equals("DETAILS")) {
                detailsQuery();
            }
            
            if (query.equals("SUBMIT")) {
                submitQuery();
            }
            
            if (query.equals("TOP")) {
                topQuery();
            }

            if (query.equals("UNSOLVED")) {
                unsolvedQuery();
            }
        }
    }

    // Forms problems objects
    // Precon: P >= 1
    // Postcon: Reads teams
    private void formsProblems() {
        if (isTest) {
            System.out.println("*** forms Problems");
        }
        
        for (int i = 0; i < values[1]; i++) {
            Problem newProblem = new Problem(i + 1);
            problemsList.add(i, newProblem);
        }

        if (isTest) {
            System.out.print("problemsList | ");
            for (int i = 0; i < problemsList.size(); i++) {
                Problem currentProblem = problemsList.get(i);
                System.out.print("#" + currentProblem.getsProblemId() +
                                ", timeSolvedFirstTime:" + currentProblem.getsTimeSolvedFirstTime() +
                                ", teamsSolved: " + currentProblem.getsTeamsSolvedList().size() + " | "
                );
            }
            System.out.println();
        }
    }

    // Reads in teams
    // Precon: N >= 1
    // Postcon: Executes initiation
    private void formsTeams() {
        if (isTest) {
            System.out.println("*** formsTeams");
        }

        // 1. Add team to teamsList
        for (int i = 0; i < values[0]; i++) {
            String teamName = sc.next();
            Team newTeam = new Team(teamName, values[1]);
            teamsList.add(i, newTeam);
        }

        // 2. Sort teams based on their names in lexicographically-increasing order
        Collections.sort(teamsList);

        // 3. Iterate through teamList and map team's name to team's current index
        for (int i = 0; i < teamsList.size(); i++) {
            Team team = teamsList.get(i);
            teamsNameIndexMap.put(team.getsTeamName(), i);
        }

        if (isTest) {
            System.out.print("* There are " + teamsList.size() + " teams: ");
            for (int i = 0; i < teamsList.size() - 1; i++) {
                System.out.print(teamsList.get(i).getsTeamName() + " ");
            }
            System.out.println(teamsList.get(teamsList.size() - 1).getsTeamName());
            System.out.println("* teamsNameIndexMap: " + teamsNameIndexMap);
        }
    }

    // Check Problems and Team attributes are correct before processing queries
    // Precon: Both N and P are each >= 1
    // Postcon: Processes Q queries
    private void preQueryCheck() {
        if (isTest) {
            System.out.println("*** preQueryCheck");
        }
        
        // 1. Check each problem's timeSolvedFirstTime is -1 and teamsSolved is 0 before query
        if (isTest) {
            for (int i = 0; i < problemsList.size(); i++) {
                Problem currentProblem = problemsList.get(i);
                System.out.print("Problem #" + currentProblem.getsProblemId() + 
                                    ": index " + i + 
                                    ", timeSolvedFirstTime: " + currentProblem.getsTimeSolvedFirstTime() +
                                    ", teamsSolved: " + currentProblem.getsTeamsSolvedList().size() + 
                                    " | "
                );
            }
            System.out.println();
        }

        // 2. Check each team's isProblemIdSolvedMap (each number of problems mapped to false)
        // and problemIdNumAttemptsMap (each number of problems mapped to 0)
        if (isTest) {
            for (int i = 0; i < teamsList.size(); i++) {
                Team currentTeam = teamsList.get(i);
                System.out.println("* team " + currentTeam.getsTeamName() +
                                    ": isProblemIdSolvedMap - " + currentTeam.getsIsProblemIdSolvedMap() +
                                    " , problemIdNumAttemptsMap - " + currentTeam.getsProblemIdNumAttemptsMap()
                );
            }
        }
    }

    // Executes SUBMIT query
    // Precon: Query entered is SUBMIT
    // Postcon: Processes next query
    private void submitQuery() {
        String teamName = sc.next();
        int problemId = sc.nextInt();
        int time = sc.nextInt();
        String verdict = sc.next();
        if (isTest) {
            System.out.println("*** submit | " +  
                                "teamName: " + teamName +  
                                ", problemId: " + problemId +  
                                ", time: " + time + 
                                ", verdict: " + verdict
            );
        }

        int problemIdIndex = problemId - 1;
        Problem problemToCheck = problemsList.get(problemIdIndex);
        int teamIndex = teamsNameIndexMap.get(teamName).intValue();
        Team teamToCheck = teamsList.get(teamIndex);
        if (isTest) {
            System.out.println("* problemId = " + problemToCheck.getsProblemId() + 
                                ", problemIdIndex = " + problemIdIndex +
                                ", teamIndex = " + teamIndex + 
                                " teamName: " + teamToCheck.getsTeamName()
            );
        }  

        // 1. Check if problem is already solved regardless of verdict entered
        if (teamToCheck.checksIsProblemIdSolved(problemId)) {
            System.out.println("problem already solved");
        } else {
            // 2. There are 2 situations: Accept solution or reject solution
            // 2.1. Accept solution
            if (verdict.equals("AC")) {
                // 2.1. Team did not solve problem earlier, so this is team's solution to problemId being accepted for first time
                // 2.1a. retrieves number of attempts user made on this problem before this submission
                int numAttempts = teamToCheck.getsNumAttempts(problemId);

                // 2.1b. print teamName, verdict, time, and number of attempts user made on this problem before this submission
                System.out.println(teamName + " " + verdict + " " + problemId + " " + numAttempts);

                // 2.1c. adds team to list of teams that solved this problem
                problemToCheck.addsTeamToTeamsSolvedList(teamToCheck);

                // 2.1d. if problem is solved for first time, set time solved to be time user entered
                if (problemToCheck.isProblemIdSolvedFirstTime()) {
                    problemToCheck.setstimeSolvedFirstTime(time);
                }

                // 2.1e. with this submission, indicate problemId has been solved,
                // increase team's number of accepted submissions, and update penalty points
                teamToCheck.updatesProblemIdSolved(problemId, numAttempts, time);
            } else {
                // 2.2. Submission rejected
                // 2.2a. retrieves number of attempts user made on this problem before this submission
                int numAttempts = teamToCheck.getsNumAttempts(problemId);

                // 2.2b. print teamName, verdict, time, and number of attempts user made on this problem before this submission
                System.out.println(teamName + " " + verdict + " " + problemId + " " + numAttempts);

                // 2.2c. so increase number of attempts team made on this problem by 1
                teamToCheck.increasesNumAttempts(problemId);
            }
        }
    }

    // Executes FIRST query
    // Precon: Query entered is FIRST
    // Postcon: Processes next query
    private void firstQuery() {
        int problemId = sc.nextInt();
        if (isTest) {
            System.out.println("*** first | problemId: " + problemId);
        }

        int problemIdIndex = problemId - 1;
        Problem problemToCheck = problemsList.get(problemIdIndex);
        // If problem not solved, print problemId not solved
        if (problemToCheck.isProblemIdSolved()) {
            System.out.println("problem " + problemId + " has not been solved");
        } else {
            // ProblemId has been solved, so print out team that solved it first time and the time team solved it
            System.out.println(problemToCheck.getsTeamNameSolvedFirst() + " " + problemToCheck.getsTimeSolvedFirstTime());
        }
    }

    // Executes DETAILS query
    // Precon: Query entered is DETAILS
    // Postcon: Processes next query
    private void detailsQuery() {
        String teamName = sc.next();
        if (isTest) {
            System.out.println("*** details | teamName: " + teamName);
        }

        int teamIndex = teamsNameIndexMap.get(teamName).intValue();
        Team teamToCheck = teamsList.get(teamIndex);
        System.out.println(teamToCheck.getsTeamName() + " " + 
                            teamToCheck.getsNumAcceptedSubmissions() + " " + 
                            teamToCheck.getsNumPenalty()
        );
    }

    // Executes UNSOLVED query
    // Precon: Query entered is UNSOLVED
    // Postcon: Processes next query
    private void unsolvedQuery() {
        if (isTest) {
            System.out.println("*** unsolved");
        }

        // 1. Iterate through each problem object
        // a. if problem object has 0 teams that have solved it, store problemId in list
        // b. If this list has no problemId, then all problems are solved
        // c. If not, print all of lists problemId with no trailing space
        ArrayList <Integer> unsolvedProblemsIdList = new ArrayList <> ();
        for (int i = 0; i < problemsList.size(); i++) {
            Problem problem = problemsList.get(i);
            if (problem.getsTeamsSolvedList().isEmpty()) {
                unsolvedProblemsIdList.add(problem.getsProblemId());
            }
        }

        if (unsolvedProblemsIdList.isEmpty()) {
            System.out.println("all problems have been solved"); 
        } else {
            if (unsolvedProblemsIdList.size() == 1) {
                System.out.println(unsolvedProblemsIdList.get(0));
            } else {
                for (int i = 0; i < unsolvedProblemsIdList.size() - 1; i++) {
                    System.out.print(unsolvedProblemsIdList.get(i) + " ");
                }
                System.out.println(unsolvedProblemsIdList.get(unsolvedProblemsIdList.size() - 1));
            }
        }
    }

    // Executes query for TOP
    // Precon: Query entered is TOP
    // Postcon: Processes next query
    private void topQuery() {
        if (isTest) {
            System.out.println("*** top");
        }

        // 1. Form Team list with highest number of accepted submissions
        ArrayList <Team> topTeamsList = new ArrayList <> ();
        Team firstTeam = teamsList.get(0);
        topTeamsList.add(firstTeam);
        int highestNumAcceptedSubmissions = firstTeam.getsNumAcceptedSubmissions();
        for (int i = 1; i < teamsList.size(); i++) {
            Team currentTeam = teamsList.get(i);
            if (currentTeam.getsNumAcceptedSubmissions() >= highestNumAcceptedSubmissions) {
                highestNumAcceptedSubmissions = currentTeam.getsNumAcceptedSubmissions();
                topTeamsList.add(currentTeam);
            }
        }

        // 2. From this list, select the team with the least number of penalty points
        Team topTeam = topTeamsList.get(0);
        for (int i = 1; i < topTeamsList.size(); i++) {
            Team currentTeam = topTeamsList.get(i);
            // a. if currentTeam has higher number of submissions than topTeam, set topTeam to be currentTeam
            if (currentTeam.getsNumAcceptedSubmissions() > topTeam.getsNumAcceptedSubmissions()) {
                topTeam = currentTeam;
            } else {
                // if currentTeam has equal number of submissions as topTeam
                // and currentTeam has lower number of penalty points, set topTeam to be currentTeam
                if ((currentTeam.getsNumAcceptedSubmissions() == topTeam.getsNumAcceptedSubmissions()) 
                        && (currentTeam.getsNumPenalty() < topTeam.getsNumPenalty())) {
                            topTeam = currentTeam;
                }
            }
        }

        // 3. Print out team name, team's number of accepted solutions, and team's number of penalty points
        System.out.println(topTeam.getsTeamName() + " " + topTeam.getsNumAcceptedSubmissions() + " " + topTeam.getsNumPenalty());
    }

    private void run() {
        readsValues();
        formsProblems();
        formsTeams();
        preQueryCheck();
        processesQuery();
        closesScanner();
    }

    public static void main(String[] args) {
        ICPC competition = new ICPC();
        competition.run();
    }
}

class Problem {
    private ArrayList <Team> teamsSolvedList;
    private int problemId;
    private int timeSolvedFirstTime;

    public Problem(int problemId) {
        this.problemId = problemId;
        this.timeSolvedFirstTime = -1;
        this.teamsSolvedList = new ArrayList <> ();
    }
    
    public boolean isProblemIdSolved() {
        return this.teamsSolvedList.isEmpty();
    }

    public boolean isProblemIdSolvedFirstTime() {
        return this.timeSolvedFirstTime == -1;
    }

    public int getsProblemId() {
        return this.problemId;
    }

    public int getsTimeSolvedFirstTime() {
        return this.timeSolvedFirstTime;
    }

    public List <Team> getsTeamsSolvedList() {
        return this.teamsSolvedList;
    }

    public String getsTeamNameSolvedFirst() {
        return this.teamsSolvedList.get(0).getsTeamName();
    }

    public void addsTeamToTeamsSolvedList(Team teamToAdd) {
        this.teamsSolvedList.add(teamToAdd);
    }

    public void setstimeSolvedFirstTime(int time) {
        this.timeSolvedFirstTime = time;
    }
}

class Team implements Comparable <Team> {
    private HashMap <Integer, Boolean> isProblemIdSolvedMap;
    private HashMap <Integer, Integer> problemIdNumAttemptsMap;
    private int numAcceptedSubmissions;
    private int numPenalty;
    private String teamName;

    public Team(String teamName, int numProblems) {
        this.isProblemIdSolvedMap = new HashMap <> (numProblems);
        this.problemIdNumAttemptsMap = new HashMap <> (numProblems);
        this.numPenalty = 0;
        this.numAcceptedSubmissions = 0;
        this.teamName = teamName;
        initialisesMaps(numProblems);
    }

    private void initialisesMaps(int numProblems) {
        for (int i = 1; i <= numProblems; i++) {
            this.isProblemIdSolvedMap.put(i, false);
            this.problemIdNumAttemptsMap.put(i, 0);
        }
    }

    public boolean checksIsProblemIdSolved(int problemId) {
        return this.isProblemIdSolvedMap.get(problemId).booleanValue();
    }

    // Arrange in lexicographically-increasing order of teamName
    public int compareTo(Team newTeam) {
        return this.getsTeamName().compareTo(newTeam.getsTeamName());
    }
    
    public int getsNumAcceptedSubmissions() {
        return this.numAcceptedSubmissions;
    }
    
    public int getsNumAttempts(int problemId) {
        return this.problemIdNumAttemptsMap.get(problemId);
    }

    public int getsNumPenalty() {
        return this.numPenalty;
    }

    public Map <Integer, Boolean> getsIsProblemIdSolvedMap() {
        return this.isProblemIdSolvedMap;
    }

    public Map <Integer, Integer> getsProblemIdNumAttemptsMap() {
        return this.problemIdNumAttemptsMap;
    }
    
    public String getsTeamName() {
        return this.teamName;
    }

    // For submissions that are rejected
    public void increasesNumAttempts(int problemId) {
        int numAttempts = this.problemIdNumAttemptsMap.get(problemId);
        this.problemIdNumAttemptsMap.put(problemId, (numAttempts + 1));
    }

    public void updatesProblemIdSolved(int problemId, int numAttempts, int timeSolved) {
        this.isProblemIdSolvedMap.put(problemId, true);
        this.numAcceptedSubmissions++;
        this.numPenalty += (timeSolved + (numAttempts * 20));
    }
}
