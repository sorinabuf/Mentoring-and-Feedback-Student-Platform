package com.poli.meets.feedback.repository;

import com.poli.meets.feedback.domain.UniversityClass;

import com.poli.meets.feedback.domain.UniversityYear;
import com.poli.meets.feedback.domain.enumeration.Year;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@SuppressWarnings("unused")
@Repository
public interface UniversityClassRepository extends JpaRepository<UniversityClass, Long> {

    @Query("select uc from UniversityClass uc, Feedback f " +
            "where f.universityClass.id = uc.id and f.student.id = :studentId and f.category.id = :categoryId")
    List<UniversityClass> findAllSubmittedSubjectsAndCategory(Long studentId, Long categoryId);

    List<UniversityClass> findAllByUniversityYear_YearIn(List<Year> years);

    List<UniversityClass> findAllByUniversityYearId(Long yearId);

    List<UniversityClass> findAllByUniversityYearYearAndUniversityYearFacultyId(Year year, Long facultyId);

    long countAllByUniversityYear(UniversityYear universityYear);

    Optional<UniversityClass> findByExternalId(Long id);
}
