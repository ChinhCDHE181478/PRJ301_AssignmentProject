package dev.chinhcd.backend.models;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;

@Entity
@Data
@Table(name = "Accounts")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_ID")
    private Integer userId;

    @Column(name = "Username")
    private String username;

    @Column(name = "Password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "Employee_ID")
    private Employee employee;

    @Column(name = "Status")
    private String status;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "User_Role",
            joinColumns = @JoinColumn(name = "User_ID"),
            inverseJoinColumns = @JoinColumn(name = "Role_ID")
    )
    private Set<Role> roles;

}
