package ru.darkt.services.invitation;

import ru.darkt.models.invitation.InvitationResponse;

import java.util.List;
import java.util.UUID;

public interface InvitationService {

    void addNewInvitation(UUID groupId);
    List<InvitationResponse> getListInvitation(UUID groupId);
    UUID codeVerification(String code);
    void deleteInvitationById(UUID invId);
}
