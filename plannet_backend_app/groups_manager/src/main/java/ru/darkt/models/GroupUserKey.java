package ru.darkt.models;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
class GroupUserKey implements Serializable {
    private UUID groupId;
    private UUID userId;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GroupUserKey that = (GroupUserKey) o;
        return Objects.equals(groupId, that.groupId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, userId);
    }
}