package com.example.tourguideapp;

public class User {

    private String ID;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;

    public User() {

    }

    public User(String email, String firstName, String lastName, String phone) {

        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    /**
     * Getters and Setters
     */

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
