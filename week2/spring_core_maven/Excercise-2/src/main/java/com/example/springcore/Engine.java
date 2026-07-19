package com.example.springcore;

public class Engine {

    private String type;
    private int horsepower;

    public void setType(String type) {
        this.type = type;
    }

    public void setHorsepower(int horsepower) {
        this.horsepower = horsepower;
    }

    public String describe() {
        return type + " engine (" + horsepower + " HP)";
    }
}