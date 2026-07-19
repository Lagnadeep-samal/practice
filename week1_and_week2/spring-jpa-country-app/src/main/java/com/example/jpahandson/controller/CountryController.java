package com.example.jpahandson.controller;

import com.example.jpahandson.entity.Country;
import com.example.jpahandson.repository.CountryRepository;
import com.example.jpahandson.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    private final CountryService countryService;
    private final CountryRepository countryRepository;

    @Autowired
    public CountryController(CountryService countryService, CountryRepository countryRepository) {
        this.countryService = countryService;
        this.countryRepository = countryRepository;
    }

    @GetMapping
    public ResponseEntity<List<Country>> getAllCountries() {
        return ResponseEntity.ok(countryService.findAll());
    }

    @GetMapping("/code/{countryCode}")
    public ResponseEntity<Country> getByCountryCode(@PathVariable String countryCode) {
        return countryService.findByCountryCode(countryCode)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Country> addCountry(@RequestBody Country country) {
        Country saved = countryService.addCountry(country);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable Long id, @RequestBody Country updatedCountry) {
        return countryRepository.findById(id)
                .map(existing -> {
                    existing.setCountryCode(updatedCountry.getCountryCode());
                    existing.setCountryName(updatedCountry.getCountryName());
                    existing.setCapital(updatedCountry.getCapital());
                    existing.setPopulation(updatedCountry.getPopulation());
                    Country saved = countryRepository.save(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long id) {
        if (!countryRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        countryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/population/{minPopulation}")
    public ResponseEntity<List<Country>> getByPopulationGreaterThan(@PathVariable Long minPopulation) {
        return ResponseEntity.ok(countryRepository.findByPopulationGreaterThan(minPopulation));
    }

    @GetMapping("/capital/{capital}")
    public ResponseEntity<List<Country>> getByCapitalHQL(@PathVariable String capital) {
        return ResponseEntity.ok(countryRepository.findByCapitalHQL(capital));
    }

    @GetMapping("/large/{minPopulation}")
    public ResponseEntity<List<Country>> getLargeCountriesNative(@PathVariable Long minPopulation) {
        return ResponseEntity.ok(countryRepository.findLargeCountriesNative(minPopulation));
    }
}