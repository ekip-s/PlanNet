package ru.darkt.services.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.darkt.ForbiddenException;
import ru.darkt.mappers.group.GroupMapper;
import ru.darkt.models.group.CreateGroupRequest;
import ru.darkt.models.group.Group;
import ru.darkt.models.group.GroupLightResponse;
import ru.darkt.models.group_service.GroupServiceModel;
import ru.darkt.models.group_user.GroupRole;
import ru.darkt.models.group_user.GroupUserResponse;
import ru.darkt.repository.GroupRepository;
import ru.darkt.repository.GroupServiceRepository;
import ru.darkt.services.TokenService;
import ru.darkt.services.group_user.GroupUserService;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)

public class GroupServiceImpl implements GroupService {

    private final TokenService tokenService;
    private final GroupMapper groupMapper;
    private final GroupRepository groupRepository;
    private final GroupUserService groupUserService;
    private final GroupServiceRepository groupServiceRepository;

    @Autowired
    public GroupServiceImpl(TokenService tokenService, GroupMapper groupMapper,
                            GroupRepository groupRepository,
                            GroupUserService groupUserService,
                            GroupServiceRepository groupServiceRepository) {
        this.tokenService = tokenService;
        this.groupMapper = groupMapper;
        this.groupRepository = groupRepository;
        this.groupUserService = groupUserService;
        this.groupServiceRepository = groupServiceRepository;
    }

    @Override
    public List<GroupLightResponse> getGroupList(String serviceName) {
        return groupMapper
                .toLightResponseList(groupRepository
                        .findByUserIdAndServiceName(tokenService.getCurrentUserId(), serviceName));
    }

    @Override
    @Transactional
    public UUID addNewGroup(CreateGroupRequest createGroupRequest) {
        Group group = groupRepository.save(new Group(createGroupRequest.getName()));
        UUID groupId = group.getId();

        groupUserService.newGroupUser(groupId,
                tokenService.getCurrentUserId(),
                tokenService.getCurrentUserLogin(),
                GroupRole.OWNER);

        linkToService(groupId, createGroupRequest.getServices());

        return groupId;
    }

    @Override
    @Transactional
    public void deleteGroup(UUID groupId) {
        GroupUserResponse groupUser = groupUserService.getOwner(groupId);

        if(groupUser.getUserId() == tokenService.getCurrentUserId()) {
            groupRepository.deleteById(groupId);
        } else {
            throw new ForbiddenException("Удалить группу может только владелец", "Запрещено");
        }
    }

    @Transactional
    private void linkToService(UUID groupId, UUID[] services) {
        if (services.length == 0) return;
        List<GroupServiceModel> groupServiceList = new ArrayList<>();

        for(UUID uuid: services) {
            groupServiceList.add(new GroupServiceModel(groupId, uuid));
        }

        groupServiceRepository.saveAll(groupServiceList);
    }
}
