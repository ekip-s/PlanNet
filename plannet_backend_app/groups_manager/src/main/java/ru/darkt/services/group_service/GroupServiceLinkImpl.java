package ru.darkt.services.group_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.darkt.NotFoundException;
import ru.darkt.mappers.service.ServiceMapper;
import ru.darkt.models.group_service.GroupServiceKey;
import ru.darkt.models.group_service.GroupServiceModel;
import ru.darkt.models.service.ServiceResponse;
import ru.darkt.repository.GroupServiceRepository;
import ru.darkt.repository.ServiceRepository;
import ru.darkt.services.group_permission.GroupPermission;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class GroupServiceLinkImpl implements GroupServiceLink {

    private final GroupPermission groupPermissionService;
    private final GroupServiceRepository groupServiceRepository;
    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;

    @Autowired
    public GroupServiceLinkImpl(GroupPermission groupPermissionService,
                                GroupServiceRepository groupServiceRepository,
                                ServiceRepository serviceRepository,
                                ServiceMapper serviceMapper) {
        this.groupPermissionService = groupPermissionService;
        this.groupServiceRepository = groupServiceRepository;
        this.serviceRepository = serviceRepository;
        this.serviceMapper = serviceMapper;
    }

    @Override
    public List<ServiceResponse> getAllServiceList() {
        return serviceMapper.toResponseList(serviceRepository.findAll());
    }

    @Override
    public List<ServiceResponse> getGroupServiceList(UUID groupId) {
        groupPermissionService.validateMembership(groupId);
        return serviceMapper.toResponseList(groupServiceRepository.findServicesByGroupId(groupId));
    }

    @Override
    @Transactional
    public void addServiceToGroup(UUID groupId, String service) {
        groupPermissionService.validateOwnership(groupId);
        groupServiceRepository.save(new GroupServiceModel(groupId, getServiceByName(service).getId()));
    }

    @Override
    @Transactional
    public void deleteServiceToGroup(UUID groupId, String service) {
        groupPermissionService.validateOwnership(groupId);
        groupServiceRepository.deleteById(new GroupServiceKey(groupId, getServiceByName(service).getId()));
    }

    private ru.darkt.models.service.Service getServiceByName(String name) {
        return serviceRepository
                .findByName(name)
                .orElseThrow(() -> new NotFoundException("Нет сервиса с переданным name", "Нет данных"));
    }
}
