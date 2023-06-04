package com.poli.meets.feedback.service;

import com.poli.meets.feedback.domain.Student;
import com.poli.meets.feedback.domain.UniversityClass;
import com.poli.meets.feedback.domain.enumeration.Grade;
import com.poli.meets.feedback.domain.enumeration.GradeDifficulty;
import com.poli.meets.feedback.repository.UniversityClassRepository;
import com.poli.meets.feedback.service.dto.*;
import com.poli.meets.feedback.service.mapper.FeedbackMapper;
import com.poli.meets.feedback.domain.Feedback;
import com.poli.meets.feedback.repository.FeedbackRepository;

import com.poli.meets.feedback.service.mapper.UniversityClassMapper;
import com.poli.meets.feedback.util.MathUtil;
import com.poli.meets.feedback.web.rest.errors.BadRequestException;
import com.poli.meets.feedback.web.rest.errors.ForbiddenException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * Service Implementation for managing {@link Feedback}.
 */
@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    private final FeedbackMapper feedbackMapper;

    private final StudentService studentService;

    private final UniversityClassService universityClassService;

    private final UniversityClassRepository universityClassRepository;

    private final UniversityClassMapper universityClassMapper;


    /**
     * Save a feedback.
     *
     * @param feedbackPostDTO the entity to save.
     * @return the persisted entity.
     */
    public FeedbackPostDTO save(String token, FeedbackPostDTO feedbackPostDTO) {
        log.debug("Request to save Feedback : {}", feedbackPostDTO);

        Student student = studentService.getCurrentUser(token);

        if (feedbackRepository.findAllByStudentIdAndUniversityClassId(student.getId(), feedbackPostDTO.getUniversityClassId())
                .stream().findAny().isPresent()) {
            throw new ForbiddenException();
        }

        Feedback feedback = feedbackMapper.toEntity(feedbackPostDTO);
        feedback.setStudent(student);
        feedback.setFeedbackDate(LocalDateTime.now());

        feedback = feedbackRepository.save(feedback);
        return feedbackMapper.toDto(feedback);
    }

    /**
     * Get all the feedbacks.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FeedbackPostDTO> findAll() {
        log.debug("Request to get all Feedbacks");
        return feedbackRepository.findAll().stream()
            .map(feedbackMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one feedback by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FeedbackPostDTO> findOne(Long id) {
        log.debug("Request to get Feedback : {}", id);
        return feedbackRepository.findById(id)
            .map(feedbackMapper::toDto);
    }

    /**
     * Delete the feedback by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Feedback : {}", id);
        feedbackRepository.deleteById(id);
    }

    public FeedbackCountDTO getFeedbackCount(String token) {
        FeedbackSubjectsDTO feedbackSubjectsDTO =
                universityClassService.findAllFeedbackSubjects(token);

        FeedbackCountDTO feedbackCountDTO = new FeedbackCountDTO();

        feedbackCountDTO.setCountActive(feedbackSubjectsDTO.getActiveSubjects().size());
        feedbackCountDTO.setCountSubmitted(feedbackSubjectsDTO.getSubmittedSubjects().size());

        return feedbackCountDTO;
    }

    public Double getOverallGradeForFeedbacks(List<Feedback> feedbacks) {
        List<Integer> grades = new ArrayList<>();
        grades.addAll(feedbacks.stream().map(Feedback::getGradeCourse).map(Grade::getValue).collect(Collectors.toList()));
        grades.addAll(feedbacks.stream().map(Feedback::getGradeExam).map(Grade::getValue).collect(Collectors.toList()));
        grades.addAll(feedbacks.stream().map(Feedback::getGradeHomework).map(Grade::getValue).collect(Collectors.toList()));
        grades.addAll(feedbacks.stream().map(Feedback::getGradeLaboratory).map(Grade::getValue).collect(Collectors.toList()));

        return MathUtil.getAverage(grades);
    }

    public Double getOverallGradeForUniversityClass(Long universityClassId) {
        return getOverallGradeForFeedbacks(feedbackRepository.findAllByUniversityClassId(universityClassId));
    }

    public List<FeedbackCategoryDTO> getFeedbackCategoriesForUniversityClass(List<Feedback> feedbacks) {

        FeedbackCategoryDTO feedbackDifficultyDTO = new FeedbackCategoryDTO();
        feedbackDifficultyDTO.setCategoryName("Difficulty");
        feedbackDifficultyDTO.setGradeCategory(MathUtil.getAverage(feedbacks.stream()
                .map(Feedback::getGradeDifficulty)
                .map(GradeDifficulty::getValue)
                .collect(Collectors.toList())));
        feedbackDifficultyDTO.setFeedbackComments(feedbacks.stream()
                .filter(f -> f.getFeedbackDifficulty() != null && !f.getFeedbackDifficulty().isEmpty())
                .map(f -> {
            FeedbackCommentDTO feedbackCommentDTO = new FeedbackCommentDTO();
            feedbackCommentDTO.setComment(f.getFeedbackDifficulty());
            feedbackCommentDTO.setFeedbackDate(f.getFeedbackDate().toLocalDate());
            return feedbackCommentDTO;
        }).collect(Collectors.toList()));
        List<RatingBreakdownDTO> ratingBreakdownDTOs = new ArrayList<>();
        List<RatingBreakdownDTO> finalRatingBreakdownDTOs = ratingBreakdownDTOs;
        Arrays.stream(GradeDifficulty.values()).forEach(gradeDifficulty -> {
            RatingBreakdownDTO ratingBreakdownDTO = new RatingBreakdownDTO();
            ratingBreakdownDTO.setGrade(gradeDifficulty.getValue());
            ratingBreakdownDTO.setNumberGrades((int) feedbacks.stream()
                    .filter(f -> f.getGradeDifficulty().equals(gradeDifficulty))
                    .count());
            finalRatingBreakdownDTOs.add(ratingBreakdownDTO);
        });
        feedbackDifficultyDTO.setRatingBreakdown(finalRatingBreakdownDTOs);

        FeedbackCategoryDTO feedbackCourseDTO = new FeedbackCategoryDTO();
        feedbackCourseDTO.setCategoryName("Course");
        feedbackCourseDTO.setGradeCategory(MathUtil.getAverage(feedbacks.stream()
                .map(Feedback::getGradeCourse)
                .map(Grade::getValue)
                .collect(Collectors.toList())));
        feedbackCourseDTO.setFeedbackComments(feedbacks.stream()
                .filter(f -> f.getFeedbackCourse() != null && !f.getFeedbackCourse().isEmpty())
                .map(f -> {
                    FeedbackCommentDTO feedbackCommentDTO = new FeedbackCommentDTO();
                    feedbackCommentDTO.setComment(f.getFeedbackCourse());
                    feedbackCommentDTO.setFeedbackDate(f.getFeedbackDate().toLocalDate());
                    return feedbackCommentDTO;
                }).collect(Collectors.toList()));
        ratingBreakdownDTOs = new ArrayList<>();
        List<RatingBreakdownDTO> finalRatingBreakdownDTOs1 = ratingBreakdownDTOs;
        Arrays.stream(Grade.values()).forEach(grade -> {
            RatingBreakdownDTO ratingBreakdownDTO = new RatingBreakdownDTO();
            ratingBreakdownDTO.setGrade(grade.getValue());
            ratingBreakdownDTO.setNumberGrades((int) feedbacks.stream()
                    .filter(f -> f.getGradeCourse().equals(grade))
                    .count());
            finalRatingBreakdownDTOs1.add(ratingBreakdownDTO);
        });
        feedbackCourseDTO.setRatingBreakdown(finalRatingBreakdownDTOs1);


        FeedbackCategoryDTO feedbackLaboratoryDTO = new FeedbackCategoryDTO();
        feedbackLaboratoryDTO.setCategoryName("Labs/Seminary");
        feedbackLaboratoryDTO.setGradeCategory(MathUtil.getAverage(feedbacks.stream()
                .map(Feedback::getGradeLaboratory)
                .map(Grade::getValue)
                .collect(Collectors.toList())));
        feedbackLaboratoryDTO.setFeedbackComments(feedbacks.stream()
                .filter(f -> f.getFeedbackLaboratory() != null && !f.getFeedbackLaboratory().isEmpty())
                .map(f -> {
                    FeedbackCommentDTO feedbackCommentDTO = new FeedbackCommentDTO();
                    feedbackCommentDTO.setComment(f.getFeedbackLaboratory());
                    feedbackCommentDTO.setFeedbackDate(f.getFeedbackDate().toLocalDate());
                    return feedbackCommentDTO;
                }).collect(Collectors.toList()));
        ratingBreakdownDTOs = new ArrayList<>();
        List<RatingBreakdownDTO> finalRatingBreakdownDTOs2 = ratingBreakdownDTOs;
        Arrays.stream(Grade.values()).forEach(grade -> {
            RatingBreakdownDTO ratingBreakdownDTO = new RatingBreakdownDTO();
            ratingBreakdownDTO.setGrade(grade.getValue());
            ratingBreakdownDTO.setNumberGrades((int) feedbacks.stream()
                    .filter(f -> f.getGradeLaboratory().equals(grade))
                    .count());
            finalRatingBreakdownDTOs2.add(ratingBreakdownDTO);
        });
        feedbackLaboratoryDTO.setRatingBreakdown(finalRatingBreakdownDTOs2);


        FeedbackCategoryDTO feedbackExamDTO = new FeedbackCategoryDTO();
        feedbackExamDTO.setCategoryName("Exam");
        feedbackExamDTO.setGradeCategory(MathUtil.getAverage(feedbacks.stream()
                .map(Feedback::getGradeExam)
                .map(Grade::getValue)
                .collect(Collectors.toList())));
        feedbackExamDTO.setFeedbackComments(feedbacks.stream()
                .filter(f -> f.getFeedbackExam() != null && !f.getFeedbackExam().isEmpty())
                .map(f -> {
                    FeedbackCommentDTO feedbackCommentDTO = new FeedbackCommentDTO();
                    feedbackCommentDTO.setComment(f.getFeedbackExam());
                    feedbackCommentDTO.setFeedbackDate(f.getFeedbackDate().toLocalDate());
                    return feedbackCommentDTO;
                }).collect(Collectors.toList()));
        ratingBreakdownDTOs = new ArrayList<>();
        List<RatingBreakdownDTO> finalRatingBreakdownDTOs3 = ratingBreakdownDTOs;
        Arrays.stream(Grade.values()).forEach(grade -> {
            RatingBreakdownDTO ratingBreakdownDTO = new RatingBreakdownDTO();
            ratingBreakdownDTO.setGrade(grade.getValue());
            ratingBreakdownDTO.setNumberGrades((int) feedbacks.stream()
                    .filter(f -> f.getGradeExam().equals(grade))
                    .count());
            finalRatingBreakdownDTOs3.add(ratingBreakdownDTO);
        });
        feedbackExamDTO.setRatingBreakdown(finalRatingBreakdownDTOs3);

        FeedbackCategoryDTO feedbackDuringSemesterDTO = new FeedbackCategoryDTO();
        feedbackDuringSemesterDTO.setCategoryName("During Semester");
        feedbackDuringSemesterDTO.setGradeCategory(MathUtil.getAverage(feedbacks.stream()
                .map(Feedback::getGradeHomework)
                .map(Grade::getValue)
                .collect(Collectors.toList())));
        feedbackDuringSemesterDTO.setFeedbackComments(feedbacks.stream()
                .filter(f -> f.getFeedbackDuringSemester() != null && !f.getFeedbackDuringSemester().isEmpty())
                .map(f -> {
                    FeedbackCommentDTO feedbackCommentDTO = new FeedbackCommentDTO();
                    feedbackCommentDTO.setComment(f.getFeedbackDuringSemester());
                    feedbackCommentDTO.setFeedbackDate(f.getFeedbackDate().toLocalDate());
                    return feedbackCommentDTO;
                }).collect(Collectors.toList()));
        ratingBreakdownDTOs = new ArrayList<>();
        List<RatingBreakdownDTO> finalRatingBreakdownDTOs4 = ratingBreakdownDTOs;
        Arrays.stream(Grade.values()).forEach(grade -> {
            RatingBreakdownDTO ratingBreakdownDTO = new RatingBreakdownDTO();
            ratingBreakdownDTO.setGrade(grade.getValue());
            ratingBreakdownDTO.setNumberGrades((int) feedbacks.stream()
                    .filter(f -> f.getGradeHomework().equals(grade))
                    .count());
            finalRatingBreakdownDTOs4.add(ratingBreakdownDTO);
        });
        feedbackDuringSemesterDTO.setRatingBreakdown(finalRatingBreakdownDTOs4);

        return List.of(feedbackDifficultyDTO, feedbackCourseDTO, feedbackLaboratoryDTO,
                feedbackExamDTO, feedbackDuringSemesterDTO);
    }

    public FeedbackSubjectDetailsDTO getUniversityClassFeedbackDetails(Long universityClassId) {
        UniversityClass universityClass = universityClassRepository.findById(universityClassId)
                .orElseThrow(BadRequestException::new);

        List<Feedback> feedbacks = feedbackRepository.findAllByUniversityClassId(universityClassId);

        FeedbackSubjectDetailsDTO subjectDetailsDTO = universityClassMapper.toDetailsDto(universityClass);

        subjectDetailsDTO.setFeedbackCategories(getFeedbackCategoriesForUniversityClass(feedbacks));
        subjectDetailsDTO.setGradeOverall(getOverallGradeForFeedbacks(feedbacks));
        subjectDetailsDTO.setCountFeedbacks(feedbacks.size());

        return subjectDetailsDTO;
    }
}
