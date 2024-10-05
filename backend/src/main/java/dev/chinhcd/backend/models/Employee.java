package dev.chinhcd.backend.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "Employees")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Employee_ID")
    private Integer employeeId;

    @Column(name = "Employee_Name")
    private String employeeName;

    @ManyToOne
    @JoinColumn(name = "Department_ID")
    private Department department;

    @Column(name = "Salary_Level")
    private Integer salaryLevel;

}
