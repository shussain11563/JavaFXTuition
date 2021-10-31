
package com.example.tuitionfx;
/**
 * TuitionManager is a user interface class that handles I/O with Roster
 * and and Students. Contains an initialized Roster Collection ready to manipulate Student objects.
 * @author Sharia Hussain, David Lam
 */

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class TuitionManager {
    /**
     * Method that is called by the RunProject2 driver and starts the Tuition Collection Manager.
     * Initializes Roster Collection object and takes input from console to perform actions.
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        Boolean runProject = false;
        Roster rosterCollection = new Roster();

        while(scanner.hasNextLine()) {
            if(runProject == false) {
                System.out.println("Tuition Manager starts running.");
                runProject = true;
            }

            String commandLineInput = scanner.nextLine();
            commandLineInput = commandLineInput.trim();

            if(commandLineInput.equals(""))
                continue;
            else if(commandLineInput.equals("Q")) {
                System.out.println("Tuition Manager terminated.");
                break;
            }
            else
                runTuitionCommands(commandLineInput, rosterCollection);
        }
    }

    /**
     * Helper method that runs the Tuition commands and checks the commandLineInput
     * and matches the command with the input.
     * @param commandLineInput the string that holds the Input from the command line
     * @param rosterCollection the roster collection that holds the list of students
     */
    public void runTuitionCommands(String commandLineInput, Roster rosterCollection) {
        if(commandLineInput.equals("P"))
            rosterCollection.print();
        else if(commandLineInput.equals("PN"))
            rosterCollection.printByNames();
        else if(commandLineInput.equals("PT"))
            rosterCollection.printByPaymentsMadeByPaymentDate();
        else if(commandLineInput.charAt(0) == 'A')
            runAddStudent(commandLineInput, rosterCollection);
        else if(commandLineInput.charAt(0) == 'R')
            runRemoveStudent(commandLineInput, rosterCollection);
        else if(commandLineInput.charAt(0) == 'C')
            runCalculateTuitionDues(rosterCollection);
        else if(commandLineInput.charAt(0) == 'T')
            runPayTuition(commandLineInput, rosterCollection);
        else if(commandLineInput.charAt(0) == 'S')
            runSetStudyAbroadStatus(commandLineInput, rosterCollection);
        else if(commandLineInput.charAt(0) == 'F')
            runSetFinancialAidAmount(commandLineInput, rosterCollection);
        else
            System.out.println("Command '" + commandLineInput + "' not supported!");
    }

    /**
     * Method that tokenizes the Roster Details string and runs a series of methods that checks the input.
     * The method calls a method that processes the correct info that is to be added to the collection.
     * @param rosterDetails the string that holds the Input from the command line
     * @param rosterCollection the roster collection that holds the list of students
     */
    public void runAddStudent(String rosterDetails, Roster rosterCollection) {
        StringTokenizer stringTokenizer = new StringTokenizer(rosterDetails, ",");
        int intCredits = 0;
        String addType, name, major, credits, additionalInfo = "";

        if(checkAddStudent(rosterDetails)) {
            addType = stringTokenizer.nextToken();
            name = stringTokenizer.nextToken();
            major = stringTokenizer.nextToken().toUpperCase();
            credits = stringTokenizer.nextToken();
        }
        else
            return;

        try {
            intCredits = Integer.parseInt(credits);
        }
        catch (NumberFormatException ex) {
            System.out.println("Invalid credit hours.");
            return;
        }
        if(!checkMinMaxCredits(intCredits))
            return;

        try {
            additionalInfo = stringTokenizer.nextToken();
        }
        catch (NoSuchElementException ex1) {
        }

        Major addMajor = Major.valueOf(major);
        runProcessAddStudent(rosterCollection, addType, name, addMajor, intCredits, additionalInfo);
    }

    /**
     * Method that instantiates a student based on the type of Student.
     * Then, the method finalizes the new object by calling a new method to add to Roster.
     * @param rosterCollection the roster collection that holds the list of students
     * @param addType the type of student to be added to the roster
     * @param name the name of the student
     * @param addMajor the major of the student
     * @param intCredits the number of credits the student is taking
     * @param additionalInfo additional info for international and tristate students
     */
    private void runProcessAddStudent(Roster rosterCollection, String addType, String name, Major addMajor, int intCredits, String additionalInfo) {
        if(addType.equals("AR")) {
            Student newResidentStudent = new Resident(name, addMajor, intCredits);
            finalizeAddStudent(rosterCollection, newResidentStudent);
        }
        else if(addType.equals("AN")) {
            Student newNonResidentStudent =  new NonResident(name, addMajor, intCredits);
            finalizeAddStudent(rosterCollection, newNonResidentStudent);
        }
        else if(addType.equals("AT")) {
            additionalInfo = additionalInfo.toUpperCase();
            if(additionalInfo.equals("NY") || additionalInfo.equals("CT")) {
                State addState = State.valueOf(additionalInfo);
                Student newTriStateStudent = new TriState(name, addMajor, intCredits, addState);
                finalizeAddStudent(rosterCollection, newTriStateStudent);
            }
            else if(additionalInfo.equals(""))
            {
                System.out.println("Missing data in command line.");
                return;
            }
            else
            {
                System.out.println("Not part of the tri-state area.");
                return;
            }
        }
        else if(addType.equals("AI")) {
            if(intCredits < 12) {
                System.out.println("International students must enroll at least 12 credits.");
                return;
            }
            else {
                boolean isInternational = Boolean.parseBoolean(additionalInfo.toLowerCase());
                Student newInternationalStudent = new International(name, addMajor, intCredits, isInternational);
                finalizeAddStudent(rosterCollection, newInternationalStudent);
            }
        }
    }
    /**
     * Method that calls the add method in Roster Collection.
     * Then, the method checks if the student has been added to the collection or not.
     * @param rosterCollection the roster collection that holds the list of students
     * @param student the type of student to be added to the roster
     */
    private void finalizeAddStudent(Roster rosterCollection, Student student) {
        if(rosterCollection.add(student))
            System.out.println("Student added.");
        else {
            System.out.println("Student is already in the roster.");
        }
    }

    /**
     * Method that checks for bad input within the Input for Add.
     * @param rosterDetails the string that holds the Input from the command line
     */
    private boolean checkAddStudent(String rosterDetails) {
        StringTokenizer stringTokenizer = new StringTokenizer(rosterDetails, ",");
        String addType, name, major, originalMajorParameter, credits = "";

        addType = stringTokenizer.nextToken();

        try {
            name = stringTokenizer.nextToken();
        }
        catch (NoSuchElementException ex1) {
            System.out.println("Missing data in command line.");
            return false;
        }

        try {
            major = stringTokenizer.nextToken();
            originalMajorParameter = major;
            major = major.toUpperCase();
        }
        catch (NoSuchElementException ex){
            System.out.println("Missing data in command line.");
            return false;
        }

        try {
            credits = stringTokenizer.nextToken();
        }
        catch (NoSuchElementException ex1) {
            System.out.println("Credit hours missing.");
            return false;
        }

        if(!(major.equals("CS") || major.equals("IT") || major.equals("BA") || major.equals("EE") || major.equals("ME"))) {
            System.out.println("'" + originalMajorParameter + "' is not a valid major.");
            return false;
        }
        return true;
    }

    /**
     * Method that checks the bounds for the Min/Max of the credit limits.
     * @param intCredits the number of credits the student is taking
     */
    private boolean checkMinMaxCredits(int intCredits) {
        if(intCredits < 0) {
            System.out.println("Credit hours cannot be negative.");
            return false;
        }
        else if(intCredits < 3) {
            System.out.println("Minimum credit hours is 3.");
            return false;
        }
        else if(intCredits > 24) {
            System.out.println("Credit hours exceed the maximum 24.");
            return false;
        }
        return true;
    }

    /**
     * Method that tokenizes the Roster Details string and removes the student from the roster.
     * The method also checks if the user is not in the roster before removing.
     * @param rosterDetails the string that holds the Input from the command line
     * @param rosterCollection the roster collection that holds the list of students
     */
    public void runRemoveStudent(String rosterDetails, Roster rosterCollection) {
        StringTokenizer stringTokenizer = new StringTokenizer(rosterDetails, ",");
        String name, major = "";

        stringTokenizer.nextToken();
        name = stringTokenizer.nextToken();
        major = stringTokenizer.nextToken().toUpperCase();
        Major addMajor = Major.valueOf(major);

        Student tempStudent = new Student(name,addMajor);

        if(rosterCollection.remove(tempStudent))
            System.out.println("Student removed from the roster.");
        else
            System.out.println("Student is not in the roster.");
    }

    /**
     * Method that runs the command in Roster Collection to calculate the tuition
     * for all students in the roster.
     * @param rosterCollection the roster collection that holds the list of students
     */
    public void runCalculateTuitionDues(Roster rosterCollection) {
        rosterCollection.calculateTuition();
        System.out.println("Calculation completed.");
    }

    /**
     * Method that tokenizes the Roster Details string and checks for correct input.
     * Then the method calls the payTuition method in Roster which then applies the payment
     * for a given students tuition.
     * @param rosterDetails the string that holds the Input from the command line
     * @param rosterCollection the roster collection that holds the list of students
     */
    public void runPayTuition(String rosterDetails, Roster rosterCollection) {
        StringTokenizer stringTokenizer = new StringTokenizer(rosterDetails, ",");
        String name, major, amount, date = "";
        Date paymentDate = null;
        double paymentAmount = 0;
        stringTokenizer.nextToken();
        name = stringTokenizer.nextToken();
        major = stringTokenizer.nextToken().toUpperCase();
        Major addMajor = Major.valueOf(major);

        try {
            amount = stringTokenizer.nextToken();
        }
        catch (NoSuchElementException ex1) {
            System.out.println("Payment amount missing.");
            return;
        }

        Student tempStudent = new Student(name,addMajor);
        paymentAmount = Double.parseDouble(amount);

        try {
            date = stringTokenizer.nextToken();
            paymentDate = new Date(date);
        }
        catch (NoSuchElementException ex1) {
        }

        Student outputStudent = rosterCollection.getStudent(tempStudent);

        if(outputStudent.getTuitionDue() < paymentAmount)
        {
            System.out.println("Amount is greater than amount due.");
            return;
        }
        else if(paymentAmount <= 0) {
            System.out.println("Invalid amount.");
            return;
        }
        else if(!paymentDate.isValid() ) {
            System.out.println("Payment date invalid.");
            return;
        }

        if(outputStudent != null) {
            outputStudent.payTuiton(paymentAmount, paymentDate);
            System.out.println("Payment applied.");
        }
    }

    /**
     * Method that tokenizes the Roster Details string and checks for correct input.
     * Then the method calls the setIsStudyAbroad to set an International Student
     * to study abroad.
     * @param rosterDetails the string that holds the Input from the command line
     * @param rosterCollection the roster collection that holds the list of students
     */
    public void runSetStudyAbroadStatus(String rosterDetails, Roster rosterCollection) {
        StringTokenizer stringTokenizer = new StringTokenizer(rosterDetails, ",");
        String name, major = "";

        stringTokenizer.nextToken();
        name = stringTokenizer.nextToken();
        major = stringTokenizer.nextToken().toUpperCase();
        Major addMajor = Major.valueOf(major);

        Student tempStudent = new Student(name,addMajor);

        Student outputStudent = rosterCollection.getStudent(tempStudent);
        if(outputStudent instanceof International) {
            ((International) outputStudent).setIsStudyAbroad();
            System.out.println("Tuition updated.");
        }
        else {
            System.out.println("Couldn't find the international student.");
        }
    }

    /**
     * Method that tokenizes the Roster Details string and checks for correct input.
     * Then the method calls the setFinancialAid to set the amount of Financial Aid given to a resident student.
     * @param rosterDetails the string that holds the Input from the command line
     * @param rosterCollection the roster collection that holds the list of students
     */
    public void runSetFinancialAidAmount(String rosterDetails, Roster rosterCollection) {
        StringTokenizer stringTokenizer = new StringTokenizer(rosterDetails, ",");
        String name, major, amount = "";
        double financialAidAmount = 0;

        stringTokenizer.nextToken();
        name = stringTokenizer.nextToken();
        major = stringTokenizer.nextToken().toUpperCase();
        Major addMajor = Major.valueOf(major);

        try {
            amount = stringTokenizer.nextToken();
        }
        catch (NoSuchElementException ex1) {
            System.out.println("Missing the amount.");
            return;
        }
        Student tempStudent = new Student(name,addMajor);
        Student outputStudent = rosterCollection.getStudent(tempStudent);


        if(outputStudent != null) {
            financialAidAmount = Double.parseDouble(amount);


            if(financialAidAmount < 0 || financialAidAmount > 10000)
                System.out.println("Invalid amount.");
            else {
                if(outputStudent.getCreditHours() >= 12) {
                    if(outputStudent instanceof Resident) {
                        if(((Resident) outputStudent).setFinancialAid(financialAidAmount) == true)
                            System.out.println("Tuition updated.");
                        else
                            System.out.println("Awarded once already.");
                    }
                    else {
                        System.out.println("Not a resident student.");
                    }
                }
                else {
                    System.out.println("Parttime student doesn't qualify for the award.");
                    return;
                }
            }
        }
        else {
            System.out.println("Student not in the roster.");
        }
    }
}