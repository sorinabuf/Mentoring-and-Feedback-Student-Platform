package com.poli.meets.mentorship.repository;

import com.poli.meets.mentorship.domain.Faculty;
import com.poli.meets.mentorship.domain.UniversityYear;

import com.poli.meets.mentorship.domain.enumeration.Year;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository for the UniversityYear entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UniversityYearRepository extends JpaRepository<UniversityYear, Long>, JpaSpecificationExecutor<UniversityYear> {
    List<UniversityYear> findAllByFacultyAndYearIn(Faculty faculty,
                                                   List<Year> years);
}
