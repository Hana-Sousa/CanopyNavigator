package com.embrapii.CanopyNavigator.services;

import com.embrapii.CanopyNavigator.models.GeoSpatialFeature;
import com.embrapii.CanopyNavigator.repositories.GeoSpatialFeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class DatabaseToCsvService {

    @Autowired
    private GeoSpatialFeatureRepository geoSpatialFeatureRepository;

    public File createCsvFromDatabase() throws IOException {
        List<GeoSpatialFeature> features = geoSpatialFeatureRepository.findAll();

        File csvFile = File.createTempFile("geospatial_features", ".csv");
        csvFile.deleteOnExit();

        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.write("latitude,longitude,name,population\n");
            for (GeoSpatialFeature feature : features) {
                writer.write(String.format("%f,%f,%s,%d\n",
                        feature.getLatitude(),
                        feature.getLongitude(),
                        feature.getName(),
                        feature.getPopulation()));
            }
        }

        return csvFile;
    }
}
