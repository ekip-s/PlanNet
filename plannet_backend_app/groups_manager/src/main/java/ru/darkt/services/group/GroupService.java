package ru.darkt.services.group;

import ru.darkt.models.group.CreateGroupRequest;
import ru.darkt.models.group.GroupLightResponse;

import java.util.List;
import java.util.UUID;

public interface GroupService {
    List<GroupLightResponse> getGroupList(String serviceName);
    UUID addNewGroup(CreateGroupRequest createGroupRequest);
    void deleteGroup(UUID groupId);
}
