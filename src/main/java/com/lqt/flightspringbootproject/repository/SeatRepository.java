package com.lqt.flightspringbootproject.repository;

import com.lqt.flightspringbootproject.model.Airport;
import com.lqt.flightspringbootproject.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    @Query("select s from Seat s where s.is_activated = true and  s.is_deleted = false")
    List<Seat> findAllByActivated();

    @Query("select s from Seat s where s.is_activated = true and  s.is_deleted = false and s.S_class = :seatClass and s.airplane.Id = :id")
    List<Seat> getAllBySeatClassAndAirplane(@Param("seatClass") int seatClass, @Param("id")Long id);

    @Query(value = "select seat_id from (select @row\\:=@row+1 AS stt, t.*\n" +
            "FROM seat t, (SELECT @row\\:=0) r\n" +
            "where s_class= :seat_class) as alias where stt= :id and airplane_id= :airplane_id", nativeQuery = true)
    Long getSeatBySeatClass(@Param("seat_class") int seat_class, @Param("id")Long stt, @Param("airplane_id")Long airplane_id);
}
