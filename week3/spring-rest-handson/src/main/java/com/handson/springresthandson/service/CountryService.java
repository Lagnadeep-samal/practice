package com.handson.springresthandson.service;

import com.handson.springresthandson.model.Country;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Backs the Country REST service (Steps 4 & 5).
 * <p>
 * The initial data comes straight from the "countryList" bean defined in
 * spring-config.xml (Step 2), then is copied into an in-memory,
 * thread-safe list so the REST layer can also create/update/delete.
 */
@Service
public class CountryService {

    private final List<Country> countries;

    public CountryService(@Qualifier("countryList") List<Country> xmlDefinedCountries) {
        this.countries = new CopyOnWriteArrayList<>(xmlDefinedCountries);
    }

    public List<Country> findAll() {
        return new ArrayList<>(countries);
    }

    public Optional<Country> findByCode(String code) {
        return countries.stream()
                .filter(c -> c.getCode().equalsIgnoreCase(code))
                .findFirst();
    }

    public Country save(Country country) {
        countries.removeIf(c -> c.getCode().equalsIgnoreCase(country.getCode()));
        countries.add(country);
        return country;
    }

    public boolean deleteByCode(String code) {
        return countries.removeIf(c -> c.getCode().equalsIgnoreCase(code));
    }
}
