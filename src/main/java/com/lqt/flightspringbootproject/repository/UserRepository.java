package com.lqt.flightspringbootproject.repository;


import com.lqt.flightspringbootproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User getUserByUsername(String username);
}
