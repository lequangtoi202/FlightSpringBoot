package com.lqt.flightspringbootproject.repository;

import com.lqt.flightspringbootproject.model.TicketPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketPriceRepository extends JpaRepository<TicketPrice, Long> {
    @Query("select t from TicketPrice t where t.is_activated = true and  t.is_deleted = false")
    List<TicketPrice> findAllByActivated();
}
