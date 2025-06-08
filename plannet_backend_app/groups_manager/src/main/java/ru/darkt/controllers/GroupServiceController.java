package ru.darkt.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.darkt.models.service.ServiceResponse;
import ru.darkt.services.group_service.GroupServiceLink;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/group_service/api/v1")
@Tag(name="group_service_controller", description = "Методы для управления сервисами группы")
public class GroupServiceController {

    private final GroupServiceLink groupServiceLink;

    @Operation(
            summary = "Возвращает сервисы группы",
            description = "Данные может получить любой участник группы"
    )
    @GetMapping
    public List<ServiceResponse> getAllServiceList() {
        log.info("GET: GroupServiceController getAllServiceList");
        return groupServiceLink.getAllServiceList();
    }

    @Operation(
            summary = "Возвращает сервисы группы",
            description = "Данные может получить любой участник группы"
    )
    @GetMapping("/{groupId}")
    public List<ServiceResponse> getGroupServiceList(@PathVariable UUID groupId) {
        log.info("GET: GroupServiceController getGroupServiceList, параметры: {}", groupId);
        return groupServiceLink.getGroupServiceList(groupId);
    }

    @Operation(
            summary = "Добавить сервис в группу",
            description = "Добавить может только owner"
    )
    @PostMapping("/{groupId}")
    public void addServiceToGroup(@PathVariable UUID groupId, @RequestParam String service) {
        log.info("POST: GroupServiceController addServiceToGroup, параметры: {} {}", groupId, service);
        groupServiceLink.addServiceToGroup(groupId, service);
    }

    @Operation(
            summary = "Удалить сервис из группы",
            description = "Удалить может только owner"
    )
    @DeleteMapping("/{groupId}")
    public void deleteServiceToGroup(@PathVariable UUID groupId, @RequestParam String service) {
        log.info("DELETE: GroupServiceController deleteServiceToGroup, параметры: {} {}", groupId, service);
        groupServiceLink.deleteServiceToGroup(groupId, service);
    }
}
