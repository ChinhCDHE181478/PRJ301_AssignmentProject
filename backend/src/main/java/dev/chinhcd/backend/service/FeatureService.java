package dev.chinhcd.backend.service;

import dev.chinhcd.backend.dtos.FeatureDTO;
import dev.chinhcd.backend.models.Feature;

import java.util.List;

public interface FeatureService {
    Feature addNewFeature(FeatureDTO featureDTO);
    List<Feature> getAllFeatures();
    Feature updateFeature(Integer id, FeatureDTO featureDTO);
    void deleteFeature(Integer id);
}
