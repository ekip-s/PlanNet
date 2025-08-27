package ru.darkt.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.darkt.models.UserInfo;
import ru.darkt.models.UserInfoRequest;
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
    public boolean existsUserInfo() {
        log.info("GET: PowerPlanController addNewTarget");
        return powerPlanService.existsUserInfo();
    }

    @Operation(
            summary = "Получение данных пользователя",
            description = "Возвращает данные пользователя"
    )
    @GetMapping("/info")
    public UserInfo getUserInfo() {
        log.info("GET: PowerPlanController getUserInfo");
        return powerPlanService.getUserInfo();
    }

    @Operation(
            summary = "Получение баланса токена",
            description = "Возвращает баланс полностью или баланс токена, если он ограничен"
    )
    @GetMapping("/balance")
    public Double getBalance() {
        log.info("GET: PowerPlanController getBalance");
        return powerPlanService.getBalance();
    }

    @Operation(
            summary = "Запись информации",
            description = "Запись и обновление информации о пользователе"
    )
    @PostMapping
    public void addNewUserInfo(@RequestBody UserInfoRequest userInfoRequest) {
        log.info("GET: PowerPlanController addNewUserInfo, параметры: {}", userInfoRequest);
        powerPlanService.addNewUserInfo(userInfoRequest);
    }
}