package com.lqt.flightspringbootproject.service.impl;

import com.lqt.flightspringbootproject.dto.TypeDto;
import com.lqt.flightspringbootproject.model.Type;
import com.lqt.flightspringbootproject.repository.TypeRepository;
import com.lqt.flightspringbootproject.service.TypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    private final TypeRepository typeRepository;

    public TypeServiceImpl(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Override
    public Type save(TypeDto typeDto) {
        try{
            Type typeSave = new Type(typeDto.getModel(),
                    typeDto.getGeneration(),
                    typeDto.getNum_o_seat());
            return typeRepository.save(typeSave);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Type> findAll() {
        return typeRepository.findAll();
    }

    @Override
    public List<Type> findAllByActivated() {
        return typeRepository.findAllByActivated();
    }

    @Override
    public Type findById(Long id) {
        return typeRepository.findById(id).get();
    }

    @Override
    public Type update(Type type) {
        Type typeUpdate = null;
        try {
            typeUpdate = typeRepository.findById(type.getId()).get();
            typeUpdate.setModel(type.getModel());
            typeUpdate.setGeneration(type.getGeneration());
            typeUpdate.setNum_o_seat(type.getNum_o_seat());

        }catch(Exception e){
            e.printStackTrace();
        }
        return typeRepository.save(typeUpdate);
    }

    @Override
    public void deleteById(Long id) {
        Type type = typeRepository.getById(id);
        type.set_deleted(true);
        type.set_activated(false);
        typeRepository.save(type);
    }

    @Override
    public void enabledById(Long id) {
        Type type = typeRepository.getById(id);
        type.set_deleted(false);
        type.set_activated(true);
        typeRepository.save(type);
    }
}
