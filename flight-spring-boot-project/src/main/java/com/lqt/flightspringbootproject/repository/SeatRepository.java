package com.lqt.flightspringbootproject.repository;

import com.lqt.flightspringbootproject.model.Airport;
import com.lqt.flightspringbootproject.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    @Query("select s from Seat s where s.is_activated = true and  s.is_deleted = false")
    List<Seat> findAllByActivated();
}
