package ru.darkt.models.group_user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import ru.darkt.models.group.Group;

import java.time.LocalDateTime;
import java.util.UUID;

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

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private GroupRole role;

    @MapsId("groupId")
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    public GroupUser(UUID groupId, UUID userId, String userLogin, GroupRole role) {
        this.id = new GroupUserKey(groupId, userId);
        this.userLogin = userLogin;
        this.createdAt = LocalDateTime.now();
        this.role = role;
    }
}