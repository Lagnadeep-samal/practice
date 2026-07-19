package com.example.jpahandson.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "country")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "country_code", unique = true, nullable = false, length = 3)
    private String countryCode;

    @Column(name = "country_name", nullable = false)
    private String countryName;

    @Column(name = "capital")
    private String capital;

    @Column(name = "population")
    private Long population;

    public Country() {
    }

    public Country(String countryCode, String countryName, String capital, Long population) {
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.capital = capital;
        this.population = population;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCountryCode() { return countryCode; }
    public void setCountryCode(String countryCode) { this.countryCode = countryCode; }

    public String getCountryName() { return countryName; }
    public void setCountryName(String countryName) { this.countryName = countryName; }

    public String getCapital() { return capital; }
    public void setCapital(String capital) { this.capital = capital; }

    public Long getPopulation() { return population; }
    public void setPopulation(Long population) { this.population = population; }

    @Override
    public String toString() {
        return "Country{id=" + id + ", countryCode='" + countryCode + "', countryName='" + countryName +
                "', capital='" + capital + "', population=" + population + "}";
    }
}