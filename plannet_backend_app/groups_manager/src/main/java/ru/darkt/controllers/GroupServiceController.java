package ru.darkt.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.darkt.models.service.ServiceResponse;
import ru.darkt.services.group_service.GroupServiceLink;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/group_service/api/v1/{groupId}")
@Tag(name="group_service_controller", description = "Методы для управления сервисами группы")
public class GroupServiceController {

    private final GroupServiceLink groupServiceLink;

    @Operation(
            summary = "Возвращает сервисы группы",
            description = "Данные может получить любой участник группы"
    )
    @GetMapping
    public List<ServiceResponse> getGroupServiceList(@PathVariable UUID groupId) {
        log.info("GET: GroupServiceController getGroupServiceList, параметры: {}", groupId);
        return groupServiceLink.getGroupServiceList(groupId);
    }
    //убрать сервис из группы
    //добавить сервис в группу
}
