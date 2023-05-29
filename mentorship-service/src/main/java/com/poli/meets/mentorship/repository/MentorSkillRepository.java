package com.poli.meets.mentorship.repository;

import com.poli.meets.mentorship.domain.MentorSkill;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository for the MentorSkill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MentorSkillRepository extends JpaRepository<MentorSkill, Long>, JpaSpecificationExecutor<MentorSkill> {
    List<MentorSkill> findByMentorId(Long mentorId);

    void deleteByMentorId(Long mentorId);
}
