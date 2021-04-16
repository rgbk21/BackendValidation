package com.example.demo.model.profile;

import com.example.demo.util.ErrorInfo;

import java.util.List;

public class ValidateUserResponse {

    private Address address;
    private Personal personal;
    private List<ErrorInfo> errorInfoList;

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

    public List<ErrorInfo> getErrorInfoList() {
        return errorInfoList;
    }

    public void setErrorInfoList(List<ErrorInfo> errorInfoList) {
        this.errorInfoList = errorInfoList;
    }
}
