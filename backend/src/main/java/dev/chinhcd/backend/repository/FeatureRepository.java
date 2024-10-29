package dev.chinhcd.backend.repository;

import dev.chinhcd.backend.models.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Integer> {
    @Query("select f from Feature f join Role r where r.roleId=:roleId")
    Set<Feature> findByRoleId(int roleId);
}
