package ru.darkt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.darkt.models.invitation.Invitation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, UUID> {

    boolean existsByCode(String code);
    @Query(value = """
        SELECT * FROM invitations\s
        WHERE code = :code\s
        AND created_at >= CURRENT_DATE - CAST(:days || ' day' AS INTERVAL)\s
        """, nativeQuery = true)
    Optional<Invitation> findByCodeAndRecent(@Param("code") String code,
                                             @Param("days") int days);
    @Query(value = """
        SELECT * FROM invitations\s
        WHERE group_id = :groupId\s
        AND created_at >= CURRENT_DATE - CAST(:days || ' day' AS INTERVAL)\s
        """, nativeQuery = true)
    List<Invitation> findByGroupIdAndRecent(@Param("groupId") UUID groupId,
                                            @Param("days") int days);
}
