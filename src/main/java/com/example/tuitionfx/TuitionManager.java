
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