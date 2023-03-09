package com.lqt.flightspringbootproject.service.impl;

import com.lqt.flightspringbootproject.dto.IdPaperDto;
import com.lqt.flightspringbootproject.model.Customer;
import com.lqt.flightspringbootproject.model.IdPaper;
import com.lqt.flightspringbootproject.repository.IdPaperRepository;
import com.lqt.flightspringbootproject.service.IdPaperService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IdPaperServiceImpl implements IdPaperService {
    private final IdPaperRepository idPaperRepository;

    public IdPaperServiceImpl(IdPaperRepository idPaperRepository) {
        this.idPaperRepository = idPaperRepository;
    }

    @Override
    public IdPaper save(IdPaperDto idPaperDto) {
        try{
            IdPaper idPaper = new IdPaper();
            idPaper.setCode(idPaperDto.getCode());
            idPaper.setCustomer(idPaperDto.getCustomer());
            idPaper.setPaper_type(idPaperDto.getPaper_type());
            return idPaperRepository.save(idPaper);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<IdPaper> findAll() {
        return idPaperRepository.findAll();
    }

    @Override
    public IdPaper update(IdPaperDto idPaperDto) {
        try{
            IdPaper idPaper = idPaperRepository.getById(idPaperDto.getId());
            idPaper.setCode(idPaperDto.getCode());
            idPaper.setCustomer(idPaperDto.getCustomer());
            idPaper.setPaper_type(idPaperDto.getPaper_type());
            return idPaperRepository.save(idPaper);
        }catch (Exception e){
        }
        return null;
    }

    @Override
    public IdPaperDto getById(Long id) {
        IdPaper idPaper = idPaperRepository.getById(id);
        return mapToDto(idPaper);
    }


    @Override
    public IdPaper findById(Long id) {
        return idPaperRepository.findById(id).get();
    }

    @Override
    public IdPaperDto getIdPaperByCustomerId(Long customerId) {
        return mapToDto(idPaperRepository.getIdPaperByCustomer(customerId));
    }


    public IdPaperDto mapToDto(IdPaper idPaper){
        IdPaperDto idPaperDto = new IdPaperDto();
        idPaperDto.setId(idPaper.getId());
        idPaperDto.setCode(idPaper.getCode());
        idPaperDto.setPaper_type(idPaper.getPaper_type());
        idPaperDto.setCustomer(idPaper.getCustomer());
        return idPaperDto;
    }

    private List<IdPaperDto> mapListToDto(List<IdPaper> idPapers){
        List<IdPaperDto> idPaperDtoList = new ArrayList<>();
        for (IdPaper idPaper : idPapers){
            IdPaperDto idPaperDto = mapToDto(idPaper);

            idPaperDtoList.add(idPaperDto);
        }

        return idPaperDtoList;
    }
}
