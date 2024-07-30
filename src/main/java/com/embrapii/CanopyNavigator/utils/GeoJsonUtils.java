package com.embrapii.CanopyNavigator.utils;


import com.embrapii.CanopyNavigator.moldels.GeoSpatialFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.geotools.api.feature.simple.SimpleFeature;
import org.geotools.api.feature.simple.SimpleFeatureType;
import org.geotools.api.referencing.FactoryException;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geojson.feature.FeatureJSON;
import org.geotools.referencing.CRS;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import java.io.StringWriter;
import java.util.List;

public class GeoJsonUtils {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final GeometryFactory geometryFactory = new GeometryFactory();

    public static String toGeoJson(List<GeoSpatialFeature> features) throws FactoryException {
        SimpleFeatureCollection featureCollection = convertToSimpleFeatureCollection(features);
        FeatureJSON featureJSON = new FeatureJSON();
        StringWriter writer = new StringWriter();
        try {
            featureJSON.writeFeatureCollection(featureCollection, writer);
        } catch (Exception e) {
            throw new RuntimeException("Error converting to GeoJSON", e);
        }
        return writer.toString();
    }

    private static SimpleFeatureCollection convertToSimpleFeatureCollection(List<GeoSpatialFeature> features) throws FactoryException {
        SimpleFeatureTypeBuilder typeBuilder = new SimpleFeatureTypeBuilder();
        typeBuilder.setName("GeoSpatialFeature");
        typeBuilder.setCRS(CRS.decode("EPSG:4326"));
        typeBuilder.add("name", String.class);
        typeBuilder.add("population", Integer.class);
        typeBuilder.add("location", Point.class);
        SimpleFeatureType featureType = typeBuilder.buildFeatureType();

        DefaultFeatureCollection featureCollection = new DefaultFeatureCollection();
        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(featureType);

        for (GeoSpatialFeature geoSpatialFeature : features) {
            Point location = geometryFactory.createPoint(new Coordinate(
                    geoSpatialFeature.getLongitude(), geoSpatialFeature.getLatitude()
            ));
            featureBuilder.add(geoSpatialFeature.getName());
            featureBuilder.add(geoSpatialFeature.getPopulation());
            featureBuilder.add(location);
            SimpleFeature feature = featureBuilder.buildFeature(null);
            featureCollection.add(feature);
        }

        return featureCollection;
    }
}
