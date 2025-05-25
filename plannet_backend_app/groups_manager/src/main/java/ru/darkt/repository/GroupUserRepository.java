package ru.darkt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.darkt.models.group_user.GroupUser;
import ru.darkt.models.group_user.GroupUserKey;

public interface GroupUserRepository extends JpaRepository<GroupUser, GroupUserKey> {
}
