package com.example.jpahandson.service;

import com.example.jpahandson.entity.Country;
import com.example.jpahandson.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public Country addCountry(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public Optional<Country> findByCountryCode(String code) {
        return countryRepository.findByCountryCode(code);
    }

    @Override
    public List<Country> findAll() {
        return countryRepository.findAll();
    }
}