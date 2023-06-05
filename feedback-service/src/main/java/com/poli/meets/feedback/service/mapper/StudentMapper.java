package com.poli.meets.feedback.service.mapper;


import com.poli.meets.feedback.domain.Student;
import com.poli.meets.feedback.service.dto.StudentDTO;
import com.poli.meets.feedback.domain.*;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Student} and its DTO {@link StudentDTO}.
 */
@Mapper(componentModel = "spring", uses = {UniversityYearMapper.class})
public interface StudentMapper extends EntityMapper<StudentDTO, Student> {

    @Mapping(source = "universityYear.id", target = "universityYearId")
    StudentDTO toDto(Student student);

    @Mapping(target = "feedbacks", ignore = true)
    @Mapping(target = "removeFeedbacks", ignore = true)
    @Mapping(source = "universityYearId", target = "universityYear")
    Student toEntity(StudentDTO studentDTO);

    @Mapping(target = "externalId", source = "id")
    Student toEntity(poli.meets.coreservice.service.dto.StudentDTO studentDTO);

    default Student fromId(Long id) {
        if (id == null) {
            return null;
        }
        Student student = new Student();
        student.setId(id);
        return student;
    }
}
