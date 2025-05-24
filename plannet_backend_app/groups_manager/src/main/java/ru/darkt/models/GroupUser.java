package ru.darkt.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "group_user", indexes = @Index(name = "idx_group_user_user_id", columnList = "user_id"))
public class GroupUser {

    @EmbeddedId
    private GroupUserKey id;

    @Column(nullable = false, length = 50)
    private String userLogin;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private short role;

    @MapsId("groupId")
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
}