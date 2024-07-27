package com.embrapii.CanopyNavigator.services;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;
import org.geotools.api.referencing.crs.CoordinateReferenceSystem;
import org.springframework.stereotype.Service;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.CRS;
import org.locationtech.jts.geom.*;
import org.geotools.data.DefaultTransaction;
import org.geotools.api.data.Transaction;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.api.data.SimpleFeatureStore;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.api.data.SimpleFeatureSource;
import org.geotools.api.feature.simple.SimpleFeature;
import org.geotools.api.feature.simple.SimpleFeatureType;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CsvToShapefileService {

    public MultipartFile convertCsvToShapefile(MultipartFile file) throws Exception {
        CoordinateReferenceSystem crs = null;
        try {
            crs = CRS.decode("EPSG:4326");
            // Your conversion logic here
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Use SimpleFeatureTypeBuilder to create a FeatureType with the CRS
        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        builder.setName("Location");
        builder.setCRS(crs); // Set the CRS

        // Add attributes
        builder.add("the_geom", Point.class); // Geometry attribute
        builder.add("name", String.class);    // String attribute
        builder.add("number", Integer.class); // Integer attribute

        // Build the type
        final SimpleFeatureType TYPE = builder.buildFeatureType();

        // A list to collect features as we create them
        List<SimpleFeature> features = new ArrayList<>();

        // GeometryFactory will be used to create the geometry attribute of each feature
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);

        try (InputStream inputStream = file.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            // First line of the data file is the header
            String line = reader.readLine();
            System.out.println("Header: " + line);

            for (line = reader.readLine(); line != null; line = reader.readLine()) {
                if (line.trim().length() > 0) { // skip blank lines
                    String[] tokens = line.split("\\,");

                    double latitude = Double.parseDouble(tokens[0]);
                    double longitude = Double.parseDouble(tokens[1]);
                    String name = tokens[2].trim();
                    int number = Integer.parseInt(tokens[3].trim());

                    // Longitude (= x coord) first!
                    Point point = geometryFactory.createPoint(new Coordinate(longitude, latitude));

                    featureBuilder.add(point);
                    featureBuilder.add(name);
                    featureBuilder.add(number);
                    SimpleFeature feature = featureBuilder.buildFeature(null);
                    features.add(feature);
                }
            }
        }

        // Get an output file name and create the new shapefile
        File newFile = getNewShapeFile(file);

        ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();
        Map<String, Serializable> params = new HashMap<>();
        params.put("url", newFile.toURI().toURL());
        params.put("create spatial index", Boolean.TRUE);

        ShapefileDataStore newDataStore = (ShapefileDataStore) dataStoreFactory.createNewDataStore(params);

        // TYPE is used as a template to describe the file contents
        newDataStore.createSchema(TYPE);

        // Write the features to the shapefile
        Transaction transaction = new DefaultTransaction("create");
        String typeName = newDataStore.getTypeNames()[0];
        SimpleFeatureSource featureSource = newDataStore.getFeatureSource(typeName);
        SimpleFeatureType SHAPE_TYPE = featureSource.getSchema();
        System.out.println("SHAPE:" + SHAPE_TYPE);

        if (featureSource instanceof SimpleFeatureStore) {
            SimpleFeatureStore featureStore = (SimpleFeatureStore) featureSource;
            SimpleFeatureCollection collection = new ListFeatureCollection(TYPE, features);
            featureStore.setTransaction(transaction);
            try {
                featureStore.addFeatures(collection);
                transaction.commit();
            } catch (Exception problem) {
                problem.printStackTrace();
                transaction.rollback();
            } finally {
                transaction.close();
            }
        } else {
            System.out.println(typeName + " does not support read/write access");
        }

        // Convert the File to MultipartFile
        return convertFileToMultipartFile(newFile);
    }

    private static final String OUTPUT_DIRECTORY = "shapefiles"; // Configure this path

    public File getNewShapeFile(MultipartFile csvFile) throws IOException {
        // Ensure the output directory exists
        Path outputPath = Paths.get(OUTPUT_DIRECTORY);
        if (!Files.exists(outputPath)) {
            Files.createDirectories(outputPath);
        }

        // Generate the new file name for the shapefile
        String originalFilename = csvFile.getOriginalFilename();
        if (originalFilename == null) {
            throw new IllegalArgumentException("CSV file has no original filename");
        }
        String newFileName = originalFilename.substring(0, originalFilename.lastIndexOf('.')) + ".shp";
        Path newFilePath = outputPath.resolve(newFileName);

        // Create the file, or delete it if it already exists
        File newFile = newFilePath.toFile();
        if (newFile.exists()) {
            if (!newFile.delete()) {
                throw new IOException("Failed to delete existing file: " + newFile.getAbsolutePath());
            }
        }
        if (!newFile.createNewFile()) {
            throw new IOException("Failed to create new file: " + newFile.getAbsolutePath());
        }

        return newFile;
    }


    private static final String UPLOAD_DIR = "uploads"; // Define your upload directory

    public File saveUploadedFile(MultipartFile file) throws IOException {
        // Create the upload directory if it doesn't exist
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Define the path and file name for the saved file
        String fileName = file.getOriginalFilename();
        File savedFile = new File(uploadDir, fileName);

        // Save the file to the defined directory
        try (OutputStream os = new FileOutputStream(savedFile)) {
            os.write(file.getBytes());
        }

        return savedFile;
    }

    public MultipartFile convertFileToMultipartFile(File file) throws IOException {
        try (FileInputStream input = new FileInputStream(file)) {
            return new MockMultipartFile(file.getName(), file.getName(), Files.probeContentType(file.toPath()), input);
        }
    }
}
