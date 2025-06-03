package ru.darkt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.darkt.models.group.Group;
import ru.darkt.models.group_user.GroupUser;
import ru.darkt.models.group_user.GroupUserKey;

import java.util.List;

public interface GroupUserRepository extends JpaRepository<GroupUser, GroupUserKey> {

    List<GroupUser> findByGroup(Group group);
}
