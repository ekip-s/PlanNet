package ru.darkt.services.invitation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.darkt.ConflictException;
import ru.darkt.ForbiddenException;
import ru.darkt.NotFoundException;
import ru.darkt.mappers.invitation.InvitationMapper;
import ru.darkt.models.invitation.Invitation;
import ru.darkt.models.invitation.InvitationResponse;
import ru.darkt.repository.InvitationRepository;
import ru.darkt.services.TokenService;
import ru.darkt.services.group_user.GroupUserService;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;
    private final GroupUserService groupUserService;
    private final TokenService tokenService;
    private final InvitationMapper invitationMapper;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    @Value("${app.invitation.code.length}")
    private int CODE_LENGTH;
    @Value("${app.invitation.code.max-attempts}")
    private int MAX_ATTEMPTS;
    @Value("${app.invitation.ttl}")
    private int INVITATION_TTL;

    @Autowired
    public InvitationServiceImpl(InvitationRepository invitationRepository, GroupUserService groupUserService, TokenService tokenService, InvitationMapper invitationMapper) {
        this.invitationRepository = invitationRepository;
        this.groupUserService = groupUserService;
        this.tokenService = tokenService;
        this.invitationMapper = invitationMapper;
    }


    @Override
    @Transactional
    public void addNewInvitation(UUID groupId) {
        if (groupUserService.getOwner(groupId).getUserId().equals(tokenService.getCurrentUserId())) {
            invitationRepository.save(new Invitation(generateUniqueCode(), groupId));
        } else {
            throw new ForbiddenException("Приглашение в группу может создать только владелец группы", "Запрещено");
        }
    }

    @Override
    public List<InvitationResponse> getListInvitation(UUID groupId) {
        if (groupUserService.getOwner(groupId).getUserId().equals(tokenService.getCurrentUserId())) {
            return invitationMapper.toResponseList(invitationRepository.findByGroupIdAndRecent(groupId, INVITATION_TTL));
        } else {
            throw new ForbiddenException("Получать данные может только владелец группы", "Запрещено");
        }
    }

    @Override
    public UUID codeVerification(String code) {
        Optional<Invitation> invitation = invitationRepository.findByCodeAndRecent(code, INVITATION_TTL);
        if (invitation.isEmpty()) {
            throw new NotFoundException("Такого кода нет или его срок жизни истёк",
                    "Нет данных");
        } else {
            return invitation.get().getGroup().getId();
        }

    }

    @Override
    @Transactional
    public void deleteInvitationById(UUID invId) {
        if (groupUserService.getOwner(getGroupByInvitationId(invId)).getUserId().equals(tokenService.getCurrentUserId())) {
            invitationRepository.deleteById(invId);
        } else {
            throw new ForbiddenException("Удалять приглашение может только владелец группы",
                    "Запрещено");
        }
    }

    private UUID getGroupByInvitationId(UUID invId) {
        Invitation invitation = invitationRepository
                .findById(invId)
                .orElseThrow(() -> new NotFoundException("Нет записи с переданным id", "Нет данных"));
        return invitation.getGroup().getId();
    }

    private String generateUniqueCode() {
        int attempts = 0;
        while (attempts < MAX_ATTEMPTS) {
            String code = generateSecureCode();
            if (!invitationRepository.existsByCode(code)) {
                return code;
            }
            attempts++;
        }
        throw new ConflictException("Ошибка генарации кода, попробуйте еще раз",
                "Failed to generate unique invitation code");
    }

    private String generateSecureCode() {
        byte[] randomBytes = new byte[CODE_LENGTH];
        SECURE_RANDOM.nextBytes(randomBytes);

        String encoded = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);

        return encoded
                .replace("=", "")
                .replace("+", "-")
                .replace("/", "_")
                .substring(0, CODE_LENGTH);
    }
}
