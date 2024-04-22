package com.project.restapi.services;

import com.project.restapi.entities.AssociatedEntity;
import com.project.restapi.entities.MainEntity;
import com.project.restapi.mappers.MainMapper;
import com.project.restapi.dtos.MainPayload;
import com.project.restapi.repositories.AssociatedRepository;
import com.project.restapi.repositories.MainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MainServiceImpl implements MainService{

    private final MainRepository mainRepository;

    private final AssociatedRepository associatedRepository;

    private final MainMapper mainMapper;

    @Autowired
    public MainServiceImpl(MainRepository mainRepository, AssociatedRepository associatedRepository){
        this.mainRepository = mainRepository;
        this.associatedRepository = associatedRepository;
        this.mainMapper = MainMapper.INSTANCE;
    }

    public MainPayload create(MainPayload mainPayload) {
        String name = mainPayload.getName();
        if(this.mainRepository.existsByNameEqualsIgnoreCase(name)){
            throw new RuntimeException("Exception in mainService.create() - Name Already Registered");
        }

        MainEntity mainEntity = this.mainMapper.toMainEntity(mainPayload);

        if(mainPayload.getName() == null){
            mainEntity.setAssociated(null);
        }else{
            mainEntity.setAssociated(this.getAssociatedEntityByName(mainPayload.getAssociated()));
        }

        MainEntity mainEntitySaved = this.mainRepository.save(mainEntity);

        return this.mainMapper.toMainPayload(mainEntitySaved);
    }

    public MainPayload getByName(String name) {
        Optional<MainEntity> mainEntityOpt = this.mainRepository.findByNameEqualsIgnoreCase(name);
        if(mainEntityOpt.isEmpty()){
            throw new RuntimeException("Exception in mainService.getByName() - MainEntity Not Found");
        }

        return this.mainMapper.toMainPayload(mainEntityOpt.get());
    }

    public List<MainPayload> getAll() {
        List<MainEntity> mainEntityList = this.mainRepository.findAll();

        return this.mainMapper.toMainPayloadList(mainEntityList);
    }

    public MainPayload update(MainPayload mainPayload) {
        String name = mainPayload.getName();
        Optional<MainEntity> mainEntityOpt = this.mainRepository.findByNameEqualsIgnoreCase(name);
        if(mainEntityOpt.isEmpty()){
            throw new RuntimeException("Exception in mainService.update() - MainEntity Not Found");
        }

        MainEntity mainEntity = this.mainMapper.toMainEntity(mainPayload);
        mainEntity.setId(mainEntityOpt.get().getId());
        MainEntity mainEntitySaved = this.mainRepository.save(mainEntity);

        return this.mainMapper.toMainPayload(mainEntitySaved);
    }

    public void deleteByName(String name) {
        Optional<MainEntity> mainEntityOpt = this.mainRepository.findByNameEqualsIgnoreCase(name);
        if(mainEntityOpt.isEmpty()){
            throw new RuntimeException("Exception in mainService.deleteByName() - MainEntity Not Found");
        }
        this.mainRepository.delete(mainEntityOpt.get());
    }

    private AssociatedEntity getAssociatedEntityByName(String name){
        Optional<AssociatedEntity> associatedEntityOpt = this.associatedRepository.findByNameEqualsIgnoreCase(name);

        return associatedEntityOpt.orElse(null);
    }
}
