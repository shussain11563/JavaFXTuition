package com.example.tuitionfx;

/**
 * Resident is the subclass for Student and defines students that are residents of NJ.
 * Contains methods for getting, setting, manipulating, paying tuition for students
 * @author Sharia Hussain, David Lam
 */

public class Resident extends Student
{
    private boolean alreadyAwarded;

    /**
     * Constructs and initializes a Resident Student object for use.
     * Used for add, remove, pay
     * @param name the name of the student
     * @param major the major of the student
     * @param creditHours the credits hours of the student
     */
    public Resident(String name, Major major, int creditHours) {
        super(name, major, creditHours); //change this
        this.alreadyAwarded = false;
    }

    /**
     * Sets the financial aid of a given student and checks if the student was already given aid
     * @param financialAid the financial aid amount to be applied
     */
    public boolean setFinancialAid(double financialAid)
    {
        if(alreadyAwarded == false) {
            double newTuition = (getTuitionDue()-financialAid > 0) ? (getTuitionDue() - financialAid) : 0 ;
            setTuitionDue(newTuition);
            this.alreadyAwarded = true;

            return true;
        }

        return false;
    }

    /**
     * Overrides the tuitionDue method in Student Parent class.
     * Calculates the tuition for a resident student
     */
    @Override
    public void tuitionDue() {
        // Full-Time and Credits > 16
        if(this.getStatus() == FULL_TIME && this.getCreditHours() > CREDIT_HOURS_MAX) {
            this.setTuitionDue(Student.RES_FULL_TIME_TUITION + Student.UNIVERSITY_FEE +
                    Student.RES_PART_TIME_TUITION_RATE * (this.getCreditHours()  - CREDIT_HOURS_MAX));
        }
        // Full-Time and Credits Between 12 and 16
        else if(this.getStatus() == FULL_TIME) {
            this.setTuitionDue(Student.RES_FULL_TIME_TUITION + Student.UNIVERSITY_FEE);
        }
        // Part-Time
        else if(this.getStatus() == PART_TIME) {
            this.setTuitionDue((Student.UNIVERSITY_FEE * PART_TIME_FEE_REDUCTION) +
                    (Student.RES_PART_TIME_TUITION_RATE * this.getCreditHours()));
        }

        double newTuition = (getTuitionDue() - getTotalPayment()) > 0 ? getTuitionDue()-getTotalPayment() : 0;
        this.setTuitionDue(newTuition);

    }

    /**
     * Overrides toString method to represent Student objects.
     * @return a textual representation of the Student's information
     */
    @Override
    public String toString() {
        return String.format("%s:resident", super.toString());
    }

}