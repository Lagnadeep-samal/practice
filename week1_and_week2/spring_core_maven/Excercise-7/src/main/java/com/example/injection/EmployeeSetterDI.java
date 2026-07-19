package com.example.injection;

public class EmployeeSetterDI {

    private String name;
    private Address address;

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void printDetails() {
        System.out.println("[Setter DI]      " + name + " lives at " + address);
    }
}