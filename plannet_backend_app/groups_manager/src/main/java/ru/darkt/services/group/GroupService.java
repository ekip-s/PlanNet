package ru.darkt.services.group;

import ru.darkt.models.group.CreateGroupRequest;

import java.util.UUID;

public interface GroupService {
    UUID addNewGroup(CreateGroupRequest createGroupRequest);
}
