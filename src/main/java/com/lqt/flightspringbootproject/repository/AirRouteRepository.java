package com.lqt.flightspringbootproject.repository;

import com.lqt.flightspringbootproject.model.AirRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirRouteRepository extends JpaRepository<AirRoute, Long> {
    @Query("select a from AirRoute a where a.is_activated = true and  a.is_deleted = false")
    List<AirRoute> findAllByActivated();

    @Query(value="SELECT a.air_route_id FROM flight.air_route as a \n" +
            "where a.departure_id = \n" +
            "\t(select ap.airport_id from flight.airport as ap where ap.location like %:depart%)\n" +
            "    and a.destination_id = \n" +
            "    (select ap.airport_id from flight.airport as ap where ap.location like %:des%)", nativeQuery = true)
    Long getAirRouteIdByName(@Param("depart") String depart,
                             @Param("des")String des);
}
