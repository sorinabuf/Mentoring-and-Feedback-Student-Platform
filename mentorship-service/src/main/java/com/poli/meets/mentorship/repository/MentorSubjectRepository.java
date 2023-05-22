package com.poli.meets.mentorship.repository;

import com.poli.meets.mentorship.domain.MentorSubject;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the MentorSubject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MentorSubjectRepository extends JpaRepository<MentorSubject, Long>, JpaSpecificationExecutor<MentorSubject> {
}
