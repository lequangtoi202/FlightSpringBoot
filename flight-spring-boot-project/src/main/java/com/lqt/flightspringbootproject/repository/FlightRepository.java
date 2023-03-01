package com.lqt.flightspringbootproject.repository;

import com.lqt.flightspringbootproject.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    @Query("select f from Flight f where f.is_activated = true and f.is_deleted = false")
    List<Flight> getAllByActivated();
}
