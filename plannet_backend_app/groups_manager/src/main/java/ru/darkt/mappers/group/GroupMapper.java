package ru.darkt.mappers.group;

import org.mapstruct.Mapper;
import ru.darkt.models.group.Group;
import ru.darkt.models.group.GroupLightResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    GroupLightResponse toLightResponse(Group group);
    List<GroupLightResponse> toLightResponseList(List<Group> group);
}
