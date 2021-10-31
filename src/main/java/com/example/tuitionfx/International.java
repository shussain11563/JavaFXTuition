package com.example.tuitionfx;

/**
 * International is the subclass for NonResident and defines students that are NonDomestic to the US.
 * Contains methods for getting, setting, manipulating, paying tuition for students.
 * @author Sharia Hussain, David Lam
 */
public class International extends NonResident
{
    private boolean isStudyAbroad;
    private int MIN_FULL_TIME_CREDIT = 12;

    /**
     * Constructs and initializes an International Student object for use.
     * Used for add, remove, pay.
     * @param name the name of the student.
     * @param major the major of the student.
     * @param creditHours the credits hours of the student.
     * @param isStudyAbroad the status if the student is studying abroad.
     */
    public International(String name, Major major, int creditHours, boolean isStudyAbroad) {
        super(name, major, creditHours);
        this.isStudyAbroad = isStudyAbroad;
    }

    /**
     * Sets the International students study abroad status to true.
     */
    public void setIsStudyAbroad()
    {
        double initialValue = 0;
        this.isStudyAbroad = true;
        this.setCreditHours(MIN_FULL_TIME_CREDIT);
        setTotalPayment(initialValue);
        setLastPaymentDate(null);
        this.tuitionDue();
    }

    /**
     * Overrides the tuitionDue method in Student Parent class.
     * Calculates the tuition for an international student.
     */
    @Override
    public void tuitionDue()
    {
        // Studies Abroad
        if(isStudyAbroad == true) {
            this.setTuitionDue( Student.UNIVERSITY_FEE + Student.ADDITIONAL_FEE);
        }
        // Full-Time and Credits > 16 and Not Study Abroad
        else if(this.getStatus() == FULL_TIME && this.getCreditHours() > CREDIT_HOURS_MAX && isStudyAbroad == false) {
            this.setTuitionDue(Student.NON_RES_FULL_TIME_TUITION + Student.UNIVERSITY_FEE + Student.ADDITIONAL_FEE +
                    Student.NON_RES_PART_TIME_TUITION_RATE * (this.getCreditHours() - CREDIT_HOURS_MAX));
        }
        // Full-Time and Credits Between 12 and 16
        else if(this.getStatus() == FULL_TIME)
        {
            this.setTuitionDue(Student.NON_RES_FULL_TIME_TUITION + Student.UNIVERSITY_FEE + Student.ADDITIONAL_FEE);
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
        String isStudyAbroadTextRepresentation = (isStudyAbroad) ? ":study abroad" : "";
        return String.format("%s:international%s", super.toString(), isStudyAbroadTextRepresentation);
    }
}