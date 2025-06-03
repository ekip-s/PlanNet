package ru.darkt.services.group_user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.darkt.ForbiddenException;
import ru.darkt.mappers.group_user.GroupUserMapper;
import ru.darkt.models.group.Group;
import ru.darkt.models.group_user.GroupRole;
import ru.darkt.models.group_user.GroupUser;
import ru.darkt.models.group_user.GroupUserResponse;
import ru.darkt.repository.GroupUserRepository;
import ru.darkt.services.TokenService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class GroupUserServiceImpl implements GroupUserService {

    private final GroupUserRepository groupUserRepository;
    private final TokenService tokenService;
    private final GroupUserMapper groupUserMapper;

    @Autowired
    public GroupUserServiceImpl(GroupUserRepository groupUserRepository, TokenService tokenService, GroupUserMapper groupUserMapper) {
        this.groupUserRepository = groupUserRepository;
        this.tokenService = tokenService;
        this.groupUserMapper = groupUserMapper;
    }

    @Override
    public void newGroupUser(UUID groupId, UUID userId, String userLogin, GroupRole role) {
        groupUserRepository.save(new GroupUser(groupId, userId, userLogin, role));
    }

    @Override
    public void memberVerification(UUID groupId) {
        getGroupMembers(groupId);
    }

    @Override
    public List<GroupUserResponse> getGroupMembers(UUID groupId) {
        List<GroupUser> members = groupUserRepository.findByGroup(new Group(groupId));

        Optional<GroupUser> user = members
                .stream()
                .filter(e -> e.getId().getUserId() == tokenService.getCurrentUserId())
                .findFirst();

        if(user.isEmpty()) {
            throw new ForbiddenException("Получать записи могут только участники группы", "Запрещено");
        } else {
            return groupUserMapper.toResponseList(members);
        }
    }

    @Override
    public GroupUserResponse getOwner(UUID groupId) {
        return getGroupMembers(groupId)
                .stream()
                .filter(e -> e.getRole() == GroupRole.OWNER)
                .findFirst()
                .get();
    }
}
