package com.embrapii.CanopyNavigator.services;

import com.embrapii.CanopyNavigator.models.SpatialLab;
import com.embrapii.CanopyNavigator.repositories.SpatialLabRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpatialLabService {

    @Autowired
    private SpatialLabRepository repository;
    public List<SpatialLab> findAll(){
        return repository.findAll();
    }

    public SpatialLab findById(Long id){
        Optional<SpatialLab> obj = repository.findById(id);
        return obj.get();
    }

}
