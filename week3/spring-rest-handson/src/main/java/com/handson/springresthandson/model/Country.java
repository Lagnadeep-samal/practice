package com.handson.springresthandson.model;

/**
 * Simple POJO representing a Country.
 * Used across Step 2 (XML config), Step 4 (Country REST service) and
 * Step 5 (get country by code).
 */
public class Country {

    private String code;
    private String name;
    private String capital;
    private long population;

    public Country() {
    }

    public Country(String code, String name, String capital, long population) {
        this.code = code;
        this.name = name;
        this.capital = capital;
        this.population = population;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    @Override
    public String toString() {
        return "Country{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", capital='" + capital + '\'' +
                ", population=" + population +
                '}';
    }
}
