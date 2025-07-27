package ru.darkt.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.darkt.models.CreateTargetRequest;
import ru.darkt.services.TargetServices;

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
}
