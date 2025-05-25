package ru.darkt.services.group_user;

import ru.darkt.models.group_user.GroupRole;

import java.util.UUID;

public interface GroupUserService {
    void newGroupUser(UUID groupId, UUID userId, String userLogin, GroupRole role);
}
