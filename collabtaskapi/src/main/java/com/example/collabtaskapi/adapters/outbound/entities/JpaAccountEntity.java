package com.example.collabtaskapi.adapters.outbound.entities;

import com.example.collabtaskapi.domain.Account;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "account")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class JpaAccountEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String profilePhotoUrl;
    private boolean isActive;

    public JpaAccountEntity(Account account) {
        this.id = account.getId();
        this.name = account.getName();
        this.email = account.getEmail();
        this.password = account.getPassword();
        this.profilePhotoUrl = account.getProfilePhotoUrl();
        this.isActive = account.getIsActive();
    }
}