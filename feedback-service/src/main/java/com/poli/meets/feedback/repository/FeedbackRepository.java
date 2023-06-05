package com.poli.meets.feedback.repository;

import com.poli.meets.feedback.domain.Feedback;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository for the Feedback entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    List<Feedback> findAllByUniversityClassId(Long universityClassId);

    List<Feedback> findAllByStudentIdAndUniversityClassIdAndCategoryId(Long studentId, Long universityClassId, Long categoryId);

    List<Feedback> findAllByStudentIdAndUniversityClassIdAndCategory_IdIn(Long studentId, Long universityClassId, List<Long> categoryIds);


}
