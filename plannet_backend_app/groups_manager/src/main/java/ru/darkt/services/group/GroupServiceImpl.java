package ru.darkt.services.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.darkt.models.group.CreateGroupRequest;
import ru.darkt.models.group.Group;
import ru.darkt.models.group_user.GroupRole;
import ru.darkt.repository.GroupRepository;
import ru.darkt.services.TokenService;
import ru.darkt.services.group_user.GroupUserService;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class GroupServiceImpl implements GroupService {

    private final TokenService tokenService;
    private final GroupRepository groupRepository;
    private final GroupUserService groupUserService;

    @Autowired
    public GroupServiceImpl(TokenService tokenService, GroupRepository groupRepository, GroupUserService groupUserService) {
        this.tokenService = tokenService;
        this.groupRepository = groupRepository;
        this.groupUserService = groupUserService;
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

        //соединяем группу с сервисом;

        
        return groupId;
    }
}
