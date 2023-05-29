package com.poli.meets.mentorship.repository;

import com.poli.meets.mentorship.domain.UniversityClass;

import com.poli.meets.mentorship.domain.UniversityYear;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository for the UniversityClass entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UniversityClassRepository extends JpaRepository<UniversityClass, Long>, JpaSpecificationExecutor<UniversityClass> {
    List<UniversityClass> findAllByUniversityYearIn(List<UniversityYear> universityYears);

    @Query(value = "select uc from UniversityClass uc, MentorSubject ms, " +
            "Mentor m where m.id = ms.mentor.id and ms.universityClass.id = " +
            "uc.id and m.id = :mentorId")
    List<UniversityClass> findMentorSubjects(Long mentorId);
}
