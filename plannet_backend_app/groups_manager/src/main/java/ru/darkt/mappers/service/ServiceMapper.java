package ru.darkt.mappers.service;

import org.mapstruct.Mapper;
import ru.darkt.models.service.Service;
import ru.darkt.models.service.ServiceResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ServiceMapper {

    ServiceResponse toResponse(Service service);
    List<ServiceResponse> toResponseList(List<Service> services);
}
