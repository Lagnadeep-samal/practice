package com.example.jpahandson.repository;

import com.example.jpahandson.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {

    Optional<Country> findByCountryCode(String countryCode);

    List<Country> findByCountryNameContainingIgnoreCase(String namePart);

    List<Country> findByPopulationGreaterThan(Long population);

    @Query("SELECT c FROM Country c WHERE c.capital = :capital")
    List<Country> findByCapitalHQL(@Param("capital") String capital);

    @Query(value = "SELECT * FROM country WHERE population > :minPopulation ORDER BY population DESC",
           nativeQuery = true)
    List<Country> findLargeCountriesNative(@Param("minPopulation") Long minPopulation);
}