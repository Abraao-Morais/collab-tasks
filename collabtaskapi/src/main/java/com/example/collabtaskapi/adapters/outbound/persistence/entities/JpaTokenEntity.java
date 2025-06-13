package com.example.collabtaskapi.adapters.outbound.persistence.entities;

import com.example.collabtaskapi.domain.enums.TokenType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "token")
@EqualsAndHashCode(of = "id")
public class JpaTokenEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;
    private boolean expired;
    private boolean revoked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private JpaAccountEntity account;
}
