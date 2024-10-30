package dev.chinhcd.backend.service;

import dev.chinhcd.backend.models.Feature;
import dev.chinhcd.backend.repository.FeatureRepository;
import dev.chinhcd.backend.service.InterfaceService.FeatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeatureServiceImpl implements FeatureService {
    private final FeatureRepository featureRepository;


    @Transactional(readOnly = true)
    @Override
    public List<Feature> getAllFeatures() {
        return featureRepository.findAll();
    }

}
