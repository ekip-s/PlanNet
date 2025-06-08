package ru.darkt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.darkt.models.group.Group;
import ru.darkt.models.group_user.GroupUser;
import ru.darkt.models.group_user.GroupUserKey;

import java.util.List;
import java.util.UUID;

@Repository
public interface GroupUserRepository extends JpaRepository<GroupUser, GroupUserKey> {

    List<GroupUser> findByGroup(Group group);

    @Query("SELECT COUNT(gu) > 0 " +
            "FROM GroupUser gu " +
            "WHERE gu.id.groupId = :groupId " +
            "AND gu.id.userId = :userId " +
            "AND gu.role = 0")
    boolean isOwner(@Param("groupId") UUID groupId,
                    @Param("userId") UUID userId);

    @Query("SELECT COUNT(gu) > 0 " +
            "FROM GroupUser gu " +
            "WHERE gu.id.groupId = :groupId " +
            "AND gu.id.userId = :userId")
    boolean isMember(@Param("groupId") UUID groupId, @Param("userId") UUID userId);

    List<GroupUser> findByGroupId(UUID groupId);
}
