package dev.chinhcd.backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "Tokens")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Token_ID")
    private int tokenId;

    @Column(name = "Token")
    private String token;

    @Column(name = "Token_Type")
    private String tokenType;

    @Column(name = "Expiration_Date")
    private LocalDateTime expirationDate;

    @Column(name = "Revoked")
    private Boolean revoked;

    @Column(name = "Expired")
    private Boolean expired;

    @ManyToOne
    @JoinColumn(name = "User_ID")
    private Account account;
}
