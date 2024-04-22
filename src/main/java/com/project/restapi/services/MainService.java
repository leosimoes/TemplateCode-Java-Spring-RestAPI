package com.project.restapi.services;

import com.project.restapi.dtos.MainPayload;
import java.util.List;

public interface MainService {

    MainPayload create(MainPayload mainPayload);

    MainPayload getByName(String name);

    List<MainPayload> getAll();

    MainPayload update(MainPayload mainPayload);

    void deleteByName(String name);

}
