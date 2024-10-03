package dev.chinhcd.backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Features")
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Feature_ID")
    private Integer featureId;

    @Column(name = "Feature_Name")
    private String featureName;

    @ManyToMany(mappedBy = "features")
    private Set<Role> roles;
}
