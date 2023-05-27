package com.poli.meets.feedback.repository;

import com.poli.meets.feedback.domain.TeachingAssistantUniversityClass;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the TeachingAssistantUniversityClass entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeachingAssistantUniversityClassRepository extends JpaRepository<TeachingAssistantUniversityClass, Long> {
}
