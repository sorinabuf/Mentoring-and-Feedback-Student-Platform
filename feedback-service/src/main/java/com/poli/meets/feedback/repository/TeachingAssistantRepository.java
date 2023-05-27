package com.poli.meets.feedback.repository;

import com.poli.meets.feedback.domain.TeachingAssistant;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the TeachingAssistant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeachingAssistantRepository extends JpaRepository<TeachingAssistant, Long> {
}
