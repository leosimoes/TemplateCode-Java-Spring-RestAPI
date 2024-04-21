package com.project.restapi.mappers;

import com.project.restapi.entities.AssociatedEntity;
import com.project.restapi.entities.MainEntity;
import com.project.restapi.records.MainPayload;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MainMapper {

    MainMapper INSTANCE = Mappers.getMapper(MainMapper.class);

    MainPayload toMainPayload(MainEntity entity);

    List<MainPayload> toMainPayloadList(List<MainEntity> entities);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "associated", expression = "java(MainMapper.INSTANCE.toAssociated(payload))")
    MainEntity toMainEntity(MainPayload payload);

    List<MainEntity> toMainEntityList(List<MainPayload> payloads);

    default AssociatedEntity toAssociated(MainPayload payload) {
        return new AssociatedEntity(null, payload.associated());
    }

    default String map(AssociatedEntity associatedEntity) {
        return associatedEntity != null ? associatedEntity.getName() : null;
    }

}