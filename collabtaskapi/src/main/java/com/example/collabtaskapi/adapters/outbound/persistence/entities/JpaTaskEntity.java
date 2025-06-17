package com.example.collabtaskapi.adapters.outbound.persistence.entities;

import com.example.collabtaskapi.domain.Task;
import com.example.collabtaskapi.domain.enums.Priority;
import com.example.collabtaskapi.domain.enums.Status;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

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

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column(name = "due_date")
    private LocalDate dueDate;

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
        this.priority = task.getPriority();
        this.dueDate = task.getDueDate();
    }

}
