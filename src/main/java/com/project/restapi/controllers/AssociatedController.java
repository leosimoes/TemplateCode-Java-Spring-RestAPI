package com.project.restapi.controllers;

import com.project.restapi.dtos.AssociatedPayload;
import com.project.restapi.services.AssociatedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/associateds")
public class AssociatedController {

    private final AssociatedService associatedService;

    @Autowired
    public AssociatedController(AssociatedService associatedService){
        this.associatedService = associatedService;
    }

    @PostMapping
    public ResponseEntity<AssociatedPayload> create(@RequestBody AssociatedPayload associatedPayload){
        AssociatedPayload associatedPayloadCreated = this.associatedService.create(associatedPayload);
        return ResponseEntity.status(HttpStatus.CREATED).body(associatedPayloadCreated);
    }

    @GetMapping("/{name}")
    public ResponseEntity<AssociatedPayload> getByName(@PathVariable("name") String name){
        AssociatedPayload associatedPayload = this.associatedService.getByName(name);
        return ResponseEntity.ok(associatedPayload);
    }

    @GetMapping
    public ResponseEntity<List<AssociatedPayload>> getAll(){
        List<AssociatedPayload> associatedPayloadList = this.associatedService.getAll();
        return ResponseEntity.ok(associatedPayloadList);
    }

    @PutMapping
    public ResponseEntity<AssociatedPayload> update(@RequestBody AssociatedPayload associatedPayload){
        AssociatedPayload associatedPayloadUpdated = this.associatedService.update(associatedPayload);
        return ResponseEntity.ok(associatedPayloadUpdated);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<AssociatedPayload> delete(@PathVariable("name") String name){
        this.associatedService.deleteByName(name);
        return ResponseEntity.ok().build();
    }

}
