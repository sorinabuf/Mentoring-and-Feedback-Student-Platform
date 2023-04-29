package poli.meets.coreservice.repository;

import java.util.Optional;

import poli.meets.coreservice.domain.UniversityYear;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the UniversityYear entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UniversityYearRepository extends JpaRepository<UniversityYear, Long> {
}
