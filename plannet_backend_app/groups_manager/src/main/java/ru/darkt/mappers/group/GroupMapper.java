package ru.darkt.mappers.group;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.darkt.models.group.Group;
import ru.darkt.models.group.GroupLightResponse;
import ru.darkt.models.group.GroupResponse;
import ru.darkt.models.group_user.GroupUser;
import ru.darkt.models.group_user.GroupUserResponse;
import ru.darkt.models.service.Service;
import ru.darkt.models.service.ServiceResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    GroupLightResponse toLightResponse(Group group);
    List<GroupLightResponse> toLightResponseList(List<Group> group);
    @Mapping(target = "groupUsers", source = "groupUsers")
    @Mapping(target = "services", source = "services")
    List<GroupResponse> toResponseList(List<Group> group);

    @Mapping(target = "userId", source = "id.userId")
    GroupUserResponse toGroupUserResponse(GroupUser groupUser);

    ServiceResponse toServiceResponse(Service service);
}
