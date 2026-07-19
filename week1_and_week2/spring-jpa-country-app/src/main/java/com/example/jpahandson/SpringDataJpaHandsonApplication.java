package com.example.jpahandson;

import com.example.jpahandson.entity.Country;
import com.example.jpahandson.repository.CountryRepository;
import com.example.jpahandson.service.CountryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringDataJpaHandsonApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpaHandsonApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(CountryService countryService, CountryRepository countryRepository) {
        return args -> {
            countryService.addCountry(new Country("IND", "India", "New Delhi", 1400000000L));
            countryService.addCountry(new Country("USA", "United States", "Washington D.C.", 331000000L));
            countryService.addCountry(new Country("JPN", "Japan", "Tokyo", 125000000L));

            System.out.println("\n--- Find a country based on country code ---");
            countryService.findByCountryCode("IND").ifPresent(System.out::println);

            System.out.println("\n--- Query Methods feature ---");
            countryRepository.findByPopulationGreaterThan(200000000L).forEach(System.out::println);

            System.out.println("\n--- HQL Query ---");
            countryRepository.findByCapitalHQL("Tokyo").forEach(System.out::println);

            System.out.println("\n--- Native SQL Query ---");
            countryRepository.findLargeCountriesNative(100000000L).forEach(System.out::println);

            System.out.println("\n--- All Countries ---");
            countryService.findAll().forEach(System.out::println);
        };
    }
}