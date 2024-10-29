package dev.chinhcd.backend.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.chinhcd.backend.repository.FeatureRepository;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name = "Accounts")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account implements UserDetails {

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

    @ManyToOne
    @JoinColumn(name = "Role_ID")
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
                authorityList.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName().toUpperCase()));
        return authorityList;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
