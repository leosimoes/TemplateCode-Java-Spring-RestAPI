package com.project.restapi.controllers;

import com.project.restapi.dtos.MainPayload;
import com.project.restapi.services.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/mains")
public class MainController {

    private final MainService mainService;

    @Autowired
    public MainController(MainService mainService){
        this.mainService = mainService;
    }

    @PostMapping
    public ResponseEntity<MainPayload> create(@RequestBody MainPayload mainPayload){
        MainPayload mainPayloadCreated = this.mainService.create(mainPayload);
        return ResponseEntity.status(HttpStatus.CREATED).body(mainPayloadCreated);
    }

    @GetMapping("/{name}")
    public ResponseEntity<MainPayload> getByName(@PathVariable("name") String name){
        MainPayload mainPayload = this.mainService.getByName(name);
        return ResponseEntity.ok(mainPayload);
    }

    @GetMapping
    public ResponseEntity<List<MainPayload>> getAll(){
        List<MainPayload> mainPayloadList = this.mainService.getAll();
        return ResponseEntity.ok(mainPayloadList);
    }

    @PutMapping
    public ResponseEntity<MainPayload> update(@RequestBody MainPayload mainPayload){
        MainPayload mainPayloadUpdated = this.mainService.update(mainPayload);
        return ResponseEntity.ok(mainPayloadUpdated);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<MainPayload> delete(@PathVariable("name") String name){
        this.mainService.deleteByName(name);
        return ResponseEntity.ok().build();
    }

}
