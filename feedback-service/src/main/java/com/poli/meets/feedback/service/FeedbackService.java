package com.poli.meets.feedback.service;

import com.poli.meets.feedback.domain.Student;
import com.poli.meets.feedback.domain.enumeration.Grade;
import com.poli.meets.feedback.service.dto.FeedbackCountDTO;
import com.poli.meets.feedback.service.dto.FeedbackPostDTO;
import com.poli.meets.feedback.service.dto.FeedbackSubjectsDTO;
import com.poli.meets.feedback.service.mapper.FeedbackMapper;
import com.poli.meets.feedback.domain.Feedback;
import com.poli.meets.feedback.repository.FeedbackRepository;

import com.poli.meets.feedback.util.MathUtil;
import com.poli.meets.feedback.web.rest.errors.ForbiddenException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
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

    public Double getOverallGradeForUniversityClass(Long universityClassId) {
        List<Feedback> feedbacks = feedbackRepository.findAllByUniversityClassId(universityClassId);

        List<Integer> grades = new ArrayList<>();
        grades.addAll(feedbacks.stream().map(Feedback::getGradeCourse).map(Grade::getValue).collect(Collectors.toList()));
        grades.addAll(feedbacks.stream().map(Feedback::getGradeExam).map(Grade::getValue).collect(Collectors.toList()));
        grades.addAll(feedbacks.stream().map(Feedback::getGradeHomework).map(Grade::getValue).collect(Collectors.toList()));
        grades.addAll(feedbacks.stream().map(Feedback::getGradeLaboratory).map(Grade::getValue).collect(Collectors.toList()));

        return MathUtil.getAverage(grades);
    }
}
