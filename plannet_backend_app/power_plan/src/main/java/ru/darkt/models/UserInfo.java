package ru.darkt.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_info")
public class UserInfo {

    @Id
    @Column(name = "user_id")
    private UUID id;
    @Column(name = "ai_token")
    private String aiToken;
    @Column(name = "model")
    @Enumerated(EnumType.STRING)
    private Model model;
    @Column(name = "current_weight")
    private Double currentWeight;
    @Column(name = "desired_weight")
    private Double desiredWeight;
    @Column(name = "automatic_update")
    private Boolean automaticUpdate;
    @Column(name = "train_plan_options")
    private String trainPlanOptions;
    @Column(name = "meal_plan_options")
    private String mealPlanOptions;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "update_at", nullable = false)
    private LocalDateTime update_at;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return Objects.equals(id, userInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
