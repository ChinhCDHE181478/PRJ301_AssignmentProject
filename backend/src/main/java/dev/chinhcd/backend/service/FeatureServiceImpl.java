package dev.chinhcd.backend.service;

import dev.chinhcd.backend.dtos.FeatureDTO;
import dev.chinhcd.backend.models.Feature;
import dev.chinhcd.backend.repository.FeatureRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FeatureServiceImpl implements FeatureService {
    private final FeatureRepository featureRepository;

    @Override
    public Feature addNewFeature(FeatureDTO featureDTO) {
        var feature = Feature.builder()
                .featureName(featureDTO.featureName())
                .build();
        return featureRepository.save(feature);
    }

    @Override
    public List<Feature> getAllFeatures() {
        return featureRepository.findAll();
    }

    @Override
    public Feature updateFeature(Integer id, FeatureDTO featureDTO) {
        var feature = featureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feature not found"));
        feature.setFeatureName(featureDTO.featureName());
        return featureRepository.save(feature);
    }

    @Override
    public void deleteFeature(Integer id) {
        var feature = featureRepository.findById(id).orElseThrow(() -> new RuntimeException("Feature not found"));
        featureRepository.delete(feature);
    }
}
