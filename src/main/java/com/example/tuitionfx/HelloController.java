package com.example.tuitionfx;

/**
 * HelloController is a class that handles all the events driven by the I/O in the application.
 * @author Sharia Hussain, David Lam
 */


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.HBox;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class HelloController
{
    /** Roster object that will contain all details when the application is running*/
    Roster roster = new Roster();

    /** BorderPane Object used for manipulation/receiving within the JavaFX UI */
    @FXML
    private BorderPane borderPane;

    /** TextField Object used for manipulation/receiving of creditHours data within the JavaFX UI */
    @FXML
    private TextField creditHours;

    /** TextField Object used for manipulation/receiving of financialAidAmount data within the JavaFX UI */
    @FXML
    private TextField financialAidAmount;

    /** CheckBox Object used for manipulation/receiving of isStudyAbroadCheckBox data within the JavaFX UI */
    @FXML
    private CheckBox isStudyAbroadCheckBox;

    /** ToggleGroup Object used for manipulation/receiving of major data within the JavaFX UI */
    @FXML
    private ToggleGroup major;

    /** ToggleGroup Object used for manipulation/receiving of majorPaymentTab data within the JavaFX UI */
    @FXML
    private ToggleGroup majorPaymentTab;

    /** ToggleGroup Object used for manipulation/receiving of nonResidentOptions data within the JavaFX UI */
    @FXML
    private ToggleGroup nonResidentOptions;

    /** TextField Object used for manipulation/receiving of paymentAmount data within the JavaFX UI */
    @FXML
    private TextField paymentAmount;

    /** DatePicker Object used for manipulation/receiving of paymentDate data within the JavaFX UI */
    @FXML
    private DatePicker paymentDate;

    /** ToggleGroup Object used for manipulation/receiving of residentialStatus data within the JavaFX UI */
    @FXML
    private ToggleGroup residentialStatus;

    /** TextField Object used for manipulation/receiving of studentName data within the JavaFX UI */
    @FXML
    private TextField studentName;

    /** TextField Object used for manipulation/receiving of studentNamePaymentFinAid data within the JavaFX UI */
    @FXML
    private TextField studentNamePaymentFinAid;

    /** textArea Object used for manipulation/receiving of textArea data within the JavaFX UI */
    @FXML
    private TextArea textArea;

    /** ToggleGroup Object used for manipulation/receiving of tristateState data within the JavaFX UI */
    @FXML
    private ToggleGroup tristateState;

    /**
     * Method that adds a student to the roster but also processes the
     * correct info that is to be added to the collection.
     * @param event the event object that is connected and responds to the UI component
     */
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

            RadioButton nonResidentOptions = ((RadioButton) this.nonResidentOptions.getSelectedToggle());
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
                    System.out.println("Here on line 74");
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

    /**
     * Method that checks for bad input within the Input for Add.
     */
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
        }

        if(creditHours.getText().isEmpty())
        {
            textArea.appendText("Credit hours missing.\n");
            return false;
        }
        return true;
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
    private void runProcessAddStudent(Roster rosterCollection, String addType, String name, Major addMajor, int intCredits, String additionalInfo)
    {
        System.out.println(addType);
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
                boolean isStudyAbroad = Boolean.parseBoolean(additionalInfo.toLowerCase());
                if(isStudyAbroad == true && intCredits != 12)
                {
                    textArea.appendText("International students in the study abroad program must enroll with 12 credits.\n");
                    return;
                }
                Student newInternationalStudent = new International(name, addMajor, intCredits, isStudyAbroad);
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
    private void finalizeAddStudent(Roster rosterCollection, Student student)
    {
        if(rosterCollection.add(student))
            textArea.appendText("Student added.\n");
        else {
            textArea.appendText("Student is already in the roster.\n");
        }
    }

    /**
     * Method that checks the bounds for the Min/Max of the credit limits.
     * @param intCredits the number of credits the student is taking
     */
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
    /**
     * Method that removes a student from the roster.
     * The method also checks if the user is not in the roster before removing.
     */
    @FXML
    void removeStudent(ActionEvent event)
    {
        String name = "";
        Major addMajor;
        if(checkRemoveStudent())
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
            textArea.appendText("Student removed from the roster.\n");
        else
            textArea.appendText("Student is not in the roster.\n");

    }
    /**
     * Method that checks the if there is an input for student to be removed.
     */
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

    /**
     * Method that toggles radioButtons based on if resident or not.
     * @param event the event object that is connected and responds to the UI component
     */
    @FXML
    void residentMenu(ActionEvent event)
    {
        //check null pointer
        //change name
        boolean disable;
        String buttonName = ((RadioButton) residentialStatus.getSelectedToggle()).getText();
        disable = buttonName.equals("Resident") ? true : false;

        for(var toggle : this.nonResidentOptions.getToggles())
        {
            //uncheck box it
            toggle.setSelected(false);
            ((RadioButton) toggle).setDisable(disable);
        }

        if(buttonName.equals("Resident"))
        {
            for(var toggle : this.tristateState.getToggles())
            {
                toggle.setSelected(false);
                ((RadioButton) toggle).setDisable(disable);
            }

            this.isStudyAbroadCheckBox.setSelected(false);
            this.isStudyAbroadCheckBox.setDisable(disable);
        }
    }

    /**
     * Method that toggles checkBoxes based on if nonResident or not.
     * @param event the event object that is connected and responds to the UI component
     */
    @FXML
    void nonResidentSubMenu(ActionEvent event)
    {
        String buttonName = ((RadioButton) this.nonResidentOptions.getSelectedToggle()).getText();

        if(buttonName.equals("Tristate")) {
            for(var toggle : this.tristateState.getToggles())
            {
                ((RadioButton) toggle).setDisable(false);
                this.isStudyAbroadCheckBox.setSelected(false);
                this.isStudyAbroadCheckBox.setDisable(true);
            }
        }
        else if(buttonName.equals("International"))
        {
            for(var toggle : this.tristateState.getToggles())
            {
                toggle.setSelected(false);
                ((RadioButton) toggle).setDisable(true);
            }
            this.isStudyAbroadCheckBox.setDisable(false);
        }
    }

    /**
     * Method that toggles financialAid after checking for valid inputs.
     * @param event the event object that is connected and responds to the UI component
     */
    @FXML
    void setFinancialAid(ActionEvent event)
    {
        String name = "";
        Major addMajor;
        double financialAidAmount = 0;
        if(checkSetFinancialAid())
        {
            name = this.studentNamePaymentFinAid.getText();
            addMajor = Major.valueOf(((RadioButton) this.majorPaymentTab.getSelectedToggle()).getText());
            financialAidAmount = Double.parseDouble(this.financialAidAmount.getText());
        }
        else
        {return;}

        Student tempStudent = new Student(name,addMajor);
        Student outputStudent = this.roster.getStudent(tempStudent);

        if(outputStudent != null)
        {

            if(financialAidAmount < 0 || financialAidAmount > 10000)
                textArea.appendText("Invalid amount.\n");
            else {
                if(outputStudent.getCreditHours() >= 12) {
                    if(outputStudent instanceof Resident) {
                        if(((Resident) outputStudent).setFinancialAid(financialAidAmount) == true)
                            textArea.appendText("Tuition updated.\n");
                        else
                            textArea.appendText("Awarded once already.\n");
                    }
                    else {
                        textArea.appendText("Not a resident student.\n");
                    }
                }
                else {
                    textArea.appendText("Parttime student doesn't qualify for the award.\n");
                    return;
                }
            }
        }
        else {
            textArea.appendText("Student not in the roster.\n");
        }

    }

    /**
     * Method that checks the if there is correct inputs for financialAid.
     */
    private boolean checkSetFinancialAid()
    {
        if(this.studentNamePaymentFinAid.getText().isEmpty())
        {
            textArea.appendText("Student name not entered.\n");
            return false;
        }

        RadioButton majorButton = (RadioButton) this.majorPaymentTab.getSelectedToggle();
        if(majorButton == null)
        {
            textArea.appendText("Major not selected.\n");
            return false;
        }

        double payment = 0;

        if(this.financialAidAmount.getText().equals(""))
        {
            textArea.appendText("Amount not entered.\n");
            return false;
        }

        try {
            payment = Double.parseDouble(this.financialAidAmount.getText());
        }
        catch (NumberFormatException ex) {
            textArea.appendText("Invalid Amount.\n");
            return false;
        }

        return true;
    }

    /**
     * Method that pays the tuition for a student but checks if all the input is given.
     * @param event the event object that is connected and responds to the UI component
     */
    @FXML
    void pay(ActionEvent event)
    {
        String name = "";
        Major addMajor;
        double paymentAmount = 0;
        Date paymentDate;

        if(checkPayment())
        {
            name = this.studentNamePaymentFinAid.getText();
            addMajor = Major.valueOf(((RadioButton) this.majorPaymentTab.getSelectedToggle()).getText());
            paymentAmount = Double.parseDouble(this.paymentAmount.getText());
            Date paymentDate = this.paymentDate.getValue().toString();

        }
        else
        {return;}

        Student tempStudent = new Student(name,addMajor);
        Student outputStudent = this.roster.getStudent(tempStudent);

        if(outputStudent != null)
        {
            if(outputStudent.getTuitionDue() < paymentAmount)
            {
                textArea.appendText("Amount is greater than amount due.\n");
                return;
            }
            else if(paymentAmount <= 0) {
                textArea.appendText("Invalid amount.\n");
                return;
            }
            else if(!paymentDate.isValid() ) {
                textArea.appendText("Payment date invalid.\n");
                return;
            }
            else
            {
                outputStudent.payTuiton(paymentAmount, paymentDate);
                textArea.appendText("Payment applied.");
            }

        }
        else
        {
            textArea.appendText("Student not in the roster.\n");
        }




    }

    /**
     * Method that checks the if there is correct inputs for student payment.
     */
    public boolean checkPayment()
    {

        if(this.studentNamePaymentFinAid.getText().isEmpty())
        {
            textArea.appendText("Student name not entered.\n");
            return false;
        }

        RadioButton majorButton = (RadioButton) this.majorPaymentTab.getSelectedToggle();
        if(majorButton == null)
        {
            textArea.appendText("Major not selected.\n");
            return false;
        }

        double payment = 0;

        if(this.paymentAmount.getText().equals(""))
        {
            textArea.appendText("Payment amount missing.\n");
            return false;
        }

        try {
            payment = Double.parseDouble(this.paymentAmount.getText());
        }
        catch (NumberFormatException ex) {
            textArea.appendText("Invalid Amount.\n");
            return false;
        }

        String test;
        try{
            test = this.paymentDate.getValue().toString();
        }
        catch (Exception e)
        {
            //use isValid
            textArea.appendText("");
        }

        return true;
    }
    /**
     * Method that converts a String date from "YYYY-MM-DD" to "MM-DD-YYYY
     * @param date string of the date to be converted
     */
    private String convertDateFormat(String date) {
        StringTokenizer stringTokenizer = new StringTokenizer(date, "-");
        String year, month, day = "";

        year = stringTokenizer.nextToken();
        month = stringTokenizer.nextToken();
        day = stringTokenizer.nextToken();


        String output = month + "-" + day + "-" + year;
        return output;
    }
    
    /*
    @FXML
    void runCalculateTuitionDue()
    {
        this.roster.calculateTuition();
        textArea.appendText("Calculation completed.\n");
    }





     */





}
