package com.example.tuitionfx;

/**
 * NonResident is the subclass for Student and defines students that are outside of NY and CT but in the US.
 * Contains methods for getting, setting, manipulating, paying tuition for students
 * @author Sharia Hussain, David Lam
 */

public class NonResident extends Student
{
    /**
     * Constructs and initializes a NonResident Student object for use.
     * Used for add, remove, pay
     * @param name the name of the student
     * @param major the major of the student
     * @param creditHours the credits hours of the student
     */
    public NonResident(String name, Major major, int creditHours) {
        super(name, major, creditHours);
    }

    /**
     * Overrides the tuitionDue method in Student Parent class.
     * Calculates the tuition for a international student
     */
    @Override
    public void tuitionDue() {
        // Full-Time and Credits > 16
        if(this.getStatus() == FULL_TIME && this.getCreditHours() > CREDIT_HOURS_MAX) {
            this.setTuitionDue(Student.NON_RES_FULL_TIME_TUITION + Student.UNIVERSITY_FEE +
                    Student.NON_RES_PART_TIME_TUITION_RATE * (this.getCreditHours()  - CREDIT_HOURS_MAX));
        }
        // Full-Time and Credits Between 12 and 16
        else if(this.getStatus() == FULL_TIME) {
            this.setTuitionDue(Student.NON_RES_FULL_TIME_TUITION + Student.UNIVERSITY_FEE);
        }
        // Part-Time
        else if(this.getStatus() == PART_TIME) {
            this.setTuitionDue((Student.UNIVERSITY_FEE * PART_TIME_FEE_REDUCTION) +
                    (Student.NON_RES_PART_TIME_TUITION_RATE * this.getCreditHours()));
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
        return String.format("%s:non-resident", super.toString());
    }
}