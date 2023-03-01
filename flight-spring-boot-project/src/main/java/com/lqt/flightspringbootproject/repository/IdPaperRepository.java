package com.lqt.flightspringbootproject.repository;

import com.lqt.flightspringbootproject.model.IdPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdPaperRepository extends JpaRepository<IdPaper, Long> {
}
