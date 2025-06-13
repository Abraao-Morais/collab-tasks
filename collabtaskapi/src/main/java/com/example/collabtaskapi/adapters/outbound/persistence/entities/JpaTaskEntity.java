package com.example.collabtaskapi.adapters.outbound.persistence.entities;

import com.example.collabtaskapi.domain.Task;
import com.example.collabtaskapi.domain.enums.Status;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.EnumType;
import jakarta.persistence.FetchType;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "task")
@EqualsAndHashCode(of = "id")
public class JpaTaskEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    private String title;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private JpaAccountEntity account;

    public JpaTaskEntity(Task task, JpaAccountEntity accountEntity) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.status = task.getStatus();
        this.description = task.getDescription();
        this.account = accountEntity;
    }

}
