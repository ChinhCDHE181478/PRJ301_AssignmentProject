package dev.chinhcd.backend.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "Worker_Schedule")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkerSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Worker_Schedule_ID")
    private Integer workerScheduleId;

    @ManyToOne
    @JoinColumn(name = "Schedule_ID")
    private ScheduleCampaign schedule;

    @ManyToOne
    @JoinColumn(name = "Employee_ID")
    private Employee employee;

    @Column(name = "Quantity")
    private Integer quantity;
}
