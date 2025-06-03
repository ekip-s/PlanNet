package ru.darkt.services.group_service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.darkt.models.service.ServiceResponse;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class GroupServiceLinkImpl implements GroupServiceLink {

    @Override
    public List<ServiceResponse> getGroupServiceList(UUID groupId) {
        //проверяем, что обращается участник группы
        //получаем сервисы
        //приводим к ответу и возвращаем
        return List.of();
    }
}
