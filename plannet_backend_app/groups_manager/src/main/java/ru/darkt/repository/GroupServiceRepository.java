package ru.darkt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.darkt.models.group_service.GroupServiceModel;
import ru.darkt.models.group_service.GroupServiceKey;


@Repository
public interface GroupServiceRepository extends JpaRepository<GroupServiceModel, GroupServiceKey> {
}
