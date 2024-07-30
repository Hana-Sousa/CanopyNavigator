package com.embrapii.CanopyNavigator.config;

import com.embrapii.CanopyNavigator.utils.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@Profile("dev")
public class DevConfig implements CommandLineRunner {
    @Autowired
    private MapUtil mapUtil;

    @Override
    public void run(String... args) throws Exception {
        mapUtil.createMapFromDatabase();

//
//
//    /**
//     * This example reads data for point locations and associated attributes from a comma separated text
//     * (CSV) file and exports them as a new shapefile. It illustrates how to build a feature type.
//     *
//     * <p>Note: to keep things simple in the code below the input file should not have additional spaces
//     * or tabs between fields.
//     */
//    class Csv2Shape {
//
//        public static void main(String[] args) throws Exception {
//            // Set cross-platform look & feel for compatability
//            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
//
//            File file = JFileDataStoreChooser.showOpenFile("csv", null);
//            if (file == null) {
//                return;
//            }
//
//            // Define the CRS
//            CoordinateReferenceSystem crs = CRS.decode("EPSG:4326"); // WGS84
//
//            // Use SimpleFeatureTypeBuilder to create a FeatureType with the CRS
//            SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
//            builder.setName("Location");
//            builder.setCRS(crs); // Set the CRS
//
//            // Add attributes
//            builder.add("the_geom", Point.class); // Geometry attribute
//            builder.add("name", String.class);    // String attribute
//            builder.add("number", Integer.class); // Integer attribute
//
//            // Build the type
//            final SimpleFeatureType TYPE = builder.buildFeatureType();
//
//            System.out.println("TYPE:" + TYPE);
//
//            // A list to collect features as we create them
//            List<SimpleFeature> features = new ArrayList<>();
//
//            // GeometryFactory will be used to create the geometry attribute of each feature
//            GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
//
//            SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);
//
//            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
//                // First line of the data file is the header
//                String line = reader.readLine();
//                System.out.println("Header: " + line);
//
//                for (line = reader.readLine(); line != null; line = reader.readLine()) {
//                    if (line.trim().length() > 0) { // skip blank lines
//                        String[] tokens = line.split("\\,");
//
//                        double latitude = Double.parseDouble(tokens[0]);
//                        double longitude = Double.parseDouble(tokens[1]);
//                        String name = tokens[2].trim();
//                        int number = Integer.parseInt(tokens[3].trim());
//
//                        // Longitude (= x coord) first!
//                        Point point = geometryFactory.createPoint(new Coordinate(longitude, latitude));
//
//                        featureBuilder.add(point);
//                        featureBuilder.add(name);
//                        featureBuilder.add(number);
//                        SimpleFeature feature = featureBuilder.buildFeature(null);
//                        features.add(feature);
//                    }
//                }
//            }
//
//            // Get an output file name and create the new shapefile
//            File newFile = getNewShapeFile(file);
//
//            ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();
//
//            Map<String, Serializable> params = new HashMap<>();
//            params.put("url", newFile.toURI().toURL());
//            params.put("create spatial index", Boolean.TRUE);
//
//            ShapefileDataStore newDataStore = (ShapefileDataStore) dataStoreFactory.createNewDataStore(params);
//
//            // TYPE is used as a template to describe the file contents
//            newDataStore.createSchema(TYPE);
//
//            // Write the features to the shapefile
//            Transaction transaction = new DefaultTransaction("create");
//
//            String typeName = newDataStore.getTypeNames()[0];
//            SimpleFeatureSource featureSource = newDataStore.getFeatureSource(typeName);
//            SimpleFeatureType SHAPE_TYPE = featureSource.getSchema();
//
//            System.out.println("SHAPE:" + SHAPE_TYPE);
//
//            if (featureSource instanceof SimpleFeatureStore) {
//                SimpleFeatureStore featureStore = (SimpleFeatureStore) featureSource;
//
//                // SimpleFeatureStore has a method to add features from a SimpleFeatureCollection object,
//                // so we use the ListFeatureCollection class to wrap our list of features.
//                SimpleFeatureCollection collection = new ListFeatureCollection(TYPE, features);
//                featureStore.setTransaction(transaction);
//                try {
//                    featureStore.addFeatures(collection);
//                    transaction.commit();
//                } catch (Exception problem) {
//                    problem.printStackTrace();
//                    transaction.rollback();
//                } finally {
//                    transaction.close();
//                }
//                System.exit(0); // success!
//            } else {
//                System.out.println(typeName + " does not support read/write access");
//                System.exit(1);
//            }
//        }
//
//        /**
//         * Prompt the user for the name and path to use for the output shapefile
//         *
//         * @param csvFile the input csv file used to create a default shapefile name
//         * @return name and path for the shapefile as a new File object
//         */
//        private static File getNewShapeFile(File csvFile) {
//            String path = csvFile.getAbsolutePath();
//            String newPath = path.substring(0, path.length() - 4) + ".shp";
//
//            JFileDataStoreChooser chooser = new JFileDataStoreChooser("shp");
//            chooser.setDialogTitle("Save shapefile");
//            chooser.setSelectedFile(new File(newPath));
//
//            int returnVal = chooser.showSaveDialog(null);
//
//            if (returnVal != JFileDataStoreChooser.APPROVE_OPTION) {
//                // the user cancelled the dialog
//                System.exit(0);
//            }
//
//            File newFile = chooser.getSelectedFile();
//            if (newFile.equals(csvFile)) {
//                System.out.println("Error: cannot replace " + csvFile);
//                System.exit(0);
//            }
//
//            return newFile;
//        }
//    }
//
    }
}