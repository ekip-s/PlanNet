package ru.darkt.services;

import ru.darkt.models.CreateTargetRequest;
import ru.darkt.models.TargetDetailResponse;
import ru.darkt.models.TargetResponse;
import ru.darkt.models.TargetStatus;

import java.util.List;
import java.util.UUID;

public interface TargetServices {

    void addNewTarget(CreateTargetRequest createTargetRequest);
    void setTargetStatus(UUID targetId, TargetStatus status);
    void deleteTargetById(UUID targetId);
    List<TargetResponse> getAllTarget(TargetStatus status);
    TargetDetailResponse getDetailTarget(UUID targetId);
}
