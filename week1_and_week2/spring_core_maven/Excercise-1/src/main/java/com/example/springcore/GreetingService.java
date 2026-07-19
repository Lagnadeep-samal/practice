package com.example.springcore;

public class GreetingService {

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void greet() {
        System.out.println("GreetingService says: " + message);
    }
}