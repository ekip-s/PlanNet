package ru.darkt.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.darkt.services.PowerPlanService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/power/api/v1")
@Tag(name="power_controller", description = "Методы для управления планами тренировок и питания")
public class PowerPlanController {

    private final PowerPlanService powerPlanService;

    @Operation(
            summary = "Инициализирован ли пользователь",
            description = "Проверяет, есть ли информация по конфигурациям пользователя"
    )
    @GetMapping
    public boolean addNewTarget() {
        log.info("GET: PowerPlanController addNewTarget");
        return powerPlanService.addNewTarget();
    }
}
