package com.poli.meets.mentorship.repository;

import com.poli.meets.mentorship.domain.Skill;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository for the Skill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SkillRepository extends JpaRepository<Skill, Long>, JpaSpecificationExecutor<Skill> {
    @Query(value = "select s from Skill s, MentorSkill ms, Mentor m where " +
            "m.id = ms.mentor.id and ms.skill.id = s.id and m.id = :mentorId")
    List<Skill> findMentorSkills(Long mentorId);
}
