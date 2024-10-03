package dev.chinhcd.backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Data
@Table(name = "Shift")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Shift_ID")
    private Integer shiftId;

    @Column(name = "Shift_Name")
    private String shiftName;

    @Column(name = "Shift_Start")
    private Date shiftStart;

    @Column(name = "Shift_End")
    private Date shiftEnd;
}
