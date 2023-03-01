package com.lqt.flightspringbootproject.repository;

import com.lqt.flightspringbootproject.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {
    @Query("select a from Airport a where a.is_activated = true and  a.is_deleted = false")
    List<Airport> findAllByActivated();
}
