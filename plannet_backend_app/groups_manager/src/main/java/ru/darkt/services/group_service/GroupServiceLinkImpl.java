package ru.darkt.services.group_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.darkt.ConflictException;
import ru.darkt.NotFoundException;
import ru.darkt.mappers.service.ServiceMapper;
import ru.darkt.models.group_service.GroupServiceKey;
import ru.darkt.models.group_service.GroupServiceModel;
import ru.darkt.models.service.ServiceResponse;
import ru.darkt.repository.GroupServiceRepository;
import ru.darkt.repository.ServiceRepository;
import ru.darkt.services.TokenService;
import ru.darkt.services.group_user.GroupUserService;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class GroupServiceLinkImpl implements GroupServiceLink {

    private final GroupUserService groupUserService;
    private final GroupServiceRepository groupServiceRepository;
    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;
    private final TokenService tokenService;

    @Autowired
    public GroupServiceLinkImpl(GroupUserService groupUserService, GroupServiceRepository groupServiceRepository, ServiceRepository serviceRepository, ServiceMapper serviceMapper, TokenService tokenService) {
        this.groupUserService = groupUserService;
        this.groupServiceRepository = groupServiceRepository;
        this.serviceRepository = serviceRepository;
        this.serviceMapper = serviceMapper;
        this.tokenService = tokenService;
    }

    @Override
    public List<ServiceResponse> getGroupServiceList(UUID groupId) {
        groupUserService.getGroupMembers(groupId);
        return serviceMapper.toResponseList(groupServiceRepository.findServicesByGroupId(groupId));
    }

    @Override
    @Transactional
    public void addServiceToGroup(UUID groupId, String service) {
        if (groupUserService.getOwner(groupId).getUserId().equals(tokenService.getCurrentUserId())) {
            groupServiceRepository.save(new GroupServiceModel(groupId, getServiceByName(service).getId()));
        } else {
            throw new ConflictException("Менять связь с сервисами может только owner", "Конфликт");
        }
    }

    @Override
    @Transactional
    public void deleteServiceToGroup(UUID groupId, String service) {
        if (groupUserService.getOwner(groupId).getUserId().equals(tokenService.getCurrentUserId())) {
            groupServiceRepository.deleteById(new GroupServiceKey(groupId, getServiceByName(service).getId()));
        } else {
            throw new ConflictException("Менять связь с сервисами может только owner", "Конфликт");
        }
    }

    private ru.darkt.models.service.Service getServiceByName(String name) {
        return serviceRepository
                .findByName(name)
                .orElseThrow(() -> new NotFoundException("Нет сервиса с переданным name", "Нет данных"));
    }
}
