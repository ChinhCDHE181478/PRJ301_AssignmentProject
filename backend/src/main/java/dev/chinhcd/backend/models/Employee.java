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
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Employee_ID")
    private int employeeID;

    @Column(name = "Employee_Name")
    private String employeeName;

    @ManyToOne
    @JoinColumn(name = "Role_ID")
    private dev.chinhcd.backend.models.Role role;

    @ManyToOne
    @JoinColumn(name = "Department_ID")
    private Department department;

    @Column(name = "Salary_Level")
    private int Salary_Level;

}
