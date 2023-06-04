package com.poli.meets.feedback.service;

import com.poli.meets.feedback.domain.Feedback;
import com.poli.meets.feedback.domain.Student;
import com.poli.meets.feedback.domain.enumeration.Grade;
import com.poli.meets.feedback.repository.FeedbackRepository;
import com.poli.meets.feedback.repository.UniversityClassRepository;
import com.poli.meets.feedback.repository.UniversityYearRepository;
import com.poli.meets.feedback.service.dto.FacultyDTO;
import com.poli.meets.feedback.service.dto.FeedbackFacultyDTO;
import com.poli.meets.feedback.service.dto.FeedbackUniversityClassDTO;
import com.poli.meets.feedback.service.dto.FeedbackUniversityYearDTO;
import com.poli.meets.feedback.service.mapper.FacultyMapper;
import com.poli.meets.feedback.domain.Faculty;
import com.poli.meets.feedback.repository.FacultyRepository;

import com.poli.meets.feedback.service.mapper.UniversityClassMapper;
import com.poli.meets.feedback.service.mapper.UniversityYearMapper;
import com.poli.meets.feedback.util.MathUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * Service Implementation for managing {@link Faculty}.
 */
@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class FacultyService {

    private final FacultyRepository facultyRepository;

    private final FacultyMapper facultyMapper;

    private final StudentService studentService;

    private final UniversityYearRepository universityYearRepository;

    private final UniversityClassRepository universityClassRepository;

    private final UniversityYearMapper universityYearMapper;

    private final UniversityClassMapper universityClassMapper;

    private final FeedbackService feedbackService;

    /**
     * Save a faculty.
     *
     * @param facultyDTO the entity to save.
     * @return the persisted entity.
     */
    public FacultyDTO save(FacultyDTO facultyDTO) {
        log.debug("Request to save Faculty : {}", facultyDTO);
        Faculty faculty = facultyMapper.toEntity(facultyDTO);
        faculty = facultyRepository.save(faculty);
        return facultyMapper.toDto(faculty);
    }

    /**
     * Get all the faculties.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FacultyDTO> findAll() {
        log.debug("Request to get all Faculties");
        return facultyRepository.findAll().stream()
            .map(facultyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one faculty by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FacultyDTO> findOne(Long id) {
        log.debug("Request to get Faculty : {}", id);
        return facultyRepository.findById(id)
            .map(facultyMapper::toDto);
    }

    /**
     * Delete the faculty by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Faculty : {}", id);
        facultyRepository.deleteById(id);
    }

    public FeedbackFacultyDTO getFeedbackFaculty(String token) {
        Student student = studentService.getCurrentUser(token);


        Faculty currentUserFaculty = student.getUniversityYear().getFaculty();

        FeedbackFacultyDTO feedbackFacultyDTO = facultyMapper.toFeedbackDto(currentUserFaculty);

        List<FeedbackUniversityYearDTO> feedbackUniversityYearDTOS =
                universityYearRepository.findAllByFaculty_Id(currentUserFaculty.getId())
                .stream().map(universityYearMapper::toFeedbackDto)
                .collect(Collectors.toList());

        feedbackFacultyDTO.setYears(feedbackUniversityYearDTOS);

        feedbackUniversityYearDTOS.forEach(feedbackUniversityYearDTO -> {
            feedbackUniversityYearDTO.setUniversityClasses(
                    universityClassRepository.findAllByUniversityYearId(feedbackUniversityYearDTO.getId())
                    .stream().map(universityClassMapper::toFeedbackDto).collect(Collectors.toList()));

            feedbackUniversityYearDTO.getUniversityClasses().forEach(feedbackUniversityClassDTO -> {
                feedbackUniversityClassDTO.setGradeOverall(
                        feedbackService.getOverallGradeForUniversityClass(feedbackUniversityClassDTO.getId()));
            });
        });

        return feedbackFacultyDTO;
    }
}
