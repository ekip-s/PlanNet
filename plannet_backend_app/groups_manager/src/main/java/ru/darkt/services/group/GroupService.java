package ru.darkt.services.group;

import ru.darkt.models.group.CreateGroupRequest;
import ru.darkt.models.group.GroupLightResponse;
import ru.darkt.models.group.GroupResponse;

import java.util.List;
import java.util.UUID;

public interface GroupService {
    List<GroupResponse> getAllGroupList();
    List<GroupLightResponse> getGroupList(String serviceName);
    UUID addNewGroup(CreateGroupRequest createGroupRequest);
    void deleteGroup(UUID groupId);
}
