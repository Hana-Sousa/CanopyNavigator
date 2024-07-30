package com.embrapii.CanopyNavigator.services;


import com.embrapii.CanopyNavigator.moldels.GeoSpatialFeature;
import com.embrapii.CanopyNavigator.repositories.GeoSpatialFeatureRepository;
import org.geotools.api.feature.simple.SimpleFeatureType;
import org.geotools.api.referencing.crs.CoordinateReferenceSystem;

import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.referencing.CRS;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class GeoSpatialFeatureService {

    @Autowired
    private GeoSpatialFeatureRepository geospatialFeatureRepository;

    private final SimpleFeatureType featureType;

    public GeoSpatialFeatureService() throws Exception {
        this.featureType = defineSchema();
    }

    private SimpleFeatureType defineSchema() throws Exception {
        CoordinateReferenceSystem crs = CRS.decode("EPSG:4326");
        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        builder.setName("City");
        builder.setCRS(crs);
        builder.add("Name", String.class);
        builder.add("Population", Integer.class);
        builder.add("Location", Point.class);
        return builder.buildFeatureType();
    }

    public GeoSpatialFeature createFeature(String name, int population, double latitude, double longitude) {
        GeometryFactory geometryFactory = new GeometryFactory();
        Point location = geometryFactory.createPoint(new Coordinate(longitude, latitude));
        GeoSpatialFeature feature = new GeoSpatialFeature(name, population, latitude, longitude);
        feature.setLocation(location);
        return geospatialFeatureRepository.save(feature);
    }

    public List<GeoSpatialFeature> getAllFeatures() {
        return geospatialFeatureRepository.findAll();
    }

    public Optional<GeoSpatialFeature> getFeatureById(Long id) {
        return geospatialFeatureRepository.findById(id);
    }

    public GeoSpatialFeature updateFeature(Long id, String name, int population, double latitude, double longitude) {
        Optional<GeoSpatialFeature> featureOpt = getFeatureById(id);
        if (featureOpt.isPresent()) {
            GeoSpatialFeature feature = featureOpt.get();
            feature.setName(name);
            feature.setPopulation(population);
            GeometryFactory geometryFactory = new GeometryFactory();
            Point location = geometryFactory.createPoint(new Coordinate(longitude, latitude));
            feature.setLocation(location);
            return geospatialFeatureRepository.save(feature);
        }
        return null;
    }

    public boolean deleteFeature(Long id) {
        if (geospatialFeatureRepository.existsById(id)) {
            geospatialFeatureRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
