package ru.darkt.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.darkt.NotFoundException;
import ru.darkt.clients.AiTunnelClient;
import ru.darkt.mappers.UserInfoMapper;
import ru.darkt.models.UserInfo;
import ru.darkt.models.UserInfoRequest;
import ru.darkt.models.balance.Balance;
import ru.darkt.repository.UserInfoRepository;

@Service
@Transactional(readOnly = true)
public class PowerPlanServiceImpl implements PowerPlanService {

    private final UserInfoRepository userInfoRepository;
    private final TokenService tokenService;
    private final UserInfoMapper userInfoMapper;
    private final AiTunnelClient aiTunnelClient;

    @Autowired
    public PowerPlanServiceImpl(UserInfoRepository userInfoRepository, TokenService tokenService, UserInfoMapper userInfoMapper, AiTunnelClient aiTunnelClient) {
        this.userInfoRepository = userInfoRepository;
        this.tokenService = tokenService;
        this.userInfoMapper = userInfoMapper;
        this.aiTunnelClient = aiTunnelClient;
    }


    @Override
    public boolean existsUserInfo() {
        return userInfoRepository.existsById(tokenService.getCurrentUserId());
    }

    @Override
    @Transactional
    public void addNewUserInfo(UserInfoRequest userInfoRequest) {
        UserInfo userInfo = userInfoMapper.toUserIngo(userInfoRequest);
        userInfo.setId(tokenService.getCurrentUserId());
        userInfoRepository.save(userInfo);
    }

    @Override
    public UserInfo getUserInfo() {
        return userInfoRepository
                .findById(tokenService
                        .getCurrentUserId())
                .orElseThrow(() -> new NotFoundException("У пользователя нет настроек", "Нет данных"));
    }

    @Override
    public Double getBalance() {
        UserInfo userInfo = getUserInfo();
        Balance balance = aiTunnelClient.getBalance(userInfo.getAiToken()).block();

        return balance.getBudget() != null ? balance.getBudget() : balance.getBalance();
    }
}
