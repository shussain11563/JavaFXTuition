
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
     * Method that instantiates a student based on the type of Student.
     * Then, the method finalizes the new object by calling a new method to add to Roster.
     * @param rosterCollection the roster collection that holds the list of students
     * @param addType the type of student to be added to the roster
     * @param name the name of the student
     * @param addMajor the major of the student
     * @param intCredits the number of credits the student is taking
     * @param additionalInfo additional info for international and tristate students
     */



    /**
     * Method that checks the bounds for the Min/Max of the credit limits.
     * @param intCredits the number of credits the student is taking
     */



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