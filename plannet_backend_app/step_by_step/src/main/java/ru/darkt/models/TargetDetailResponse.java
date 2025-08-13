package ru.darkt.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TargetDetailResponse {
    private UUID id;
    private TargetType type;
    private TargetStatus status;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private List<TargetResponse> subtarget;
}
