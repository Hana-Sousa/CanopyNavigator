package com.embrapii.CanopyNavigator.utils;

import org.geotools.api.data.FileDataStore;
import org.geotools.api.data.FileDataStoreFinder;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.api.style.Style;
import org.geotools.styling.SLD;
import org.geotools.swing.JMapFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.logging.Logger;

@Component
public class MapUtil {
    private static final Logger LOGGER = Logger.getLogger(MapUtil.class.getName());

    @Autowired
    private ShapefileCreationUtil shapefileCreationUtil;

    public void displayMapFromDatabase() {
        try {
            File shapefile = shapefileCreationUtil.createShapefileFromDatabase();
            FileDataStore store = FileDataStoreFinder.getDataStore(shapefile);
            if (store == null) {
                LOGGER.severe("Shapefile could not be loaded.");
                return;
            }

            Layer layer = createLayerFromStore(store);
            if (layer == null) {
                LOGGER.severe("No valid layer found to display on the map.");
                return;
            }

            displayMapWithLayer(layer);
        } catch (Exception e) {
            LOGGER.severe("Error displaying map: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Layer createLayerFromStore(FileDataStore store) throws Exception {
        Style style = SLD.createSimpleStyle(store.getSchema());
        Layer layer = new org.geotools.map.FeatureLayer(store.getFeatureSource(), style);
        return layer;
    }

    private void displayMapWithLayer(Layer layer) {
        MapContent map = new MapContent();
        map.setTitle("Database Map");
        map.addLayer(layer);
        JMapFrame.showMap(map);
    }
}
