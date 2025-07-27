package ru.darkt.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "target",
        indexes = {
                @Index(name = "idx_target_user_type", columnList = "user_id, type"),
                @Index(name = "idx_target_parent_user", columnList = "parent_id, user_id"),
                @Index(name = "idx_target_user", columnList = "user_id")
        }
)
public class Target {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private UUID id;
    @Column(name = "user_id", nullable = false)
    private UUID userId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "fk_parent"))
    private Target parent;
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TargetType type;
    @Column(name = "title", nullable = false, length = 255)
    private String title;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return "Target{" +
                "id=" + id +
                ", userId=" + userId +
                ", parent=" + parent +
                ", type=" + type +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Target target = (Target) o;
        return Objects.equals(id, target.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}