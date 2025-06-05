package ru.darkt.services.group_permission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.darkt.ConflictException;
import ru.darkt.ForbiddenException;
import ru.darkt.models.group_user.GroupRole;
import ru.darkt.repository.GroupUserRepository;
import ru.darkt.services.TokenService;

import java.util.UUID;

@Service
public class GroupPermissionImpl implements GroupPermission {

    private final GroupUserRepository groupUserRepository;
    private final TokenService tokenService;

    @Autowired
    public GroupPermissionImpl(GroupUserRepository groupUserRepository, TokenService tokenService) {
        this.groupUserRepository = groupUserRepository;
        this.tokenService = tokenService;
    }

    public boolean isGroupOwner(UUID groupId) {
        return groupUserRepository.isOwner(groupId, tokenService.getCurrentUserId(), GroupRole.OWNER);
    }

    public boolean isGroupMember(UUID groupId) {
        return groupUserRepository.isMember(groupId, tokenService.getCurrentUserId());
    }

    public void validateMembership(UUID groupId) {
        if (!isGroupMember(groupId)) {
            throw new ForbiddenException("Действие разрешено только участникам группы", "Запрещено");
        }
    }

    public void validateOwnership(UUID groupId) {
        if (!isGroupOwner(groupId)) {
            throw new ForbiddenException("Действие разрешено только владельцу группы", "Запрещено");
        }
    }

    public void validateNotOwner(UUID groupId) {
        if (isGroupOwner(groupId)) {
            throw new ConflictException("Владелец не может выполнить это действие", "Конфликт");
        }
    }
}