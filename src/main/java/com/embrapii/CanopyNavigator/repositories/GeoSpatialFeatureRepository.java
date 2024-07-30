package com.embrapii.CanopyNavigator.repositories;

import com.embrapii.CanopyNavigator.moldels.GeoSpatialFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeoSpatialFeatureRepository extends JpaRepository<GeoSpatialFeature, Long> {
}
