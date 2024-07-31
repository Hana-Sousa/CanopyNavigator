package com.embrapii.CanopyNavigator.utils;

import com.embrapii.CanopyNavigator.models.GeoSpatialFeature;
import com.embrapii.CanopyNavigator.repositories.GeoSpatialFeatureRepository;
import org.geotools.api.data.*;
import org.geotools.api.feature.simple.SimpleFeature;
import org.geotools.api.feature.simple.SimpleFeatureType;
import org.geotools.data.DataUtilities;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Component
public class ShapefileCreationUtil {

    private static final Logger LOGGER = Logger.getLogger(ShapefileCreationUtil.class.getName());

    @Autowired
    private GeoSpatialFeatureRepository geoSpatialFeatureRepository;

    public File createShapefileFromDatabase() throws Exception {
        // Retrieve data from the database
        List<GeoSpatialFeature> features = geoSpatialFeatureRepository.findAll();
        LOGGER.info("Retrieved " + features.size() + " features from the database.");

        // Define a SimpleFeatureType
        final SimpleFeatureType TYPE = DataUtilities.createType(
                "Location",
                "the_geom:Point:srid=4326," + // geometry
                        "name:String," +   // name
                        "population:Integer" // population
        );

        // Create a temporary shapefile
        File newFile = File.createTempFile("geospatial_features", ".shp");
        LOGGER.info("Created temporary shapefile: " + newFile.getAbsolutePath());
        newFile.deleteOnExit();

        Map<String, Serializable> params = new HashMap<>();
        params.put("url", newFile.toURI().toURL());

        DataStoreFactorySpi factory = new ShapefileDataStoreFactory();
        ShapefileDataStore newDataStore = (ShapefileDataStore) factory.createNewDataStore(params);
        newDataStore.createSchema(TYPE);
        newDataStore.setCharset(StandardCharsets.UTF_8);

        Transaction transaction = new DefaultTransaction("create");

        String typeName = newDataStore.getTypeNames()[0];
        FeatureWriter<SimpleFeatureType, SimpleFeature> writer = newDataStore.getFeatureWriterAppend(typeName, transaction);
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();

        for (GeoSpatialFeature geoSpatialFeature : features) {
            double latitude = geoSpatialFeature.getLatitude();
            double longitude = geoSpatialFeature.getLongitude();

            if (latitude < -90 || latitude > 90 || longitude < -180 || longitude > 180) {
                LOGGER.warning("Invalid coordinates for feature: " + geoSpatialFeature.getName());
                continue;
            }

            Point point = geometryFactory.createPoint(new Coordinate(longitude, latitude));
            SimpleFeature feature = writer.next();
            feature.setAttribute("the_geom", point);
            feature.setAttribute("name", geoSpatialFeature.getName());
            feature.setAttribute("population", geoSpatialFeature.getPopulation());
            writer.write();
        }

        transaction.commit();
        transaction.close();
        writer.close();
        LOGGER.info("Shapefile created successfully.");

        return newFile;
    }
}
