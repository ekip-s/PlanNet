package ru.darkt.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/task/api/v1")
@Tag(name="task_controller", description = "Методы для управления тасками")
public class TaskController {

    @Operation(
            summary = "Тест",
            description = "Тест"
    )
    @GetMapping({"", "/"})
    public String getAllTasks() {
      return "Успех";
    }
}
