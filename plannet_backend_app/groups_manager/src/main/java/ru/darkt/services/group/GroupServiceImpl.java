package ru.darkt.services.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.darkt.NotFoundException;
import ru.darkt.mappers.group.GroupMapper;
import ru.darkt.models.group.CreateGroupRequest;
import ru.darkt.models.group.Group;
import ru.darkt.models.group.GroupLightResponse;
import ru.darkt.models.group.GroupResponse;
import ru.darkt.models.group_service.GroupServiceModel;
import ru.darkt.models.group_user.GroupRole;
import ru.darkt.repository.GroupRepository;
import ru.darkt.repository.GroupServiceRepository;
import ru.darkt.services.TokenService;
import ru.darkt.services.group_user.GroupUserService;
import ru.darkt.services.group_permission.GroupPermission;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)

public class GroupServiceImpl implements GroupService {

    private final TokenService tokenService;
    private final GroupMapper groupMapper;
    private final GroupPermission groupPermissionService;
    private final GroupRepository groupRepository;
    private final GroupUserService groupUserService;
    private final GroupServiceRepository groupServiceRepository;

    @Autowired
    public GroupServiceImpl(TokenService tokenService,
                            GroupMapper groupMapper,
                            GroupPermission groupPermissionService,
                            GroupRepository groupRepository,
                            GroupUserService groupUserService,
                            GroupServiceRepository groupServiceRepository) {
        this.tokenService = tokenService;
        this.groupMapper = groupMapper;
        this.groupPermissionService = groupPermissionService;
        this.groupRepository = groupRepository;
        this.groupUserService = groupUserService;
        this.groupServiceRepository = groupServiceRepository;
    }

    @Override
    public List<GroupResponse> getAllGroupList() {
        return groupMapper
                .toResponseList(groupRepository
                        .findByUserId(tokenService.getCurrentUserId()));
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
        Group group;
        if (createGroupRequest.getId() == null) {
            group = createGroup(createGroupRequest);
        } else {
            group = updateGroup(createGroupRequest);
        }
        return group.getId();
    }

    @Override
    @Transactional
    public void deleteGroup(UUID groupId) {
        groupPermissionService.validateOwnership(groupId);
        groupRepository.deleteById(groupId);
    }

    @Transactional
    private Group createGroup(CreateGroupRequest request) {
        Group group = groupRepository.save(new Group(request.getName()));
        addGroupOtherData(group.getId(), request.getServices());
        return group;
    }

    @Transactional
    private Group updateGroup(CreateGroupRequest request) {
        groupPermissionService.validateOwnership(request.getId());
        Group group = getGroupById(request.getId());
        group.setName(request.getName());
        return groupRepository.save(group);
    }

    private Group getGroupById(UUID id) {
        return groupRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Такой группы нет", "Нет данных"));
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

    @Transactional
    private void addGroupOtherData(UUID groupId, UUID[] services) {
        groupUserService.newGroupUser(groupId,
                tokenService.getCurrentUserId(),
                tokenService.getCurrentUserLogin(),
                GroupRole.OWNER);

        if(services.length == 0) {
            linkToService(groupId, services);
        }
    }
}
