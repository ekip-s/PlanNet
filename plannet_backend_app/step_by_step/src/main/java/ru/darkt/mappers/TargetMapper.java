package ru.darkt.mappers;

import org.mapstruct.Mapper;
import ru.darkt.models.CreateTargetRequest;
import ru.darkt.models.Target;

@Mapper(componentModel = "spring")
public interface TargetMapper {

    Target toTarget(CreateTargetRequest createTargetRequest);
}
