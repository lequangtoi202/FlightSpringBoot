package com.lqt.flightspringbootproject.repository;

import com.lqt.flightspringbootproject.model.Airplane;
import com.lqt.flightspringbootproject.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirplaneRepository extends JpaRepository<Airplane, Long> {
    @Query("select a from Airplane a where a.is_activated = true and  a.is_deleted = false")
    List<Airplane> findAllByActivated();

}
