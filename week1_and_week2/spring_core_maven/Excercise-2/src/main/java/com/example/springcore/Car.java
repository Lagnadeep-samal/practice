package com.example.springcore;

public class Car {

    private final Engine engine;
    private String model;

    public Car(Engine engine) {
        this.engine = engine;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void drive() {
        System.out.println(model + " is driving with a " + engine.describe());
    }
}