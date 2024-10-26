package dev.chinhcd.backend.models;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Table(name = "Attendent")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@Entity
public class Attendent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Attendent_ID", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "Worker_Schedule_ID")
    private WorkerSchedule workerSchedule;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "Alpha")
    private Double alpha;

}