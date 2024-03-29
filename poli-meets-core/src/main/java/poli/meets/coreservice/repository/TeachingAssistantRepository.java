package poli.meets.coreservice.repository;

import java.util.Optional;

import poli.meets.coreservice.domain.TeachingAssistant;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the TeachingAssistant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeachingAssistantRepository extends JpaRepository<TeachingAssistant, Long> {
}
