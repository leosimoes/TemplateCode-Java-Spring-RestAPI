package com.project.restapi.repositories;

import com.project.restapi.entities.MainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MainRepository extends JpaRepository<MainEntity, UUID> {

    Boolean existsByNameEqualsIgnoreCase(String name);
    Optional<MainEntity> findByNameEqualsIgnoreCase(String name);

}
