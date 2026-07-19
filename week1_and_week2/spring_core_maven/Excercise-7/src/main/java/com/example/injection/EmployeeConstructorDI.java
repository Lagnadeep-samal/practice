package com.example.injection;

public class EmployeeConstructorDI {

    private final String name;
    private final Address address;

    public EmployeeConstructorDI(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public void printDetails() {
        System.out.println("[Constructor DI] " + name + " lives at " + address);
    }
}