package com.embrapii.CanopyNavigator.services;

import com.embrapii.CanopyNavigator.entities.GeoSpatialFeature;
import org.geotools.api.referencing.FactoryException;

import java.util.List;

public interface GeoDataManagementService {
    GeoSpatialFeature createFeature(String name, int population, double latitude, double longitude);
    GeoSpatialFeature updateFeature(Long id, String name, int population, double latitude, double longitude);
    GeoSpatialFeature getFeatureById(Long id);
    List<GeoSpatialFeature> getAllFeatures();
    String getFeaturesGeoJson() throws FactoryException;
}