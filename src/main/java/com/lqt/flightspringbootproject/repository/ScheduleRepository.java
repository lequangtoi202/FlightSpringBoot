package com.lqt.flightspringbootproject.repository;

import com.lqt.flightspringbootproject.model.Airport;
import com.lqt.flightspringbootproject.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("select s from Schedule s where s.is_activated = true and  s.is_deleted = false")
    List<Schedule> findAllByActivated();

    @Query("select s from Schedule s where s.is_activated = true and  s.is_deleted = false and s.flight.id = :id")
    Schedule findByFlight(@Param("id") Long id);
}
