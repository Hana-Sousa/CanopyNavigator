package com.embrapii.CanopyNavigator.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * Ensure to create repositories for modifying databases in Spring Boot applications.
 * Creating entities alone will not have any effect.
 */
@Repository
public interface SpatialLabRepository extends JpaRepository<SpatialLabRepository,Long > {
}
