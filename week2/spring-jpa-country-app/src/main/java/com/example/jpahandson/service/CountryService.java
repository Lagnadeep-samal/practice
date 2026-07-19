package com.example.jpahandson.service;

import com.example.jpahandson.entity.Country;
import java.util.List;
import java.util.Optional;

public interface CountryService {
    Country addCountry(Country country);
    Optional<Country> findByCountryCode(String code);
    List<Country> findAll();
}