package com.project.restapi.services;

import com.project.restapi.entities.AssociatedEntity;
import com.project.restapi.mappers.AssociatedMapper;
import com.project.restapi.dtos.AssociatedPayload;
import com.project.restapi.repositories.AssociatedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssociatedServiceImpl implements AssociatedService{

    private final AssociatedRepository associatedRepository;
    private final AssociatedMapper associatedMapper;

    @Autowired
    public AssociatedServiceImpl(AssociatedRepository associatedRepository){
        this.associatedRepository = associatedRepository;
        this.associatedMapper = AssociatedMapper.INSTANCE;
    }

    public AssociatedPayload create(AssociatedPayload associatedPayload) {
        String name = associatedPayload.getName();
        if(this.associatedRepository.existsByNameEqualsIgnoreCase(name)){
            throw new RuntimeException("Exception in associatedService.create() - Name Already Registered");
        }

        AssociatedEntity associatedEntity = this.associatedMapper.toAssociatedEntity(associatedPayload);
        AssociatedEntity associatedEntitySaved = this.associatedRepository.save(associatedEntity);

        return this.associatedMapper.toAssociatedPayload(associatedEntitySaved);
    }

    public AssociatedPayload getByName(String name) {
        Optional<AssociatedEntity> associatedEntityOpt = this.associatedRepository.findByNameEqualsIgnoreCase(name);
        if(associatedEntityOpt.isEmpty()){
            throw new RuntimeException("Exception in associatedService.getByName() - AssociatedEntity Not Found");
        }

        return this.associatedMapper.toAssociatedPayload(associatedEntityOpt.get());
    }

    public List<AssociatedPayload> getAll() {
        List<AssociatedEntity> associatedEntityList = this.associatedRepository.findAll();

        return this.associatedMapper.toAssociatedPayloadList(associatedEntityList);
    }

    public AssociatedPayload update(AssociatedPayload associatedPayload) {
        String name = associatedPayload.getName();
        Optional<AssociatedEntity> associatedEntityOpt = this.associatedRepository.findByNameEqualsIgnoreCase(name);
        if(associatedEntityOpt.isEmpty()){
            throw new RuntimeException("Exception in associatedService.update() - AssociatedEntity Not Found");
        }
        AssociatedEntity associatedEntitySaved = this.associatedRepository.save(associatedEntityOpt.get());

        return this.associatedMapper.toAssociatedPayload(associatedEntitySaved);
    }

    public void deleteByName(String name) {
        Optional<AssociatedEntity> associatedEntityOpt = this.associatedRepository.findByNameEqualsIgnoreCase(name);
        if(associatedEntityOpt.isEmpty()){
            throw new RuntimeException("Exception in associatedService.deleteByName() - AssociatedEntity Not Found");
        }
        this.associatedRepository.delete(associatedEntityOpt.get());
    }
}
