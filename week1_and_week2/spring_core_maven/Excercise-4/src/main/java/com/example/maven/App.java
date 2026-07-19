package com.example.maven;

public class App {

    public static void main(String[] args) {
        System.out.println("Hello from a Maven-configured project!");

        Calculator calculator = new Calculator();
        int sum = calculator.add(4, 7);
        System.out.println("4 + 7 = " + sum);
    }
}