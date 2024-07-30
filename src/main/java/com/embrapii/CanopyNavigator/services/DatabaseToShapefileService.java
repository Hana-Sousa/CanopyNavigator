package com.embrapii.CanopyNavigator.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class DatabaseToShapefileService {

    @Autowired
    private DatabaseToCsvService databaseToCsvService;

    @Autowired
    private CsvToShapefileService csvToShapefileService;

    public MultipartFile createShapefileFromDatabase() throws Exception {
        File csvFile = databaseToCsvService.createCsvFromDatabase();
        MultipartFile multipartFile = csvToShapefileService.convertFileToMultipartFile(csvFile);
        return csvToShapefileService.convertCsvToShapefile(multipartFile);
    }
}
