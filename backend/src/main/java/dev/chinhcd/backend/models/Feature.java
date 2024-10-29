package dev.chinhcd.backend.models;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "Path")
    private String path;

    @Column(name = "Method")
    private String method;

    @ManyToOne
    @JoinColumn(name = "Role_ID")
    private Role role;
}
