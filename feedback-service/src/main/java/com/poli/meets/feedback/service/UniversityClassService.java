package com.poli.meets.feedback.service;

import com.poli.meets.feedback.client.AuthClient;
import com.poli.meets.feedback.domain.Student;
import com.poli.meets.feedback.domain.enumeration.Year;
import com.poli.meets.feedback.repository.UniversityClassRepository;
import com.poli.meets.feedback.service.dto.FeedbackSubjectsDTO;
import com.poli.meets.feedback.service.dto.SubjectDTO;
import com.poli.meets.feedback.service.dto.UniversityClassDTO;
import com.poli.meets.feedback.service.mapper.UniversityClassMapper;
import com.poli.meets.feedback.domain.UniversityClass;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * Service Implementation for managing {@link UniversityClass}.
 */
@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class UniversityClassService {

    private final UniversityClassRepository universityClassRepository;

    private final UniversityClassMapper universityClassMapper;

    private final StudentService studentService;


    /**
     * Save a universityClass.
     *
     * @param universityClassDTO the entity to save.
     * @return the persisted entity.
     */
    public UniversityClassDTO save(UniversityClassDTO universityClassDTO) {
        log.debug("Request to save UniversityClass : {}", universityClassDTO);
        UniversityClass universityClass = universityClassMapper.toEntity(universityClassDTO);
        universityClass = universityClassRepository.save(universityClass);
        return universityClassMapper.toDto(universityClass);
    }

    /**
     * Get all the universityClasses.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UniversityClassDTO> findAll() {
        log.debug("Request to get all UniversityClasses");
        return universityClassRepository.findAll().stream()
            .map(universityClassMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one universityClass by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SubjectDTO> findOne(Long id) {
        log.debug("Request to get UniversityClass : {}", id);
        return universityClassRepository.findById(id)
            .map(universityClassMapper::toSubjectDto);
    }

    /**
     * Delete the universityClass by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UniversityClass : {}", id);
        universityClassRepository.deleteById(id);
    }


    public FeedbackSubjectsDTO findAllFeedbackSubjects(String token) {
        Student student = studentService.getCurrentUser(token);

        FeedbackSubjectsDTO feedbackSubjectsDTO = new FeedbackSubjectsDTO();

        Set<SubjectDTO> submittedSubjects = universityClassRepository
                .findAllSubmittedSubjects(student.getId()).stream()
                .map(universityClassMapper::toSubjectDto)
                .collect(Collectors.toSet());

        feedbackSubjectsDTO.setSubmittedSubjects(submittedSubjects);

        Set<SubjectDTO> activeSubjects = universityClassRepository
                .findAllByUniversityYearId(student.getUniversityYear().getId()).stream()
                .map(universityClassMapper::toSubjectDto)
                .sorted(Comparator.comparing(SubjectDTO::getSemester))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        activeSubjects.removeAll(submittedSubjects);

        feedbackSubjectsDTO.setActiveSubjects(activeSubjects);

        return feedbackSubjectsDTO;
    }
}
