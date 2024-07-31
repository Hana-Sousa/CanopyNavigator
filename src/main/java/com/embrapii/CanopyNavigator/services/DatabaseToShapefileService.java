package com.embrapii.CanopyNavigator.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

@Service
public class DatabaseToShapefileService {

    private final DatabaseToCsvService databaseToCsvService;
    private final CsvToShapefileService csvToShapefileService;

    @Autowired
    public DatabaseToShapefileService(DatabaseToCsvService databaseToCsvService, CsvToShapefileService csvToShapefileService) {
        this.databaseToCsvService = databaseToCsvService;
        this.csvToShapefileService = csvToShapefileService;
    }

    public MultipartFile createShapefileFromDatabase() throws Exception {
        try {
            File csvFile = databaseToCsvService.createCsvFromDatabase();
            MultipartFile multipartFile = csvToShapefileService.convertFileToMultipartFile(csvFile);
            return csvToShapefileService.convertCsvToShapefile(multipartFile);
        } catch (IOException e) {
            // Handle IOException specifically
            throw new RuntimeException("Error creating shapefile from database", e);
        }
    }
}
