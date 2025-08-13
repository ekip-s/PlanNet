package ru.darkt.services.group_permission;

import java.util.UUID;

public interface GroupPermission {

    boolean isGroupOwner(UUID groupId, UUID userId);
    boolean isGroupMember(UUID groupId);
    void validateMembership(UUID groupId);
    void validateOwnership(UUID groupId);
    void validateNotOwner(UUID groupId, UUID userId);
}