/**
* Name : Sheikh Umar
*/

import java.util.*;

public class IVLE {
    private boolean isTest = false;
    private HashMap <String, Module> modules = new HashMap <> ();
    private HashMap <String, Student> students = new HashMap <> ();
    private Scanner sc = new Scanner(System.in);

    // Displays total number of students enrolled
    // Precon: User entered the word total
    // Postcon: Nil
    private void totalQuery() {
        if (isTest) {
            System.out.println("*** todo: Total");
        }

        if (students.isEmpty()) {
            System.out.println("No students registered for modules");
        } else {
            System.out.println(students.size());
        }
    }

    // Adds module to modules arraylist
    // Precon: No module have been created
    // Postcon: At least one module has been created
    private void addsModule(int N) {
        for (int i = 0; i < N; i++) {
            String moduleName = sc.next();
            int numModuleCredits = sc.nextInt();
            int numMaxStudents = sc.nextInt();

            Module module = new Module(numModuleCredits, numMaxStudents, 0);
            modules.put(moduleName, module);

            if (isTest) {
                System.out.println("*** Added module: " + moduleName
                                    + " --> credit: " + module.getsNumModularCredits()
                                    + "; students enrolled: " + module.getsNumStudentsEnrolled()
                                    + "; max num students: " + module.getsNumMaxStudents()
                );
            }
        }
    }

    // Finds out student(s) with highest total number of modular credits if possible
    // Precon: At least 1 student has attained highest number of modular credits
    // Postcon: Nil
    private void highestQuery() {
        if (isTest) {
            System.out.println("*** todo: Highest");
        }

        if (students.isEmpty()) {
            System.out.println("No students registered for modules");
        } else {
            // 1. Iterate through hashmap students to identify highest MC
            // 2. Iterate through hashmap and store students with highest MCs into hashset
            // 3. Display highest, followed by each element in hashset
            ArrayList <String> namesHighestMCs = new ArrayList<> ();
            int highestModularCredits = 0;

            for (Student currentStudent : students.values()) {
                if (currentStudent.getsTotalNumCredits() > highestModularCredits) {
                    highestModularCredits = currentStudent.getsTotalNumCredits();
                }
            }

            // Check all student objects for value of highest MC
            // If student has highest total MCs, add student's name
            // to list of students with highest MCs.
            for (Student currentStudent : students.values()) {
                if (currentStudent.getsTotalNumCredits() == highestModularCredits) {
                    namesHighestMCs.add(currentStudent.getsStudentName());
                }
            }

            Collections.sort(namesHighestMCs);
            System.out.print(highestModularCredits + " ");
            if (namesHighestMCs.size() == 1) {
                System.out.println(namesHighestMCs.get(0));
            } else {
                for (int i = 0; i < namesHighestMCs.size() - 1; i++) {
                    System.out.print(namesHighestMCs.get(i) + " ");
                }
                System.out.println(namesHighestMCs.get(namesHighestMCs.size() - 1));
            }
        }
    }

    // Register student into a module
    // Precon: Student has not been registered to a module
    // Postcon: At least 1 student registered into at least 1 module
    private void registerQuery() {
        String studentName = sc.next();
        String moduleName = sc.next();

        if (isTest) {
            System.out.println("*** todo: register");
            System.out.println("*** studentName: " + studentName + ", moduleName = " + moduleName);
        }

        // Situation A: Student exists
        if (students.containsKey(studentName)) {
            Student studentToCheck = students.get(studentName);
            ArrayList <String> studentToCheckModuleList = studentToCheck.getsModulesRegistered();

            // A1. Student exists, and is already enrolled in module
            if (studentToCheckModuleList.contains(moduleName)) {
                System.out.println(studentName + " is already registered into " + moduleName);
            } else {
                // A2. Student exists, is not enrolled in module, and module has enrolled its maximum number of students
                Module moduleToCheck = modules.get(moduleName);
                if (moduleToCheck.checksHasMaxNumStudentsReached()) {
                    System.out.println(moduleName + " is full");
                } else {
                    // A3. Student exists, is not enrolled in module, and module has vacancy
                    // register student to module via updating module list and increasing module's number of students registered
                    studentToCheck.addsModule(moduleName, moduleToCheck.getsNumModularCredits());
                    moduleToCheck.increasesNumStudentsRegistered();
                    System.out.println(studentName + " successfully registered into " + moduleName);
                }
            }
        } else {
            // Situation B: Student does not exist
            Module moduleToCheck = modules.get(moduleName);
            // B1. Student does not exist, and module has no vacancy
            if (moduleToCheck.checksHasMaxNumStudentsReached()) {
                System.out.println(moduleName + " is full");
            } else {
                // B2. Student does not exist, and module has vacancy 
                Student newStudent = new Student(studentName, moduleName, moduleToCheck.getsNumModularCredits());
                students.put(studentName, newStudent);
                moduleToCheck.increasesNumStudentsRegistered();
                System.out.println(studentName + " successfully registered into " + moduleName);
            }   
        }
    }

