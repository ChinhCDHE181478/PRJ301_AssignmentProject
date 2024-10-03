package dev.chinhcd.backend.controller;

import dev.chinhcd.backend.dtos.FeatureDTO;
import dev.chinhcd.backend.models.Feature;
import dev.chinhcd.backend.service.FeatureService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/feature")
public class FeatureController {
    private final FeatureService featureService;

    @PostMapping
    public ResponseEntity<Feature> createFeature(@RequestBody FeatureDTO featureDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(featureService.addNewFeature(featureDTO));
    }

    @GetMapping
    public ResponseEntity<List<Feature>> getAllFeatures() {
        return ResponseEntity.ok(featureService.getAllFeatures());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Feature> updateFeature(@PathVariable Integer id, @RequestBody FeatureDTO featureDTO) {
        return ResponseEntity.ok(featureService.updateFeature(id, featureDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeature(@PathVariable Integer id) {
        featureService.deleteFeature(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
