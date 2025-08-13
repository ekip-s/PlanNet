package ru.darkt.services;

import ru.darkt.ConflictException;
import ru.darkt.models.TargetStatus;
import ru.darkt.models.TargetType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StatusTransitionManager {
    private static final Map<TargetType, Map<TargetStatus, Set<TargetStatus>>> allowedTransitions = new HashMap<>();

    static {
        // Инициализация для target и subtarget
        Map<TargetStatus, Set<TargetStatus>> typeTargetAndSubtargetTransitions = new HashMap<>();
        typeTargetAndSubtargetTransitions.put(TargetStatus.NEW, Set.of(TargetStatus.IN_PROGRESS, TargetStatus.CANCELED));
        typeTargetAndSubtargetTransitions.put(TargetStatus.IN_PROGRESS, Set.of(TargetStatus.COMPLETED, TargetStatus.CANCELED, TargetStatus.NEW));

        allowedTransitions.put(TargetType.target, typeTargetAndSubtargetTransitions);
        allowedTransitions.put(TargetType.subtarget, typeTargetAndSubtargetTransitions);
    }

    public static void validateTransition(TargetType taskType, TargetStatus currentStatus, TargetStatus nextStatus) {
        Map<TargetStatus, Set<TargetStatus>> transitions = allowedTransitions.get(taskType);
        if (transitions == null) {
            throw new ConflictException("Переход цели в указанный статус запрещен", "Конфликт");
        }

        Set<TargetStatus> allowedNext = transitions.get(currentStatus);
        if (allowedNext == null || !allowedNext.contains(nextStatus)) {
            throw new ConflictException("Переход цели в указанный статус запрещен", "Конфликт");
        }
    }
}