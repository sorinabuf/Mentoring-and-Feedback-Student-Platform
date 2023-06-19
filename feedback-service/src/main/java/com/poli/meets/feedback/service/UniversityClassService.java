package com.poli.meets.feedback.service;

import com.poli.meets.feedback.domain.Category;
import com.poli.meets.feedback.domain.Student;
import com.poli.meets.feedback.domain.UniversityYear;
import com.poli.meets.feedback.domain.enumeration.Year;
import com.poli.meets.feedback.repository.CategoryRepository;
import com.poli.meets.feedback.repository.UniversityClassRepository;
import com.poli.meets.feedback.service.dto.CategorySubjectsDTO;
import com.poli.meets.feedback.service.dto.SubjectDTO;
import com.poli.meets.feedback.service.dto.UniversityClassDTO;
import com.poli.meets.feedback.service.mapper.CategoryMapper;
import com.poli.meets.feedback.service.mapper.UniversityClassMapper;
import com.poli.meets.feedback.domain.UniversityClass;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class UniversityClassService {

    private final UniversityClassRepository universityClassRepository;

    private final UniversityClassMapper universityClassMapper;

    private final StudentService studentService;

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    public List<CategorySubjectsDTO> findAllCategorySubjects(String token) {
        Student student = studentService.getCurrentUser(token);
        UniversityYear universityYearStudent = student.getUniversityYear();


        List<Category> categories = categoryRepository.findByOrderById();

        return categories.stream().map(category -> {
            CategorySubjectsDTO categorySubjectsDTO = categoryMapper.toCategorySubjectsDto(category);


            Set<SubjectDTO> submittedSubjects = universityClassRepository
                    .findAllSubmittedSubjectsAndCategory(student.getId(), category.getId()).stream()
                    .map(universityClassMapper::toSubjectDto)
                    .collect(Collectors.toSet());

            categorySubjectsDTO.setSubmittedSubjects(submittedSubjects);

            Set<SubjectDTO> activeSubjects = !student.getUniversityYear().getYear().equals(Year.YEAR_IV_DEGREE)
                    ? universityClassRepository.findAllByUniversityYearId(universityYearStudent.getId()).stream()
                    .map(universityClassMapper::toSubjectDto)
                    .sorted(Comparator.comparing(SubjectDTO::getSemester))
                    .collect(Collectors.toCollection(LinkedHashSet::new))
                    : universityClassRepository
                    .findAllByUniversityYearYearAndUniversityYearFacultyId(universityYearStudent.getYear(), universityYearStudent.getFaculty().getId()).stream()
                    .map(universityClassMapper::toSubjectDto)
                    .sorted(Comparator.comparing(SubjectDTO::getSemester))
                    .collect(Collectors.toCollection(LinkedHashSet::new));

            activeSubjects.removeAll(submittedSubjects);

            categorySubjectsDTO.setActiveSubjects(activeSubjects);
            return categorySubjectsDTO;
        }).collect(Collectors.toList());
    }

}
