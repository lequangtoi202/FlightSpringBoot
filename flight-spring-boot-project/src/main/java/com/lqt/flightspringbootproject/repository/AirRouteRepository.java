package com.lqt.flightspringbootproject.repository;

import com.lqt.flightspringbootproject.model.AirRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirRouteRepository extends JpaRepository<AirRoute, Long> {
    @Query("select a from AirRoute a where a.is_activated = true and  a.is_deleted = false")
    List<AirRoute> findAllByActivated();
}
