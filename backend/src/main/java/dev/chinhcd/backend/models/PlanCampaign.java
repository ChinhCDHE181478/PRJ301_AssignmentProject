package dev.chinhcd.backend.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "Production_Plan_Campaigns")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanCampaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Campaign_ID")
    private Integer campaignId;

    @ManyToOne
    @JoinColumn(name = "Plan_ID")
    private ProductionPlan plan;

    @ManyToOne
    @JoinColumn(name = "Product_ID")
    private Product product;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "Unit_Effort_Days")
    private Integer unitEffort_days;
}
