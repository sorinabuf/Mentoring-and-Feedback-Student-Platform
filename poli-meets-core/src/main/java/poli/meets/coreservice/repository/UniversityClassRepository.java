package poli.meets.coreservice.repository;

import java.util.Optional;

import poli.meets.coreservice.domain.UniversityClass;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the UniversityClass entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UniversityClassRepository extends JpaRepository<UniversityClass, Long> {
}
