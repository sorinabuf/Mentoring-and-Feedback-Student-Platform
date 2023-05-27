package com.poli.meets.feedback.repository;

import com.poli.meets.feedback.domain.UniversityClass;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the UniversityClass entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UniversityClassRepository extends JpaRepository<UniversityClass, Long> {
}
