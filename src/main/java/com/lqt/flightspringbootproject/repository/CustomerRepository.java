package com.lqt.flightspringbootproject.repository;

import com.lqt.flightspringbootproject.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer getByUser(Long id);

    @Query(value = "select * from customer as c inner join id_paper as i on i.customer_id = c.customer_id where i.code = :code", nativeQuery = true)
    Customer getCustomerByCode(@Param("code") String code);
}
