package ru.darkt.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.darkt.models.group.CreateGroupRequest;
import ru.darkt.models.group.GroupLightResponse;
import ru.darkt.services.group.GroupService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/group/api/v1")
@Tag(name="group_controller", description = "Методы для управления группами")
public class GroupController {

    private final GroupService groupService;

    @Operation(
            summary = "Получить свои группы",
            description = "Возвращает группы пользователя"
    )
    @GetMapping("/service/{serviceName}")
    public List<GroupLightResponse> getGroupList(@PathVariable String serviceName) {
        log.info("GET: GroupController getGroupList, параметры: {}", serviceName);
        return groupService.getGroupList(serviceName);
    }

    @Operation(
            summary = "Создать группу",
            description = "Создает группу, делает создателя owner-ом"
    )
    @PostMapping
    public UUID addNewGroup(@RequestBody CreateGroupRequest createGroupRequest) {
        log.info("POST: GroupController addNewGroup, параметры: {}", createGroupRequest);
        return groupService.addNewGroup(createGroupRequest);
    }

    @Operation(
            summary = "Удалить группу",
            description = "Удалить группу может только owner, если пользователь не owner -> он может выйти из группы"
    )
    @DeleteMapping("/{groupId}")
    public void deleteGroup(@PathVariable UUID groupId) {
        log.info("DELETE: GroupController deleteGroup, параметры: {}", groupId);
        groupService.deleteGroup(groupId);
    }
}
