package ru.darkt.models.group_service;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "group_services")
public class GroupServiceModel {

    @EmbeddedId
    private GroupServiceKey id;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public GroupServiceModel(UUID groupId, UUID serviceId) {
        this.id = new GroupServiceKey(groupId, serviceId);
        this.createdAt = LocalDateTime.now();
    }
}