package com.example.tuitionfx;

/**
 * The Roster class is an arraylist data structure that contains Students and related types.
 * This class contains methods that allow adding, deleting, sorting, lending, removing.
 * and other manipulations of the arraylist.
 * @author Sharia Hussain, David Lam
 */
public class Roster {
    private Student[] roster;
    private int size; //keep track of the number of students in the roster

    private static final int NOT_FOUND = -1;
    private static final int INITIAL_CAPACITY = 4;
    private static final int GROWTH_FACTOR = 4;
    private static final int EMPTY = 0;

    /**
     * Constructs and initializes a Student Roster object.
     * Initializes a roster array with size of INITIAL_CAPACITY (4).
     */
    public Roster() {
        this.roster = new Student[INITIAL_CAPACITY];
        this.size = 0;
    }

    /**
     * Finds a student within the Student Roster.
     * @param student the student to find within the Student Roster.
     * @return index of student in rosters array, returns NOT_FOUND (-1) if not found.
     */
    private int find(Student student) {
        for (int i = 0; i < this.size; i++) {
            if (this.roster[i] != null && this.roster[i].getProfile().getName().equals(student.getProfile().getName()) &&
                    this.roster[i].getProfile().getMajor().equals(student.getProfile().getMajor())) {
                return i;
            }
        }

        return NOT_FOUND;
    }

    /**
     * Returns the Student from the Student Roster.
     * @param student the student to find and return.
     * @return the student if the student is within the Student Roster, null otherwise.
     */
    public Student getStudent(Student student) {
        int index = find(student);

        if (index == NOT_FOUND) {
            return null;
        } else {
            return this.roster[index];
        }

    }

    /**
     * Grows the Student Roster by GROWTH_FACTOR.
     * grow() is called in add() when the roster array is full.
     */
    private void grow() {
        Student[] oldRoster = this.roster;
        int oldRosterLength = oldRoster.length;
        this.roster = new Student[oldRosterLength + GROWTH_FACTOR];

        for (int i = 0; i < oldRosterLength; i++) {
            this.roster[i] = oldRoster[i];
        }
    }

    /**
     * shifting() is called after a removal in the Roster is done in remove().
     * Keeps the rosters contiguous with no gaps by shifting.
     */
    private void shifting() {
        for (int i = 0; i < this.roster.length - 1; i++) {
            if (this.roster[i] == null) {
                this.roster[i] = this.roster[i + 1];
                this.roster[i + 1] = null;
            }
        }
    }

    /**
     * Adds a student to the Student Roster and increases counter.
     * Grows the Student Roster to store more than the capacity of the Student Roster.
     * @param student the student to add to the Roster.
     * @return true if adding student to the Roster was successful, false if the student is already in the Roster.
     */
    public boolean add(Student student) {

        if (find(student) != NOT_FOUND) {
            return false;
        }

        if (size == this.roster.length) {
            grow();
        }

        this.roster[this.size] = student;
        this.size++;
        return true;
    }

    /**
     * Removes a specified Student from the roster array.
     * @param student the student to remove from the roster.
     * @return true if removing a student from the roster collection is successful, returns false otherwise.
     */
    public boolean remove(Student student) {
        int indexOfStudent = find(student);

        if (indexOfStudent == NOT_FOUND) {
            return false;
        }

        this.roster[indexOfStudent] = null;
        this.size--;
        shifting();
        return true;
    }

    /**
     * Returns each Student's information in the roster collection
     * to be printed in the JavaFX TextArea.
     */
    public String print() {
        String resultant = "";
        if (this.size == EMPTY) {
            resultant = "Student roster is empty!\n";
            return resultant;
        }

        resultant += "* list of students in the roster **\n";

        for (int i = 0; i < this.size; i++) {
            resultant += resultant + this.roster[i].toString() + "\n";
        }

        resultant += "* end of roster **\n";

        return resultant;

    }

    /**
     * Returns each Student's information in the roster collection by name
     * to be printed in the JavaFX TextArea.
     */
    public String printByNames()
    {
        String resultant = "";
        if (this.size == EMPTY) {
            resultant = "Student roster is empty!\n";
            return resultant;
        }

        resultant += "* list of students ordered by name **\n";

        String[] names = new String[this.roster.length];

        for (int i = 0; i < this.roster.length; i++) {
            if (this.roster[i] != null) {
                names[i] = this.roster[i].getProfile().getName();
            }
        }

        insertionSort(names);
        bubbleNullToEnd();

        for (int i = 0; i < this.size; i++) {
            resultant += resultant + this.roster[i].toString() + "\n";
        }
        resultant += "* end of roster **\n";
        return resultant;
    }

    /**
     * Returns each Student's information in the roster collection by Payments made by their payment date
     * to be printed in the JavaFX TextArea.
     */
    public String printByPaymentsMadeByPaymentDate()
    {
        String resultant = "";
        if (this.size == EMPTY) {
            resultant = "Student roster is empty!\n";
            return resultant;
        }

        resultant += "* list of students made payments ordered by payment date **\n";

        Date paymentMades[] = new Date[this.roster.length];

        for (int i = 0; i < this.roster.length; i++) {
            if (this.roster[i] != null && this.roster[i].getLastPaymentDate() != null) {
                paymentMades[i] = this.roster[i].getLastPaymentDate();
            }
        }

        insertionSort(paymentMades);

        for (int i = 0; i < this.roster.length; i++) {
            if (paymentMades[i] != null) {
                resultant += resultant + this.roster[i].toString() +"\n";
            }
        }

        bubbleNullToEnd();
        resultant += "* end of roster **\n";
        return resultant;
    }

    /**
     * Used as an accessory to our insertion sort method.
     * Bubbles multiple null students to the end.
     */
    private void bubbleNullToEnd() {
        Student[] oldRoster = this.roster;
        Student[] newRoster = new Student[oldRoster.length];
        int indexCounter = 0;

        for (int i = 0; i < oldRoster.length; i++) {
            if (oldRoster[i] != null) {
                newRoster[indexCounter] = oldRoster[i];
                indexCounter++;
            }
        }
        this.roster = newRoster;
    }

    /**
     * This method provides a safe version of invoking compareTo for our insertionSort
     * by defaulting the case of null.
     * @param first generic object to compare
     * @param second generic object to compare
     * @param <T> generic.
     * @return an integer to determine if first or second is greater such as lexicographic order.
     */
    private <T extends Comparable<T>> int safeNullCompareTo(T first, T second) {
        if (first == null) {
            return -1;
        } else if (second == null) {
            return 1;
        } else {
            return first.compareTo(second);
        }

    }

    /**
     * Sorting algorithm to sort Roster collection based on a generic object.
     * Used to sort by name and last payment date.
     * @param arr array which the sorting is applied
     */
    private <T extends Comparable<T>> void insertionSort(T[] arr) {
        for (int i = 0; i < this.roster.length; i++) {
            T key = arr[i];

            Student keyPointer = this.roster[i];

            int j = i - 1;
            while (j >= 0 && arr[j] != null && safeNullCompareTo(arr[j], key) > 0) {

                arr[j + 1] = arr[j];
                this.roster[j + 1] = this.roster[j];
                j = j - 1;

            }

            arr[j + 1] = key;
            this.roster[j + 1] = keyPointer;
        }
    }

    /**
     * Calculates all the tuition for all students in the roster collection.
     */
    public void calculateTuition() {
        for (int i = 0; i < this.size; i++) {
            this.roster[i].tuitionDue();
        }
    }
}