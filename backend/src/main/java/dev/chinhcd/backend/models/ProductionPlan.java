package dev.chinhcd.backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Data
@Table(name = "Production_Plans")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductionPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Plan_ID")
    private Integer planId;

    @Column(name = "Start_Date")
    private Date starDate;


}
