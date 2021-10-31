package com.example.tuitionfx;
import java.text.DecimalFormat;


/**
 * The Student class acts as a blueprint for a Student object
 * which contains attributes such as profile, credit hours,
 * tuition due, total payment due, and last payment date.
 * This class also contains methods to allow
 * student objects to have representations such as how they are equal.
 * @author Sharia Hussain, David Lam
 */
public class Student {
    private Profile profile;
    private int creditHours;
    private double tuitionDue;
    private double totalPayment;
    private Date lastPaymentDate;
    private boolean isStatus;

    /** Constants used to show if a student is full time*/
    public static final boolean FULL_TIME = true;

    /** Constants used to show if a student is part time*/
    public static final boolean PART_TIME = false;

    /** Constants used to hold the additional fee for being an international student*/
    public static final double ADDITIONAL_FEE = 2650;

    /** Constants used to hold university that every student pays*/
    public static final double UNIVERSITY_FEE = 3268;

    /** Constants used to hold the amount for resident full-time tuition*/
    public static final double RES_FULL_TIME_TUITION = 12536;

    /** Constants used to hold the amount for nonresident full-time tuition*/
    public static final double NON_RES_FULL_TIME_TUITION = 29737;

    /** Constants used to hold the amount for resident part-time tuition rate*/
    public static final double RES_PART_TIME_TUITION_RATE = 404;

    /** Constants used to hold the amount for nonresident part-time tuition rate*/
    public static final double NON_RES_PART_TIME_TUITION_RATE = 966;

    /** Constants used to hold the amount for the part-time unversity fee reduction*/
    public static final double PART_TIME_FEE_REDUCTION = .8;

    /** Constants used to hold the max for credits before applying a credit rate*/
    public static final double CREDIT_HOURS_MAX = 16;

    /** Constants used to hold the min for part-time until applying the tuition rate*/
    public static final double CREDIT_HOURS_MAX_PART_TIME = 12;

    /**
     * Constructs and initializes a Student object for use.
     * Used for add, remove, pay, study abroad.
     * @param name the name of the student
     * @param major the major of the student
     * @param creditHours the credits hours of the student
     */
    public Student(String name, Major major, int creditHours) {
        this.profile = new Profile(name, major);
        this.creditHours = creditHours;
        this.totalPayment = 0;

        if(this.creditHours >= CREDIT_HOURS_MAX_PART_TIME) {
            this.isStatus = FULL_TIME;
        }
        else {
            this.isStatus = PART_TIME;
        }

        this.lastPaymentDate = null;
    }

    /**
     * Constructs and initializes a Student object for temporary use.
     * Used for add, remove, pay, study abroad.
     * @param name the name of the student.
     * @param major the major of the student.
     */
    public Student(String name, Major major) {
        this.profile = new Profile(name, major);
    }

    /**
     * Retrieves the profile of the student.
     * @return the profile of the student.
     */
    public Profile getProfile()
    {
        return this.profile;
    }

    /**
     * Retrieves the credits of the student.
     * @return the credits of the student.
     */
    public int getCreditHours()
    {
        return this.creditHours;
    }

    /**
     * Retrieves the tuition due of the student.
     * @return the tuition due of the student.
     */
    public double getTuitionDue()
    {
        return this.tuitionDue;
    }

    /**
     * Retrieves the total payment for the tuition of the student.
     * @return the total payment for the tuition of the student.
     */
    public double getTotalPayment()
    {
        return this.totalPayment;
    }

    /**
     * Retrieves the date of the last payment made of the student.
     * @return the date of the last payment made of the student.
     */
    public Date getLastPaymentDate()
    {
        return this.lastPaymentDate;
    }

    /**
     * Retrieves the status of the student if part-time or full-time.
     * @return the status of the student if part-time or full-time.
     */
    public boolean getStatus()
    {
        return this.isStatus;
    }

    /**
     * Sets the tuition due of the student.
     * @param tuitionDue the amount for the tuition that is due.
     */
    public void setTuitionDue(double tuitionDue)
    {
        this.tuitionDue = tuitionDue;
    }

    /**
     * Sets the credit hours of the student.
     * @param creditHours the number of credits to set for the student.
     */
    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    /**
     * Sets the most recent payment date for the student.
     * @param lastPaymentDate the most recent payment date for the student.
     */
    public void setLastPaymentDate(Date lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
    }

    /**
     * Sets the total payment for the student.
     * @param totalPayment the total payment to be set for a given student.
     */
    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
    }

    /**
     * Pays the tuition with the given amount and also sets the last payment date.
     * @param paidTuition the amount that is being paid for the tuition.
     * @param lastPaymentDate the last payment date of the paid tuition.
     */
    public void payTuiton(double paidTuition, Date lastPaymentDate) {
        setTotalPayment(getTotalPayment() + paidTuition);
        setTuitionDue(getTuitionDue() - paidTuition);
        this.lastPaymentDate = lastPaymentDate;
    }

    /**
     * Overrides toString method to represent Student objects.
     * @return a textual representation of the Student's information.
     */
    @Override
    public String toString() {
        String date = (this.getLastPaymentDate() != null) ? this.lastPaymentDate.toString() : "--/--/--";
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return String.format("%s:%s:%d credit hours:tuition due:%s:total payment:%s:last payment date: %s", this.profile.getName(),
                this.profile.getMajor(), this.creditHours, df.format(this.tuitionDue), df.format(this.totalPayment), date);
    }

    /**
     * Overriden by subclasses of Student.
     * Calculates the tuition for each type of Student.
     */
    public void tuitionDue() {
    }
}