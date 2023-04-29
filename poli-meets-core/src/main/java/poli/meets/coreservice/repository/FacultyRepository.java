package poli.meets.coreservice.repository;

import java.util.Optional;

import poli.meets.coreservice.domain.Faculty;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the Faculty entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {
}
