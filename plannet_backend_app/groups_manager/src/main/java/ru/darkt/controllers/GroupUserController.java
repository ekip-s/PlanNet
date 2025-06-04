package ru.darkt.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.darkt.models.group_user.GroupUserResponse;
import ru.darkt.services.group_user.GroupUserService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/group_user/api/v1")
@Tag(name="group_user_controller", description = "Методы для управления пользователями группы")
public class GroupUserController {

    private final GroupUserService groupUserService;

    @Operation(
            summary = "Получить пользователей группы",
            description = "Только участники группы могут получать данные о группе"
    )
    @GetMapping("/{groupId}")
    public List<GroupUserResponse> getGroupMembers(@PathVariable UUID groupId) {
        log.info("GET: GroupUserController getGroupMembers, параметры: {}", groupId);
        return groupUserService.getGroupMembers(groupId);
    }

    @Operation(
            summary = "Проверка, является ли пользователь участником группы",
            description = ""
    )
    @GetMapping("/{groupId}/verifi")
    public void memberVerification(@PathVariable UUID groupId) {
        log.info("GET: GroupUserController memberVerification, параметры: {}", groupId);
        groupUserService.memberVerification(groupId);
    }

    @Operation(
            summary = "Вступить в группу",
            description = "Вступить можно по пригласительной ссылке"
    )
    @PostMapping("/{code}")
    public void joinGroup(@PathVariable String code) {
        log.info("POST: GroupUserController joinGroup, параметры: {}", code);
        groupUserService.joinGroup(code);
    }


    //выйти из группы (владелец не может выйти)
    //удалить пользователя из группы для владельца;
}