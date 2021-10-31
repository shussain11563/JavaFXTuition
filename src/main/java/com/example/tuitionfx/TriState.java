package com.example.tuitionfx;

/**
 * TriState is the subclass for NonResident and defines students that are inside of NY and CT.
 * Contains methods for getting, setting, manipulating, paying tuition for students.
 * @author Sharia Hussain, David Lam
 */
public class TriState extends NonResident
{
    public State state;

    private static final double NEW_YORK_TUITION_DISCOUNT = 4000;
    private static final double CONNECTICUT_TUITION_DISCOUNT = 5000;

    /**
     * Constructs and initializes a TriState student object for use.
     * Used for add, remove, pay.
     * @param name the name of the student.
     * @param major the major of the student.
     * @param creditHours the credits hours of the student.
     * @param state the state of the student.
     */
    public TriState(String name, Major major, int creditHours, State state)
    {
        super(name, major, creditHours);
        this.state = state;
    }

    /**
     * Overrides the tuitionDue method in Student Parent class.
     * Calculates the tuition for an international student.
     */
    @Override
    public void tuitionDue()
    {
        State nyState = State.NY;
        State ctState = State.CT;

        if(state.equals(nyState))
        {
            if(this.getStatus() == FULL_TIME && this.getCreditHours() > CREDIT_HOURS_MAX) {
                this.setTuitionDue((Student.NON_RES_FULL_TIME_TUITION - NEW_YORK_TUITION_DISCOUNT) + Student.UNIVERSITY_FEE +
                        Student.NON_RES_PART_TIME_TUITION_RATE * (this.getCreditHours()  - CREDIT_HOURS_MAX));
            }
            // Full-Time and Credits Between 12 and 16
            else if(this.getStatus() == FULL_TIME) {
                this.setTuitionDue(Student.NON_RES_FULL_TIME_TUITION + Student.UNIVERSITY_FEE - NEW_YORK_TUITION_DISCOUNT);
            }
            // Part-Time
            else if(this.getStatus() == PART_TIME) {
                this.setTuitionDue((Student.UNIVERSITY_FEE * PART_TIME_FEE_REDUCTION) +
                        (Student.NON_RES_PART_TIME_TUITION_RATE * this.getCreditHours()));
            }
        }
        else if(state.equals(ctState))
        {
            if(this.getStatus() == FULL_TIME && this.getCreditHours() > CREDIT_HOURS_MAX) {
                this.setTuitionDue((Student.NON_RES_FULL_TIME_TUITION - CONNECTICUT_TUITION_DISCOUNT) + Student.UNIVERSITY_FEE +
                        Student.NON_RES_PART_TIME_TUITION_RATE * (this.getCreditHours()  - CREDIT_HOURS_MAX));
            }
            // Full-Time and Credits Between 12 and 16
            else if(this.getStatus() == FULL_TIME) {
                this.setTuitionDue(Student.NON_RES_FULL_TIME_TUITION + Student.UNIVERSITY_FEE - CONNECTICUT_TUITION_DISCOUNT);
                this.setTuitionDue(Student.NON_RES_FULL_TIME_TUITION + Student.UNIVERSITY_FEE - CONNECTICUT_TUITION_DISCOUNT);
            }
            // Part-Time
            else if(this.getStatus() == PART_TIME) {
                this.setTuitionDue((Student.UNIVERSITY_FEE * PART_TIME_FEE_REDUCTION) +
                        (Student.NON_RES_PART_TIME_TUITION_RATE * this.getCreditHours()));
            }
        }

        double newTuition = (getTuitionDue() - getTotalPayment()) > 0 ? getTuitionDue()-getTotalPayment() : 0;
        this.setTuitionDue(newTuition);
    }
    /**
     * Overrides toString method to represent Student objects.
     * @return a textual representation of the Student's information.
     */
    @Override
    public String toString() {
        return String.format("%s(tri-state):%s", super.toString(), this.state);
    }



}