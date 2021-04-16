package com.example.demo.model.profile;

public class ValidateUserRequest {

    private Address address;
    private Personal personal;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Personal getPersonal() {
        return personal;
    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }
}
