package com.embrapii.CanopyNavigator.Entities;

import jakarta.persistence.*;
import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.geom.Point;


import java.io.Serializable;

@Entity
@Table(name = "spatial_lab")
public class SpatialLab implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE )
    @SequenceGenerator(name = "spatial_seq_gen", sequenceName = "spatial_seq", allocationSize = 1)
    Long id;

    @Column(name = "point")
    Point point;

    @Column(name = "multipoint")
    MultiPoint multiPoint;


}
