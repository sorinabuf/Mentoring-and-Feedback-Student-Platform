package com.poli.meets.feedback.repository;

import com.poli.meets.feedback.domain.UniversityYear;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository for the UniversityYear entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UniversityYearRepository extends JpaRepository<UniversityYear, Long> {

    List<UniversityYear> findAllByFaculty_Id(Long facultyId);
}
