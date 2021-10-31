package com.example.tuitionfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class HelloController
{
    Roster roster = new Roster();

    @FXML
    private RadioButton CS;

    @FXML
    private BorderPane borderPane;

    @FXML
    private TextField creditHours;

    @FXML
    private ToggleGroup major;

    @FXML
    private HBox majors;

    @FXML
    private ToggleGroup residentialStatus;

    @FXML
    private TextField studentName;

    @FXML
    private TextArea textArea;

    //-----ADD STUDENT

    void validateInformation()
    {
        //String studentName;

    }


    @FXML
    void addStudent(ActionEvent event)
    {
        int intCredits = 0;
        String addType, name, major, credits, additionalInfo = "";

        /*
        if(checkAddStudent(this.roster))
        {

        }
        */




        //first we check if everything is valid

        //valid name



        String studentName = this.studentName.getText();
        //cint intCredits;
        //if credit hours
        //System.out.println(major.getSelectedToggle().isSelected());
        try
        {
            intCredits = Integer.parseInt(this.creditHours.getText());
        }
        catch (NumberFormatException ex)
        {
            textArea.appendText("Invalid credit hours.\n");
            return;
        }

        //get selected RadioButton, needs error handling
        Major addMajor = Major.valueOf(((RadioButton) major.getSelectedToggle()).getText());
        System.out.println(addMajor);


        //runProcessAddStudent(this.roster, "TEST",studentName,intCredits,"TEST");
        //fix this
        runProcessAddStudent(roster, "", studentName, addMajor, intCredits, "");




        //RadioButton selectedRadioButton = (RadioButton) major.getSelectedToggle();
        //String toogleGroupValue = selectedRadioButton.getText();
        //System.out.println(toogleGroupValue);


        /*
        for(var toggle : major.getToggles())
        {
            //((RadioButton) toggle).setD
            System.out.println("-->" + ((RadioButton) toggle).getText());
        }
        */
        //System.out.println(major.getSelectedToggle().isSelected());
        //Major addMajor = Major.valueOf(major);

        //test.add()

        //
    }

    private boolean checkAddStudent(String name, Major major)


    private void runProcessAddStudent(Roster rosterCollection, String addType, String name, Major addMajor, int intCredits, String additionalInfo)
    {
        if(addType.equals("Resident")) {
            Student newResidentStudent = new Resident(name, addMajor, intCredits);
            finalizeAddStudent(rosterCollection, newResidentStudent);
        }
        else if(addType.equals("AN")) {
            Student newNonResidentStudent = new NonResident(name, addMajor, intCredits);
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
                //CHANGE THIS
                textArea.appendText("Missing data in command line.\n");
                return;
            }
            else
            {
                //CHANGE THIS
                textArea.appendText("Not part of the tri-state area.\n");
                return;
            }
        }
        else if(addType.equals("AI")) {
            if(intCredits < 12)
            {
                textArea.appendText("International students must enroll at least 12 credits.\n");
                return;
            }
            else {
                boolean isInternational = Boolean.parseBoolean(additionalInfo.toLowerCase());
                Student newInternationalStudent = new International(name, addMajor, intCredits, isInternational);
                finalizeAddStudent(rosterCollection, newInternationalStudent);
            }
        }
    }

    private void finalizeAddStudent(Roster rosterCollection, Student student)
    {
        if(rosterCollection.add(student))
            textArea.appendText("Student added.");
        else {
            textArea.appendText("Student is already in the roster.\n");
        }
    }

    private boolean checkMinMaxCredits(int intCredits) {
        if(intCredits < 0) {
            textArea.appendText("Credit hours cannot be negative.\n");
            return false;
        }
        else if(intCredits < 3) {
            textArea.appendText("Minimum credit hours is 3.\n");
            return false;
        }
        else if(intCredits > 24) {
            textArea.appendText("Credit hours exceed the maximum 24.\n");
            return false;
        }
        return true;
    }


    @FXML
    void removeStudent(ActionEvent event) {

    }



}
