package com.poli.meets.mentorship.repository;

import com.poli.meets.mentorship.domain.UniversityClass;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the UniversityClass entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UniversityClassRepository extends JpaRepository<UniversityClass, Long>, JpaSpecificationExecutor<UniversityClass> {
}
