package ru.darkt.services.group_invitation_manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.darkt.models.group_user.GroupRole;
import ru.darkt.services.TokenService;
import ru.darkt.services.group_user.GroupUserService;
import ru.darkt.services.invitation.InvitationService;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class GroupInvitationManagerImpl implements GroupInvitationManager {
    private final InvitationService invitationService;
    private final GroupUserService groupUserService;
    private final TokenService tokenService;

    @Autowired
    public GroupInvitationManagerImpl(InvitationService invitationService, GroupUserService groupUserService, TokenService tokenService) {
        this.invitationService = invitationService;
        this.groupUserService = groupUserService;
        this.tokenService = tokenService;
    }

    @Transactional
    public void joinGroupByCode(String code) {
        UUID groupId = invitationService.codeVerification(code);
        groupUserService.newGroupUser(
                groupId,
                tokenService.getCurrentUserId(),
                tokenService.getCurrentUserLogin(),
                GroupRole.USER
        );
    }
}