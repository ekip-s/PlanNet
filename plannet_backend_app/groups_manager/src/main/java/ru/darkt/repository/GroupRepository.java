package ru.darkt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.darkt.models.group.Group;

import java.util.List;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {

    @Query("SELECT DISTINCT g FROM Group g " +
            "JOIN GroupUser gu ON g.id = gu.id.groupId " +
            "JOIN GroupServiceModel gs ON g.id = gs.id.groupId " +
            "JOIN Service s ON gs.id.serviceId = s.id " +
            "WHERE gu.id.userId = :userId AND s.name = :serviceName")
    List<Group> findByUserIdAndServiceName(
            @Param("userId") UUID userId,
            @Param("serviceName") String serviceName
    );
}