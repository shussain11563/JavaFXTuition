package com.example.tuitionfx;

/**
 * Profile allows a student to define their own profile under their name and major.
 * @author Sharia Hussain, David Lam
 */
public class Profile {
    private String name;
    private Major major;

    /**
     * Constructs and initializes a profile for use.
     * Used for students background information.
     * @param name the name of the student.
     * @param major the major of the student.
     */
    public Profile(String name, Major major) {
        this.name = name;
        this.major = major;
    }

    /**
     * Retrieves the name of the student.
     * @return the name of the student.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Retrieves the major of the student
     * @return the major of the student
     */
    public String getMajor() {
        return this.major.toString();
    }
}