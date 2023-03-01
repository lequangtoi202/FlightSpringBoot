package com.lqt.flightspringbootproject.repository;

import com.lqt.flightspringbootproject.model.Airport;
import com.lqt.flightspringbootproject.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("select t from Ticket t where t.is_activated = true and  t.is_deleted = false")
    List<Ticket> findAllByActivated();
}
