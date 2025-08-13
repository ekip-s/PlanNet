package ru.darkt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.darkt.models.Target;
import ru.darkt.models.TargetStatus;
import ru.darkt.models.TargetType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TargetRepository extends JpaRepository<Target, UUID>  {

    Optional<Target> findByIdAndUserId(@Param("id") UUID id, @Param("userId") UUID userId);
    boolean existsByIdAndUserId(@Param("id") UUID id, @Param("userId") UUID userId);
    List<Target> findAllByUserIdAndTypeOrderByStatusDescCreatedAtAsc(@Param("userId") UUID userId,
                                                          @Param("type") TargetType type);
    List<Target> findAllByUserIdAndTypeAndStatusOrderByCreatedAtAsc(@Param("userId") UUID userId,
                                                        @Param("type") TargetType type,
                                                        @Param("status") TargetStatus status);
    List<Target> findAllByUserIdAndTypeAndParentOrderByStatusAscCreatedAtAsc(@Param("userId") UUID userId,
                                                                             @Param("type") TargetType type,
                                                                             @Param("parent") Target target);

}
