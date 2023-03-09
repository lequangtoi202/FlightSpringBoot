package com.lqt.flightspringbootproject.service;

import com.lqt.flightspringbootproject.dto.IdPaperDto;
import com.lqt.flightspringbootproject.model.Customer;
import com.lqt.flightspringbootproject.model.IdPaper;

import java.util.List;


public interface IdPaperService {
    IdPaper save(IdPaperDto idPaperDto);

    List<IdPaper> findAll();

    IdPaper update(IdPaperDto idPaperDto);

    IdPaperDto getById(Long id);

    IdPaper findById(Long id);

    IdPaperDto getIdPaperByCustomerId(Long customerId);
}
