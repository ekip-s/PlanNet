package ru.darkt.services.invitation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.darkt.ConflictException;
import ru.darkt.NotFoundException;
import ru.darkt.mappers.invitation.InvitationMapper;
import ru.darkt.models.invitation.Invitation;
import ru.darkt.models.invitation.InvitationResponse;
import ru.darkt.repository.InvitationRepository;
import ru.darkt.services.group_permission.GroupPermission;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;
    private final GroupPermission groupPermissionService;
    private final InvitationMapper invitationMapper;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    @Value("${app.invitation.code.length}")
    private int CODE_LENGTH;
    @Value("${app.invitation.code.max-attempts}")
    private int MAX_ATTEMPTS;
    @Value("${app.invitation.ttl}")
    private int INVITATION_TTL;

    @Autowired
    public InvitationServiceImpl(InvitationRepository invitationRepository,
                                 GroupPermission groupPermissionService,
                                 InvitationMapper invitationMapper) {
        this.invitationRepository = invitationRepository;
        this.groupPermissionService = groupPermissionService;
        this.invitationMapper = invitationMapper;
    }


    @Override
    @Transactional
    public void addNewInvitation(UUID groupId) {
        groupPermissionService.validateOwnership(groupId);

        invitationRepository.save(new Invitation(generateUniqueCode(), groupId));
    }

    @Override
    public List<InvitationResponse> getListInvitation(UUID groupId) {
        groupPermissionService.validateOwnership(groupId);
        return invitationMapper.toResponseList(invitationRepository.findByGroupIdAndRecent(groupId, INVITATION_TTL));
    }

    @Override
    public UUID codeVerification(String code) {
        Optional<Invitation> invitation = invitationRepository.findByCodeAndRecent(code, INVITATION_TTL);
        if (invitation.isEmpty()) {
            throw new NotFoundException("Такого кода нет или его срок жизни истёк",
                    "Нет данных");
        } else {
            invitationRepository.deleteById(invitation.get().getId());
            return invitation.get().getGroup().getId();
        }

    }

    @Override
    @Transactional
    public void deleteInvitationById(UUID invId) {
        UUID groupId = getGroupByInvitationId(invId);
        groupPermissionService.validateOwnership(groupId);
        invitationRepository.deleteById(invId);
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
