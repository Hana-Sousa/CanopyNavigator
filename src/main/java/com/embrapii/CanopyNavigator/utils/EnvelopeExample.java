package com.embrapii.CanopyNavigator.utils;

import org.locationtech.jts.geom.*;

public class EnvelopeExample {
    public static void main(String[] args) {
        // Create an Envelope (bounding box)
        Envelope envelope = new Envelope(0, 10, 0, 10);
        System.out.println("Envelope: " + envelope);

        // Create a GeometryFactory
        GeometryFactory geometryFactory = new GeometryFactory();

        // Create a Point within the envelope
        Coordinate coord = new Coordinate(5, 5);
        Point point = geometryFactory.createPoint(coord);
        System.out.println("Point within envelope: " + envelope.contains(point.getCoordinate()));
    }
}
