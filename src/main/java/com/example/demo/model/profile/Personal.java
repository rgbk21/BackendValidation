package com.example.demo.model.profile;

public class Personal {

    private String firstName;
    private String lastName;
    private String middleName;
    private String dateOfBirth;
    private String suffix;
    private String ssnTin;

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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getSsnTin() {
        return ssnTin;
    }

    public void setSsnTin(String ssnTin) {
        this.ssnTin = ssnTin;
    }
}
