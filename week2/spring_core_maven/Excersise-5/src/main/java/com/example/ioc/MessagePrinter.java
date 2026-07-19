package com.example.ioc;

public class MessagePrinter {

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public void init() {
        System.out.println("[MessagePrinter] Initialized by the container.");
    }

    public void print() {
        System.out.println("[MessagePrinter] " + message);
    }

    public void destroy() {
        System.out.println("[MessagePrinter] Being destroyed by the container.");
    }
}