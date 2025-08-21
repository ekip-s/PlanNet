package ru.darkt.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.darkt.ConflictException;
import ru.darkt.NotFoundException;
import ru.darkt.mappers.TargetMapper;
import ru.darkt.models.*;
import ru.darkt.repository.TargetRepository;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class TargetServicesImpl implements TargetServices {

    private final TargetRepository targetRepository;
    private final TargetMapper targetMapper;
    private final TokenService tokenService;

    @Autowired
    public TargetServicesImpl(TargetRepository targetRepository, TargetMapper targetMapper, TokenService tokenService) {
        this.targetRepository = targetRepository;
        this.targetMapper = targetMapper;
        this.tokenService = tokenService;
    }

    @Override
    @Transactional
    public void addNewTarget(CreateTargetRequest createTargetRequest) {

        if(createTargetRequest.getId() == null) {
            createNewTarget(createTargetRequest);
        } else {
            updateTarget(createTargetRequest);
        }
    }

    @Override
    @Transactional
    public void setTargetStatus(UUID targetId, TargetStatus status) {
        Target target = getTargetById(targetId);
        StatusTransitionManager.validateTransition(target.getType(), target.getStatus(), status);
        target.setStatus(status);
        targetRepository.save(target);
    }

    @Override
    @Transactional
    public void deleteTargetById(UUID targetId) {
        if (targetRepository.existsByIdAndUserId(targetId, tokenService.getCurrentUserId())) {
            targetRepository.deleteById(targetId);
        } else {
            throw new NotFoundException("Такой записи нет", "Нет данных");
        }
    }

    @Override
    public List<TargetResponse> getAllTarget(TargetStatus status) {

        if(status == null) {
            return targetMapper
                    .toResponseList(targetRepository
                            .findAllByUserIdAndTypeOrderByStatusDescCreatedAtAsc(tokenService.getCurrentUserId(),
                    TargetType.target));
        } else {
            return targetMapper.toResponseList(targetRepository.findAllByUserIdAndTypeAndStatusOrderByCreatedAtAsc(tokenService.getCurrentUserId(),
                    TargetType.target, status));
        }
    }

    @Override
    public TargetDetailResponse getDetailTarget(UUID targetId) {
        Target target = getTargetById(targetId);
        List<Target> targets = getSubTargetByParent(target);
        TargetDetailResponse detailResponse = targetMapper.toDetailResponse(target);
        detailResponse.setSubtarget(targetMapper.toResponseList(targets));
        return detailResponse;
    }

    @Override
    public Page<TargetResponse> getActionList(UUID targetId, Pageable pageable) {
        return targetRepository
                        .findAllByUserIdAndParent(tokenService.getCurrentUserId(),
                                new Target(targetId), pageable).map(targetMapper::toResponse);
    }

    private void createNewTarget(CreateTargetRequest request) {

        if (!request.getType().equals(TargetType.target) &&
                request.getParentId() == null) {
            throw new ConflictException("Для объекта обязательно указание parentId", "Некорректный запрос");
        }

        Target target = targetMapper.toTarget(request);
        target.ititTarget(request.getParentId(), tokenService.getCurrentUserId());
        targetRepository.save(target);
    }

    private void updateTarget(CreateTargetRequest request) {
        Target target = getTargetById(request.getId());

        if (request.getTitle() != null) {
            target.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            target.setDescription(request.getDescription());
        }

        targetRepository.save(target);
    }

    private Target getTargetById(UUID id) {
        return targetRepository
                .findByIdAndUserId(id, tokenService.getCurrentUserId())
                .orElseThrow(() -> new NotFoundException("Записи с таким id нет", "Нет данных"));
    }

    private List<Target> getSubTargetByParent(Target target) {
        return targetRepository
                .findAllByUserIdAndTypeAndParentOrderByStatusAscCreatedAtAsc(tokenService.getCurrentUserId(),
                        TargetType.subtarget,
                        target);
    }
}
