package ru.darkt.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.darkt.repository.UserInfoRepository;

@Service
public class PowerPlanServiceImpl implements PowerPlanService {

    private final UserInfoRepository userInfoRepository;
    private final TokenService tokenService;

    @Autowired
    public PowerPlanServiceImpl(UserInfoRepository userInfoRepository, TokenService tokenService) {
        this.userInfoRepository = userInfoRepository;
        this.tokenService = tokenService;
    }


    @Override
    public boolean addNewTarget() {
        return userInfoRepository.existsById(tokenService.getCurrentUserId());
    }
}
