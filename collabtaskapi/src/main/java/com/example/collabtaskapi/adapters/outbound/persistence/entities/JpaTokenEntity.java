package com.example.collabtaskapi.adapters.outbound.persistence.entities;

import com.example.collabtaskapi.domain.enums.TokenType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.EnumType;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@Table(name = "token")
public class JpaTokenEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    @Column(length = 512, nullable = false, unique = true)
    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;
    private boolean revoked;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private JpaAccountEntity account;

}
