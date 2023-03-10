package com.lqt.flightspringbootproject.repository;

import com.lqt.flightspringbootproject.dto.FlightResponse;
import com.lqt.flightspringbootproject.dto.FlightSeatResponse;
import com.lqt.flightspringbootproject.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    @Query("select f from Flight f where f.is_activated = true and f.is_deleted = false")
    List<Flight> getAllByActivated();

    @Query(value="select new com.lqt.flightspringbootproject.dto.FlightResponse(f.id, f.airplane, f.airRoute, f.ticketPrice, f.is_activated, f.is_deleted, s.date, s.timeStart, s.timeEnd) " +
            "from Flight f JOIN Schedule s on s.flight.id = f.id  where f.airRoute.id = :id and s.date = :date")
    List<FlightResponse> getFlightByAirRouteId(@Param("id") Long id, @Param("date")Date date);

    @Query("select new com.lqt.flightspringbootproject.dto.FlightResponse(f.id, f.airplane, f.airRoute, f.ticketPrice, f.is_activated, f.is_deleted, s.date, s.timeStart, s.timeEnd)" +
            "from Flight f join Schedule s on s.flight.id = f.id where f.id = :id and f.is_activated = true")
    FlightResponse getFlightByIdAndIsActivated(@Param("id") Long id);

    @Query("select new com.lqt.flightspringbootproject.dto.FlightSeatResponse(f.id, f.ticketPrice, s.num_o_F_seat, s.num_o_S_seat)" +
            "from Flight f join Schedule s on s.flight.id = f.id where f.id = :id and f.is_activated = true")
    FlightSeatResponse getFlightSeat(@Param("id") Long id);

    @Query("select f.airplane.Id from Flight f where f.is_deleted = false and f.is_activated = true and f.id = :id")
    Long getAirplaneIdByFlight(@Param("id")Long id);
}
