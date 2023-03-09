package com.lqt.flightspringbootproject.repository;

import com.lqt.flightspringbootproject.model.Customer;
import com.lqt.flightspringbootproject.model.IdPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IdPaperRepository extends JpaRepository<IdPaper, Long> {
    @Query(value = "select * from id_paper as i where customer_id =:id", nativeQuery = true)
    IdPaper getIdPaperByCustomer(Long id);
}
