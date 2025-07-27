package ru.darkt.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.darkt.ConflictException;
import ru.darkt.mappers.TargetMapper;
import ru.darkt.models.CreateTargetRequest;
import ru.darkt.models.Target;
import ru.darkt.models.TargetType;
import ru.darkt.repository.TargetRepository;

@Service
@Transactional(readOnly = true)
public class TargetServicesImpl implements TargetServices {

    private final TargetRepository targetRepository;
    private final TargetMapper targetMapper;

    @Autowired
    public TargetServicesImpl(TargetRepository targetRepository, TargetMapper targetMapper) {
        this.targetRepository = targetRepository;
        this.targetMapper = targetMapper;
    }

    @Override
    public void addNewTarget(CreateTargetRequest createTargetRequest) {

        if (!createTargetRequest.getType().equals(TargetType.target) &&
                createTargetRequest.getParentId() == null) {
            throw new ConflictException("Для объекта обязательно указание parentId", "Некорректный запрос");
        }

        System.out.println(createTargetRequest);

        Target target = targetMapper.toTarget(createTargetRequest);
        System.out.println(target);
    }
}
