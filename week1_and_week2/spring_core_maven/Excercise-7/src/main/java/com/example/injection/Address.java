package com.example.injection;

public class Address {

    private String city;
    private String zipCode;

    public void setCity(String city) {
        this.city = city;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return city + " - " + zipCode;
    }
}