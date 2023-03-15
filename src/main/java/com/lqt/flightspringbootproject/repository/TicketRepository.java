package com.lqt.flightspringbootproject.repository;

import com.lqt.flightspringbootproject.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("select count(t) from Ticket t where t.flight.id = :id and t.seat_class = :seat_class")
    int countTicketBySeat_class(@Param("id") Long id,@Param("seat_class") int seat_class);

    @Query("select t from Ticket t where t.user.id = :id order by t.sold_time desc")
    List<Ticket> getAllByBuyerId(@Param("id")Long id);


}