    // Removes a student from being registered in a module
    // Precon: Student to be removed is registered in module to be removed
    // Postcon: Student is no longer registered in module to be removed
    private void removesQuery() {
        String studentName = sc.next();
        String moduleName = sc.next();

        if (isTest) {
            System.out.println("*** todo: remove");
            System.out.println("*** studentName: " + studentName + ", moduleName = " + moduleName);
        }

        // Situation A: Student exists
        if (students.containsKey(studentName)) {
            Student studentToCheck = students.get(studentName);
            ArrayList <String> studentToCheckModulesRegistered = studentToCheck.getsModulesRegistered();
            // Situation A1: Student exists and is enrolled in module to be removed
            if (studentToCheckModulesRegistered.contains(moduleName)) {
                Module moduleToUpdate = modules.get(moduleName);
                // Situation A1.1: Student exists, is enrolled in module to be removed, and this is only module that student is enrolled in
                if (studentToCheckModulesRegistered.size() > 1) {
                    moduleToUpdate.decreasesNumStudentsRegistered();
                    studentToCheck.removesModule(moduleName, moduleToUpdate.getsNumModularCredits());
                    System.out.println(studentName + " has been removed from " + moduleName);
                } else {
                    // Situation A1.2: Student exists, is enrolled in module to be removed, and this is not module that student is enrolled in
                    // hence reduce number of students enrolled in module by 1, and remove studenToCheck from student hashmap
                    moduleToUpdate.decreasesNumStudentsRegistered();
                    students.remove(studentName);
                    System.out.println(studentName + " has been removed from " + moduleName);
                }
            } else {
                // Situation A2: Student exists and is not enrolled in module to be removed
                System.out.println(studentName + " is not registered into " + moduleName);
            }

        } else {
            // Situation 2: Student does not exist, hence no registered modules to be removed from student
            System.out.println(studentName + " is not registered into " + moduleName);
        }
    }

    // Finds out all modules that student is enrolled in as well as student's total number of modular credits
    // Precon: Student has been registered on IVLE
    // Postcon: Nil
    private void detailsQuery() {
        String studentName = sc.next();
        if (isTest) {
            System.out.println("*** todo: details");
        }

        // If student does not exist, print out student has no modules
        if (!students.containsKey(studentName)) {
            System.out.println(studentName + " has no modules");
        } else {
            // Student exists, so retrieve student's total MCs and display list of student's modules
            Student currentStudent = students.get(studentName);
            System.out.print(currentStudent.getsTotalNumCredits() + " ");

            ArrayList <String> moduleNames = currentStudent.getsModulesRegistered();
            if (moduleNames.size() == 1) {
                System.out.println(moduleNames.get(0));
            } else {
                for (int i = 0; i < moduleNames.size() - 1; i++) {
                    System.out.print(moduleNames.get(i) + " ");
                }
                System.out.println(moduleNames.get(moduleNames.size() - 1));
            }
        }
    }

    // Reads integer value
    // Precon: Integer variable is assigned value of 0
    // Postcon: Desired integer is assigned value of > 0
    private int readsIntegerValue() {
        int value = sc.nextInt();
        if (isTest) {
            System.out.println("* entered: " + value);
        }

        return value;
    }

    // Processes Q queries
    // Precon: Q > 0
    // Postcon: Closes scanner
    private void processesQuery(int Q) {
        for (int i = 0; i < Q; i++) {
            String queryType = sc.next();

            if (queryType.equals("details")) {
                detailsQuery();
            }

            if (queryType.equals("highest")) {
                highestQuery();
            }

            if (queryType.equals("register")) {
                registerQuery();
            }

            if (queryType.equals("remove")) {
                removesQuery();
            }

            if (queryType.equals("total")) {
                totalQuery();
            }
        }
    }

    // Closes scanner
    // Precon: All queries have been processed
    // Postcon: End of program
    private void closesScanner() {
        sc.close();
    }
    
    private void run() {
        int N = readsIntegerValue();
        int Q = readsIntegerValue();
        addsModule(N);
        processesQuery(Q);
        closesScanner();
    }
    
    public static void main(String[] args) {
        IVLE ivle = new IVLE();
        ivle.run();
    }
}

class Module {
    private int numMaxStudents;
    private int numModularCredits;
    private int numStudentsEnrolled;

    public Module(int modularCredits, int numMaxStudents, int numStudentsEnrolled) {
        this.numMaxStudents = numMaxStudents;
        this.numModularCredits = modularCredits;
        this.numStudentsEnrolled = numStudentsEnrolled;
    }

    public boolean checksHasMaxNumStudentsReached() {
        return this.numStudentsEnrolled == this.numMaxStudents;
    }

    public int getsNumMaxStudents() {
        return this.numMaxStudents;
    }

    public int getsNumModularCredits() {
        return this.numModularCredits;
    }

    public int getsNumStudentsEnrolled() {
        return this.numStudentsEnrolled;
    }

    public void increasesNumStudentsRegistered() {
        this.numStudentsEnrolled++;
    }

    public void decreasesNumStudentsRegistered() {
        this.numStudentsEnrolled--;
    }
}

class Student {
    private ArrayList <String> modulesRegistered = new ArrayList <> ();
    private int totalNumCredits;
    private String studentName;

    public Student(String studentName, String moduleName, int numModuleCredits) {
        this.modulesRegistered.add(moduleName);
        this.studentName = studentName;
        this.totalNumCredits += numModuleCredits;
    }

    public ArrayList <String> getsModulesRegistered() {
        return this.modulesRegistered;
    }

    public boolean checksRegisteredModule (String moduleName) {
        return modulesRegistered.contains(moduleName);
    }

    public int getsTotalNumCredits() {
        return this.totalNumCredits;
    }

    public String getsStudentName() {
        return this.studentName;
    }
    
    public void addsModule(String moduleName, int numModuleCredits) {
        this.modulesRegistered.add(moduleName);
        this.totalNumCredits += numModuleCredits;
        Collections.sort(modulesRegistered);
    }

    public void removesModule(String moduleName, int numModuleCredits) {
        this.modulesRegistered.remove(moduleName);
        this.totalNumCredits -= numModuleCredits;
        Collections.sort(modulesRegistered);
    }
}
