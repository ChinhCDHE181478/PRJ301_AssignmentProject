package dev.chinhcd.backend.service;

import dev.chinhcd.backend.models.Feature;
import dev.chinhcd.backend.repository.FeatureRepository;
import dev.chinhcd.backend.service.InterfaceService.FeatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FeatureServiceImpl implements FeatureService {
    private final FeatureRepository featureRepository;


    @Transactional(readOnly = true)
    @Override
    public Set<Feature> getAllFeatures() {
        return new HashSet<>(featureRepository.findAll());
    }

}
