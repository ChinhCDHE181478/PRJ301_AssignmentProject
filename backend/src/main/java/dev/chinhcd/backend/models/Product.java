package dev.chinhcd.backend.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "Products")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Product_ID")
    private Integer productId;

    @Column(name = "Product_Name")
    private String productName;
}
