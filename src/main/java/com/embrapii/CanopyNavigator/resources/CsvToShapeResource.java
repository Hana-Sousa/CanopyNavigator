package com.embrapii.CanopyNavigator.resources;

import com.embrapii.CanopyNavigator.services.CsvToShapefileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/files")
public class CsvToShapeResource {

    @Autowired
    private CsvToShapefileService csvToShapefileService;

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            MultipartFile newFile = csvToShapefileService.convertCsvToShapefile(file);
            File savedFile = csvToShapefileService.saveUploadedFile(newFile);
            return "File saved to: " + savedFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return "File upload failed: " + e.getMessage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
