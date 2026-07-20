package com.handson.springresthandson.controller;

import com.handson.springresthandson.model.Country;
import com.handson.springresthandson.service.CountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Step 4: REST - Country Web Service.
 * Step 5: REST - Get country based on country code.
 * <p>
 * Endpoints:
 *   GET    /countries            -> list all countries
 *   GET    /countries/{code}     -> get one country by its code (Step 5)
 *   POST   /countries            -> create/replace a country
 *   DELETE /countries/{code}     -> remove a country
 */
@RestController
@RequestMapping("/countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public List<Country> getAllCountries() {
        return countryService.findAll();
    }

    // Step 5: REST - Get country based on country code
    @GetMapping("/{code}")
    public ResponseEntity<Country> getCountryByCode(@PathVariable String code) {
        return countryService.findByCode(code)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Country> createOrUpdateCountry(@RequestBody Country country) {
        Country saved = countryService.save(country);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteCountry(@PathVariable String code) {
        boolean removed = countryService.deleteByCode(code);
        return removed ? ResponseEntity.noContent().build()
                        : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
