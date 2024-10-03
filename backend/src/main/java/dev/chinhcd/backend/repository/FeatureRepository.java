package dev.chinhcd.backend.repository;

import dev.chinhcd.backend.models.Feature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeatureRepository extends JpaRepository<Feature, Integer> {
}
