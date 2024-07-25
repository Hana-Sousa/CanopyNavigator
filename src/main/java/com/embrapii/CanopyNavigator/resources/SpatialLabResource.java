package com.embrapii.CanopyNavigator.resources;

import com.embrapii.CanopyNavigator.entities.SpatialLab;
import com.embrapii.CanopyNavigator.services.SpatialLabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/spatiallabs")

//End-point to access the users
public class SpatialLabResource{

    @Autowired
    private SpatialLabService service;

    @GetMapping
    public ResponseEntity<List<SpatialLab>> findAll() {
        List<SpatialLab> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SpatialLab> findById(@PathVariable Long id){
        SpatialLab obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

}