package ru.darkt.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.darkt.models.group.CreateGroupRequest;
import ru.darkt.services.group.GroupService;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/group/api/v1")
@Tag(name="group_controller", description = "Методы для управления группами")
public class GroupController {

    private final GroupService groupService;

    @Operation(
            summary = "Создать группу",
            description = "Создает группу, делает создателя owner-ом"
    )
    @PostMapping
    public UUID addNewGroup(@RequestBody CreateGroupRequest createGroupRequest) {
        log.info("POST: GroupController addNewGroup, параметры: {}", createGroupRequest);
        return groupService.addNewGroup(createGroupRequest);
    }

    //создай группу -> и того, кто написал назначим owner-ом и участником группы (указываем сервисы)
    //удалить группу и всех участников, удалять может только owner
    //получить свои группы лайт (с фильром по сервису)
    // получить тяжелую группу;
    //проверка, что пользователь, участник группы
    //пользователь может выйти из группы (проверить таски при выходе из группы и изменить их)

    //добавить сервис
    //удалить сервис

    //создание приглашения для группы
}
