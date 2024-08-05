package com.embrapii.CanopyNavigator.utils;

import org.geotools.api.data.FileDataStore;
import org.geotools.api.data.FileDataStoreFinder;
import org.geotools.api.data.SimpleFeatureSource;
import org.geotools.api.feature.simple.SimpleFeatureType;
import org.geotools.api.filter.FilterFactory;
import org.geotools.api.style.StyleFactory;
import org.geotools.api.style.Style;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.styling.SLD;
import org.geotools.swing.JMapFrame;
import org.geotools.swing.JMapPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.Color;
import java.io.File;
import java.util.logging.Logger;

@Component
public class MapUtil {
    private static final Logger LOGGER = org.geotools.util.logging.Logging.getLogger(MapUtil.class);

    @Autowired
    private ShapefileCreationUtil shapefileCreationUtil;

    static StyleFactory styleFactory = CommonFactoryFinder.getStyleFactory(null);
    static FilterFactory filterFactory = CommonFactoryFinder.getFilterFactory(null);

    public void displayMapFromDatabase() {
        try {
            // Create the shapefile
            File shapefile = shapefileCreationUtil.createShapefileFromDatabase();
            if (shapefile == null || !shapefile.exists()) {
                LOGGER.severe("Shapefile could not be created or does not exist.");
                return;
            }

            LOGGER.info("Shapefile path: " + shapefile.getAbsolutePath());
            LOGGER.info("Checking if shapefile exists: " + shapefile.exists());

            // Load the shapefile
            FileDataStore store = FileDataStoreFinder.getDataStore(shapefile);
            if (store == null) {
                LOGGER.severe("Shapefile could not be loaded.");
                return;
            }

            LOGGER.info("Shapefile loaded successfully.");

            SimpleFeatureSource featureSource = store.getFeatureSource();

            // Check if featureSource is not null
            if (featureSource == null) {
                LOGGER.severe("FeatureSource could not be retrieved from the shapefile.");
                return;
            }

            LOGGER.info("FeatureSource retrieved successfully.");

            // Create a map content and add our shapefile to it
            MapContent map = new MapContent();
            map.setTitle("Database Map");

            // Use the same style as Quickstart to ensure the map displays correctly
            Style style = SLD.createSimpleStyle(featureSource.getSchema());

            // Check if style is created successfully
            if (style == null) {
                LOGGER.severe("Style could not be created.");
                return;
            }

            LOGGER.info("Style created successfully.");

            // Add a layer with the feature source and style to the map content
            Layer layer = new FeatureLayer(featureSource, style);
            map.addLayer(layer);

            LOGGER.info("Layer added to map.");

            // Display the map
            JMapFrame mapFrame = new JMapFrame(map);
            mapFrame.enableToolBar(true);
            mapFrame.enableStatusBar(true);

            // Set the map pane to use a custom renderer if necessary
            JMapPane mapPane = mapFrame.getMapPane();
            mapPane.setBackground(Color.WHITE);

            LOGGER.info("Map frame created. Making it visible...");
            mapFrame.setSize(800, 600);
            mapFrame.setVisible(true);

            LOGGER.info("Map rendering initiated. Waiting for map frame to be visible...");
        } catch (Exception e) {
            LOGGER.severe("Error displaying map: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
