package com.poli.meets.feedback.repository;

import com.poli.meets.feedback.domain.TeachingAssistant;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository for the TeachingAssistant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeachingAssistantRepository extends JpaRepository<TeachingAssistant, Long> {

    @Query("select ta from TeachingAssistant ta, TeachingAssistantUniversityClass tac " +
            "where ta.id = tac.teachingAssistant.id and tac.universityClass.id = :universityClassId")
    List<TeachingAssistant> findAllByUniversityClass(Long universityClassId);
}
