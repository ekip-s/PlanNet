package ru.darkt.services.group_service;

import ru.darkt.models.service.ServiceResponse;

import java.util.List;
import java.util.UUID;

public interface GroupServiceLink {

    List<ServiceResponse> getGroupServiceList(UUID groupId);
}
