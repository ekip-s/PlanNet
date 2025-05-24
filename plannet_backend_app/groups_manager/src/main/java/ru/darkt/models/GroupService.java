package ru.darkt.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;


import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "group_services", indexes = {
        @Index(name = "idx_group_services_group", columnList = "group_id"),
        @Index(name = "idx_group_services_name", columnList = "name")
})
public class GroupService {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private UUID id;

    @Column(nullable = false)
    private short name;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}