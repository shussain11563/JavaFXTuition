package com.example.tuitionfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class HelloController
{
    Roster roster = new Roster();

    @FXML
    private BorderPane borderPane;

    @FXML
    private TextField creditHours;

    @FXML
    private CheckBox isStudyAbroadCheckBox;

    @FXML
    private ToggleGroup major;

    @FXML
    private ToggleGroup nonResidentOptions;

    @FXML
    private ToggleGroup residentialStatus;

    @FXML
    private TextField studentName;

    @FXML
    private TextArea textArea;

    @FXML
    private ToggleGroup tristateState;


    @FXML
    void addStudent(ActionEvent event)
    {
        String addType = "", name, credits, additionalInfo = "";
        int intCredits = 0;
        Major addMajor;
        if(checkAddStudent())
        {
            name = this.studentName.getText();
            //addType =
            addType = ((RadioButton) this.residentialStatus.getSelectedToggle()).getText();
            addMajor = Major.valueOf(((RadioButton) this.major.getSelectedToggle()).getText());
            credits = this.creditHours.getText();

            RadioButton nonResidentOptions = ((RadioButton) this.residentialStatus.getSelectedToggle());
            if(nonResidentOptions != null)
            {
                addType = nonResidentOptions.getText();
                if(addType.equals("Tristate"))
                {
                    additionalInfo = ((RadioButton) this.tristateState.getSelectedToggle()).getText();
                }
                else if(addType.equals("International"))
                {
                    additionalInfo = isStudyAbroadCheckBox.isSelected() ? "true" : "false";
                }
            }
        }
        else
        {
            return;
        }

        try {
            intCredits = Integer.parseInt(credits);
        }
        catch (NumberFormatException ex) {
            textArea.appendText("Invalid credit hours.\n");
            return;
        }
        if(!checkMinMaxCredits(intCredits))
            return;



        //addType Test
        runProcessAddStudent(this.roster, addType, name, addMajor, intCredits, additionalInfo);
    }










        //System.out.println(toogleGroupValue);
        //int intCredits = 0;
        //String addType, name, major, credits, additionalInfo = "";

        /*
        if(checkAddStudent(this.roster))
        {

        }
        */




        //first we check if everything is valid

        //valid name



        //String studentName = this.studentName.getText();
        //cint intCredits;
        //if credit hours
        //System.out.println(major.getSelectedToggle().isSelected());
        /*
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
        //System.out.println(addMajor);


        //runProcessAddStudent(this.roster, "TEST",studentName,intCredits,"TEST");
        //fix this
        //runProcessAddStudent(roster, "", studentName, addMajor, intCredits, "");




        RadioButton selectedRadioButton = (RadioButton) major.getSelectedToggle();
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
    //}

    //maybe use try catch exceptions
    private boolean checkAddStudent()
    {

        if(studentName.getText().isEmpty())
        {
            textArea.appendText("Student name not entered.\n");
            return false;
        }

        RadioButton majorButton = (RadioButton) major.getSelectedToggle();
        if(majorButton == null)
        {
            textArea.appendText("Major not selected.\n");
            return false;
        }

        //needs more choices for nonresident and tuition
        //RadioButton majorButton = (RadioButton) major.getSelectedToggle();
        RadioButton status = (RadioButton) this.residentialStatus.getSelectedToggle();
        if(status == null)
        {
            textArea.appendText("Residential status not selected.\n");
            return false;
        }

        RadioButton nonResidentOptionButton = (RadioButton) this.nonResidentOptions.getSelectedToggle();
        if(nonResidentOptionButton != null && nonResidentOptionButton.getText().equals("Tristate"))
        {
            RadioButton states = (RadioButton) this.tristateState.getSelectedToggle();
            if (states == null)
            {
                textArea.appendText("No tristate area selected.\n");
                return false;
            }
            //if
        }
        /*
        else if(status.getText().equals("Non-Resident"))
        {
            //we

        }*/
        //else if nonresident and tristate selected but no state


        if(creditHours.getText().isEmpty())
        {
            textArea.appendText("Credit hours missing.\n");
            return false;
        }

       //if()
        return true;
    }


    private void runProcessAddStudent(Roster rosterCollection, String addType, String name, Major addMajor, int intCredits, String additionalInfo)
    {
        if(addType.equals("Resident")) {
            Student newResidentStudent = new Resident(name, addMajor, intCredits);
            finalizeAddStudent(rosterCollection, newResidentStudent);
        }
        //change addtype
        else if(addType.equals("Non-Resident")) {
            Student newNonResidentStudent = new NonResident(name, addMajor, intCredits);
            finalizeAddStudent(rosterCollection, newNonResidentStudent);
        }
        //change addtype
        else if(addType.equals("Tristate")) {
            additionalInfo = additionalInfo.toUpperCase();
            //change addtype
            if(additionalInfo.equals("NY") || additionalInfo.equals("CT")) {
                State addState = State.valueOf(additionalInfo);
                Student newTriStateStudent = new TriState(name, addMajor, intCredits, addState);
                finalizeAddStudent(rosterCollection, newTriStateStudent);
            }
            //change addtype
            else if(additionalInfo.equals(""))
            {
                //CHANGE THIS
                textArea.appendText("No Tristate Area selected.\n");
                return;
            }
            else
            {
                //CHANGE THIS
                textArea.appendText("Not part of the tri-state area.\n");
                return;
            }
        }
        //change addtype
        else if(addType.equals("International"))
        {
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
            textArea.appendText("Student added.\n");
        else {
            textArea.appendText("Student is already in the roster.\n");
        }
    }

    //remove magic numberc
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
    void removeStudent(ActionEvent event)
    {
        String name = "";
        Major addMajor;
        if(checkAddStudent())
        {
            name = this.studentName.getText();
            addMajor = Major.valueOf(((RadioButton) this.major.getSelectedToggle()).getText());
        }
        else
        {
            return;
        }

        Student tempStudent = new Student(name,addMajor);

        if(this.roster.remove(tempStudent))
            System.out.println("Student removed from the roster.");
        else
            System.out.println("Student is not in the roster.");

    }

    private boolean checkRemoveStudent()
    {
        if(studentName.getText().isEmpty())
        {
            textArea.appendText("Student name not entered.\n");
            return false;
        }

        RadioButton majorButton = (RadioButton) major.getSelectedToggle();
        if(majorButton == null)
        {
            textArea.appendText("Major not selected.\n");
            return false;
        }

        return true;
    }

    public void runRemoveStudent(String rosterDetails, Roster rosterCollection) {

        StringTokenizer stringTokenizer = new StringTokenizer(rosterDetails, ",");
        String name, major = "";

        stringTokenizer.nextToken();
        name = stringTokenizer.nextToken();
        major = stringTokenizer.nextToken().toUpperCase();
        Major addMajor = Major.valueOf(major);

        Student tempStudent = new Student(name,addMajor);

        if(rosterCollection.remove(tempStudent))
            textArea.appendText("Student removed from the roster.\n");
        else
            textArea.appendText("Student is not in the roster.\n");
    }

    /*
    //make this into one method
    @FXML
    void nonResidentMenu(ActionEvent event)
    {

        for(var toggle : nonResidentOptions.getToggles())
        {
            ((RadioButton) toggle).setDisable(false);
        }

    }
    */


    @FXML
    void residentMenu(ActionEvent event)
    {
        //check null pointer
        //change name
        boolean disable;
        String buttonName = ((RadioButton) residentialStatus.getSelectedToggle()).getText();
        disable = buttonName.equals("Resident") ? true : false;

        for(var toggle : nonResidentOptions.getToggles())
        {
            ((RadioButton) toggle).setDisable(disable);
        }
    }

    @FXML
    void nonResidentSubMenu(ActionEvent event)
    {


        String buttonName = ((RadioButton) this.nonResidentOptions.getSelectedToggle()).getText();

        System.out.println(buttonName);
        if(buttonName.equals("Tristate"))
        {
            for(var toggle : this.tristateState.getToggles())
            {
                ((RadioButton) toggle).setDisable(false);

                this.isStudyAbroadCheckBox.setDisable(true);
            }
        }
        else if(buttonName.equals("International"))
        {
            for(var toggle : this.tristateState.getToggles())
            {
                ((RadioButton) toggle).setDisable(true);
            }

            this.isStudyAbroadCheckBox.setDisable(false);
            //
            //disable tristate
            //disable tristate staes
        }

        //disable = buttonName.equals("Tristate") ? true : false;
        //toggleGroup = buttonName.equals("Tristate") ? this.tristateState : this



    }





}
