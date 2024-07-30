package com.embrapii.CanopyNavigator.utils;

import com.embrapii.CanopyNavigator.models.GeoSpatialFeature;
import com.embrapii.CanopyNavigator.repositories.GeoSpatialFeatureRepository;

import org.geotools.api.feature.simple.SimpleFeature;
import org.geotools.api.feature.simple.SimpleFeatureType;
import org.geotools.data.DataUtilities;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.styling.SLD;
import org.geotools.api.style.Style;
import org.geotools.swing.JMapFrame;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
public class MapUtil {

    private static final Logger LOGGER = Logger.getLogger(MapUtil.class.getName());

    @Autowired
    private GeoSpatialFeatureRepository geoSpatialFeatureRepository;

    public void createMapFromDatabase() throws Exception {
        // Retrieve data from the database
        List<GeoSpatialFeature> features = geoSpatialFeatureRepository.findAll();

        // Define a SimpleFeatureType
        final SimpleFeatureType TYPE = DataUtilities.createType(
                "Location",
                "the_geom:Point:srid=4326," + // geometry
                        "name:String," +   // name
                        "population:Integer" // population
        );

        // Create features from the database data
        List<SimpleFeature> featureList = new java.util.ArrayList<>();
        GeometryFactory geometryFactory = new GeometryFactory();
        for (GeoSpatialFeature geoSpatialFeature : features) {
            double latitude = geoSpatialFeature.getLatitude();
            double longitude = geoSpatialFeature.getLongitude();

            if (latitude < -90 || latitude > 90 || longitude < -180 || longitude > 180) {
                LOGGER.warning("Invalid coordinates for feature: " + geoSpatialFeature.getName());
                continue;
            }

            Point point = geometryFactory.createPoint(new Coordinate(longitude, latitude));
            SimpleFeature feature = SimpleFeatureBuilder.build(TYPE, new Object[]{
                    point, geoSpatialFeature.getName(), geoSpatialFeature.getPopulation()
            }, null);
            featureList.add(feature);
        }

        if (featureList.isEmpty()) {
            LOGGER.warning("No valid features found to display on the map.");
            return;
        }

        // Create a feature collection
        SimpleFeatureCollection featureCollection = DataUtilities.collection(featureList);

        // Create a map content and add our features to it
        MapContent map = new MapContent();
        map.setTitle("Database Map");

        Style style = SLD.createSimpleStyle(TYPE);
        Layer layer = new FeatureLayer(featureCollection, style);
        map.addLayer(layer);

        try {
            // Now display the map
            JMapFrame.showMap(map);
        } catch (Exception e) {
            LOGGER.severe("Error displaying map: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
