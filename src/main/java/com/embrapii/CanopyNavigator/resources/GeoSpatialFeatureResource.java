package com.embrapii.CanopyNavigator.resources;


import com.embrapii.CanopyNavigator.entities.GeoSpatialFeature;
import com.embrapii.CanopyNavigator.services.GeoSpatialFeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/features")
public class GeoSpatialFeatureResource {

    @Autowired
    private GeoSpatialFeatureService geoSpatialFeatureService;

    @PostMapping
    public ResponseEntity<GeoSpatialFeature> createFeature(@RequestParam String name,
                                                           @RequestParam int population,
                                                           @RequestParam double latitude,
                                                           @RequestParam double longitude) {
        GeoSpatialFeature feature = geoSpatialFeatureService.createFeature(name, population, latitude, longitude);
        return new ResponseEntity<>(feature, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<GeoSpatialFeature>> getAllFeatures() {
        List<GeoSpatialFeature> features = geoSpatialFeatureService.getAllFeatures();
        return ResponseEntity.ok(features);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeoSpatialFeature> getFeatureById(@PathVariable Long id) {
        Optional<GeoSpatialFeature> feature = geoSpatialFeatureService.getFeatureById(id);
        return feature.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeoSpatialFeature> updateFeature(@PathVariable Long id,
                                                           @RequestParam String name,
                                                           @RequestParam int population,
                                                           @RequestParam double latitude,
                                                           @RequestParam double longitude) {
        GeoSpatialFeature feature = geoSpatialFeatureService.updateFeature(id, name, population, latitude, longitude);
        if (feature != null) {
            return ResponseEntity.ok(feature);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeature(@PathVariable Long id) {
        boolean deleted = geoSpatialFeatureService.deleteFeature(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
