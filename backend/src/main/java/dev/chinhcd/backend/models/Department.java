package dev.chinhcd.backend.models;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "Departments")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Department_ID")
    private Integer departmentId;

    @Column(name = "Department_Name")
    private String departmentName;

    @Column(name = "Department_Type")
    private String departmentType;

}
