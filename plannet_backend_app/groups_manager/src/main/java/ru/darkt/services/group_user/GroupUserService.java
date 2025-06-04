package ru.darkt.services.group_user;

import ru.darkt.models.group_user.GroupRole;
import ru.darkt.models.group_user.GroupUserResponse;

import java.util.List;
import java.util.UUID;

public interface GroupUserService {
    void newGroupUser(UUID groupId, UUID userId, String userLogin, GroupRole role);
    void memberVerification(UUID groupId);
    List<GroupUserResponse> getGroupMembers(UUID groupId);
    GroupUserResponse getOwner(UUID groupId);
    void joinGroup(String code);
}
