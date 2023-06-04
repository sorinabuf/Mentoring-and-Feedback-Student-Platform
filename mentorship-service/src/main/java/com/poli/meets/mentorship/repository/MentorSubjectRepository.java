package com.poli.meets.mentorship.repository;

import com.poli.meets.mentorship.domain.MentorSubject;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository for the MentorSubject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MentorSubjectRepository extends JpaRepository<MentorSubject, Long>, JpaSpecificationExecutor<MentorSubject> {
    List<MentorSubject> findByMentorId(Long mentorId);

    List<MentorSubject> findByMentorIdAndUniversityClass_Id(Long mentorId,
                                                            Long universityClassId);

    void deleteByMentorId(Long mentorId);

    void deleteByMentorIdAndUniversityClass_Id(Long mentorId,
                                               Long universityClassId);
}
