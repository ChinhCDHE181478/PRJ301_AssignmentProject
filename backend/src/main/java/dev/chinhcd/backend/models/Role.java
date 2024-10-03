package dev.chinhcd.backend.models;

import jakarta.persistence.*;
import jdk.jfr.Name;
import lombok.*;

import java.util.Set;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Role_iD")
    private int roleID;

    @Column(name = "Role_Name")
    private String roleName;

    @ManyToMany
    @JoinTable(
            name = "Role_Features",
            joinColumns = @JoinColumn(name = "Role_ID"),
            inverseJoinColumns = @JoinColumn(name = "Feature_ID")
    )
    private Set<Feature> features;
}
