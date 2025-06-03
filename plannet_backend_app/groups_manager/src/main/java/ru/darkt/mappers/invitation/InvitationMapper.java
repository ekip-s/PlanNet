package ru.darkt.mappers.invitation;

import org.mapstruct.Mapper;
import ru.darkt.models.invitation.Invitation;
import ru.darkt.models.invitation.InvitationResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InvitationMapper {

    InvitationResponse toResponse(Invitation invitation);
    List<InvitationResponse> toResponseList(List<Invitation> invitations);
}
