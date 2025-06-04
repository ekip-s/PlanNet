package ru.darkt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.darkt.models.group_service.GroupServiceModel;
import ru.darkt.models.group_service.GroupServiceKey;
import ru.darkt.models.service.Service;

import java.util.List;
import java.util.UUID;


@Repository
public interface GroupServiceRepository extends JpaRepository<GroupServiceModel, GroupServiceKey> {

    @Query("SELECT s FROM Service s JOIN GroupServiceModel gs ON s.id = gs.id.serviceId WHERE gs.id.groupId = :groupId")
    List<Service> findServicesByGroupId(@Param("groupId") UUID groupId);
}
