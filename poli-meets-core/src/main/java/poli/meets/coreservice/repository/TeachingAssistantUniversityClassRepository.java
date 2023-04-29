package poli.meets.coreservice.repository;

import java.util.Optional;

import poli.meets.coreservice.domain.TeachingAssistantUniversityClass;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the TeachingAssistantUniversityClass entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeachingAssistantUniversityClassRepository extends JpaRepository<TeachingAssistantUniversityClass, Long> {
}
