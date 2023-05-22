package com.poli.meets.mentorship.repository;

import com.poli.meets.mentorship.domain.UniversityYear;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the UniversityYear entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UniversityYearRepository extends JpaRepository<UniversityYear, Long>, JpaSpecificationExecutor<UniversityYear> {
}
