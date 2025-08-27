package ru.darkt.mappers;

import org.mapstruct.Mapper;
import ru.darkt.models.UserInfo;
import ru.darkt.models.UserInfoRequest;

@Mapper(componentModel = "spring")
public interface UserInfoMapper {

    UserInfo toUserIngo(UserInfoRequest userInfoRequest);
}
