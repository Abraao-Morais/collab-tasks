package com.example.collabtaskapi.adapters.outbound.entities;

import com.example.collabtaskapi.domain.Task;
import com.example.collabtaskapi.domain.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "task")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
