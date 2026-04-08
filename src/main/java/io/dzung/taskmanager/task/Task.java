package io.dzung.taskmanager.task;

import java.time.LocalDate;
import java.time.LocalDateTime;

import io.dzung.taskmanager.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    // the value of enum type is number by default
    // enum TaskPriority {
    //    LOW,      -> 0
    //    MEDIUM,   -> 1
    //    HIGH      -> 2
    // }
    // we need this annotation for storing (ex: "LOW", "MEDIUM", "HIGH") 
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;
    
    // set fetching type to LAZE to prevent N + 1 problem with Hibernate 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate dueDate;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // JPA will call this function before repository.save()
    // so it doesn't need to be public
    @PrePersist
    private void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
