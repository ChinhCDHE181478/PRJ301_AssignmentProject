package dev.chinhcd.backend.models;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "Accounts")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_ID")
    private int userID;

    @Column(name = "Username")
    private String username;

    @Column(name = "Password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "Employee_ID")
    private Employee employee;

    @Column(name = "Status")
    private String status;

}
