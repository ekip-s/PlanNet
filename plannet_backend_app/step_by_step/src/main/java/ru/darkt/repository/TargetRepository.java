package ru.darkt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.darkt.models.Target;

import java.util.UUID;

@Repository
public interface TargetRepository extends JpaRepository<Target, UUID>  {
}
