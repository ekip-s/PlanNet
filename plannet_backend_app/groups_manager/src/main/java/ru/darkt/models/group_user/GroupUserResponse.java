package ru.darkt.models.group_user;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupUserResponse {

    private UUID userId;
    @Column(nullable = false, length = 50)
    private String userLogin;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private GroupRole role;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GroupUserResponse that = (GroupUserResponse) o;
        return Objects.equals(userId, that.userId) && Objects.equals(userLogin, that.userLogin) && Objects.equals(createdAt, that.createdAt) && role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userLogin, createdAt, role);
    }
}
