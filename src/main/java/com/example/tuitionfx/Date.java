package com.example.tuitionfx;

import java.util.StringTokenizer;
import java.util.Calendar;

/**
 * The Date class gives representation to the Date bases on
 * the Gregorian Calendar and contains methods that compare
 * and check the validity of a Date.
 * @author Sharia Hussain, David Lam
 */
public class Date implements Comparable<Date>
{
    private final int month;
    private final int day;
    private final int year;

    /** Constants used to calculate if year is a leap year*/
    public static final int QUADRENNIAL = 4;
    /** Constants used to calculate if year is a leap year*/
    public static final int CENTENNIAL = 100;
    /** Constants used to calculate if year is a leap year*/
    public static final int QUATERCENTENNIAL = 400;

    /**
     * Constructs and initializes a Date based on a textual representation of "MM/DD/YYYY".
     * @param date is a textual representation of the date
     */
    public Date(String date) {
        StringTokenizer stringTokenizer = new StringTokenizer(date, "/");
        this.month = Integer.parseInt(stringTokenizer.nextToken());
        this.day = Integer.parseInt(stringTokenizer.nextToken());
        this.year = Integer.parseInt(stringTokenizer.nextToken());
    }

    /**
     * Date constructor that initializes the instance variables to the present date.
     */
    public Date() {
        Calendar currentDate = Calendar.getInstance();
        this.month = currentDate.get(Calendar.MONTH) + 1;
        this.day = currentDate.get(Calendar.DATE);
        this.year = currentDate.get(Calendar.YEAR);
    }

    /**
     * Method that checks the validity of the date.
     * @return Returns true if the date is valid and false if the date is not valid.
     */
    public boolean isValid()
    {
        int MONTH_MIN = 1;
        int MONTH_MAX = 12;
        int DAY_MIN = 1;
        int DAY_MAX_THIRTHY = 30;
        int DAY_MAX_THIRTHY_ONE = 31;
        int DAY_MAX_LEAPYEAR = 29;
        int DAY_MAX_NOT_LEAPYEAR = 28;
        Calendar currentDate = Calendar.getInstance();

        if(this.year != currentDate.get(Calendar.YEAR)) {
            return false;
        }
        else if(this.year == currentDate.get(Calendar.YEAR) && this.month > (currentDate.get(Calendar.MONTH))) {
            return false;
        }
        else if(this.month >= MONTH_MIN && this.month <= MONTH_MAX) {
            if(this.month % 2 == 1) {
                return (this.day >= DAY_MIN && this.day <= DAY_MAX_THIRTHY_ONE);
            }
            else if(this.month % 2 == 0 && this.month == 2) {
                if(this.year % QUADRENNIAL == 0) {
                    if(this.year % CENTENNIAL == 0) {
                        if(this.year % QUATERCENTENNIAL == 0) {
                            return (this.day >= DAY_MIN && this.day <= DAY_MAX_LEAPYEAR);
                        }
                        else {
                            return (this.day >= DAY_MIN && this.day <= DAY_MAX_NOT_LEAPYEAR);
                        }
                    }
                    else {
                        return (this.day >= DAY_MIN && this.day <= DAY_MAX_LEAPYEAR);
                    }
                }
                else {
                    return (this.day >= DAY_MIN && this.day <= DAY_MAX_NOT_LEAPYEAR);
                }
            }
            else {
                return (this.day >= DAY_MIN && this.day <= DAY_MAX_THIRTHY);
            }
        }
        else {


            return false;
        }
    }

    /**
     *  This method overrides the compareTo method and it compares two date objects and returns based on which dates are the same, before, or after.
     * @param date object being compared to the current date.
     * @return Returns -1 if Date1 < Date2, Returns 0 if Date1 == Date2, and Returns 1 if Date1 > Date2.
     */
    @Override
    public int compareTo(Date date)
    {
        if(date == null)
        {
            return 1;
        }
        if(this.year < date.year) {
            return -1;
        }
        else if(this.year == date.year)
        {
            if(this.month < date.month) {
                return -1;
            }
            else if(this.month == date.month)
            {
                if(this.day < date.day)
                {
                    return -1;
                }
                else if(this.day == date.day) {
                    return 0;
                }
            }
        }
        else {
            return 1;
        }

        return 10;
    }


    /**
     * Method that returns a formatted string of the Date Object.
     * @return Returns a formatted string in the format of Month/Day/Year.
     */
    @Override
    public String toString()
    {
        return String.format("%d/%d/%d", this.month, this.day, this.year);
    }

}