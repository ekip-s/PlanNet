package ru.darkt.services;

import ru.darkt.models.UserInfo;
import ru.darkt.models.UserInfoRequest;

public interface PowerPlanService {
    boolean existsUserInfo();
    void addNewUserInfo(UserInfoRequest userInfoRequest);
    UserInfo getUserInfo();
    Double getBalance();
}
