package com.lqt.flightspringbootproject.repository;

import com.lqt.flightspringbootproject.dto.FlightResponse;
import com.lqt.flightspringbootproject.dto.FlightSeatResponse;
import com.lqt.flightspringbootproject.dto.StatisticResponse;
import com.lqt.flightspringbootproject.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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

    @Query(value = "SELECT ar.*,count(t.ticket_id) , (count(t.ticket_id) * tp.f_price )\n" +
            "FROM flight.air_route ar, flight.flight f, flight.ticket t, flight.ticket_price tp\n" +
            "where ar.air_route_id = f.air_route_id and f.flight_id = t.flight_id \n" +
            "and f.ticket_price_id = tp.ticket_price_id and t.seat_class = :seatClass and year(t.sold_time)=:year and month(t.sold_time) = :month\n" +
            "group by ar.air_route_id, t.flight_id ", nativeQuery = true)
    List<String> statistic(@Param("seatClass") int seatClass, @Param("year")int year, @Param("month")int month);
}
