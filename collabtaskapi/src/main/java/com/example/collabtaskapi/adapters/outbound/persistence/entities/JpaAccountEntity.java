package com.example.collabtaskapi.adapters.outbound.persistence.entities;

import com.example.collabtaskapi.domain.Account;
import com.example.collabtaskapi.domain.enums.RoleType;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.EnumType;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account")
@EqualsAndHashCode(of = "id")

public class JpaAccountEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String profilePhotoUrl;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    private boolean isActive;

    @OneToMany(mappedBy = "account")
    private List<JpaTokenEntity> tokens;

    public JpaAccountEntity(Account account) {
        this.id = account.getId();
        this.name = account.getName();
        this.email = account.getEmail();
        this.password = account.getPassword();
        this.profilePhotoUrl = account.getProfilePhotoUrl();
        this.role = account.getRole();
        this.isActive = account.isActive();
    }
}