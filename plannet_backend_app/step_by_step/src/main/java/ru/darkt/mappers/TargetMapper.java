package ru.darkt.mappers;

import org.mapstruct.Mapper;
import ru.darkt.models.CreateTargetRequest;
import ru.darkt.models.Target;
import ru.darkt.models.TargetDetailResponse;
import ru.darkt.models.TargetResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TargetMapper {

    Target toTarget(CreateTargetRequest createTargetRequest);
    TargetResponse toResponse(Target target);
    List<TargetResponse> toResponseList(List<Target> targetList);
    TargetDetailResponse toDetailResponse(Target target);
}
