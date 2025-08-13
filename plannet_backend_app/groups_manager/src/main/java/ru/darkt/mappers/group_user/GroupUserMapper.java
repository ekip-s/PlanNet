package ru.darkt.mappers.group_user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.darkt.models.group_user.GroupUser;
import ru.darkt.models.group_user.GroupUserKey;
import ru.darkt.models.group_user.GroupUserResponse;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface GroupUserMapper {

    @Mapping(source = "id", target = "userId", qualifiedByName = "extractUserId")
    GroupUserResponse toResponse(GroupUser groupUser);

    @Mapping(source = "id", target = "userId", qualifiedByName = "extractUserId")
    List<GroupUserResponse> toResponseList(List<GroupUser> groupUser);

    @Named("extractUserId")
    default UUID extractUserId(GroupUserKey key) {
        return key != null ? key.getUserId() : null;
    }
}
