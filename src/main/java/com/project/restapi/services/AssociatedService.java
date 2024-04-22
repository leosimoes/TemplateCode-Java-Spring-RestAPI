package com.project.restapi.services;

import com.project.restapi.dtos.AssociatedPayload;

import java.util.List;

public interface AssociatedService {

    AssociatedPayload create(AssociatedPayload associatedPayload);

    AssociatedPayload getByName(String name);

    List<AssociatedPayload> getAll();

    AssociatedPayload update(AssociatedPayload associatedPayload);

    void deleteByName(String name);

}
