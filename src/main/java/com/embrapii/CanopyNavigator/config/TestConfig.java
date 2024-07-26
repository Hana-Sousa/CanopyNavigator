package com.embrapii.CanopyNavigator.config;

import com.embrapii.CanopyNavigator.entities.SpatialLab;
import com.embrapii.CanopyNavigator.repositories.SpatialLabRepository;

import org.locationtech.jts.geom.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
    @Autowired
    private SpatialLabRepository spatialLabRepository;

    @Override
    public void run(String... args) throws Exception {

        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

        // Point example
        Point point = geometryFactory.createPoint(new Coordinate(30.0, 10.0));

        // MultiPoint example
        MultiPoint multiPoint = geometryFactory.createMultiPointFromCoords(new Coordinate[]{
                new Coordinate(10.0, 40.0),
                new Coordinate(40.0, 30.0),
                new Coordinate(20.0, 20.0),
                new Coordinate(30.0, 10.0)
        });

        // LineString example
        LineString lineString = geometryFactory.createLineString(new Coordinate[]{
                new Coordinate(30.0, 10.0),
                new Coordinate(10.0, 30.0),
                new Coordinate(40.0, 40.0)
        });

        // MultiLineString example
        MultiLineString multiLineString = geometryFactory.createMultiLineString(new LineString[]{
                geometryFactory.createLineString(new Coordinate[]{
                        new Coordinate(10.0, 10.0),
                        new Coordinate(20.0, 20.0),
                        new Coordinate(10.0, 40.0)
                }),
                geometryFactory.createLineString(new Coordinate[]{
                        new Coordinate(40.0, 40.0),
                        new Coordinate(30.0, 30.0),
                        new Coordinate(40.0, 20.0),
                        new Coordinate(30.0, 10.0)
                })
        });

        // Polygon example
        Polygon polygon = geometryFactory.createPolygon(new Coordinate[]{
                new Coordinate(30.0, 10.0),
                new Coordinate(40.0, 40.0),
                new Coordinate(20.0, 40.0),
                new Coordinate(10.0, 20.0),
                new Coordinate(30.0, 10.0)
        });

        // MultiPolygon example
        MultiPolygon multiPolygon = geometryFactory.createMultiPolygon(new Polygon[]{
                geometryFactory.createPolygon(new Coordinate[]{
                        new Coordinate(30.0, 10.0),
                        new Coordinate(40.0, 40.0),
                        new Coordinate(20.0, 40.0),
                        new Coordinate(10.0, 20.0),
                        new Coordinate(30.0, 10.0)
                }),
                geometryFactory.createPolygon(new Coordinate[]{
                        new Coordinate(15.0, 5.0),
                        new Coordinate(25.0, 25.0),
                        new Coordinate(10.0, 20.0),
                        new Coordinate(5.0, 10.0),
                        new Coordinate(15.0, 5.0)
                })
        });

        // GeometryCollection example
        GeometryCollection geometryCollection = geometryFactory.createGeometryCollection(new Geometry[]{
                point, multiPoint, lineString, polygon
        });

        // Create SpatialLab instance
        SpatialLab spatialLab = new SpatialLab();
        spatialLab.setPoint(point);
        spatialLab.setMultiPoint(multiPoint);
        spatialLab.setLineString(lineString);
        spatialLab.setMultiLineString(multiLineString);
        spatialLab.setPolygon(polygon);
        spatialLab.setMultiPolygon(multiPolygon);
        spatialLab.setGeometryCollection(geometryCollection);

        // Create SpatialLab instance
        SpatialLab spatialLab1 = new SpatialLab();
        spatialLab.setPoint(point);
        spatialLab.setMultiPoint(multiPoint);
        spatialLab.setLineString(lineString);
        spatialLab.setMultiLineString(multiLineString);
        spatialLab.setPolygon(polygon);
        spatialLab.setMultiPolygon(multiPolygon);
        spatialLab.setGeometryCollection(geometryCollection);

        // Save to repository
        spatialLabRepository.save(spatialLab);
        spatialLabRepository.save(spatialLab1);


}
}