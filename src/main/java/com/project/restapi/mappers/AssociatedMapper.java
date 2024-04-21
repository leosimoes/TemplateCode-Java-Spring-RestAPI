package com.project.restapi.mappers;

import com.project.restapi.entities.AssociatedEntity;
import com.project.restapi.records.AssociatedPayload;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AssociatedMapper {

    AssociatedMapper INSTANCE = Mappers.getMapper(AssociatedMapper.class);

    AssociatedPayload toAssociatedPayload(AssociatedEntity entity);

    List<AssociatedPayload> toAssociatedPayloadList(List<AssociatedEntity> entities);

    @Mapping(target = "id", ignore = true)
    AssociatedEntity toAssociatedEntity(AssociatedPayload payload);

    List<AssociatedEntity> toAssociatedEntityList(List<AssociatedPayload> payloads);

}