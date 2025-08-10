package ru.darkt.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.darkt.models.CreateTargetRequest;
import ru.darkt.models.TargetDetailResponse;
import ru.darkt.models.TargetResponse;
import ru.darkt.models.TargetStatus;
import ru.darkt.services.TargetServices;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/target/api/v1")
@Tag(name="target_controller", description = "Методы для управления целями")
public class TargetController {

    private final TargetServices targetServices;

    @Operation(
            summary = "Создать цель",
            description = "Создает цель, под-цель или действие"
    )
    @PostMapping
    public void addNewTarget(@RequestBody CreateTargetRequest createTargetRequest) {
        log.info("POST: TargetController addNewTarget, параметры: {}", createTargetRequest);
        targetServices.addNewTarget(createTargetRequest);
    }

    @Operation(
            summary = "Обновить статус цели",
            description = "Меняет статус и валидирует, что переход статуса разрешен"
    )
    @PatchMapping("/{targetId}")
    public void setTargetStatus(@PathVariable UUID targetId, @RequestParam TargetStatus status) {
        log.info("PATCH: TargetController setTargetStatus, параметры: {}, {}", targetId, status);
        targetServices.setTargetStatus(targetId, status);
    }

    @Operation(
            summary = "Удалить цель",
            description = "Удаляет цель и ее дочерние элементы"
    )
    @DeleteMapping("/{targetId}")
    public void deleteTargetById(@PathVariable UUID targetId) {
        log.info("DELETE: TargetController deleteTargetById, параметры: {}", targetId);
        targetServices.deleteTargetById(targetId);
    }

    @Operation(
            summary = "Получить основные цели",
            description = "Возвращает в все основные цели"
    )
    @GetMapping("/all")
    public List<TargetResponse> getAllTarget(@RequestParam(required = false) TargetStatus status) {
        log.info("GET: TargetController getAllTarget, параметры: {}", status);
        return targetServices.getAllTarget(status);
    }

    @Operation(
            summary = "Получить детальные данные цели",
            description = "Возвращает детальные данные по записи"
    )
    @GetMapping("/{targetId}")
    public TargetDetailResponse getDetailTarget(@PathVariable UUID targetId) {
        log.info("GET: TargetController getDetailTarget, параметры: {}", targetId);
        return targetServices.getDetailTarget(targetId);
    }
    //при завершении/отмене цели -> завершаем/отменяем подзадачи, сделаем через шедулер;
}
