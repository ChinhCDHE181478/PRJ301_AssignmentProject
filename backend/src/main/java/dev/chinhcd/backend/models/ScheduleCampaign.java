package dev.chinhcd.backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Schedule_Campaigns")
@Builder
public class ScheduleCampaign {
    @Id
    @Column(name = "Schedule_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer scheduleId;

    @ManyToOne
    @JoinColumn(name = "Campaign_ID")
    private PlanCampaign campaign;

    @Column(name = "Date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "Shift_ID")
    private Shift shift;

    @Column(name = "Quantity")
    private Integer quantity;
}
