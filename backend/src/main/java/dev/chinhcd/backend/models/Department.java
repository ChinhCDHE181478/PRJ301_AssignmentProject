package dev.chinhcd.backend.models;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Data
@Table(name = "Departments")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Department_ID", nullable = false)
    private Integer departmentId;

    @Column(name = "Department_Name")
    private String departmentName;

    @Column(name = "Department_Type")
    private String departmentType;

}
