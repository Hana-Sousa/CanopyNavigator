package com.embrapii.CanopyNavigator.entities;

import jakarta.persistence.*;
import org.locationtech.jts.geom.*;

import java.io.Serializable;

@Entity
@Table(name="spatial_lab")
public class SpatialLab implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "spatial_seq_gen")
    @SequenceGenerator(name = "spatial_seq_gen", sequenceName = "spatial_seq", allocationSize = 1)
    Long id;

    @Column(name = "point")
    Point point;
    @Column(name = "multipoint")
    MultiPoint multiPoint;
    @Column(name = "linestring")
    private LineString lineString;

    @Column(name = "multilinestring")
    private MultiLineString multiLineString;

    @Column(name = "polygon")
    private Polygon polygon;

    @Column(name = "multipolygon")
    private MultiPolygon multiPolygon;

    @Column(name = "geometrycollection")
    private GeometryCollection geometryCollection;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public MultiPoint getMultiPoint() {
        return multiPoint;
    }

    public void setMultiPoint(MultiPoint multiPoint) {
        this.multiPoint = multiPoint;
    }

    public LineString getLineString() {
        return lineString;
    }

    public void setLineString(LineString lineString) {
        this.lineString = lineString;
    }

    public MultiLineString getMultiLineString() {
        return multiLineString;
    }

    public void setMultiLineString(MultiLineString multiLineString) {
        this.multiLineString = multiLineString;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public MultiPolygon getMultiPolygon() {
        return multiPolygon;
    }

    public void setMultiPolygon(MultiPolygon multiPolygon) {
        this.multiPolygon = multiPolygon;
    }

    public GeometryCollection getGeometryCollection() {
        return geometryCollection;
    }

    public void setGeometryCollection(GeometryCollection geometryCollection) {
        this.geometryCollection = geometryCollection;
    }
}
