package com.embrapii.CanopyNavigator.services.Impl;


import com.embrapii.CanopyNavigator.entities.GeoSpatialFeature;
import com.embrapii.CanopyNavigator.repositories.GeoSpatialFeatureRepository;
import com.embrapii.CanopyNavigator.services.GeoDataManagementService;
import com.embrapii.CanopyNavigator.utils.GeoJsonUtils;
import org.geotools.api.referencing.FactoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

@Service
public class GeoDataManagementServiceImpl implements GeoDataManagementService {

    @Autowired
    private GeoSpatialFeatureRepository geoSpatialFeatureRepository;

    @Override
    public GeoSpatialFeature createFeature(String name, int population, double latitude, double longitude) {
        GeoSpatialFeature feature = new GeoSpatialFeature();
        feature.setName(name);
        feature.setPopulation(population);
        feature.setLatitude(latitude);
        feature.setLongitude(longitude);
        return geoSpatialFeatureRepository.save(feature);
    }

    @Override
    public GeoSpatialFeature updateFeature(Long id, String name, int population, double latitude, double longitude) {
        GeoSpatialFeature feature = geoSpatialFeatureRepository.findById(id).orElse(null);
        if (feature != null) {
            feature.setName(name);
            feature.setPopulation(population);
            feature.setLatitude(latitude);
            feature.setLongitude(longitude);
            return geoSpatialFeatureRepository.save(feature);
        }
        return null;
    }

    @Override
    public GeoSpatialFeature getFeatureById(Long id) {
        return geoSpatialFeatureRepository.findById(id).orElse(null);
    }

    @Override
    public List<GeoSpatialFeature> getAllFeatures() {
        return geoSpatialFeatureRepository.findAll();
    }

    @Override
    public String getFeaturesGeoJson() throws FactoryException {
        List<GeoSpatialFeature> features = getAllFeatures();
        return GeoJsonUtils.toGeoJson(features);
    }
}
