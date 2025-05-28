package ru.darkt.services.group_user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.darkt.models.group_user.GroupRole;
import ru.darkt.models.group_user.GroupUser;
import ru.darkt.repository.GroupUserRepository;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class GroupUserServiceImpl implements GroupUserService {

    private final GroupUserRepository groupUserRepository;

    @Autowired
    public GroupUserServiceImpl(GroupUserRepository groupUserRepository) {
        this.groupUserRepository = groupUserRepository;
    }

    @Override
    public void newGroupUser(UUID groupId, UUID userId, String userLogin, GroupRole role) {
        groupUserRepository.save(new GroupUser(groupId, userId, userLogin, role));
    }
}
