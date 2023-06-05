package com.poli.meets.feedback.service;

import com.poli.meets.feedback.domain.Category;
import com.poli.meets.feedback.domain.Student;
import com.poli.meets.feedback.domain.UniversityClass;
import com.poli.meets.feedback.domain.enumeration.Grade;
import com.poli.meets.feedback.repository.CategoryRepository;
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

    private final UniversityClassRepository universityClassRepository;

    private final UniversityClassMapper universityClassMapper;

    private final CategoryRepository categoryRepository;


    /**
     * Save a feedback.
     *
     * @param feedbackPostDTO the entity to save.
     * @return the persisted entity.
     */
    public FeedbackPostDTO save(String token, FeedbackPostDTO feedbackPostDTO) {
        log.debug("Request to save Feedback : {}", feedbackPostDTO);

        Student student = studentService.getCurrentUser(token);

        if (feedbackRepository.findAllByStudentIdAndUniversityClassIdAndCategoryId(
                student.getId(), feedbackPostDTO.getUniversityClassId(), feedbackPostDTO.getCategoryId())
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

        FeedbackCountDTO feedbackCountDTO = new FeedbackCountDTO();

        feedbackCountDTO.setCountActive(3);
        feedbackCountDTO.setCountSubmitted(5);

        return feedbackCountDTO;
    }

    public Double getOverallGradeForFeedbacks(List<Feedback> feedbacks) {
        List<Integer> grades = new ArrayList<>();

        grades.addAll(feedbacks.stream().filter(feedback -> !feedback.getCategory().getName().equals("Difficulty"))
                .map(Feedback::getGrade).map(Grade::getValue).collect(Collectors.toList()));
        grades.addAll(feedbacks.stream().filter(feedback -> feedback.getCategory().getName().equals("Difficulty"))
                .map(Feedback::getGrade).map(grade -> 6 - grade.getValue()).collect(Collectors.toList()));

        return MathUtil.getAverage(grades);
    }

    public Double getOverallGradeForUniversityClass(Long universityClassId) {
        return getOverallGradeForFeedbacks(feedbackRepository.findAllByUniversityClassId(universityClassId));
    }

    public List<FeedbackCategoryDTO> getFeedbackCategoriesForUniversityClass(List<Feedback> feedbacks) {

        List<FeedbackCategoryDTO> feedbackCategoryDTOS = new ArrayList<>();
        List<Category> categories = categoryRepository.findByOrderById();

        for (Category category : categories) {
            List<Feedback> feedbackForCategory = feedbacks.stream()
                    .filter(feedback -> feedback.getCategory().getId().equals(category.getId()))
                    .collect(Collectors.toList());

            FeedbackCategoryDTO feedbackCategoryDTO = new FeedbackCategoryDTO();
            feedbackCategoryDTO.setCategoryName(category.getName());
            feedbackCategoryDTO.setGradeCategory(MathUtil.getAverage(feedbackForCategory.stream()
                    .map(Feedback::getGrade)
                    .map(Grade::getValue)
                    .collect(Collectors.toList())));
            feedbackCategoryDTO.setCountFeedbacks(feedbackForCategory.size());

            feedbackCategoryDTO.setFeedbackComments(feedbackForCategory.stream()
                    .filter(f -> f.getFeedbackText() != null && !f.getFeedbackText().isEmpty())
                    .map(f -> {
                        FeedbackCommentDTO feedbackCommentDTO = new FeedbackCommentDTO();
                        feedbackCommentDTO.setComment(f.getFeedbackText());
                        feedbackCommentDTO.setFeedbackDate(f.getFeedbackDate().toLocalDate());
                        return feedbackCommentDTO;
                    }).collect(Collectors.toList()));
            List<RatingBreakdownDTO> ratingBreakdownDTOs = new ArrayList<>();
            Arrays.stream(Grade.values()).forEach(grade -> {
                RatingBreakdownDTO ratingBreakdownDTO = new RatingBreakdownDTO();
                ratingBreakdownDTO.setGrade(grade.getValue());
                ratingBreakdownDTO.setNumberGrades((int) feedbackForCategory.stream()
                        .filter(f -> f.getGrade().equals(grade))
                        .count());
                ratingBreakdownDTOs.add(ratingBreakdownDTO);
            });
            feedbackCategoryDTO.setRatingBreakdown(ratingBreakdownDTOs);

            feedbackCategoryDTOS.add(feedbackCategoryDTO);
        }

       return feedbackCategoryDTOS;
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
