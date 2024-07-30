package com.embrapii.CanopyNavigator.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.locationtech.jts.geom.Point;

import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "geospatial_features")
public class GeoSpatialFeature implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int population;

    private double latitude;

    private double longitude;

    @JsonIgnore
    @Transient
    private Point location;

    public GeoSpatialFeature(Long id, String name, int population, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.population = population;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public GeoSpatialFeature() {

    }

    public GeoSpatialFeature(String name, int population, double latitude, double longitude) {
        this.name = name;
        this.population = population;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeoSpatialFeature that = (GeoSpatialFeature) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
