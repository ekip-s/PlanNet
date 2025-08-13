package ru.darkt.services.group_invitation_manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.darkt.models.group_user.GroupRole;
import ru.darkt.services.TokenService;
import ru.darkt.services.group_permission.GroupPermission;
import ru.darkt.services.group_user.GroupUserService;
import ru.darkt.services.invitation.InvitationService;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class GroupInvitationManagerImpl implements GroupInvitationManager {
    private final InvitationService invitationService;
    private final GroupUserService groupUserService;
    private final TokenService tokenService;
    private final GroupPermission groupPermission;

    @Autowired
    public GroupInvitationManagerImpl(InvitationService invitationService,
                                      GroupUserService groupUserService,
                                      TokenService tokenService, GroupPermission groupPermission) {
        this.invitationService = invitationService;
        this.groupUserService = groupUserService;
        this.tokenService = tokenService;
        this.groupPermission = groupPermission;
    }

    @Transactional
    public void joinGroupByCode(String code) {
        UUID groupId = invitationService.codeVerification(code);
        groupPermission.validateNotOwner(groupId, tokenService.getCurrentUserId());
        groupUserService.newGroupUser(
                groupId,
                tokenService.getCurrentUserId(),
                tokenService.getCurrentUserLogin(),
                GroupRole.USER
        );
    }
}