
package com.example.tuitionfx;
/**
 * TuitionManager is a user interface class that handles I/O with Roster
 * and and Students. Contains an initialized Roster Collection ready to manipulate Student objects.
 * @author Sharia Hussain, David Lam
 */

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class TuitionManager {

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
        else if(commandLineInput.charAt(0) == 'T')
            runPayTuition(commandLineInput, rosterCollection);
        else if(commandLineInput.charAt(0) == 'S')
            runSetStudyAbroadStatus(commandLineInput, rosterCollection);
        else
            System.out.println("Command '" + commandLineInput + "' not supported!");
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

}