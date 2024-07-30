package com.embrapii.CanopyNavigator.utils;

import com.embrapii.CanopyNavigator.services.DatabaseToShapefileService;
import org.geotools.api.data.FileDataStore;
import org.geotools.api.data.FileDataStoreFinder;

import org.geotools.api.data.SimpleFeatureSource;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.styling.SLD;
import org.geotools.api.style.Style;
import org.geotools.swing.JMapFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.logging.Logger;

@Component
public class MapUtil {

    private static final Logger LOGGER = Logger.getLogger(MapUtil.class.getName());

    @Autowired
    private DatabaseToShapefileService databaseToShapefileService;

    public void createMapFromDatabase() throws Exception {
        // Create a shapefile from the database
        MultipartFile shapefileMultipart = databaseToShapefileService.createShapefileFromDatabase();

        // Convert MultipartFile to File
        File shapefile = new File(System.getProperty("java.io.tmpdir") + "/" + shapefileMultipart.getOriginalFilename());
        shapefileMultipart.transferTo(shapefile);

        // Load the shapefile and display the map
        FileDataStore store = FileDataStoreFinder.getDataStore(shapefile);
        SimpleFeatureSource featureSource = store.getFeatureSource();

        // Log the feature source details
        LOGGER.info("Feature source schema: " + featureSource.getSchema());

        // Create a map content and add our shapefile to it
        MapContent map = new MapContent();
        map.setTitle("Database Map");

        // Define the style for the points
        Style style = SLD.createPointStyle("circle", java.awt.Color.RED, java.awt.Color.BLACK, 1.0f, 10.0f);

        Layer layer = new FeatureLayer(featureSource, style);
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
