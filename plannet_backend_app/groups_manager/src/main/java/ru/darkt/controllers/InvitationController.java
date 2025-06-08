package ru.darkt.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.darkt.models.invitation.InvitationResponse;
import ru.darkt.services.invitation.InvitationService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/invitation/api/v1")
@Tag(name="invitation_controller", description = "Методы для управления приглашениями")
public class InvitationController {

    private final InvitationService invitationService;

    @Operation(
            summary = "Создаем новое приглашение",
            description = "Создает новое приглашение для группы"
    )
    @PostMapping("/{groupId}")
    public void addNewInvitation(@PathVariable UUID groupId) {
        log.info("POST: InvitationController addNewInvitation, параметры: {}", groupId);
        invitationService.addNewInvitation(groupId);
    }

    @Operation(
            summary = "Получить активные приглашения",
            description = "Получить приглашения может только owner"
    )
    @GetMapping("/{groupId}")
    public List<InvitationResponse> getListInvitation(@PathVariable UUID groupId) {
        log.info("GET: InvitationController getListInvitation, параметры: {}", groupId);
        return invitationService.getListInvitation(groupId);
    }

    @Operation(
            summary = "Удалить приглашение",
            description = "Удалить может только owner"
    )
    @DeleteMapping("/{invId}")
    public void deleteInvitationById(@PathVariable UUID invId) {
        log.info("GET: InvitationController deleteInvitationById, параметры: {}", invId);
        invitationService.deleteInvitationById(invId);
    }
}
