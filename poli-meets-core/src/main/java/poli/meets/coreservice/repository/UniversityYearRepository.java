package poli.meets.coreservice.repository;

import java.util.List;
import java.util.Optional;

import poli.meets.coreservice.domain.UniversityYear;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import poli.meets.coreservice.domain.enumeration.Year;

/**
 * Spring Data repository for the UniversityYear entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UniversityYearRepository extends JpaRepository<UniversityYear, Long> {

    List<UniversityYear> findAllByYearAndFacultyId(Year year, Long faculty_id);
}
